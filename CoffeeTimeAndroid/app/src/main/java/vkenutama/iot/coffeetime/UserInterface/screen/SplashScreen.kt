package vkenutama.iot.coffeetime.UserInterface.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import vkenutama.iot.coffeetime.R

@Composable
fun SplashScreen(
    navController: NavController = rememberNavController()
){

    val coroutineScope = rememberCoroutineScope()


    fun navigateToLoginPage(){
        coroutineScope.launch {
            delay(2000)
            navController.navigate(NavigationItem.LoginScreen.route)

        }
    }

    navigateToLoginPage()


    Image(painter = painterResource(id = R.drawable.splash_screen_v2),
            contentDescription = null,
            modifier = Modifier.width(1100.dp))

}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    SplashScreen()
}
