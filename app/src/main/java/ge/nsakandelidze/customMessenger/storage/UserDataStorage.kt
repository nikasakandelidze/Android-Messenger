package ge.nsakandelidze.customMessenger.storage

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ge.nsakandelidze.customMessenger.domain.User
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Semaphore

class UserDataStorage {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val usersRef = database.getReference("users")

    fun updateUserWithIdOf(userId: Long, nickname: String, profession: String) {
        usersRef.child("1").setValue(User(nickname, null, profession))
    }

    fun getUserDataWithIdOf(userId: String, consumer: (User) -> Unit) {
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                val value = p0.getValue(User::class.java)
                value?.let {
                    consumer(it)
                }
            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }

    companion object {
        private lateinit var INSTANCE: UserDataStorage

        fun createDb() {
            INSTANCE = UserDataStorage()
        }

        fun getInstance(): UserDataStorage {
            return INSTANCE
        }
    }
}