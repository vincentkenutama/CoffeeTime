package vkenutama.iot.coffeetime.UserInterface.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import vkenutama.iot.coffeetime.Data.Model.UserField
import vkenutama.iot.coffeetime.Data.ViewModel.UserViewModel
import vkenutama.iot.coffeetime.R
import vkenutama.iot.coffeetime.UserInterface.component.ReusableSpacer
import vkenutama.iot.coffeetime.Util.toDp


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){

    val userViewModels: UserViewModel = viewModel()

    Column (
        modifier = modifier.fillMaxSize()
    ){
        Image(painter = painterResource(id = R.drawable.header), contentDescription = null)

        Column (
            modifier = Modifier.padding( 26.dp)
        ){
//            Spacer(
//                modifier = Modifier.height(20.dp)
//            )

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Login",
//                fontFamily = robotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 42.sp
                )
                Spacer(modifier = Modifier.width(16.dp))
                Image(painter = painterResource(id = R.drawable.coffee_beans_2), contentDescription = null,
                    modifier = Modifier.height(45.dp))
            }
            Spacer(modifier = Modifier.height(11.dp))

            Text(
                text = "Please sign in to continue.",
                fontSize = 15.sp,
                color = Color(0xFFBFBDBD)
            )

            Spacer(modifier = Modifier.height(25.dp))

            TextWithIcon(icon = R.drawable.profile_user, placeholderText = "Username", userViewModels, UserField.Username)

            Spacer(modifier = Modifier.height(25.dp))

            TextWithIcon(icon = R.drawable.lock, placeholderText = "Password", userViewModels, UserField.Password)

            ReusableSpacer(height = 100.toDp())

            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(Color(0xFFA67B5B)),
                shape = RoundedCornerShape(12.toDp()),
                modifier = Modifier
                    .height(130.toDp())
                    .width(282.toDp())
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 18.sp)
            }

            ReusableSpacer(height = 60.toDp())

            Text(
                text = "Forgot username or password?",
                modifier = Modifier.clickable {},
                color = Color(0xFFB39A7C),
                fontWeight = FontWeight.Bold)

            ReusableSpacer(height = 60.toDp())

            Row {
                Text(
                    text = "Don't have an account?",
                    color = Color(0xFFB39A7C),
                    fontWeight = FontWeight.Bold)

                ReusableSpacer(width = 20.toDp())

                Text(text = "Sign Up",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        navController.navigate(NavigationItem.SignUpScreen.route)
                    })

//                Text(text = "-> ${userViewModels.user.value.username ?: ""}")
            }
        }

    }
}

@Composable
fun TextWithIcon(icon: Int,
                 placeholderText: String,
                 userViewModels : UserViewModel,
                 field: UserField){

    val user by userViewModels.user.collectAsState()

    val value = when(field){
        UserField.Username -> user.username ?: ""
        UserField.Password -> user.password ?: ""
    }

    Row (
        modifier = Modifier
            .border(2.dp, color = Color(0xFF986B54), shape = RoundedCornerShape(3.dp))
            .height(58.dp)
            .width(358.dp),
        verticalAlignment = Alignment.CenterVertically,
        ){
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(16.dp)
                .height(26.dp))

        TextField(
            value = value,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            placeholder = {
                Text(text = placeholderText)
            },
            onValueChange = {
                newValue ->
                when(field){
                    UserField.Username -> userViewModels.updateUsername(newValue)
                    UserField.Password -> userViewModels.updatePasswordField(newValue)
                }
            })
    }


}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true)
@Composable
fun LoginPagePreview(){
    LoginPage()
}