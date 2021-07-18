package ge.nsakandelidze.customMessenger.storage

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ge.nsakandelidze.customMessenger.domain.Conversation
import ge.nsakandelidze.customMessenger.domain.User
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Semaphore

class UserDataStorage {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val usersRef = database.getReference("users")

    fun updateUserWithIdOf(userId: String, nickname: String, password: String ,profession: String) {
        usersRef.child(userId).setValue(User(nickname, password, profession))
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

    fun addUser(
        username: String,
        password: String,
        profession: String,
        successCallback: (User) -> Unit,
        failCallback: (Unit) -> Unit
    ) {
        checkIfUsernameExist(username, {
            val user = User(username, password, profession)
            usersRef.child(username).setValue(user)
            successCallback(user)
        }, {
            failCallback(Unit)
        })
    }

     fun checkIfUsernameExist(
        username: String,
        successCallback: (Unit) -> Unit,
        failCallback: (Unit) -> Unit
    ) {
        getUsers {
            var found = false
            for (user in it) {
                if (user?.nickname == username) {
                    failCallback(Unit)
                    found = true
                }
            }
            if (!found) {
                successCallback(Unit)
            }
        }
    }

    fun checkIfUserExist(
        username: String,
        password: String,
        successCallback: (User) -> Unit,
        failCallback: (Unit) -> Unit
    ) {
        getUsers {
            var found = false
            for (user in it) {
                if (user?.nickname == username && user.password == password) {
                    successCallback(user)
                    found = true
                }
            }
            if (!found) {
                failCallback(Unit)
            }
        }
    }

    private fun getUsers(consumer: (MutableList<User?>) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.children
                    .map {
                        it.getValue(User::class.java)
                    }.toCollection(mutableListOf())
                consumer(users)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
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