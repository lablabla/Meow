package com.lablabla.meow.presentation.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.lablabla.meow.core.google.GoogleApiContract
import com.lablabla.meow.presentation.viewmodel.LoginViewModel
import com.lablabla.meow.presentation.login.components.SignInGoogleButton
import com.lablabla.meow.presentation.viewmodel.BaseViewModel.UIEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    navController: NavController,
    client: GoogleSignInClient,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val signInRequestCode = 1

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val accountLauncher = rememberLauncherForActivityResult(contract = GoogleApiContract(client)) { task ->
        try {
            val gsa = task?.getResult(ApiException::class.java)
            gsa?.let {
                viewModel.onEvent(LoginViewModel.LoginEvent.Login(it))
            }
        } catch (e: ApiException) {
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.background)
                .fillMaxSize()
        ) {
            SignInGoogleButton(
                onClick = { accountLauncher.launch(signInRequestCode) }
            )
        }
    }
}