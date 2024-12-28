# Create a resource group

resource "azurerm_resource_group" "mitocode" {
  location = "East US"
  name     = "rg-mitocode"
  tags = {
    environment = "Development"
    project = "Microservices"
  }
}

# create acr Container Registry
resource "azurerm_container_registry" "acrmitocode" {
  name                = "acrmitocode"
  resource_group_name = azurerm_resource_group.mitocode.name
  location            = azurerm_resource_group.mitocode.location
  sku                 = "Basic"
  admin_enabled       = true
}

# create AKS Cluster
resource "azurerm_kubernetes_cluster" "aks" {
  name                = "mitocode-aks"
  location            = azurerm_resource_group.mitocode.location
  resource_group_name = azurerm_resource_group.mitocode.name
  dns_prefix          = "mitocode-dns"

  default_node_pool {
    name       = "default"
    node_count = 1
    vm_size    = "Standard_A2_v2"
  }

  identity {
    type = "SystemAssigned"
  }

  tags = {
    Environment = "Production"
  }
}
# create role assignment for aks acr pull
resource "azurerm_role_assignment" "mitocode" {
  principal_id                     = azurerm_kubernetes_cluster.aks.kubelet_identity[0].object_id
  role_definition_name             = "AcrPull"
  scope                            = azurerm_container_registry.acrmitocode.id
  skip_service_principal_aad_check = true
}