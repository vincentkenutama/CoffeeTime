package vkenutama.iot.coffeetime.Data.Model

import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.util.Date

data class Users(
    val id: String? = null, // Changed to String for more flexibility with unique identifiers
    val username: String? = null,
    val name: String? = null,
    val email: String? = null,
    val dateOfBirth: LocalDate? = null, // Using LocalDate for better date handling
    val hashedPassword: String? = null, // Consider storing the hashed password instead
    val password: String? = null,
    val gender: Gender? = null
)

enum class Gender {
    WOMAN, MAN, NOT_SPECIFIED
}

sealed class UserField{
    object Username : UserField()
    object Password : UserField()
}

