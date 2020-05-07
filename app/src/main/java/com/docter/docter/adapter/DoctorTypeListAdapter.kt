/*
 * Created by M1048012 on 23/12/2019
 * Copyright (c) CARTUS B2B 2019 .
 * All rights reserved.
 * Last modified 12/23/19 3:05 PM
 *
 * CHANGE LOG
 * —------------------------------------------------------
 * ID     Bug ID   Author Name     Date         Description
 * —------------------------------------------------------
 */

package com.docter.docter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.docter.docter.R
import com.docter.docter.databinding.DoctertypeBinding
import com.docter.docter.interfaces.DoctorTypeItemClickListener
import com.docter.docter.responseModel.DoctorTypeList

class DoctorTypeListAdapter(
    val doctortypeItemClickListener: DoctorTypeItemClickListener, val context: Context?
) :
    RecyclerView.Adapter<DoctorTypeListAdapter.DoctorTypeListAdapterViewHolder>() {
    private var doctorTypeList: MutableList<DoctorTypeList> = arrayListOf()
    private lateinit var binding: DoctertypeBinding
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DoctorTypeListAdapterViewHolder {

        val itemView =
            LayoutInflater.from(context).inflate(R.layout.doctertype, parent, false)

        return DoctorTypeListAdapterViewHolder(itemView)
    }


    override fun getItemCount(): Int {
        return doctorTypeList.size
    }

    override fun onBindViewHolder(holder: DoctorTypeListAdapterViewHolder, position: Int) {
        binding.txtDoctorlistitem.text = doctorTypeList.get(position).type
        if (position == 0) {
            binding.txtDoctorlistitem.setCompoundDrawablesWithIntrinsicBounds(
                null, context?.getDrawable(R.drawable.adultprimarycare), null, null
            )
        } else if (position == 1) {
            binding.txtDoctorlistitem.setCompoundDrawablesWithIntrinsicBounds(
                null, context?.getDrawable(R.drawable.adultspecialist), null, null
            )
        } else if (position == 2) {
            binding.txtDoctorlistitem.setCompoundDrawablesWithIntrinsicBounds(
                null, context?.getDrawable(R.drawable.childrenprimarycare), null, null
            )
        } else if (position == 3) {
            binding.txtDoctorlistitem.setCompoundDrawablesWithIntrinsicBounds(
                null, context?.getDrawable(R.drawable.childrenspecialist), null, null
            )
        } else if (position == 4) {
            binding.txtDoctorlistitem.setCompoundDrawablesWithIntrinsicBounds(
                null, context?.getDrawable(R.drawable.adolescent), null, null
            )
        }
        binding.txtDoctorlistitem.setOnClickListener {

            doctortypeItemClickListener.onDoctorTypeItemClick(position, it)
        }
    }

    //To set data dynamically
    fun setDoctortypeData(
        listItems: List<DoctorTypeList>
    ) {
        if (!listItems.isNullOrEmpty()) {
            this.doctorTypeList = listItems.toMutableList()
        } else {
            this.doctorTypeList.clear()
        }
        notifyDataSetChanged()
    }

    inner class DoctorTypeListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            binding = DataBindingUtil.bind(itemView)!!
        }
    }
}

