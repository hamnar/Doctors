package com.docter.docter.ui.splash

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.docter.docter.R
import com.docter.docter.databinding.SearchdoctorBynameBinding
import com.docter.docter.responseModel.SearchedItemList
import com.docter.docter.utils.CustomProgressDialog
import com.docter.docter.viewmodel.SearchDoctorByNameViewmodel
import kotlinx.android.synthetic.main.layout_header_nav.*
import kotlinx.android.synthetic.main.searchdoctor_byname.*


class SearchDoctorByName : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {
    lateinit var searchDoctorByNameViewmodel: SearchDoctorByNameViewmodel
    lateinit var searchdoctorBynameBinding: SearchdoctorBynameBinding
    lateinit var searcheditemlist: SearchedItemList
    private lateinit var progressDialog: Dialog  // Progress Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)
        distance_seekbar_byname.setOnSeekBarChangeListener(this)
    }


    private fun setupBindings(savedInstanceState: Bundle?) {
        searchdoctorBynameBinding = DataBindingUtil.setContentView(this, R.layout.searchdoctor_byname)
        searchDoctorByNameViewmodel = ViewModelProvider(this).get(SearchDoctorByNameViewmodel::class.java)
        searchdoctorBynameBinding.searchdoctornameviewmodel = searchDoctorByNameViewmodel
        progressDialog = CustomProgressDialog.progressDialog(this)

        searcheditemlist = (intent.getSerializableExtra("sercheditem") as? SearchedItemList)!!
        back.visibility = View.VISIBLE
        toolbar_title.visibility = View.GONE

        txt_location.visibility = View.VISIBLE
        txt_location.text = searcheditemlist.city
        user_icon.visibility = View.GONE
        initObservers()
    }


    //To initiate all live data observers
    private fun initObservers() {

        searchDoctorByNameViewmodel.enableProgressBar.observe(this, androidx.lifecycle.Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        //this observes list and assign to adapter
        searchDoctorByNameViewmodel.searchByDoctorTypeResponseModel.observe(this, androidx.lifecycle.Observer {

            for (doctorTypeList in it.data!!) {
                println(doctorTypeList)
            }
            startActivity(
                Intent(this, DoctorList::class.java)
                    .putExtra("searchedlocation", searcheditemlist.location)
                    .putExtra("sercheditem", it)
            )
        })
    }


    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        txt_distancemiles_byname.text = String.format(progress.toString() + " " + getString(R.string.miles))
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    fun SearchDoctor(view: View) {
        searcheditemlist.distance = distance_seekbar_byname.progress
        searcheditemlist.name = edittxt_doctorname.text.toString()
        searchDoctorByNameViewmodel.getSearchedDoctorbyName(searcheditemlist)


    }

    fun backpressed(view: View) {
        finish()
    }


}