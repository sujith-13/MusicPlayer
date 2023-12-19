package com.example.maya

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class AlbumAdapter(val context: Context, var album: MutableList<Album_info>):
    RecyclerView.Adapter<AlbumAdapter.MyViewHolder>()
{

    private lateinit var Listener:onClickListener
    interface onClickListener{
        fun onClicking(position: Int)
    }
    fun setItemListener(Listener:onClickListener)
    {
        this.Listener=Listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.albumgrid,parent,false)
        return MyViewHolder(view,Listener)
    }

    override fun getItemCount(): Int {
        return album.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val song=album[position].songs[0]
        holder.title.text=album[position].albumname
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
               // val bit=Bitmap.createScaledBitmap(bitmap,65,65,false)
                holder.image.setImageBitmap(bitmap)}
            catch (e:Exception)
            {
            }
        }
        else{
            holder.image.setImageResource(R.drawable.maya)

        }
//

    }

    class MyViewHolder(itemView:View, listener: onClickListener):RecyclerView.ViewHolder(itemView){
        val title: TextView =itemView.findViewById<TextView>(R.id.albumtitle)
        val image: ShapeableImageView =itemView.findViewById<ShapeableImageView>(R.id.albumposter)
        init {
            itemView.setOnClickListener {
                listener.onClicking(adapterPosition)

            }
        }
    }

}