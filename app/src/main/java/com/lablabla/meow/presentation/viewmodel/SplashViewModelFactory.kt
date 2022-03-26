package com.lablabla.meow.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.lablabla.meow.data.remote.FirebaseSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class SplashViewModelFactory(
    @ApplicationContext private val context: Context,
    private val firebaseAuth: FirebaseAuth,
    private val firebaseSource: FirebaseSource,
    private val gs: GoogleSignInClient
) :ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(context, firebaseAuth, firebaseSource, gs) as T
    }
}