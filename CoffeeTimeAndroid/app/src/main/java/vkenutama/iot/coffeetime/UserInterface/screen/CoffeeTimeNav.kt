package vkenutama.iot.coffeetime.UserInterface.screen

enum class Screen {
    LOGIN_PAGE,
    SIGNUP_PAGE,
    HOME_PAGE,
    SPLASH_PAGE
}

sealed class NavigationItem(val route: String){
    data object LoginScreen : NavigationItem(Screen.LOGIN_PAGE.name)
    data object SignUpScreen : NavigationItem(Screen.SIGNUP_PAGE.name)
    data object HomeScreen : NavigationItem(Screen.HOME_PAGE.name)
    data object SplashScreen : NavigationItem(Screen.SPLASH_PAGE.name)
}
