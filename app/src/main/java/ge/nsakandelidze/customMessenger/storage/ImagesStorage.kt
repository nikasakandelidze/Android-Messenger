package ge.nsakandelidze.customMessenger.storage

import com.google.firebase.storage.FirebaseStorage
import java.io.InputStream

class ImagesStorage {
    private val storage: FirebaseStorage = FirebaseStorage.getInstance();
    private val reference = storage.getReference("images")


    fun uploadImage(inputStream: InputStream, userId: String) {
        val uploadTask = reference.child(userId).putStream(inputStream)
        uploadTask.addOnSuccessListener { }
            .addOnFailureListener { }
    }

    fun getImageForUserId(
        userId: String,
        sucessConsumer: (ByteArray) -> Unit,
        failureConsumer: (String) -> Unit
    ) {
        reference.child(userId).getBytes(5000000)
            .addOnSuccessListener { sucessConsumer(it) }
            .addOnFailureListener { failureConsumer(it.localizedMessage) }
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