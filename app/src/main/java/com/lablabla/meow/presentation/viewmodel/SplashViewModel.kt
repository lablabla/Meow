package com.lablabla.meow.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.lablabla.meow.data.remote.FirebaseSource
import com.lablabla.meow.data.remote.dto.FirestoreUserDto
import com.lablabla.meow.presentation.data.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseSource: FirebaseSource,
    private val gs: GoogleSignInClient
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _firstScreen = MutableStateFlow(Screen.LOGIN)
    val firstScreen = _firstScreen.asStateFlow()

    init {
        viewModelScope.launch {
            // TODO: Check if user is signed in or not.
            //gs.signOut().await()
            //firebaseAuth.signOut()
            val acct = GoogleSignIn.getLastSignedInAccount(context)
            if (acct != null) {
                firebaseAuth.currentUser?.let {
                    // Check if firestore has user already
                    val user = firebaseSource.fetchUser()?.await()
                    if (user == null || user.data == null)
                    {
                        val firstoreUser = FirestoreUserDto(
                        id = firebaseAuth.uid,
                        email = acct.email,
                        name = acct.displayName,
                        phone = null
                    )
                        firebaseSource.saveUser(firstoreUser)?.await()
                    }
                    _firstScreen.value = Screen.CONTACT_LIST
                }
            }
            _isLoading.value = false
        }
    }
}