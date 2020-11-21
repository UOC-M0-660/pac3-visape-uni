package edu.uoc.pac3.data

import android.util.Log
import edu.uoc.pac3.data.network.Endpoints
import edu.uoc.pac3.data.oauth.OAuthConstants
import edu.uoc.pac3.data.oauth.OAuthException
import edu.uoc.pac3.data.oauth.OAuthTokensResponse
import edu.uoc.pac3.data.oauth.UnauthorizedException
import edu.uoc.pac3.data.streams.StreamsResponse
import edu.uoc.pac3.data.user.User
import io.ktor.client.*
import io.ktor.client.request.*
import kotlin.jvm.Throws

/**
 * Created by alex on 24/10/2020.
 */

class TwitchApiService(private val httpClient: HttpClient) {
    private val TAG = "TwitchApiService"

    /// Gets Access and Refresh Tokens on Twitch
    suspend fun getTokens(authorizationCode: String): OAuthTokensResponse? {
        //https://id.twitch.tv/oauth2/token
        //    ?client_id=<your client ID>
        //    &client_secret=<your client secret>
        //    &code=<authorization code received above>
        //    &grant_type=authorization_code
        //    &redirect_uri=<your registered redirect URI>

        httpClient.use {
            val response = it.post<OAuthTokensResponse>(Endpoints.baseTokenUrl) {
                parameter("client_id", OAuthConstants.clientId)
                parameter("client_secret", OAuthConstants.secretId)
                parameter("code", authorizationCode)
                parameter("grant_type", "authorization_code")
                parameter("redirect_uri", Endpoints.redirectUri)
            }
            Log.d(TAG, "Access Token: ${response.accessToken}. Refresh Token: ${response.refreshToken}")
            return response
        }
    }

    /// Gets Streams on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getStreams(cursor: String? = null, accessToken: String? = null): StreamsResponse? {
        //curl -X GET 'https://api.twitch.tv/helix/streams' \
        //-H 'Authorization: Bearer 2gbdx6oar67tqtcmt49t3wpcgycthx' \
        //-H 'Client-Id: wbmytr93xzw8zbg0p1izqyzzc5mbiz'
        if (accessToken != null) {
            httpClient.use {
                //try {
                    val response = it.get<StreamsResponse>(Endpoints.streamsUrl) {
                        this.header("Authorization","Bearer ${accessToken}")
                        this.header("Client-Id", OAuthConstants.clientId)
                        /*headers {
                            append("Authorization", "Bearer ${accessToken}")
                            append("Client_ID", OAuthConstants.clientId)
                        }*/
                        Log.d(TAG, "HEADERS: ${accessToken} + Client_id: ${OAuthConstants.clientId}")
                    }
                    Log.d(TAG, "StreamsResponse: ${response}")
                    return response
                /*} catch (e: Exception) {
                    Log.e(TAG, "StreamsResponseException: " + e.message)
                }
                return null*/
            }

            //TODO("Support Pagination")
        } else {
            throw UnauthorizedException
        }
    }

    /// Gets Current Authorized User on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun getUser(): User? {
        TODO("Get User from Twitch")
    }

    /// Gets Current Authorized User on Twitch
    @Throws(UnauthorizedException::class)
    suspend fun updateUserDescription(description: String): User? {
        TODO("Update User Description on Twitch")
    }
}