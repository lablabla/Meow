package com.lablabla.meow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lablabla.meow.presentation.user_list.ContactListScreen
import com.lablabla.meow.ui.theme.MeowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MeowTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "meow_contact_list"
                    ) {
                        composable("meow_authentication_screen") {

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