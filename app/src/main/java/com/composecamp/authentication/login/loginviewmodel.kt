package com.composecamp.authentication.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composecamp.authentication.repository.AuthRepo
import kotlinx.coroutines.launch

class Loginviewmodel(
    //used to get the auth repo
    private val repository : AuthRepo = AuthRepo()
) : ViewModel() {
    val currentUser = repository.currentUser

    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUserNameChange(userName: String){
        loginUiState = loginUiState.copy(userName = userName)
    }
    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }
    fun onUserNameSignupChange(userNameSignup: String){
        loginUiState = loginUiState.copy(userNameSignup = userNameSignup)
    }
    fun onPasswordSignupChange(passwordSignup: String){
        loginUiState = loginUiState.copy(passwordSignup = passwordSignup)
    }
    fun onConfirmPasswordChange(confirmPasswordSignup: String){
        loginUiState = loginUiState.copy(confirmPasswordSignup = confirmPasswordSignup)
    }

    //validate forms

    private fun validatelogin()=
        loginUiState.userName.isNotBlank() &&
                loginUiState.password.isNotBlank()

    private fun validatesignup()=
        loginUiState.userNameSignup.isNotBlank() &&
                loginUiState.passwordSignup.isNotBlank() &&
                loginUiState.confirmPasswordSignup.isNotBlank()


    //create user function
    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validatesignup()){
                throw IllegalArgumentException("Email and Password can't be empty")
            }
            loginUiState=loginUiState.copy(isLoading = true)
            if (loginUiState.passwordSignup !=
                loginUiState.confirmPasswordSignup){
                throw IllegalArgumentException(
                    "Passwords do not match")

            }
            loginUiState=loginUiState.copy(signUpError = null)
            repository.createUser(
                loginUiState.userNameSignup,
                loginUiState.passwordSignup
            ){isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState=loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState=loginUiState.copy(isSuccessLogin = false)
                }

            }

        } catch (e : Exception){
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }
    //create a login function
    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validatelogin()){
                throw IllegalArgumentException("Email and Password can't be empty")
            }
            loginUiState=loginUiState.copy(isLoading = true)
            loginUiState=loginUiState.copy(loginError = null)
            repository.login(
                loginUiState.userName,
                loginUiState.password
            ){isSuccessful ->
                if (isSuccessful){
                    Toast.makeText(
                        context,
                        "success Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState=loginUiState.copy(isSuccessLogin = true)
                }else{
                    Toast.makeText(
                        context,
                        "Failed Login",
                        Toast.LENGTH_SHORT
                    ).show()
                    loginUiState=loginUiState.copy(isSuccessLogin = false)
                }

            }

        } catch (e : Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        }finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

}
//that's going to hold up our ui states
data class LoginUiState(
    val userName: String = "",
    val password: String = "",
    val userNameSignup: String = "",
    val passwordSignup: String = "",
    val confirmPasswordSignup: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError : String? = null
)