package com.example.maya

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    public companion object{
//        private lateinit var contentResolver: ContentResolver

        val permissions = arrayOf("android.Manifest.permission.READ_EXTERNAL_STORAGE")
        val requestCode = 1
        lateinit var song:MutableList<Song_info>
        fun getSongs(context: Context):MutableList<Song_info> {
            val songs= mutableListOf<Song_info>()
            var pos=0
//            val contentResolver: ContentResolver = ContentResolver()
            val uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            val projection= arrayOf(
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
            )
            val cursor=context.contentResolver.query(uri,projection,null,null,MediaStore.Audio.Media.TITLE+" ASC")
            if(cursor!=null)
            {
                try {
                    val idIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
                    val titleIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
                    val artistIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
                    val dataIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                    val albumIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
                    val durationIndex = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
                    while(cursor.moveToNext())
                    {
                        val id = cursor.getLong(idIndex)
                        val title = cursor.getString(titleIndex)
                        val artist = cursor.getString(artistIndex)
                        val path = cursor.getString(dataIndex)
                        val album = cursor.getString(albumIndex)
                        val duration=cursor.getLong(durationIndex)
                        songs.add(Song_info(id,title,artist,path,album,duration,pos++))
                    }
                }
                catch (e: IllegalArgumentException) {
                    Log.e(ContentValues.TAG, "readSongs: ",e )
                }
                cursor.close()

            }
            song=songs
            return songs
        }
        fun getAlbums(context: Context): MutableList<Album_info> {
            val songs= song
            val album= mutableListOf<Album_info>()
            val albumlist= mutableListOf<String>()
            for(song in songs)
            {
                val al=song.album
                if(albumlist.contains(al))
                {
                    continue
                }
                else
                {
                    albumlist.add(al)
                    val slist= mutableListOf<Song_info>()
                    for(s in songs)
                    {


                        if(s.album==al) {
                            slist.add(s)

                        }
                    }
                    album.add(Album_info(al,slist))
                }

            }
            return album;
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomviewnav=findViewById<BottomNavigationView>(R.id.bottom_nav)
        replacewithFragment(Song())

//        ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
//
//         if(Build.VERSION.SDK_INT>=33)

//
        ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        }




        bottomviewnav.setOnItemSelectedListener {
            when(it.itemId)
            {

                R.id.songs->replacewithFragment(Song())
                R.id.albums->replacewithFragment(Album())

                else->{

                }

            }
            true
        }

    }

    fun replacewithFragment(fragment: Fragment) {
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
          fragmentTransaction.commit()
    }
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == 1) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // Read data from external storage
//            } else {
//                // Handle permission denied
//            }
//        }
//    }



}