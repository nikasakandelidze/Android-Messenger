package ge.nsakandelidze.customMessenger.storage

import android.content.ContentValues
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ge.nsakandelidze.customMessenger.domain.User

class UserDataStorage {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    private val usersRef = database.getReference("users")

    fun updateUserWithIdOf(
        userId: String,
        nickname: String,
        password: String,
        profession: String,
        successCallback: (Unit) -> Unit,
        failCallback: (Unit) -> Unit
    ) {
        getUsers {
            val find: User? = it.find { !(it?.id.equals(userId)) && it?.nickname.equals(nickname) }
            if (find != null) {
                failCallback(Unit)
            } else {
                usersRef.child(userId).setValue(User(userId, nickname, password, profession))
                successCallback(Unit)
            }
        }
        usersRef.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                val value = p0.getValue(User::class.java)
                if (value == null) {
                    usersRef.child(userId).removeValue()
                    usersRef.child(nickname).setValue(User(nickname, password, profession))
                    successCallback(Unit)
                } else {
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
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
            val id = usersRef.push().key
            val user = User(id, username, password, profession)
            id?.let {
                usersRef.child(id).setValue(user)
                successCallback(user)
            }
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

    fun getUsers(consumer: (MutableList<User?>) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = dataSnapshot.children
                    .map {
                        val value = it.getValue(User::class.java)
                        value?.id = it.key
                        value
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