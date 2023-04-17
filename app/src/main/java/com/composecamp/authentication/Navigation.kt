package com.composecamp.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composecamp.authentication.homepage.Home
import com.composecamp.authentication.login.*


enum class  LoginRoutes{
    Signup,
    SignIn,
}
enum class HomeRoutes{
    Home,
    Detail,
}


@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    loginviewmodel: Loginviewmodel,
){
    NavHost(
        navController = navController,
        startDestination = LoginRoutes.SignIn.name
    ){
        composable(route = LoginRoutes.SignIn.name){
            loginScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name){
                    launchSingleTop = true
                    popUpTo(route = LoginRoutes.SignIn.name){
                        inclusive = true
                    }
                }
            },
                loginviewmodel = loginviewmodel

            ) {
                navController.navigate(LoginRoutes.Signup.name){
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name){
                        inclusive = true
                    }
                }
            }
               }

        composable(route = LoginRoutes.Signup.name){
            signUpScreen(onNavToHomePage = {
                navController.navigate(HomeRoutes.Home.name){
                    popUpTo(LoginRoutes.Signup.name){
                        inclusive = true
                    }
                }
            },
            loginviewmodel = loginviewmodel
                ){
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }

        composable(route = HomeRoutes.Home.name){
            Home()
        }


    }



}