package vkenutama.iot.coffeetime

import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vkenutama.iot.coffeetime.Data.ViewModel.UserViewModel
import vkenutama.iot.coffeetime.UserInterface.screen.LoginPage
import vkenutama.iot.coffeetime.Util.Database
import vkenutama.iot.coffeetime.ui.theme.CoffeeTimeTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {


            CoffeeTimeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginPage(
                        modifier = Modifier.padding(innerPadding)
                    )
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
            }
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var message by remember { mutableStateOf("") }
    var users by remember{ mutableStateOf(mutableListOf<CobaUser>()) }

    val coroutineScope = rememberCoroutineScope()

    fun send(){
        val db = Firebase.firestore

        // Create a new user with a first, middle, and last name
                val user = hashMapOf(
                    "first" to "Alan",
                    "middle" to "Mathison",
                    "last" to "Turing",
                    "born" to 1912,
                )

        // Add a new document with a generated ID
                db.collection("users")
                    .add(user)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding document", e)
                    }
    }

    suspend fun readUsers() : MutableList<CobaUser>{
        val db = Firebase.firestore
        val data: MutableList<CobaUser> = mutableListOf()

        try{
            val result = db.collection("users").get().await()
            for(document in result){
                val user = document.toObject<CobaUser>(CobaUser::class.java)
                data.add(user)
            }

            return data
        }
        catch (e : Exception){
            println(e)
            return data
        }
    }

    fun basicReadWrite(){
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                message = value.toString()
//                Log.d(TAG, "Value is: $value")
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    Column (
        modifier = modifier
    ){
        Text(
            text = "Message is ${message}",
            modifier = modifier
        )

        Button(onClick = {send()}) {
            Text(text = "Send")
        }

        Button(onClick = { coroutineScope.launch {
            val db =  Database(CobaUser::class.java)
            users = db.selectAll("users") as MutableList<CobaUser>
        }
        }) {
            Text(text = "Read")
        }
        
        Button(onClick = {println(users)}) {
            Text(text = "Print")
        }
        
        Image(painter = painterResource(id = R.drawable.header), contentDescription = null)
    }

}

data class CobaUser(
    var born: Int = 0,             // Default value for Int
    var first: String = "",       // Default value for String
    var last: String = "",        // Default value for String
    var middle: String = ""       // New property with default value
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CoffeeTimeTheme {
        Greeting("Android")
    }
}