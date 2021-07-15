package ge.nsakandelidze.customMessenger.presenter.signUp

import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import ge.nsakandelidze.customMessenger.domain.User
import ge.nsakandelidze.customMessenger.storage.UserDataStorage
import ge.nsakandelidze.customMessenger.view.homepage.HomePageActivity
import ge.nsakandelidze.customMessenger.view.signUp.ISignUpView

class SignUpPresenter(val view: ISignUpView) {

    private val validator: SignUpValidator = SignUpValidator()


    private val db =
        Firebase.database("https://custom-messenger-c9f70-default-rtdb.europe-west1.firebasedatabase.app/");
    private val myRef = db.getReference("User")

    fun signUpNewUser(username: String, password: String, profession: String): Boolean {
        val inputParametersValid = validator.isInputParametersValid(username, password, profession)
        if (inputParametersValid) {
            var key = myRef.push().key
            if (key != null) {
                val user = User(username, password, profession)
                myRef.child(key).setValue(user)
            }
            return true
        } else {
            view.showFailedSignInMessage("All three input parameters are mandatory to fill.")
            return false
        }
    }
}