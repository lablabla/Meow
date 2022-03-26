package com.lablabla.meow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.lablabla.meow.data.remote.FirebaseSource
import com.lablabla.meow.presentation.viewmodel.SplashViewModel
import com.lablabla.meow.presentation.login.LoginScreen
import com.lablabla.meow.presentation.user_list.ContactListScreen
import com.lablabla.meow.presentation.viewmodel.SplashViewModelFactory
import com.lablabla.meow.ui.theme.MeowTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    lateinit var viewModel: SplashViewModel

    @Inject
    lateinit var client: GoogleSignInClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseSource: FirebaseSource

    @Inject
    lateinit var gs: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lateinit var firstRoute: String
        viewModel = ViewModelProvider(this, SplashViewModelFactory(this, firebaseAuth, firebaseSource, gs))[SplashViewModel::class.java]
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            MeowTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    firstRoute = viewModel.firstScreen.collectAsState().value.route
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = firstRoute
                    ) {
                        composable("meow_login") {
                            LoginScreen(
                                navController = navController,
                                client =  client)
                        }
                        composable("meow_contact_list") {
                            ContactListScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}