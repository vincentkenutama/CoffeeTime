package vkenutama.iot.coffeetime.Data.Model

import java.util.Date

enum class TransactionStatus {
    SUCCESS, FAILED, PENDING
}

data class Transaction(
    val transactionDate: Date,
    val transactionStatus: TransactionStatus,
    val transactionId: String,
    val userId: String,
    val product: Product,
    val amount: Double,
    val totalPrice: Double
)

data class TransactionDocument(
    val userId: String,
    val transactions: List<Transaction>? = null
)