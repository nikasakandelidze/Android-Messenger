package ge.nsakandelidze.customMessenger.storage

import com.google.firebase.database.FirebaseDatabase

class UserDataStorage {
    // Write a message to the database
    val database: FirebaseDatabase = FirebaseDatabase.getInstance();
    val usersRef = database.getReference("users")

    fun updateUserWithIdOf(userId: String, nickname: String, profession: String) {
      usersRef.setValue("value from android app")
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