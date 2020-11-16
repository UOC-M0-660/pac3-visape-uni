package edu.uoc.pac3.oauth

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Endpoints
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.oauth.OAuthConstants
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.post
import kotlinx.android.synthetic.main.activity_oauth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OAuthActivity : AppCompatActivity() {

    private val TAG = "OAuthActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oauth)

        launchOAuthAuthorization()
    }

    fun buildOAuthUri(): Uri {
        // TODO: Create URI

        //https://id.twitch.tv/oauth2/authorize
        //    ?client_id=<your client ID>
        //    &redirect_uri=<your registered redirect URI>
        //    &response_type=code
        //    &scope=<space-separated list of scopes>
        val uri = Uri.parse(Endpoints.baseAuthorizeUrl).buildUpon().appendQueryParameter("client_id",OAuthConstants.clientId)
            .appendQueryParameter("redirect_uri", Endpoints.redirectUri)
            .appendQueryParameter("response_type", OAuthConstants.responseType)
            .appendQueryParameter("scope", OAuthConstants.scope)
            .appendQueryParameter("state", OAuthConstants.uniqueState)
            .build()
        return uri
    }

    private fun launchOAuthAuthorization() {
        //  Create URI
        val uri = buildOAuthUri()

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.let {
                    if (request.url.toString().startsWith(Endpoints.redirectUri)) {
                        val responseState = request.url.getQueryParameter("state")
                        if (responseState == OAuthConstants.uniqueState) {
                            request.url.getQueryParameter("code")?.let {
                                Log.d(TAG, "Authorization code $it")
                                onAuthorizationCodeRetrieved(it)
                            } ?: run {
                                Log.e(TAG, "Error login")
                            }
                        } else {
                            Log.e(TAG, "Not Unique State")
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, request)
            }
        }


        // Load OAuth Uri
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(uri.toString())
    }

    // Call this method after obtaining the authorization code
    // on the WebView to obtain the tokens
    private fun onAuthorizationCodeRetrieved(authorizationCode: String) {

        // Show Loading Indicator
        progressBar.visibility = View.VISIBLE



        // TODO: Create Twitch Service

        // TODO: Get Tokens from Twitch

        // TODO: Save access token and refresh token using the SessionManager class

        CoroutineScope(Dispatchers.IO).launch {

            TwitchApiService(Network.createHttpClient(applicationContext)).getTokens(authorizationCode).let { tokenResponse ->
                val sessionManager = SessionManager(applicationContext)
                tokenResponse?.accessToken?.let { sessionManager.saveAccessToken(it) }
                tokenResponse?.refreshToken?.let { sessionManager.saveRefreshToken(it) }

                Log.d(TAG, sessionManager.getAccessToken())
            }
        }
    }
}