package com.composecamp.authentication.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composecamp.authentication.ui.theme.AuthenticationTheme

//login screen
@Composable
fun loginScreen(
    loginviewmodel: Loginviewmodel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit,
){
    val loginUiState =loginviewmodel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

        ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary

        )
        if(isError){
            Text(
                text = loginUiState?.loginError ?: "Unknown Error",
                color = Color.Red
            )

        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userName ?: "",
            onValueChange = {loginviewmodel?.onUserNameChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Email")
            },
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.password ?: "",
            onValueChange = {loginviewmodel?.onPasswordChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(onClick = {loginviewmodel?.loginUser(context)}){
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            ){
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = {onNavToSignUpPage.invoke()}){
                Text(text = "Sign Up")
            }
        }

        if(loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginviewmodel?.hasUser){
            if(loginviewmodel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }

    }

}
//sign up screen
@Composable
fun signUpScreen(
    loginviewmodel: Loginviewmodel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit,
){
    val loginUiState =loginviewmodel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "Sign Up",
            style = MaterialTheme.typography.h3,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colors.primary

        )
        if(isError){
            Text(
                text = loginUiState?.signUpError ?: "Unknown Error",
                color = Color.Red
            )

        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userNameSignup ?: "",
            onValueChange = {loginviewmodel?.onUserNameSignupChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Email")
            },
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.passwordSignup ?: "",
            onValueChange = {loginviewmodel?.onPasswordSignupChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.confirmPasswordSignup?: "",
            onValueChange = {loginviewmodel?.onConfirmPasswordChange(it)},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                )
            },
            label = {
                Text(text = "Confirm Password")
            },
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(onClick = {loginviewmodel?.createUser(context)}){
            Text(text = "Sign In")
        }
        Spacer(modifier = Modifier.size(16.dp))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ){
            Text(text = "Already have an account?")
            Spacer(modifier = Modifier.size(8.dp))
            TextButton(onClick = {onNavToLoginPage.invoke()}){
                Text(text = "Sign In")
            }
        }

        if(loginUiState?.isLoading == true){
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginviewmodel?.hasUser){
            if(loginviewmodel?.hasUser == true){
                onNavToHomePage.invoke()
            }
        }

    }

}
@Preview(showSystemUi = true)
@Composable
fun prevloginScreen(){
    AuthenticationTheme {
        loginScreen(onNavToHomePage = {/* TODO */}){

        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun prevSignUpScreen(){
    AuthenticationTheme {
        signUpScreen(onNavToHomePage = {}){

        }
    }
}