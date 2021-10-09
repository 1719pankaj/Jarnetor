package com.example.jarnetor.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.jarnetor.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_on_boarding.view.*


class onBoarding : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        view.id_txt.text = currentUser?.uid
        view.name_txt.text = currentUser?.displayName
        view.email_txt.text = currentUser?.email
        Glide.with(this).load(currentUser?.photoUrl).into(view.profile_image)
        view.sign_out_btn.setOnClickListener {
            try {
                Log.i("TAGGER", mAuth.signOut().toString())
            } catch (e: Exception) {
                Log.i("TAGGER", e.toString())
            }
            findNavController().navigate(R.id.action_onBoarding_to_loginFrag2)
        }
        view.nextOnbBt.setOnClickListener { findNavController().navigate(R.id.action_onBoarding_to_initialFrag2) }

        return view
    }

}