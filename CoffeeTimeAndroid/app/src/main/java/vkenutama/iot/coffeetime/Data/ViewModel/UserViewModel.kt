package vkenutama.iot.coffeetime.Data.ViewModel

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
    private val _user = MutableStateFlow(Users())

    private val _username = mutableStateOf("")

    val username: MutableState<String> = _username

    val user:StateFlow<Users> = _user.asStateFlow()

    fun setUsername(updatedValue: String){
        _username.value = updatedValue
    }

    fun updateUsername(updatedValue: String){
        _user.update {
            currentState ->
            currentState.copy(
                username = updatedValue
            )
        }
    }

    fun updatePasswordField(updateValue: String){
        _user.update{
            curr ->
            curr.copy(
                password = updateValue
            )
        }
    }
}