package ge.nsakandelidze.customMessenger.storage

import android.content.Context
import android.content.SharedPreferences

class UserStateStorage {

    private lateinit var sharedPreferences: SharedPreferences

    private val PREFERENCES_USER_ID_KEY = "user_id"

    fun getIdOfUser(): Long {
        return sharedPreferences.getLong(PREFERENCES_USER_ID_KEY, -1L)
    }

    fun signIn(userId: Long) {
        with(sharedPreferences.edit()) {
            putLong(PREFERENCES_USER_ID_KEY, userId)
            commit()
        }
    }

    fun signOut() {
        with(sharedPreferences.edit()) {
            remove(PREFERENCES_USER_ID_KEY)
            commit()
        }
    }

    companion object {
        private lateinit var INSTANCE: UserStateStorage

        fun createDb(context: Context) {
            INSTANCE = UserStateStorage()
            INSTANCE.sharedPreferences =
                context.getSharedPreferences("storage", Context.MODE_PRIVATE)
        }

        fun getInstance(): UserStateStorage {
            return INSTANCE
        }
    }
}