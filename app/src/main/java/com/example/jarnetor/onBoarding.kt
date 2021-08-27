package com.example.jarnetor

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class onBoarding : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_on_boarding, container, false)

//        view.onBoardingTV.setOnClickListener{ findNavController().navigate(R.id.action_onBoarding_to_loginFragment) }

        return view
    }

}