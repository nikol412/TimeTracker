package com.example.timetracker.common

import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object BiometricAuth {

    fun isBiometricAvailable(context: Context): Boolean {
        val biometricManager = androidx.biometric.BiometricManager.from(context)
        return when (biometricManager.canAuthenticate()) {
            androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS -> true
            else -> false
        }
    }

    fun launchBiometricAuth(
        context: Context, fragment: Fragment,
        onResult: (biometricResult: BIOMETRIC_AUTH_RESULT) -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(context)

        val biometricCallback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)

                onResult.invoke(BIOMETRIC_AUTH_RESULT.SUCCESS)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()

                onResult.invoke(BIOMETRIC_AUTH_RESULT.FAILED)
            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                if (errorCode == BIOMETRIC_AUTH_NEED_PIN) {
                    onResult.invoke(BIOMETRIC_AUTH_RESULT.NEED_PIN)
                } else {
                    onResult.invoke(BIOMETRIC_AUTH_RESULT.ERROR)
                }
            }
        }

        val biometricPrompt = BiometricPrompt(fragment, executor, biometricCallback)

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login to app")
            .setNegativeButtonText("Use pin")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}

enum class BIOMETRIC_AUTH_RESULT {
    SUCCESS, FAILED, NEED_PIN, ERROR
}

const val BIOMETRIC_AUTH_NEED_PIN = 13