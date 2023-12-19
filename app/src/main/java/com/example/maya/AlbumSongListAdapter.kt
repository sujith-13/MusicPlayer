package com.example.maya

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class AlbumSongListAdapter(val context: Context, var songs:List<Song_info>):
    RecyclerView.Adapter<AlbumSongListAdapter.MyViewHolder>()
{

    private lateinit var Listener:onSongItemClickListener
    interface onSongItemClickListener{
        fun onClicking(position: Int)
    }
    fun setItemClickListenr1(Listener:onSongItemClickListener)
    {
        this.Listener=Listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.song_row,parent,false)
        return MyViewHolder(view,Listener)
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val song=songs[position]
        holder.title.text=songs[position].title
        holder.artist.text=songs[position].artist
        val retriever=MediaMetadataRetriever()
        try {
            retriever.setDataSource(song.path)
        } catch (e: Exception) {
        }
        val albumArt=retriever.embeddedPicture
        if(albumArt!=null)
        {
            try{
                val bitmap = BitmapFactory.decodeByteArray(albumArt, 0, albumArt.size)
                val bit=Bitmap.createScaledBitmap(bitmap,65,65,false)
                holder.image.setImageBitmap(bitmap)
            }

            catch (e:Exception)
            {
            }
        }
        else{
            holder.image.setImageResource(R.drawable.maya)


        }
//

    }

    class MyViewHolder(itemView:View,listener: onSongItemClickListener):RecyclerView.ViewHolder(itemView){
        val title: TextView =itemView.findViewById<TextView>(R.id.songname)
        val artist: TextView =itemView.findViewById<TextView>(R.id.artistname)
        val image: ShapeableImageView =itemView.findViewById<ShapeableImageView>(R.id.poster)

        init {
            itemView.setOnClickListener {
                listener.onClicking(adapterPosition)

            }
        }
    }

}