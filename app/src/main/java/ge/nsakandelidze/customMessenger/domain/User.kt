package ge.nsakandelidze.customMessenger.domain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class User {
    var nickname: String? = null
    var password: String? = null
    var profession: String? = null

    constructor()

    constructor(
        nickname: String? = null,
        password: String? = null,
        profession: String? = null
    ) {
        this.nickname = nickname
        this.password = password
        this.profession = profession
    }
}
