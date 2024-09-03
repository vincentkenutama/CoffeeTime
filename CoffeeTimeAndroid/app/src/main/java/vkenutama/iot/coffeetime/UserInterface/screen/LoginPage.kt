package vkenutama.iot.coffeetime.UserInterface.screen

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import vkenutama.iot.coffeetime.Data.Model.UserField
import vkenutama.iot.coffeetime.Data.Model.Users
import vkenutama.iot.coffeetime.Data.ViewModel.UserViewModel
import vkenutama.iot.coffeetime.R
import vkenutama.iot.coffeetime.UserInterface.component.ReusableSpacer
import vkenutama.iot.coffeetime.Util.Database
import vkenutama.iot.coffeetime.Util.DatabaseLibrary
import vkenutama.iot.coffeetime.Util.toDp
import vkenutama.iot.coffeetime.UserInterface.screen.NavigationItem
import vkenutama.iot.coffeetime.Util.Encryption


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){

    val userViewModels: UserViewModel = viewModel()

    var notification by remember {
        mutableStateOf("")
    }

    fun verifyUserLogin(){
        /*
                Check apakah form sudah valid
         */

        Log.d(TAG, "Username : ${userViewModels.getUsername()} password : ${userViewModels.getPassword()}")

        if(userViewModels.getUsername().isEmpty() or userViewModels.getPassword().isEmpty()){
            notification = "Please fill all the form!"
            return
        }

        runBlocking {
            val db = Firebase.firestore
            val userRef = db.collection(DatabaseLibrary.USER_COLLECTION).whereEqualTo("username", userViewModels.getUsername())

            val userSnapshot = userRef.get().await()
            val user: List<Users> = userSnapshot.toObjects<Users>()

            //Tidak ditemukan user pada database
            if(userSnapshot.isEmpty()){
                notification = "Username not found..."
                return@runBlocking
            }

            //Jika password tidak sama dengan di database
            Log.d(TAG, "Password : ${Encryption().applyHash(userViewModels.getPassword())} DB : ${user[0].hashedPassword}")

            if(Encryption().applyHash(userViewModels.getPassword()) != user[0].hashedPassword){
                notification = "Password is not correct"
                return@runBlocking
            }


//            navController.navigate(NavigationItem.HomeScreen.route)
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Image(painter = painterResource(id = R.drawable.header), contentDescription = null)

        Column (
            modifier = Modifier.padding( 26.dp)
        ){

            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Login",
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

            if(notification.isNotEmpty()){
                ReusableSpacer(height = 30.toDp())
                Text(text = notification,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(25.dp))

            TextWithIcon(icon = R.drawable.profile_user, placeholderText = "Username", userViewModels, UserField.Username)

            Spacer(modifier = Modifier.height(25.dp))

            TextWithIcon(icon = R.drawable.lock, placeholderText = "Password", userViewModels, UserField.Password)

            ReusableSpacer(height = 100.toDp())

            Button(
                onClick = {
                    verifyUserLogin()
                },
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

@RequiresApi(Build.VERSION_CODES.O)
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
            value = when(field){
                UserField.Username -> value
                UserField.Password -> "*".repeat(userViewModels.getPassword().length)
            },
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