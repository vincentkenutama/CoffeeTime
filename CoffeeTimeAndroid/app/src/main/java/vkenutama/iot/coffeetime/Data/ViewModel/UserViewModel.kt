package vkenutama.iot.coffeetime.Data.ViewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import vkenutama.iot.coffeetime.Data.Model.Gender
import vkenutama.iot.coffeetime.Data.Model.Users
import java.time.LocalDate

class UserViewModel : ViewModel() {
    @RequiresApi(Build.VERSION_CODES.O)
    private val _user = MutableStateFlow(Users())

    private val _username = mutableStateOf("")

    val username: MutableState<String> = _username

    @RequiresApi(Build.VERSION_CODES.O)
    val user:StateFlow<Users> = _user.asStateFlow()

    fun setUsername(updatedValue: String){
        _username.value = updatedValue
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getUsername(): String{
        return _user.value.username
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getPassword(): String{
        return _user.value.password
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUsername(updatedValue: String){
        _user.update {
            currentState ->
            currentState.copy(
                username = updatedValue
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updatePasswordField(updateValue: String){
        _user.update{
            curr ->
            curr.copy(
                password = updateValue
            )
        }
    }
}