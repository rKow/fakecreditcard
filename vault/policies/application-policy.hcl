path "auth/approle/login" {
  capabilities = [ "create", "read" ]
}

path "transit/encrypt/applicationtransit" {
   capabilities = [ "update" ]
}

path "transit/decrypt/applicationtransit" {
   capabilities = [ "update" ]
}

path "secret/application*"
{
  capabilities = ["read", "create"]
}

path "secret/api*"
{
  capabilities = ["read", "create"]
}
