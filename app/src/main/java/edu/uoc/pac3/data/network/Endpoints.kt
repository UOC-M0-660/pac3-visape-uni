package edu.uoc.pac3.data.network

import java.util.*

/**
 * Created by alex on 07/09/2020.
 */
object Endpoints {

    val redirectUri = "http://localhost"

    // OAuth2 API Endpoints
    private const val oauthBaseUrl = "https://id.twitch.tv/oauth2"
    // TODO: Add all remaining endpoints
    val baseAuthorizeUrl = oauthBaseUrl + "/authorize"
    val baseTokenUrl = oauthBaseUrl + "/token"

    // Twitch API Endpoints
    private const val twitchBaseUrl = "https://api.twitch.tv/helix"
    // TODO: Add all remaining endpoints
    val streamsUrl = twitchBaseUrl + "/streams"
    val userUrl = twitchBaseUrl + "/users"
}