package com.example.jarnetor.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jarnetor.data.AddressBook.FirebaseAddress.firebaseAddress
import com.example.jarnetor.R
import kotlinx.android.synthetic.main.fragment_subfolder.view.*


class SubfolderFrag : Fragment() {

    val SHARED_PREF = "shared_pref"
    lateinit var sharedPreferences : SharedPreferences //TODO(Didn't)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subfolder, container, false)

        sharedPreferences = activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)!!

        view.textView4.text = firebaseAddress

        return view
    }

    override fun onDestroy() {
        super.onDestroy()

        for(i in firebaseAddress.length..0) {
            if(firebaseAddress.last().equals("/")) {
                firebaseAddress.dropLast(1)
                break
            } else {
                firebaseAddress.dropLast(1)
            }
        }

        Log.i("TAGGER", firebaseAddress)
        Log.i("Lifecycler", "Destroy")
    }


}