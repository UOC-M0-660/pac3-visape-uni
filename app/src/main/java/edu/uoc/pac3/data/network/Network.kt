package edu.uoc.pac3.data.network

import android.content.Context
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

/**
 * Created by alex on 07/09/2020.
 */
object Network {

    private const val TAG = "Network"

    fun createHttpClient(context: Context): HttpClient {
        return HttpClient(OkHttp) {
            // TODO: Setup HttpClient
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }
}