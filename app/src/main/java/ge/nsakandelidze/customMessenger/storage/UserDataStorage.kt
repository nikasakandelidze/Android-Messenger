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

    fun addUser(username: String, password: String, profession: String): Boolean {
        Log.d("adduser", "bla")
        val exists = checkIfUserameExist(username)
        Log.d("exists", exists.toString())
        if(exists) {
            var key = usersRef.push().key
            if (key != null) {
                val user = User(username, password, profession)
                usersRef.child(key).setValue(user)
            }
            return true
        }
        return false
    }

    fun checkIfUserameExist(username: String): Boolean {
        var users = getUsers()
        for (user in users){
            if(user.nickname == username){
                return false
            }
        }
        return true
    }

    fun checkIfUserExist(username: String, password: String): Boolean{
        var users = getUsers()
        for (user in users){
            if(user.nickname == username && password == password){
                return true
            }
        }
        return fasle
    }

    fun getUsers(consumer: (MutableList<User?>) -> Unit) {
        val userList = mutableListOf<User>()
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.children
                    .map{
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