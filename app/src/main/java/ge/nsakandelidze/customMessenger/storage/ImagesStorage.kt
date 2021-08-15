package ge.nsakandelidze.customMessenger.storage

import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream
import java.lang.Exception

class ImagesStorage {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance();
    private val reference = storage.getReference("images")


    fun uploadImage(
        inputStream: InputStream,
        userId: String,
        sucessCallback: (Unit) -> Unit,
        failureCallback: (Unit) -> Unit
    ) {
        val uploadTask = reference.child(userId).putStream(inputStream)
        uploadTask.addOnSuccessListener { sucessCallback(Unit) }
            .addOnFailureListener { failureCallback(Unit) }
    }

    fun getImageForUserId(
        userId: String,
        sucessConsumer: (ByteArray) -> Unit,
        failureConsumer: (String) -> Unit
    ) {
        reference.listAll().addOnSuccessListener {
            if (it.items.map { e -> e.name }.toCollection(mutableListOf()).contains(userId)) {
                reference.child(userId).getBytes(5000000)
                    .addOnSuccessListener {
                        try {
                            sucessConsumer(it)
                        } catch (e: Exception) {
                        }
                    }
                    .addOnFailureListener {
                        try {
                            failureConsumer(it.localizedMessage)
                        } catch (e: Exception) {
                        }
                    }
            } else {
                failureConsumer("Couldnt find image for userId: " + userId)
            }
        }.addOnFailureListener {
            failureConsumer("Couldnt find image for userId: " + userId)
        }
    }

    companion object {
        private lateinit var INSTANCE: ImagesStorage

        fun createDb() {
            INSTANCE = ImagesStorage()
        }

        fun getInstance(): ImagesStorage {
            return INSTANCE
        }
    }

}