package com.example.jarnetor.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.jarnetor.R
import kotlinx.android.synthetic.main.fragment_initial.view.*

class InitialFrag : Fragment() {

    val SHARED_PREF = "shared_pref"

    var yearPos = 0
    var deptPos = 0
    var dept_code = "EE"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_initial, container, false)
        val sharedPreferences = activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)


        view.yearSpinner.setSelection(getPosition(sharedPreferences, "yearPos"))
        view.yearSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                yearPos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        view.deptSpinner.setSelection(getPosition(sharedPreferences, "deptPos"))
        view.deptSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                deptPos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        view.saveInitBt.setOnClickListener {
            saveData(sharedPreferences)
            Toast.makeText(context, "Preference Saved", Toast.LENGTH_SHORT).show()
            view.saveInitBt.visibility = View.GONE
        }

        view.nextInitBt.setOnClickListener {
            saveClass(sharedPreferences)
            findNavController().navigate(R.id.action_initialFrag2_to_subjectFrag)
        }


        return view
    }

    fun saveData(sharedPreferences: SharedPreferences?) {
        val editor = sharedPreferences?.edit()
        editor?.apply {
            putInt("yearPos", yearPos)
            putInt("deptPos", deptPos)
            putString("deapatment", dept_code)
            apply()
        }
    }

    fun getPosition(sharedPreferences: SharedPreferences?, key: String): Int {
        val position = sharedPreferences?.getInt(key, 0)
        if (position == null) {
            return 0
        } else
            return position
    }

    private fun saveClass(sharedPreferences: SharedPreferences?) {
        val itemNames = resources.getStringArray(R.array.department_list)
        val dept = itemNames[getPosition(sharedPreferences, "deptPos")]
        val sem = getPosition(sharedPreferences, "yearPos") + 1
        val collection = "subjects$sem$dept"

        val editor = sharedPreferences?.edit()
        editor?.apply {
            putString("class", collection)
            apply()
        }
    }

}