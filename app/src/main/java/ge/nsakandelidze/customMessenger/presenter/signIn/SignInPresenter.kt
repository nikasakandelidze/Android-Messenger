package ge.nsakandelidze.customMessenger.presenter.signIn

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.view.signIn.ISignIn


class SignInPresenter(val view: ISignIn) {

    private val validator: SignInValidator = SignInValidator()
    private val database: FirebaseDatabase =
        Firebase.database("https://custom-messenger-c9f70-default-rtdb.europe-west1.firebasedatabase.app/");
    private val usersRef = database.getReference("User")

    fun signInUser(username: String, password: String): Boolean {
        Log.d("ak var", "blabla")
        val inputParametersValid = validator.isInputParametersValid(username, password)
        if (inputParametersValid) {
            checkUser(username, password)
            return true
        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
            return false
        }
    }

    private fun checkUser(username: String, password: String): Boolean {
        getUsers()
        return true
    }
    fun getUsers()  {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    val userId: String? = ds.key
                    Log.d("id", userId.toString())
                    usersRef.child(userId!!)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(p0: DataSnapshot) {
                                val value = p0.value.toString()
                            }

                            override fun onCancelled(p0: DatabaseError) {
                            }
                        })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
            }
        })
    }
}