package com.docter.docter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.docter.docter.R
import com.docter.docter.databinding.DoctorlistitemsBinding
import com.docter.docter.responseModel.Data
import com.docter.docter.responseModel.SearchByDoctorNameResponseModel
import com.docter.docter.ui.splash.DoctorList


class DoctorListAdapter(doctorist: DoctorList, val context: Context?) :
    RecyclerView.Adapter<DoctorListAdapter.DoctorListAdapterViewHolder>() {
    private var doctorList: MutableList<Data> = arrayListOf()
    lateinit var binding: DoctorlistitemsBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListAdapterViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.doctorlistitems, parent, false)
        return DoctorListAdapterViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return doctorList.size
    }

    override fun onBindViewHolder(holder: DoctorListAdapterViewHolder, position: Int) {
        binding.txtDoctorname.text = doctorList.get(position).name
        binding.txtDoctorspecialist.text = doctorList.get(position).speciality?.get(0).toString()
        binding.txtCalldoctor.text = doctorList.get(position).address?.phone
        if (doctorList.get(position).gender?.toString().equals("F"))
            binding.imgDoctorprofile.setImageDrawable(context?.getDrawable(R.drawable.profile_dummy_female))
        else
            binding.imgDoctorprofile.setImageDrawable(context?.getDrawable(R.drawable.profile_dummy_male))

        binding.txtAddress.text = doctorList.get(position).address?.lineAddress
        binding.txtAddress2.text =
            doctorList.get(position).address?.city + "," + doctorList.get(position).address?.state + " " + doctorList.get(
                position
            ).address?.zip
//        binding.txtGetdirection.setOnClickListener(View.OnClickListener {
        //            callGoogleMaps(doctorList.ge)
//        })
    }


    fun setDoctorListdata(
        searchByDoctorNameResponseModel: SearchByDoctorNameResponseModel
    ) {
        if (!searchByDoctorNameResponseModel.data.isNullOrEmpty()) {
            this.doctorList = searchByDoctorNameResponseModel.data?.toMutableList()!!
        } else {
            this.doctorList.clear()
        }
        notifyDataSetChanged()
    }

    var onItemClick: ((pos: Int, view: View) -> Unit)? = null

    inner class DoctorListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        override fun onClick(v: View?) {
            onItemClick?.invoke(adapterPosition, v!!)
        }

        init {
            binding = DataBindingUtil.bind(itemView)!!
            binding.txtGetdirection.setOnClickListener(this)
            binding.txtCalldoctor.setOnClickListener(this)
            binding.doctorlistsclick.setOnClickListener(this)
        }
    }

}
