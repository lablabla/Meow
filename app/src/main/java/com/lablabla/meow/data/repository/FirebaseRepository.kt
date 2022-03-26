package com.lablabla.meow.data.repository

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.lablabla.meow.data.remote.FirebaseSource
import javax.inject.Inject

class FirebaseRepository @Inject constructor(private val firebaseSource: FirebaseSource) {

    fun signUpUser(email: String, password: String, fullName: String) = firebaseSource.signUpUser(email, password, fullName)

    fun signInUser(email: String, password: String) = firebaseSource.signInUser(email, password)

    fun signInWithGoogle(acct: GoogleSignInAccount) = firebaseSource.signInWithGoogle(acct)

//    fun saveUser(email: String, name: String) = firebaseSource.saveUser(email, name)

    fun fetchUser() = firebaseSource.fetchUser()

    fun sendForgotPassword(email: String)=firebaseSource.sendForgotPassword(email)
}