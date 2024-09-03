package vkenutama.iot.coffeetime.Data.Model

data class Product(
    val productId: String = "",
    val productName: String = "",
    val productImageSource: String = "",
    val productImageSourceInt: Int = 0,
    val productDescription: String = "",
    val productPrice: Double = 0.0
)
