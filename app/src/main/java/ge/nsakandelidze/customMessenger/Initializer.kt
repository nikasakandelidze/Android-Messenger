package ge.nsakandelidze.customMessenger

import android.app.Application
import ge.nsakandelidze.customMessenger.storage.ConversationStorage
import ge.nsakandelidze.customMessenger.storage.ImagesStorage
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.storage.UserStateStorage

class Initializer : Application() {
    override fun onCreate() {
        super.onCreate()
        UserStateStorage.createDb(this)
        UserDataStorage.createDb()
        ConversationStorage.createDb()
        ImagesStorage.createDb()
    }
}