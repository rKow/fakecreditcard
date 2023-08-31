#!/bin/sh

rm /vault/data/*.txt

## INIT VAULT
echo "Init vault..."
vault operator init > /vault/data/initresult.txt
VAULT_TOKEN=$(grep 'Initial Root Token:' /vault/data/initresult.txt | awk '{print substr($NF, 1, length($NF))}')

## UNSEAL VAULT
echo "Unseal..."
vault operator unseal $(grep 'Key 1:' /vault/data/initresult.txt | awk '{print $NF}')
vault operator unseal $(grep 'Key 2:' /vault/data/initresult.txt | awk '{print $NF}')
vault operator unseal $(grep 'Key 3:' /vault/data/initresult.txt | awk '{print $NF}')

## AUTH
echo "Login..."
vault login ${VAULT_TOKEN}

## CREATE USER
echo "Creating user"
vault auth enable userpass
vault policy write admin /vault/policies/admin-policy.hcl
vault write auth/userpass/users/admin password=admin policies=admin

# Enable approle auth
vault auth enable approle
# Create policy
vault policy write application /vault/policies/application-policy.hcl

# Create roles
vault write auth/approle/role/application policies=application
# Get roleId and secretId for role
vault read auth/approle/role/application/role-id > /vault/data/application-approle.txt
vault write -f auth/approle/role/application/secret-id >> /vault/data/application-approle.txt

## ENABLE secrets
vault secrets enable transit

# Enable transit
vault write -f transit/keys/applicationtransit exportable=true allow_plaintext_backup=true
vault read --field=backup /transit/backup/applicationtransit > /vault/data/applicationtransit.txt

unset VAULT_TOKEN
