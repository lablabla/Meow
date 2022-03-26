package com.lablabla.meow.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.lablabla.meow.R
import com.lablabla.meow.data.remote.FirebaseSource
import com.lablabla.meow.data.repository.FirebaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    @Singleton
    fun provideFireBaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseRepository(firebaseSource: FirebaseSource) = FirebaseRepository(firebaseSource)

    @Provides
    @Singleton
    fun provideGso(@ApplicationContext context: Context) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    @Provides
    @Singleton
    fun provideGoogleClient(@ApplicationContext context: Context, gso:GoogleSignInOptions) = GoogleSignIn.getClient(context, gso)

    @Provides
    @Singleton
    fun provideFirestore() = FirebaseFirestore.getInstance()
}