package edu.uoc.pac3.data.oauth

import java.util.*

/**
 * Created by alex on 07/09/2020.
 */
object OAuthConstants {

    // TODO: Set OAuth2 Variables
    val baseUrl = "https://id.twitch.tv/oauth2/authorize"
    val clientId = "h3haa2o0glz6rgmzvg3a11l3byoap7"
    val redirectUri = "http://localhost"
    val responseType = "code"
    val scope = "user:edit"
    val uniqueState = UUID.randomUUID().toString()
    val baseTokenUrl = "https://id.twitch.tv/oauth2/token"
    val secretId = "8h1tb6vaqbzm4h71ll9qkx3k13w4nc"

}