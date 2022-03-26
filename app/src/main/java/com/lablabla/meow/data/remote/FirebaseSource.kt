package com.lablabla.meow.data.remote

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.lablabla.meow.data.remote.dto.FirestoreUserDto
import javax.inject.Inject

class FirebaseSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
    ) {

    fun signUpUser(email:String,password:String,fullName:String) = firebaseAuth.createUserWithEmailAndPassword(email,password)

    fun signInUser(email: String,password: String) = firebaseAuth.signInWithEmailAndPassword(email,password)

    fun signInWithGoogle(acct: GoogleSignInAccount) = firebaseAuth.signInWithCredential(GoogleAuthProvider.getCredential(acct.idToken,null))

    fun saveUser(firestoreUserDto: FirestoreUserDto)=
        firebaseAuth.uid?.let { firestore.collection("users").document(it).set(firestoreUserDto) }

    fun fetchUser()=
        firebaseAuth.uid?.let { firestore.collection("users").document(it).get() }

    fun fetchAllUsers()=
        firebaseAuth.uid?.let { firestore.collection("users").get() }

    fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)

}