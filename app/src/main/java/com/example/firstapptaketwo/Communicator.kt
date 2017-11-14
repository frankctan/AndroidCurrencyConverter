package com.example.firstapptaketwo

import android.app.DownloadManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

/**
 * Created by GrapeFruit on 11/13/17.
 */
//
//interface CommunicatorDelegate {
//    fun interestingEvent()
//}
//
//class Communicator {
//    var delegate: CommunicatorDelegate?
//    private val url: String = "http://api.fixer.io/latest?base=USD"
//
//    fun startDownload() {
//        makeRequestWithVolley(this.url)
//    }
//
//    private fun makeRequestWithVolley(url: String) {
//        val queue = Volley.newRequestQueue(this) // 1
//
//        val stringRequest = StringRequest(DownloadManager.Request.Method.GET, url, // 2
//                Response.Listener<String> { response ->
//                    try {
//                        downloadComplete(retrieveRepositoriesFromResponse(response)) // 3
//                    } catch (e: JSONException) {
//                        e.printStackTrace()
//                    }
//                }, Response.ErrorListener { })
//        queue.add(stringRequest) // 3
//
//    }
//
//    private fun downloadComplete(retrieveRepositoriesFromResponse: Any) {
//
//    }
//
//    private fun retrieveRepositoriesFromResponse(response: String?): Any {
//        this.delegate.interestingEvent()
//    }
//}