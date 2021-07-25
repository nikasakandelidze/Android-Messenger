package ge.nsakandelidze.customMessenger.view.profile

import ge.nsakandelidze.customMessenger.domain.User

interface IUsersSearch {
    fun updateUsersList(users: MutableList<User?>)
}