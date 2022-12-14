package xyz.starestarrysky.library.util.encrypt

import java.util.Base64

object EncryptUtil {
    fun encode(content: String, publicKey: String): String {
        var all = content.toByteArray()
        var result = ""
        val key = Base64.getDecoder().decode(publicKey)
        while (all.size > 117) {
            val block = all.sliceArray(0..116)
            result += RSACrypto.encrypt(block, key) + "||||"
            all = all.sliceArray(IntRange(117,all.size - 1))
        }
        return result + RSACrypto.encrypt(all, key)
    }

    fun decode(content: String, rsaPrivate: String): String {
        val blocks = content.split("||||")
        val all = mutableListOf<Byte>()
        blocks.forEach {
            val block = RSACrypto.decryptToBytes(it, rsaPrivate)
            all.addAll(block.toTypedArray())
        }
        return String(all.toByteArray())
    }
}
