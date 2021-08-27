package com.example.jarnetor

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_on_boarding.view.*


class onBoarding : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        //If already loggd in, send to initial frag or send to login
        Handler().postDelayed({
            if (user != null) {
                findNavController().navigate(R.id.action_onBoarding_to_initialFrag)
            } else {
                findNavController().navigate(R.id.action_onBoarding_to_loginFrag)
            }
        },2000)

        return view
    }

}