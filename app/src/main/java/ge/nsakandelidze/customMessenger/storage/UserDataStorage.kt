package ge.nsakandelidze.customMessenger.storage

import android.content.Context

class UserDataStorage {
    fun updateUserWithIdOf(userId: String, nickname: String, profession: String) {
        print("updateing user using the firebase")
    }

    companion object {
        private lateinit var INSTANCE: UserDataStorage

        fun createDb(context: Context) {
            INSTANCE = UserDataStorage()
        }

        fun getInstance(): UserDataStorage {
            return INSTANCE
        }
    }
}