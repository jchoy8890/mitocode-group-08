pipeline {
    
    agent any

    tools {
        maven "Maven3"
    }
    
    environment {
        registryName = "${env.SERVICE_NAME}"
        registryCredential = "acrmitocode"
        registryURL = "acrmitocode.azurecr.io"
        dockerImage = ""
    }

    stages {
        stage('Checkout code') {
            steps {
                checkout changelog: false, poll: false, scm: scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/jchoy8890/mitocode-jenkins-discovery-server.git']])
            }
        }
        stage('Build project'){
            steps{
                sh "mvn clean install"
            }   
        }
        stage('Build image'){
            steps{
                script{                    
                    // sh "docker build -t mitocode/discovery-server ."
                    dockerImage = docker.build registryName
                }
            }   
        }
        stage('Push Image'){
            steps{
                script{
                    docker.withRegistry("http://${registryURL}", registryCredential){
                        dockerImage.push()
                    }
                }
            }
        }
        
        stage("Deploy to K8S"){
            steps{
                withKubeConfig(caCertificate: '', clusterName: '', contextName: '', credentialsId: 'mitocode-aks', namespace: '', restrictKubeConfigAccess: false, serverUrl: '') {
                    sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'  
                    sh 'chmod u+x ./kubectl'  
                    sh "./kubectl apply -f k8s-deploy-from-acr.yml"
                }
            }
        }
        
    }
}
