package ge.nsakandelidze.customMessenger.view.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import ge.nsakandelidze.customMessenger.R
import ge.nsakandelidze.customMessenger.presenter.profile.ProfilePresenter
import ge.nsakandelidze.customMessenger.view.signIn.SignInActivity

class ProfilePage : Fragment(R.layout.activity_profile), IProfile {
    private lateinit var nickNameEditText: TextInputEditText
    private lateinit var professionEditText: TextInputEditText
    private lateinit var updateButton: Button
    private lateinit var signOutButton: Button
    private lateinit var image: ImageView
    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.activity_profile, container, false)
        initState(view)
        return view
    }

    private fun initState(view: View) {
        initViewComponents(view)
        initListeners()
        this.profilePresenter = ProfilePresenter(this)
        this.profilePresenter.getUserData()
        progressBar.visibility = View.VISIBLE
        this.profilePresenter.getImageForUser({
            progressBar.visibility = View.GONE
            image.setImageResource(R.drawable.avatar_image_placeholder)
        }, {
            showImage(it)
            progressBar.visibility = View.GONE
        })
    }

    private fun initViewComponents(view: View) {
        nickNameEditText = view.findViewById(R.id.nickname)
        professionEditText = view.findViewById(R.id.profession)
        updateButton = view.findViewById(R.id.update_button)
        signOutButton = view.findViewById(R.id.sign_out_button)
        image = view.findViewById(R.id.profile_pic)
        progressBar = view.findViewById(R.id.loader_progress_bar)
    }

    private fun initListeners() {
        updateButton.setOnClickListener {
            val nickname: String = nickNameEditText.text.toString()
            val profession: String = professionEditText.text.toString()
            profilePresenter.updateUserData(nickname, profession)
        }
        signOutButton.setOnClickListener {
            profilePresenter.signOut()
        }
        image.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT;
            intent.type = "image/*";
            startActivityForResult(intent, 200)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == 200) {
            val stream = data?.data?.let { context?.contentResolver?.openInputStream(it) }
            if (stream != null) {
                profilePresenter.updateImage(stream)
            }
        } else {
            Toast.makeText(context, "Faield to choose image", Toast.LENGTH_LONG).show()
        }
    }

    override fun updateUserFields(nickname: String, profession: String) {
        nickNameEditText.setText(nickname)
        professionEditText.setText(profession)
    }

    override fun showMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    override fun redirectToView(viewName: String) {
        // redirect to another activity
        val intent = Intent(activity, SignInActivity::class.java)
        startActivity(intent)
    }

    override fun showImage(byteArray: ByteArray) {
        val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size);
        image.setImageBitmap(bitmap)
    }
}