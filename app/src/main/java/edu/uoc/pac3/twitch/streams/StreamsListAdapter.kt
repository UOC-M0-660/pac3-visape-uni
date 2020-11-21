package edu.uoc.pac3.twitch.streams

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.uoc.pac3.R
import edu.uoc.pac3.data.streams.Stream

class StreamsListAdapter(private var streams: List<Stream>) : RecyclerView.Adapter<StreamsListAdapter.ViewHolder>() {

    private fun getStream(position: Int): Stream {
        return streams[position]
    }

    fun setStreams(streams: List<Stream>) {
        this.streams = streams
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_stream_list_content, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stream = getStream(position)
        holder.titleView.text = stream.title
        holder.userNameView.text = stream.userName

        val thumbnailUrl =
            stream.thumbnailUrl?.replace("{width}","1280")
                ?.replace("{height}","720")

        Glide.with(holder.thumbnailView.context)
                .load(thumbnailUrl)
                .into(holder.thumbnailView)
    }

    override fun getItemCount(): Int {
        return streams.size
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.title)
        val userNameView: TextView = view.findViewById(R.id.username)
        val thumbnailView: ImageView = view.findViewById(R.id.thumbnail)
    }
}