package com.example.jarnetor.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.jarnetor.ui.SubjectFrag
import com.example.jarnetor.R

class SubjectAdapter(val activity: Activity, val context: Context, private val listener: SubjectItemClicked): RecyclerView.Adapter<SubjectAdapter.SubjectAdapterViewHolder>() {

    private var subjects: ArrayList<String> = ArrayList()
    private val subFrag = SubjectFrag ()
    private var pos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        val viewHolder = SubjectAdapterViewHolder(view)

        view.setOnClickListener {
            listener.onItemClicked(subjects[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: SubjectAdapterViewHolder, position: Int) {

        val currentSubject = subjects[position]
//        holder.folderIv
        holder.subNameTv.text = currentSubject
        holder.optionIv.setOnClickListener {
            Toast.makeText(context, currentSubject, Toast.LENGTH_SHORT).show()
        }

    }


    override fun getItemCount(): Int {
        return subjects.size
    }

    fun clear() {
        subjects.clear()
    }

    fun appendSubject(subject: String) {
        subjects.add(subject)
        notifyDataSetChanged()
    }

    inner class SubjectAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var folderIv: ImageView
        var subNameTv: TextView
        var optionIv: ImageView

        init {
            folderIv = itemView.findViewById(R.id.folderIv)
            subNameTv = itemView.findViewById(R.id.subNameTv)
            optionIv = itemView.findViewById(R.id.optionIv)
        }
    }

    interface SubjectItemClicked {
        fun onItemClicked(subName: String)
    }


}