package yogesh.example.mychatbot

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import yogesh.example.mychatbot.ui.theme.MychatbotTheme
import ys.viewmodel.AuthViewModel
import ysh.screen.ChatRoomListScreen
import ysh.screen.ChatScreen
import ysh.screen.LoginScreen
import ysh.screen.SignUpScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            MychatbotTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph(navController = navController, authViewModel = authViewModel)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    fun NavigationGraph(
        navController: NavHostController,
        authViewModel: AuthViewModel
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.SignupScreen.route
        ) {
            composable(Screen.SignupScreen.route) {
                SignUpScreen(
                    authViewModel = authViewModel,
                    onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
                )
            }
            composable(Screen.LoginScreen.route) {
                LoginScreen(
                    authViewModel = authViewModel,
                    onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) }
                ) {
                    navController.navigate(Screen.ChatRoomsScreen.route)
                }
            }
            composable(Screen.ChatRoomsScreen.route) {
                ChatRoomListScreen {
                    navController.navigate("${Screen.ChatScreen.route}/${it.id}")
                }
            }

            composable("${Screen.ChatScreen.route}/{roomId}") {
                val roomId: String = it
                    .arguments?.getString("roomId") ?: ""
                ChatScreen(roomId = roomId)
            }
        }
    }
}

