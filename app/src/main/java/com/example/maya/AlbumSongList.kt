package com.example.maya

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maya.MainActivity.Companion.getAlbums

class AlbumSongList : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_song_list)
        val pos=intent.getIntExtra("albumposition",-1)
        val albums=getAlbums(this)
        val songs=albums[pos].songs
        val img=findViewById<ImageView>(R.id.bigalbumposter)
        val retriever= MediaMetadataRetriever()
        try {
            retriever.setDataSource(songs[0].path)
        } catch (e: Exception) {
        }
        val albumArt=retriever.embeddedPicture
        if(albumArt!=null)
        {
            try{
                val bitmap = BitmapFactory.decodeByteArray(albumArt, 0, albumArt.size)
                val bit= Bitmap.createScaledBitmap(bitmap,65,65,false)
                img.setImageBitmap(bitmap)
            }

            catch (e:Exception)
            {
            }
        }
        else{
            img.setImageResource(R.drawable.maya)


        }
        recyclerView=findViewById(R.id.albumlistrv)
        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter=AlbumSongListAdapter(this,songs)
        recyclerView.adapter=adapter
        adapter.setItemClickListenr1(object : AlbumSongListAdapter.onSongItemClickListener{
            override fun onClicking(position: Int) {
                MyMediaPlayer.getInstance().reset()
                MyMediaPlayer.pos=songs[position].position
                val intent= Intent(this@AlbumSongList,MusicPlayer::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        })

    }
}