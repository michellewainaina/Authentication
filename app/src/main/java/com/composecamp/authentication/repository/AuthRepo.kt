package com.composecamp.authentication.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepo {
    //call current user
    val currentUser : FirebaseUser? = Firebase.auth.currentUser

    //check if user is logged in or not
    fun hasUser() : Boolean = Firebase.auth.currentUser != null

    //get the user ID
    fun getUserId() : String = Firebase.auth.currentUser?.uid.orEmpty()

    //function to createuser
    suspend fun createUser(
        email: String,
        password: String,
        onComplete:(Boolean) ->Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onComplete.invoke(true)
                } else{
                    onComplete.invoke(false)
                }
            }.await()
    }
    //function to sign in user
    suspend fun login(
        email: String,
        password: String,
        onComplete:(Boolean) ->Unit
    ) = withContext(Dispatchers.IO){
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    onComplete.invoke(true)
                } else{
                    onComplete.invoke(false)
                }
            }.await()
    }


}