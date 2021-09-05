package com.example.jarnetor.Fragments

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jarnetor.Adapters.SubjectAdapter
import com.example.jarnetor.AddressBook.FirebaseAddress
import com.example.jarnetor.AddressBook.FirebaseAddress.firebaseAddress
import com.example.jarnetor.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_subject.view.*
import kotlinx.android.synthetic.main.new_sub_dialog.view.*


class SubjectFrag : Fragment(), SubjectAdapter.SubjectItemClicked {

    lateinit var mAdapter: SubjectAdapter
    val SHARED_PREF = "shared_pref"
    lateinit var sharedPreferences : SharedPreferences //TODO(Didn't barf)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_subject, container, false)
        sharedPreferences = activity?.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)!!
        firebaseAddress = getClass(sharedPreferences)

        val manager = LinearLayoutManager(context)
        view.subjectRecycler.layoutManager = manager
        mAdapter = SubjectAdapter(requireActivity(), requireContext(), this)
        view.subjectRecycler.adapter = mAdapter
        fetchSubs()
        view.subSwipeContainer.setOnRefreshListener {
            fetchSubs()
            view.subSwipeContainer.setRefreshing(false)
        }

        view.initFragBt.setOnClickListener { findNavController().navigate(R.id.action_subjectFrag_to_initialFrag2) }

        view.addSubFab.setOnClickListener { AddSub() }

        return view
    }

    private fun fetchSubs() {

        mAdapter.clear()
        getSubFirebase()
        mAdapter.notifyDataSetChanged()
    }

    private fun getSubFirebase() {

        val db = FirebaseFirestore.getInstance()

        val collection = getClass(sharedPreferences)

        Log.i("TAGGER", collection)

        db.collection(collection)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAGGER", "${document.id} => ${document.data}")
                    val subject = document.id
                    mAdapter.appendSubject(subject)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAGGER", "Error getting documents: ", exception)
            }


    }

    fun UpdateSub() {
        var subName: String
        var subCode: String

        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.new_sub_dialog, null)


        with(builder) {

            setPositiveButton("OK") { dialog, which ->
                subName = dialogLayout.subNameEt.text.toString()
                subCode = dialogLayout.subCodeEt.text.toString()

                setSubFirebase(subName, subCode)

                Toast.makeText(context, "Subject was added", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel"){dialog, which ->
                Toast.makeText(context, "No subjects were added", Toast.LENGTH_SHORT).show()
            }
            setView(dialogLayout)
            show()


        }
    }


    fun AddSub() {
        var subName: String
        var subCode: String

        val builder = AlertDialog.Builder(context)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.new_sub_dialog, null)


        with(builder) {

            setPositiveButton("OK") { dialog, which ->
                subName = dialogLayout.subNameEt.text.toString()
                subCode = dialogLayout.subCodeEt.text.toString()

                setSubFirebase(subName, subCode)

                Toast.makeText(context, "Subject was added", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("Cancel"){dialog, which ->
                Toast.makeText(context, "No subjects were added", Toast.LENGTH_SHORT).show()
            }
            setView(dialogLayout)
            show()


        }

    }




    private fun setSubFirebase(subName: String, subCode: String) {

        val db = FirebaseFirestore.getInstance()

        val collection = getClass(sharedPreferences)

        val metadata = hashMapOf(
            "subName" to subName,
            "subCode" to subCode
        )

        val lab = hashMapOf(
            "lab1" to "url",
            "lab1Thumb" to "url"
        )

        val termPaper = hashMapOf(
            "TP1" to "url",
            "TP1Thumb" to "url"
        )

        val material = hashMapOf(
            "material1" to "url",
            "material1Thumb" to "url"
        )

        val misc = hashMapOf(
            "misc1" to "url",
            "misc1Thumb" to "url"
        )

        val placeholder = hashMapOf(
            "placeholder" to "url"
        )

        firestoreCreate(db, collection, subName, subCode, metadata, "Metadata", "Metadata 1")
        firestoreCreate(db, collection, subName, subCode, lab, "Lab", "Lab 1")
        firestoreCreate(db, collection, subName, subCode, termPaper, "TermPaper", "TermPaper 1")
        firestoreCreate(db, collection, subName, subCode, material, "Study Material", "Material 1")
        firestoreCreate(db, collection, subName, subCode, misc, "Misc", "Misc 1")


        db.collection(collection)
            .document("$subName - $subCode")
            .set(placeholder)
            .addOnSuccessListener { Log.d("TAGGER", "Dummy placeholder Written!") }
            .addOnFailureListener { e -> Log.w("TAGGER", "Dummy writing failed", e) }

        fetchSubs()
    }

    private fun firestoreCreate(
        db: FirebaseFirestore,
        collection: String,
        subName: String,
        subCode: String,
        metadata: HashMap<String, String>,
        collectionName: String,
        docName: String
    ) {

        db.collection(collection)
            .document("$subName - $subCode")
            .collection(collectionName)
            .document(docName)
            .set(metadata)
            .addOnSuccessListener { Log.d("TAGGER", "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w("TAGGER", "Error writing document", e) }
    }


    fun getClass(sharedPreferences: SharedPreferences): String {
        val collection = sharedPreferences.getString("class", "null")
        if(collection == null)
            return "carp"
        else
            return collection
    }


    override fun onItemClicked(subName: String) {

        firebaseAddress += "${getClass(sharedPreferences)}/$subName"
        Log.i("TAGGER", firebaseAddress)

        Toast.makeText(context, subName, Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_subjectFrag_to_subfolderFrag)
    }

}