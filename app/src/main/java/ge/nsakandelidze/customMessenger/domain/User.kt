package ge.nsakandelidze.customMessenger.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val nickname: String? = null,
    val password: String? = null,
    val profession: String? = null
)
