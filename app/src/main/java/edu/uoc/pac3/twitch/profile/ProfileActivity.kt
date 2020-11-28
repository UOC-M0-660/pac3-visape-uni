package edu.uoc.pac3.twitch.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import edu.uoc.pac3.LaunchActivity
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.user.UserResponse
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {

    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        CoroutineScope(Dispatchers.Main).launch {
            TwitchApiService(Network.createHttpClient(applicationContext))
                    .getUser(SessionManager(applicationContext).getAccessToken()).let { userResponse ->
                        cargarInfoUser(userResponse)
                    }
        }

        updateDescriptionButton.setOnClickListener(View.OnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                TwitchApiService(Network.createHttpClient(applicationContext))
                        .updateUserDescription(userDescriptionEditText.text.toString(), SessionManager(applicationContext).getAccessToken()).let {userResponse ->
                            cargarInfoUser(userResponse)
                            Toast.makeText(applicationContext, "Description have been successfully updated", Toast.LENGTH_LONG).show()
                        }
            }
        })

        logoutButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, LaunchActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            SessionManager(applicationContext).clearAccessToken()
            SessionManager(applicationContext).clearRefreshToken()
            startActivity(intent)
            finish()
        })
    }

    private fun cargarInfoUser(userResponse: UserResponse?) {
        if (userResponse != null && userResponse.data != null && userResponse.data.size > 0) {
            userResponse.data.get(0).userName?.let { userNameTextView.text = it }
            userResponse.data.get(0).description?.let { userDescriptionEditText.setText(it) }
            userResponse.data.get(0).imageUrl?.let { Glide.with(applicationContext)
                    .load(it)
                    .into(imageView) }
            userResponse.data.get(0).viewCount?.let {
                viewsText.text = it.toString()
            }
        }
    }
}