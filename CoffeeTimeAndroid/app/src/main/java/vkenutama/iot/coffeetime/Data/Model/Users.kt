package vkenutama.iot.coffeetime.Data.Model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Users @RequiresApi(Build.VERSION_CODES.O) constructor(
    val username: String = "",
    val name: String = "",
    val email: String = "",
    val dateOfBirth: String = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
    val hashedPassword: String = "", // Consider storing the hashed password instead
    val password: String = "",
    val gender: Gender = Gender.NOT_SPECIFIED
)

enum class Gender {
    WOMAN, MAN, NOT_SPECIFIED
}

sealed class UserField{
    object Username : UserField()
    object Password : UserField()
}

sealed class UserForms{
    object Fullname : UserForms()
    object Username : UserForms()
    object Email : UserForms()
    object Password : UserForms()
    object ConfirmationPassword : UserForms()
}

