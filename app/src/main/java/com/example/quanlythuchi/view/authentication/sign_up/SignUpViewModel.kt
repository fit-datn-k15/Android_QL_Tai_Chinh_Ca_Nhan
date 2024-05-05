package com.example.quanlythuchi.view.authentication.sign_up

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.example.quanlythuchi.R
import com.example.quanlythuchi.base.BaseViewModel
import com.example.quanlythuchi.extension.isPasswordValid
import com.example.quanlythuchi.extension.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    @ApplicationContext var applicationContext: Context
): BaseViewModel() {
    var emailInput = "sssss"
        set(value) {
            field = value
            checkValidEmail()
            checkEnableButtonSignUp()
        }
    var passwordInput = ""
        set(value) {
            field = value
            checkValidPassword()
            checkValidConfirmPassword()
            checkEnableButtonSignUp()
        }
    var confirmPassword = ""
        set(value) {
            field = value
            checkValidConfirmPassword()
            checkEnableButtonSignUp()
        }

    var errorEmail = MutableLiveData("")
    var errorPassword = MutableLiveData("")
    var errorConfirmPassword = MutableLiveData("")
    var isEnableButtonSignUp = MutableLiveData(false)

    private fun checkValidEmail() {
        errorEmail.value = if (emailInput.isValidEmail()) "" else applicationContext.getString(R.string.error_email)
    }
    private fun checkValidPassword() {
        errorPassword.value = if (passwordInput.isPasswordValid()) "" else applicationContext.getString(R.string.error_password)
    }
    private fun checkValidConfirmPassword() {
        if ((confirmPassword.isPasswordValid()
            && passwordInput.isPasswordValid()
            && passwordInput == confirmPassword)
            || confirmPassword.isEmpty())
        {
            errorConfirmPassword.value = ""
        }
        else
            errorConfirmPassword.value = applicationContext.getString(R.string.error_confirm_password)
    }
    private fun checkEnableButtonSignUp(){
        isEnableButtonSignUp.value = (
                emailInput.isValidEmail()
                && passwordInput.isPasswordValid()
                && confirmPassword.isPasswordValid()
                && passwordInput == confirmPassword)
    }
}