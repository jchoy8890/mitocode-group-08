#!/bin/bash

MONGO_USER="jchoy"
MONGO_PASSWORD="jchoy"

# Prepare the user credentials for MongoDB
q_MONGO_USER=$(jq --arg v "$MONGO_USER" -n '$v')
q_MONGO_PASSWORD=$(jq --arg v "$MONGO_PASSWORD" -n '$v')

# Run MongoDB commands
mongosh -u "mitocode" -p "mitocode" admin <<EOF
    use mitocode;
    db.createUser({
        user: $q_MONGO_USER,
        pwd: $q_MONGO_PASSWORD,
        roles: ["readWrite"],
    });
    db.createCollection('cl_product')
    db.createCollection('cl_user')
EOF