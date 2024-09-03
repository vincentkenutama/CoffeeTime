package vkenutama.iot.coffeetime.UserInterface.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomePage(navController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Text(text = "Test")
    }
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    HomePage(navController = rememberNavController())
}