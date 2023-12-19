package com.example.maya

import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.maya.MainActivity.Companion.getSongs
import java.io.IOException

class MusicPlayer : AppCompatActivity() {
    var musicService:MusicService?=null
    lateinit var imgView: ImageView
    lateinit var artist: TextView
    lateinit var title:TextView
    lateinit var shuffle:ImageButton
    lateinit var previous: ImageView

    lateinit var next:ImageView
    lateinit var repeat:ImageButton
    lateinit var playpause:ImageButton
    lateinit var uri: Uri
    lateinit var seekBar: SeekBar
    private val handler = Handler()
    lateinit var start:TextView
    lateinit var end:TextView
    lateinit var cursong:Song_info
    lateinit var songs:MutableList<Song_info>
    var position=MyMediaPlayer.pos
    val mediaPlayer=MyMediaPlayer.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)
        imgView=findViewById<ImageView>(R.id.imageView)
        artist=findViewById<TextView>(R.id.artist)
        title=findViewById<TextView>(R.id.title)
        shuffle=findViewById<ImageButton>(R.id.shuffle)
        previous=findViewById<ImageView>(R.id.previous)

        next=findViewById<ImageView>(R.id.next)
        repeat=findViewById<ImageButton>(R.id.repeat)
        seekBar=findViewById(R.id.seekBar)
       playpause=findViewById(R.id.playpause)
        start=findViewById(R.id.start)
        end=findViewById(R.id.end)
      songs= getSongs(this@MusicPlayer)
        setResources()
        this.runOnUiThread(object :Runnable {
            override fun run() {
                if(mediaPlayer!=null)
                {
                    seekBar.progress=mediaPlayer.currentPosition
                    start.text=formatduration(mediaPlayer.currentPosition.toLong())
                    if(mediaPlayer.isPlaying)
                    {
                        playpause.setImageResource(R.drawable.baseline_pause_circle_filled_24)
                    }
                    else{
                        playpause.setImageResource(R.drawable.playbtn)
                    }
                }
                  handler.postDelayed(this,1000)
            }
        })

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if(p2)
                {
                    mediaPlayer.seekTo(p1)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })



    }

    private fun setResources() {

        cursong=songs[MyMediaPlayer.pos]
        title.text=cursong.title
        artist.text=cursong.artist
        end.text=formatduration(cursong.duration)
        next.setOnClickListener {
            playNext()
        }
        previous.setOnClickListener {
            playPrev()
        }
        playpause.setOnClickListener {


            playpause()
        }
        repeat.setOnClickListener {
            loopSong()
        }

        playMusic()
        setAlbumArt()
    }

    private fun loopSong() {
        if(mediaPlayer.isLooping)
        {
            repeat.setImageResource(R.drawable.repeat_one)
            mediaPlayer.setLooping(false)
        }
        else{
            repeat.setImageResource(R.drawable.baseline_repeat_one_on_24)
            mediaPlayer.setLooping(true)
        }
    }

    private fun setAlbumArt() {
        val retriever=MediaMetadataRetriever()
        retriever.setDataSource(songs[MyMediaPlayer.pos].path)
        val artwork=retriever.embeddedPicture
        if(artwork!=null)
        {
            val bitmap=BitmapFactory.decodeByteArray(artwork,0,artwork.size)
            imgView.setImageBitmap(bitmap)
        }
        else
        {
            imgView.setImageResource(R.drawable.maya)
        }
    }

    private fun playMusic()
    {

        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(songs[MyMediaPlayer.pos].path)
            mediaPlayer.prepare()
            mediaPlayer.start()
            seekBar.progress=0
            seekBar.max=mediaPlayer.duration
        }
        catch (e : IOException)
        {
            e.printStackTrace()
        }

    }
    private fun playNext()
    {
        if(MyMediaPlayer.pos==songs.size-1)
        {
            Toast.makeText(this,"Reached End of List",Toast.LENGTH_SHORT).show()
        }
        //MyMediaPlayer.pos+=1
        MyMediaPlayer.pos=if(mediaPlayer.isLooping) MyMediaPlayer.pos else MyMediaPlayer.pos-1
        mediaPlayer.reset()
        setResources()

    }
    private fun playPrev()
    {
        if(MyMediaPlayer.pos==0)
        {
            Toast.makeText(this,"Reached End of List",Toast.LENGTH_SHORT).show()
        }
//        MyMediaPlayer.pos-=1
        MyMediaPlayer.pos=if(mediaPlayer.isLooping) MyMediaPlayer.pos else MyMediaPlayer.pos-1
        mediaPlayer.reset()
        setResources()

    }
    private fun playpause()
    {
       if(mediaPlayer.isPlaying)
       {

           mediaPlayer.pause()
       }
        else
       {

           mediaPlayer.start()
       }
    }

    private fun formatduration(duration: Long): String {
        val durationSecs=duration/1000
        val mins=durationSecs/60
        val secs=durationSecs%60
        return String.format("%02d:%02d",mins,secs)
    }


}