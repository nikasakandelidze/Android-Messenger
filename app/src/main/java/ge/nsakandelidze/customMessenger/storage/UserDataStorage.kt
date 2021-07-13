package ge.nsakandelidze.customMessenger.storage

class UserDataStorage {
    fun updateUserWithIdOf(userId: String, nickname: String, profession: String) {
        print("updateing user using the firebase")
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