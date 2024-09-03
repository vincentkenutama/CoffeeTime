package vkenutama.iot.coffeetime.UserInterface.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = NavigationItem.SplashScreen.route
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ){
        composable(NavigationItem.SplashScreen.route){
            SplashScreen(navController = navController)
        }
        composable(NavigationItem.LoginScreen.route){
            LoginPage(navController = navController)
        }
        composable(NavigationItem.SignUpScreen.route){
            SignUpPage(navController = navController)
        }
        composable(NavigationItem.HomeScreen.route){
            HomePage(navController = navController)
        }
    }
}