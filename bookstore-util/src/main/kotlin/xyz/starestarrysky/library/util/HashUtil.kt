package xyz.starestarrysky.library.util

import java.io.ByteArrayInputStream
import java.security.MessageDigest
import java.util.Base64

object HashUtil {
    fun hash(src: ByteArray): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val bais = ByteArrayInputStream(src)
        val buff = ByteArray(1024)
        var len = bais.read(buff, 0, 1024)
        while (len >= 0) {
            if (len > 0) {
                digest.update(buff, 0, len)
            }
            len = bais.read(buff, 0, 1024)
        }
        return Base64.getEncoder().encodeToString(digest.digest())
    }

    fun validate(src: ByteArray, code: String): Boolean {
        return hash(src) == code
    }
}
