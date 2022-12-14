package xyz.starestarrysky.library.util.encrypt

import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64
import javax.crypto.Cipher

internal object RSACrypto {
    @Throws(NoSuchAlgorithmException::class)
    fun genKeyPair(): StringKeyPair {
        val keyPairGen = KeyPairGenerator.getInstance("RSA")
        keyPairGen.initialize(1024, SecureRandom())
        val keyPair = keyPairGen.generateKeyPair()
        val publicKeyString = Base64.getEncoder().encodeToString(keyPair.public.encoded)
        val privateKeyString = Base64.getEncoder().encodeToString(keyPair.private.encoded)
        return StringKeyPair().apply {
            publicKey = publicKeyString
            privateKey = privateKeyString
        }
    }

    @Throws(Exception::class)
    fun encrypt(str: String, publicKey: String): String {
        return encrypt(str.toByteArray(), Base64.getDecoder().decode(publicKey))
    }

    @Throws(Exception::class)
    fun encrypt(content: ByteArray, publicKey: ByteArray): String {
        val pubKey = KeyFactory.getInstance("RSA").generatePublic(X509EncodedKeySpec(publicKey))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        return Base64.getEncoder().encodeToString(cipher.doFinal(content))
    }

    @Throws(Exception::class)
    fun decrypt(str: String, privateKey: String): String {
        return String(decryptToBytes(str, privateKey))
    }

    @Throws(Exception::class)
    fun decryptToBytes(str: String, privateKey: String): ByteArray {
        val inputByte = Base64.getDecoder().decode(str.toByteArray(charset("UTF-8")))
        val decoded = Base64.getDecoder().decode(privateKey)
        val priKey = KeyFactory.getInstance("RSA").generatePrivate(PKCS8EncodedKeySpec(decoded))
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, priKey)
        return cipher.doFinal(inputByte)
    }
}
