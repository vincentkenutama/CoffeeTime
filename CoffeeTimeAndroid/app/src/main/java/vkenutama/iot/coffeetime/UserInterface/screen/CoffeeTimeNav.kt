package vkenutama.iot.coffeetime.UserInterface.screen

enum class Screen {
    LOGIN_PAGE,
    SIGNUP_PAGE
}

sealed class NavigationItem(val route: String){
    object LoginScreen : NavigationItem(Screen.LOGIN_PAGE.name)
    object SignUpScreen : NavigationItem(Screen.SIGNUP_PAGE.name)
}