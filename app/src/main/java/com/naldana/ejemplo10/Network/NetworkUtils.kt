package com.naldana.ejemplo10.Network

import android.net.Uri
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class NetworkUtils {

    val COINS_API_URL = ""

    fun buildSearchUri(coins: String) : URL {
        val buildUri = Uri.parse(COINS_API_URL)
            .buildUpon()
            .appendQueryParameter("t",coins)
            .build()
        return try{
            URL(buildUri.toString())
        }catch (e: MalformedURLException){
            URL("")
        }
    }

    @Throws(IOException::class)
    fun getResponseFromHttpUrl(url: URL):String{
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val `in` = urlConnection.inputStream

            val scanner = Scanner(`in`)
            scanner.useDelimiter("\\A")

            val hasInput = scanner.hasNext()
            return if(hasInput){
                scanner.next()
            }else{
                ""
            }
        }finally {
            urlConnection.disconnect()
        }
    }

}