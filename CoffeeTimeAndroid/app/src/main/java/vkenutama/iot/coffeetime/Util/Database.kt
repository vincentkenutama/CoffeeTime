package vkenutama.iot.coffeetime.Util

import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class Database<T>(
    private val classType: Class<T>
) {

    private val db = Firebase.firestore

    private suspend fun getAllData(path: String): List<T>?{
        return try{
            val snapshot = db.collection(path).get().await()

            val dataList = snapshot.documents.mapNotNull {
                documentSnapshot -> documentSnapshot.toObject(classType)
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

    suspend fun selectAll(path: String) : List<T>?{
        val data: List<T>? = select(path) { true }

        return data
    }

}