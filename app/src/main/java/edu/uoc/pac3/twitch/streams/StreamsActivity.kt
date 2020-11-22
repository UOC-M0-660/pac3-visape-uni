package edu.uoc.pac3.twitch.streams

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.uoc.pac3.R
import edu.uoc.pac3.data.SessionManager
import edu.uoc.pac3.data.TwitchApiService
import edu.uoc.pac3.data.network.Network
import edu.uoc.pac3.data.oauth.OAuthConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StreamsActivity : AppCompatActivity() {

    private val TAG = "StreamsActivity"

    private lateinit var adapter: StreamsListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_streams)
        // Init RecyclerView
        initRecyclerView()
        // TODO: Get Streams
        CoroutineScope(Dispatchers.Main).launch {
            TwitchApiService(Network.createHttpClient(applicationContext))
                    .getStreams(adapter.getPagination()?.cursor, SessionManager(applicationContext).getAccessToken()).let { streamsResponse ->
                        if (streamsResponse != null) {
                            streamsResponse.data?.let { adapter.addStreams(it) }
                            streamsResponse.pagination?.let { adapter.setPagination(it) }
                        }
            }
        }
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = StreamsListAdapter(ArrayList())
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var loading = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val pastVisibleItems = (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                    if(!loading && (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        Log.d(TAG, "More Streams")
                        loading = true
                        CoroutineScope(Dispatchers.Main).launch {
                            TwitchApiService(Network.createHttpClient(applicationContext))
                                .getStreams(adapter.getPagination()?.cursor, SessionManager(applicationContext).getAccessToken()).let { streamsResponse ->
                                    if (streamsResponse != null) {
                                        Log.d(TAG, "STREAMS CARGADOS")
                                        streamsResponse.data?.let { adapter.addStreams(it) }
                                        streamsResponse.pagination?.let { adapter.setPagination(it) }
                                        loading = false
                                    }
                                }
                        }
                    }
                }
            }
        })
    }

}