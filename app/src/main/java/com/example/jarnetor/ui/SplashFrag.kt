package com.example.jarnetor.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jarnetor.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFrag : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        //If already loggd in, send to initial frag or send to login
        lifecycleScope.launch(Dispatchers.Main) {
            delay(2000)
            if (user != null) {
                findNavController().navigate(R.id.action_splashFrag_to_subjectFrag)
            } else {
                findNavController().navigate(R.id.action_splashFrag_to_loginFrag2)
            }
        }

        return view
    }

}