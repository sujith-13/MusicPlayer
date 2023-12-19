package com.example.maya

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.maya.MainActivity.Companion.getAlbums
import com.example.maya.MainActivity.Companion.getSongs
import com.example.maya.R.layout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Album.newInstance] factory method to
 * create an instance of this fragment.
 */
class Album : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(layout.fragment_album, container, false)
        if(ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(
                requireContext() as Activity,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), MainActivity.requestCode
            )

        }
        val songs=getSongs(requireContext())
//        val album= mutableListOf<Album_info>()
//        for(song in songs)
//        {
//            val al=song.album
//            val slist= mutableListOf<Song_info>()
//            for(s in songs)
//            {
//
//                if(s.album==al) {
//                    slist.add(s)
//                }
//            }
//            album.add(Album_info(al,slist))
//        }
        val album= getAlbums(requireContext())

        recyclerView=view.findViewById(R.id.albumrecyclerview)
        recyclerView.layoutManager=GridLayoutManager(requireContext(),3)
        val adapter=AlbumAdapter(requireContext(),album)
        recyclerView.adapter=adapter
        adapter.setItemListener(object :AlbumAdapter.onClickListener{
            override fun onClicking(position: Int) {

                 val intent= Intent(requireContext(),AlbumSongList::class.java)
                intent.putExtra("albumposition",position)

                startActivity(intent)
            }
        })



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Album.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Album().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}