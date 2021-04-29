package com.example.timetracker.data.preferences

import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

class TimeTrackerPreferencesImpl(
    private val sharedPreferences: SharedPreferences
) : TimeTrackerPreferences {
    private enum class KeyPreferences(val key: String) {
        PIN("pin"),
        BIOMETRIC("biometric"),
        FIRST_ENTER("first_enter"),
        TODAY_SECTION("today_section"),
        MOVE_STARRED_TASKS_TO_TOP("move_starred_tasks"),
        CONFIRM_BEFORE_DELETING("confirm_to_delete")
    }

    private val keystoreName = "AndroidKeyStore"
    private val keystoreAlias = "TimeTracker"

    private val secretKey: SecretKey? by lazy {
        val keyStore = KeyStore.getInstance(keystoreName)
        keyStore.load(null)

        if (!keyStore.containsAlias(keystoreAlias)) {
            val keyGenerator =
                KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, keystoreName)
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                keystoreAlias,
                KeyProperties.PURPOSE_ENCRYPT.or(KeyProperties.PURPOSE_DECRYPT)
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .build()

            keyGenerator.init(keyGenParameterSpec)
            return@lazy keyGenerator.generateKey()
        } else {
            return@lazy (keyStore.getEntry(
                keystoreAlias,
                null
            ) as KeyStore.SecretKeyEntry).secretKey
        }
    }

    private fun encrypt(data: String): String {
        Cipher.getInstance("AES/CBC/PKCS7Padding").also {
            it.init(Cipher.ENCRYPT_MODE, secretKey)

            val stringIV = Base64.encodeToString(it.iv, Base64.DEFAULT)
            val stringEncrypted =
                Base64.encodeToString(it.doFinal(data.toByteArray()), Base64.DEFAULT)
            return "$stringEncrypted,$stringIV"
        }
    }

    private fun decrypt(data: String): String {
        Cipher.getInstance("AES/CBC/PKCS7Padding").also {
            val parts = data.split(",")

            val encryptedData = Base64.decode(parts.first(), Base64.DEFAULT)
            val iv = Base64.decode(parts.last(), Base64.DEFAULT)

            it.init(Cipher.DECRYPT_MODE, secretKey, IvParameterSpec(iv))
            return String(it.doFinal(encryptedData))
        }
    }

    override var pin: String?
        get() {
            val storedValue = sharedPreferences.getString(KeyPreferences.PIN.key, null)
            return storedValue?.let {
                decrypt(it)
            } ?: kotlin.run {
                null
            }
        }
            set(value) {
                value?.let { safeValue ->
                    val encryptedValue = encrypt(safeValue)
                    sharedPreferences.edit()
                        .putString(KeyPreferences.PIN.key, encryptedValue)
                        .commit()
                } ?: run {
                    sharedPreferences.edit().putString(KeyPreferences.PIN.key, null).commit()
                }
            }

            override var confirmBeforeDeleting: Boolean
            get() = sharedPreferences.getBoolean(KeyPreferences.CONFIRM_BEFORE_DELETING.key, true)
            set(value) {
                sharedPreferences.edit()
                    .putBoolean(KeyPreferences.CONFIRM_BEFORE_DELETING.key, value)
                    .apply()
            }

            override var isFirstEnter: Boolean
            get() = sharedPreferences.getBoolean(KeyPreferences.FIRST_ENTER.key, true)
            set(value) {
                sharedPreferences.edit()
                    .putBoolean(KeyPreferences.FIRST_ENTER.key, value)
                    .apply()
            }

            override var isMoveStarredTasksToTop: Boolean
            get() = sharedPreferences.getBoolean(KeyPreferences.MOVE_STARRED_TASKS_TO_TOP.key, true)
            set(value) {
                sharedPreferences.edit()
                    .putBoolean(KeyPreferences.MOVE_STARRED_TASKS_TO_TOP.key, value)
                    .apply()
            }

            override var isTodaySectionEnabled: Boolean
            get() = sharedPreferences.getBoolean(KeyPreferences.TODAY_SECTION.key, true)
            set(value) {
                sharedPreferences.edit()
                    .putBoolean(KeyPreferences.TODAY_SECTION.key, value)
                    .apply()
            }

            override var isUseBiometric: Boolean
            get() = sharedPreferences.getBoolean(KeyPreferences.BIOMETRIC.key, true)
            set(value) {
                sharedPreferences.edit()
                    .putBoolean(KeyPreferences.BIOMETRIC.key, value)
                    .apply()
            }

            override fun clearPreferences() {
                isUseBiometric = true
                isFirstEnter = true
                isTodaySectionEnabled = true
                isMoveStarredTasksToTop = true
                confirmBeforeDeleting = true

                pin = null

            }
        }