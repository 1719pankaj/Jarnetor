package com.example.jarnetor.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jarnetor.AddressBook.FirebaseAddress
import com.example.jarnetor.R
import kotlinx.android.synthetic.main.fragment_subfolder.view.*


class SubfolderFrag : Fragment() {

    val SHARED_PREF = "shared_pref"
    lateinit var sharedPreferences : SharedPreferences //TODO(Didn't)
    lateinit var fbAddAdap: FirebaseAddress

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subfolder, container, false)

        sharedPreferences = activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)!!

        fbAddAdap = FirebaseAddress(sharedPreferences)

        view.textView4.text = fbAddAdap.getFirebaseAddress()

        return view
    }

}