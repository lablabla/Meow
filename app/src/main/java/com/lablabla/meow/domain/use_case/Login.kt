package com.lablabla.meow.domain.use_case

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.lablabla.meow.core.util.Resource
import com.lablabla.meow.data.remote.FirebaseSource
import com.lablabla.meow.data.remote.dto.FirestoreUserDto
import com.lablabla.meow.data.repository.FirebaseRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.CoroutineContext

class Login(
    private val firebaseRepository: FirebaseRepository,
    private val firebaseSource: FirebaseSource,
    private val firebaseAuth: FirebaseAuth
) : CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(acct: GoogleSignInAccount) = coroutineScope {
        callbackFlow<Resource<Boolean>> {
            // Start signing in
            try {
                firebaseRepository.signInWithGoogle(acct).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        launch {
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
                        }
                    } else {
                        trySend(Resource.Error(message = "Couldn't sign in"))
                    }
                }
                awaitClose { }
            } catch (e: Exception) {
                trySend(Resource.Error(message = e.localizedMessage ?: "Failed sending meow"))
            }
        }

    }
}