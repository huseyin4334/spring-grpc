# Output files
# ca.key: Certificate Authority private key file (this shouldn't be shared in real-life)
# ca.crt: Certificate Authority trust certificate (this should be shared with users in real-life)
# server.key: Server private key, password protected (this shouldn't be shared)
# server.csr: Server certificate signing request (this should be shared with the CA owner)
# server.crt: Server certificate signed by the CA (this would be sent back by the CA owner) - keep on server
# server.pem: Conversion of server.key into a format gRPC likes (this shouldn't be shared)

# Summary
# Private files: ca.key, server.key, server.pem, server.crt
# "Share" files: ca.crt (needed by the client), server.csr (needed by the CA)

passCode=dfgHfdghrR

# Step 1: Generate Certificate Authority + Trust Certificate (ca.crt)
openssl genrsa -passout pass:${passCode} -des3 -out ca.key 4096
openssl req -passin pass:${passCode} -new -x509 -days 365 -key ca.key -out ca.crt -subj "/CN=ca"

# Step 2: Copy this file to the client resources directory
cp ca.crt ./grpc-client/src/main/resources/certs/ca.crt

# Step 3: Copy this file to the server resources directory
cp ca.crt ./grpc-server/src/main/resources/certs/ca.crt
cp ca.key ./grpc-server/src/main/resources/certs/ca.key