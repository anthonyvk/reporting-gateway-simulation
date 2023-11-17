**Non secured API :** 
\
` curl -X GET "https://localhost:8443/api/hello" --insecure
`

**Secured API**
`curl -X GET "https://localhost:8443/secured/hello" -H "accept: */*" --cert client_public_certificate.crt --key client_private_key.key --cacert server_public_certificate.crt`

---------------

`curl -X GET "https://localhost:8080/api/hello" -H "accept: */*" --cert client_public_certificate.crt --key client_private_key.key --cacert server_public_certificate.crt  --insecure --verbose
`