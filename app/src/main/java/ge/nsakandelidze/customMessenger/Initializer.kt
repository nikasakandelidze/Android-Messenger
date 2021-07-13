package ge.nsakandelidze.customMessenger

import android.app.Application
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage

class Initializer : Application() {
    override fun onCreate() {
        super.onCreate()
        UserStateStorage.createDb(this)
        UserDataStorage.createDb()
    }
}