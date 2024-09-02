package vkenutama.iot.coffeetime.UserInterface.screen

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import vkenutama.iot.coffeetime.Data.Model.Gender
import vkenutama.iot.coffeetime.Data.Model.UserForms
import vkenutama.iot.coffeetime.Data.Model.Users
import vkenutama.iot.coffeetime.R
import vkenutama.iot.coffeetime.UserInterface.component.ReusableSpacer
import vkenutama.iot.coffeetime.Util.DatabaseLibrary
import vkenutama.iot.coffeetime.Util.Encryption
import vkenutama.iot.coffeetime.Util.toDp
import java.time.LocalDate
import java.util.Date

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpPage(
    modifier: Modifier = Modifier,
    navController: NavController = rememberNavController()
){

    var fullName by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var confirmPassword by remember { mutableStateOf("")}
    var username by remember { mutableStateOf("")}

    var validUsername by remember{ mutableStateOf(true) }
    var validPassword by remember{ mutableStateOf(true) }
    var validEmail by remember{ mutableStateOf(true) }
    var isNotEmpty by remember { mutableStateOf(true) }
    var validationMessage by remember { mutableStateOf("") }
    var registrationSuccess by remember{ mutableStateOf(false) }

    val allUser : MutableList<Users> = mutableListOf()

    val topPadding: Double = 100.0
    val formPadding: Double = 50.0


    fun handleValueChange(field: UserForms, newValue: String){
        when(field){
            UserForms.Fullname -> fullName = newValue
            UserForms.Email -> email = newValue
            UserForms.Password -> password = newValue
            UserForms.Username -> username = newValue
            UserForms.ConfirmationPassword -> {
                confirmPassword = newValue
            }
        }
    }

    fun resetVerify(){
        validUsername = true
        validEmail = true
        validPassword = true
        isNotEmpty = true
        registrationSuccess = false
    }

    fun verifyUserInformation(userInformation: Users){
        runBlocking {
            val db = Firebase.firestore
            val docRef = db.collection(DatabaseLibrary.USER_COLLECTION)

            resetVerify()

            /*
                Verify To Do:
                -> Verify if the username is using the right format [x]
                -> Verify if the password and confirmation password is same [x]
                -> Verify if the username is not added yet in database [x]
                -> Verify if the email is unique and have certain characteristic [x]
             */

            //We do this first for performance because it is not request to database yet
            //Check if the username is valid
            if(fullName.isNotEmpty() && username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
                isNotEmpty = true
            }
            else{
                isNotEmpty = false
                return@runBlocking
            }


            if(username.length < 8 || username.length > 20)
            {
                validUsername = false
                return@runBlocking
            }

            //Check if the password and confirmation password is same
            if(password != confirmPassword && password.length >= 8) {
                validPassword = false
                return@runBlocking
            }

            //Cek apakah email valid
            if(!email.contains("@") || !email.contains(".com")){
                validEmail = false
                return@runBlocking
            }
            
            //If all is good then grab all user documents from database
            val allUserSnapshot = docRef.get().await()

            //Check if the username is available on database
            for(userData in allUserSnapshot){
                val selectedUser = userData.toObject<Users>()

                if(username == selectedUser.username){
                    validUsername = false
                }
                if(email == selectedUser.email){
                    validEmail = false
                }
            }

            if(validPassword && validUsername && validEmail && isNotEmpty){
                addUserToDatabase(userInformation)
                registrationSuccess = true
                Log.d(TAG, "Added to database")
            }

            else
                Log.d(TAG, "Not added to database")
        }
    }

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 65.toDp())
            .verticalScroll(rememberScrollState()),
    ){
        ReusableSpacer(height = (topPadding/2.0).toInt().toDp())

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Sign Up",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold)
//            Icon(painter = painterResource(id = R.drawable.coffee_beans), contentDescription = null)
            ReusableSpacer(width = 45.toDp())
            Image(painter = painterResource(id = R.drawable.coffee_beans),
                contentDescription = null,
                modifier = Modifier.width(112.toDp()))
        }

        Text(text = "Fill the form below",
            color = Color(0xFFBFBDBD),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        when{
            !isNotEmpty -> validationMessage = "Please fill all the forms"
            !validUsername -> validationMessage = "Username is not valid or already exist"
            !validEmail -> validationMessage = "Email is not valid or already exist"
            !validPassword -> validationMessage = "Confirmation password doesn't match"
            else ->{
                validationMessage = ""
            }
        }



        if(validationMessage.isNotEmpty()){
            ReusableSpacer(height = 20.toDp())
            Text(text = validationMessage,
                color = Color.Red,
                fontWeight = FontWeight.Bold)
            ReusableSpacer(height = 40.toDp())
        }

        if(registrationSuccess){
            ReusableSpacer(height = 20.toDp())
            Text(text = "Registration Success!",
                color = Color.Green,
                fontWeight = FontWeight.Bold)
            ReusableSpacer(height = 40.toDp())
        }

        if(validationMessage.isEmpty() && !registrationSuccess)
        {
            ReusableSpacer(height = 100.toDp())
        }



        SignUpForm(
            fieldName = "Full name",
            value = fullName,
            handleChange = {
                newValue ->
                handleValueChange(UserForms.Fullname, newValue)
        })

        ReusableSpacer(height = formPadding.toInt().toDp())

        SignUpForm(
            fieldName = "Username",
            value = username,
            handleChange = {
                newValue ->
                handleValueChange(UserForms.Username, newValue)
        })

        ReusableSpacer(height = formPadding.toInt().toDp())

        SignUpForm(
            fieldName = "Email",
            value = email,
            handleChange = {
                    newValue ->
                handleValueChange(UserForms.Email, newValue)
            })

        ReusableSpacer(height = 56.toDp())

        SignUpForm(
            fieldName = "Password",
            value = "*".repeat(password.length),
            handleChange = {
                    newValue ->
                handleValueChange(UserForms.Password, newValue)
            })

        ReusableSpacer(height = formPadding.toInt().toDp())

        SignUpForm(
            fieldName = "Confirm Password",
            value = "*".repeat(confirmPassword.length),
            handleChange = {
                    newValue ->
                handleValueChange(UserForms.ConfirmationPassword, newValue)
            })

        ReusableSpacer(height = formPadding.toInt().toDp())

        Column (
//            modifier = Modifier,
            modifier = Modifier
                .fillMaxWidth()
                .width(177.toDp())
                .height(155.toDp()),
            horizontalAlignment = Alignment.CenterHorizontally,

        ){
            Button(
                onClick = {
                    val newUser = Users(
                        username = username,
                        name = fullName,
                        email = email,
                        dateOfBirth = "",
                        hashedPassword = Encryption().applyHash("a"),
                        password = "",
                        gender = Gender.NOT_SPECIFIED
                    )

                    verifyUserInformation(newUser)
                },
                colors = ButtonDefaults.buttonColors(Color(0xFFA67B5B)),
                shape = RoundedCornerShape(12.toDp()),
                modifier = Modifier
                            .height(155.toDp())
            ) {
                Text(
                    text = "Sign Up",
                    fontSize = 22.sp
                )
            }
        }

        ReusableSpacer(height = 60.toDp())

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Already have an account?", fontWeight = FontWeight.Bold,
                color = Color(0xFFA67B5B))
            Text(text = "Sign In",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    navController.navigate(NavigationItem.LoginScreen.route)
                })
        }

    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun addUserToDatabase(newUserData: Users){
    val db = Firebase.firestore

    db.collection("users").document(newUserData!!.username).set(newUserData)
}

@Composable
fun SignUpForm(fieldName: String, value: String,  handleChange: (newValue: String) -> Unit){
    Column {
        Text(text = fieldName,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp)

        ReusableSpacer(height = 40.toDp())

        Row(
            modifier = Modifier.border(width = 2.dp, color = Color(0xFF986B54), shape = RoundedCornerShape(8.toDp()))
        ){
            TextField(
                value = value,
                onValueChange = {
                    handleChange(it)
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.width(941.dp)

            )
        }


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SignUpPagePreview(){
    SignUpPage()
}