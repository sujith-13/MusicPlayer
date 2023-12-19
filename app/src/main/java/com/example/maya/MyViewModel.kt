package com.example.maya

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel :ViewModel(){

//    private val _artist = MutableLiveData<String>()
//    private val _title = MutableLiveData<String>()
//    private val _path = MutableLiveData<String>()
     private var _song=MutableLiveData<Song_info>()
      val song: LiveData<Song_info> = _song

    fun setSongInfo(song:Song_info) {
           _song.value=song
    }

}