package com.dave.coding_interview_webapp.utils

import org.apache.xerces.impl.dv.util.Base64

import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.xml.bind.DatatypeConverter
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.*
import kotlin.streams.asSequence

/**
 * Provide methods for [String] encryption
 *
 */
class EncryptionUtility {

    companion object {

        private const val AES_KEY = "9DE99833C7053C092BBC9213B8968B25709B1C6E8F6922BBB0DC38A5E8D8AC60"

        const val AES_KEY_CLIENT = "8AC60339DE9986922BBB0DC38A5E8DC7053C092BBC92F13B8968B25709B1C6E8"

        /**
         * @param base [String] to encrypt
         * @return a SHA 256 String generate from base
         */
        fun sha256From(base: String): Optional<String>? {
            val digest: MessageDigest
            var encoded: Optional<String>? = null

            try {
                digest = MessageDigest.getInstance("SHA-256")
                val byteOfTextToHash = base.toByteArray(charset("UTF-8"))
                val hashedByteArray = digest.digest(byteOfTextToHash)
                encoded = Optional.of(DatatypeConverter.printHexBinary(Base64.encode(hashedByteArray).toByteArray()).toUpperCase())
            } catch (e: NoSuchAlgorithmException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }

            return encoded
        }

        /**
         * Generate 4 Digits pin code
         */
        fun randomizePinCode(): String {
            return ((Math.random() * 9000).toInt() + 1000).toString()
        }

        /**
         * Generate 4 Digits pin code
         */
        fun randomizeStringCode(length: Long): String {
            val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            return Random().ints(length, 0, source.length)
                    .asSequence()
                    .map(source::get)
                    .joinToString("")
        }

        @Throws(Exception::class)
        @JvmOverloads
        fun encrypt(plainText: String, key: String = AES_KEY): String {
            val clean = plainText.toByteArray()

            // Generating IV.
            val ivSize = 16
            val iv = ByteArray(ivSize)
            val random = SecureRandom()
            random.nextBytes(iv)
            val ivParameterSpec = IvParameterSpec(iv)

            // Hashing key.
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update(key.toByteArray(charset("UTF-8")))
            val keyBytes = ByteArray(16)
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.size)
            val secretKeySpec = SecretKeySpec(keyBytes, "AES")

            // Encrypt.
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec)
            val encrypted = cipher.doFinal(clean)

            // Combine IV and encrypted part.
            val encryptedIVAndText = ByteArray(ivSize + encrypted.size)
            System.arraycopy(iv, 0, encryptedIVAndText, 0, ivSize)
            System.arraycopy(encrypted, 0, encryptedIVAndText, ivSize, encrypted.size)

            return DatatypeConverter.printHexBinary(encryptedIVAndText).toUpperCase()
        }

        @Throws(Exception::class)
        @JvmOverloads
        fun decrypt(encryptedIvTextHex: String, key: String = AES_KEY): String {
            val ivSize = 16
            val keySize = 16

            val encryptedIvTextBytes = DatatypeConverter.parseHexBinary(encryptedIvTextHex)

            // Extract IV.
            val iv = ByteArray(ivSize)
            System.arraycopy(encryptedIvTextBytes, 0, iv, 0, iv.size)
            val ivParameterSpec = IvParameterSpec(iv)

            // Extract encrypted part.
            val encryptedSize = encryptedIvTextBytes.size - ivSize
            val encryptedBytes = ByteArray(encryptedSize)
            System.arraycopy(encryptedIvTextBytes, ivSize, encryptedBytes, 0, encryptedSize)

            // Hash key.
            val keyBytes = ByteArray(keySize)
            val md = MessageDigest.getInstance("SHA-256")
            md.update(key.toByteArray())
            System.arraycopy(md.digest(), 0, keyBytes, 0, keyBytes.size)
            val secretKeySpec = SecretKeySpec(keyBytes, "AES")

            // Decrypt.
            val cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
            val decrypted = cipherDecrypt.doFinal(encryptedBytes)

            return String(decrypted)
        }
    }
}
