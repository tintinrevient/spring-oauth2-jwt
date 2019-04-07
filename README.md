# spring-oauth2-jwt

1. To generate the asymmetric keypair to sign the JWT token, we use below command to produce a .jks file:

```
keytool -genkeypair -alias auth -keystore auth.jks -keyalg RSA -keypass mypass -storepass mypass
```

2. To export the public key from the .jks file, we use below command:

```
keytool -list -rfc --keystore auth.jks | openssl x509 -inform pem -pubkey
```

3. Copy and paste the public key to the resource server under the directory: src/main/resources/public.txt, and the resource server will use this public key to verify the JWT token provided by the client applications.
