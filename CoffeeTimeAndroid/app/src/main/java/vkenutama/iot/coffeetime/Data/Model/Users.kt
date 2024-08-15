package vkenutama.iot.coffeetime.Data.Model

import java.time.LocalDate
import java.util.Date

data class Users(
    val id: String, // Changed to String for more flexibility with unique identifiers
    val username: String,
    val name: String,
    val firstname: String,
    val lastname: String,
    val email: String,
    val dateOfBirth: LocalDate, // Using LocalDate for better date handling
    val hashedPassword: String, // Consider storing the hashed password instead
    val gender: Gender? = null,

    )

enum class Gender {
    WOMAN, MAN, NOT_SPECIFIED
}
