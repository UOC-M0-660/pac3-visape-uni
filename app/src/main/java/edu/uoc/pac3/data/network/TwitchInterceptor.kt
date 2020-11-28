package edu.uoc.pac3.data.network

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import edu.uoc.pac3.data.MyApp
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import io.ktor.client.engine.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import kotlin.coroutines.coroutineContext

class TwitchInterceptor: Interceptor {
    private val TAG = "TwitchInterceptor"
    public override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val response = chain.proceed(request)

        Log.i(TAG, "Response code: " + response.code)

        if (response.code == 401) {

            CoroutineScope(Dispatchers.IO).launch {
                Log.d(TAG, "Refresh")
                MyApp.context?.let { context ->
                    Log.d(TAG, "RefreshContext")
                    TwitchApiService(Network.createHttpClient(context))
                            .refreshToken(SessionManager(context).getAccessToken()).let { tokenResponse ->
                                val sessionManager = SessionManager(context)
                                tokenResponse?.accessToken?.let {
                                    sessionManager.saveAccessToken(it)
                                    val newRequest = request.newBuilder().addHeader("Authorization", it).build()
                                    chain.proceed(newRequest)
                                }
                                tokenResponse?.refreshToken?.let { sessionManager.saveRefreshToken(it) }

                            }

                }
            }
        }

        return response
    }
}