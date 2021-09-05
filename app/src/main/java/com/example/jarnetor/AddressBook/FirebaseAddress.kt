package com.example.jarnetor.AddressBook

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.jarnetor.Fragments.SubjectFrag

class FirebaseAddress(private val sharedPreferences: SharedPreferences) {
    private val subFrag = SubjectFrag ()

    public var firebaseAddress: String = subFrag.getClass(sharedPreferences)

    public fun addDest(dest: String) {
//        firebaseAddress += "/$dest"
        firebaseAddress = "Dolphins"
        Log.i("TAGGER", firebaseAddress)
    }

    public fun popDest() {

    }


    fun getFirebaseAddress(): CharSequence {
        return firebaseAddress
    }

}