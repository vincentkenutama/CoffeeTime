package vkenutama.iot.coffeetime.Util

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import vkenutama.iot.coffeetime.CobaUser

class Database<T>(
    private val clazz: Class<T>
) {

    private val db = Firebase.firestore

    suspend fun getAllData(path: String): List<T>?{
        return try{
            val snapshot = db.collection(path).get().await()

            val dataList = snapshot.documents.mapNotNull { document ->
                document.toObject(clazz)
            }

            dataList
        }
        catch (e: Exception){
            null
        }
    }
    suspend fun select(path: String, filter: (T) -> Boolean) : List<T>? {
        val data: List<T>? = getAllData(path)

        return data?.filter(filter)
    }

}