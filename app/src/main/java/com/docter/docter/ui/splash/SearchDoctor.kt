package com.docter.docter.ui.splash

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cartus.broker2broker.utils.CommonUtils
import com.docter.docter.R
import com.docter.docter.adapter.DoctorTypeListAdapter
import com.docter.docter.adapter.RecyclerCustomDecorator
import com.docter.docter.databinding.SearchdocterBinding
import com.docter.docter.interfaces.DoctorTypeItemClickListener
import com.docter.docter.main.MainApplication
import com.docter.docter.responseModel.SearchedItemList
import com.docter.docter.utils.CustomProgressDialog
import com.docter.docter.utils.toast
import com.docter.docter.viewmodel.SearchDoctorViewmodel
import kotlinx.android.synthetic.main.layout_header_nav.*
import kotlinx.android.synthetic.main.searchdocter.*


class SearchDoctor : AppCompatActivity(), SeekBar.OnSeekBarChangeListener,
    DoctorTypeItemClickListener {
    var selectedPosition: Int = 100
    lateinit var searchDoctorViewmodel: SearchDoctorViewmodel
    lateinit var searchdocterBinding: SearchdocterBinding
    val specialityItems: MutableList<String> = ArrayList()
    val subSpecialityItems: MutableList<String> = ArrayList()
    val doctorTypeItems: MutableList<String> = ArrayList()
    lateinit var searcheditemlist: SearchedItemList
    private lateinit var progressDialog: Dialog  // Progress Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)

    }


    private fun setupBindings(savedInstanceState: Bundle?) {
        searchdocterBinding = DataBindingUtil.setContentView(this, R.layout.searchdocter)
        searchDoctorViewmodel = ViewModelProvider(this).get(SearchDoctorViewmodel::class.java)
        progressDialog = CustomProgressDialog.progressDialog(this)
        searchdocterBinding.searchdoctor = searchDoctorViewmodel
        initObservers()

        searcheditemlist = (intent.getSerializableExtra("sercheditem") as? SearchedItemList)!!

        back.visibility = View.VISIBLE
        toolbar_title.visibility = View.GONE

        txt_location.visibility = View.VISIBLE
        txt_location.text = searcheditemlist.city
        user_icon.visibility = View.GONE
        if (savedInstanceState == null) {
            searchdocterBinding.recyclerDoctortype.run {
                adapter = DoctorTypeListAdapter(this@SearchDoctor, context)
                addItemDecoration(
                    RecyclerCustomDecorator(
                        resources.getDimension(R.dimen.vertical_padding).toInt()
                    )
                )
            }
            distance_seekbar.setOnSeekBarChangeListener(this)
            if (CommonUtils.checkIfConnectedToInternet(MainApplication.appContext)) {
                searchDoctorViewmodel.getAllDoctorType()
            } else {
                toast(getString(R.string.no_internet_error_message), Toast.LENGTH_SHORT);
            }
        }
    }

    //To initiate all live data observers
    private fun initObservers() {

        searchDoctorViewmodel.enableProgressBar.observe(this, androidx.lifecycle.Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        //this observes list and assign to adapter
        searchDoctorViewmodel.doctorTypeResponseModel.observe(this, androidx.lifecycle.Observer {

            for (doctorTypeList in it.result) {
                doctorTypeItems.add(doctorTypeList.type)
                println(doctorTypeList.type)
            }
            (searchdocterBinding.recyclerDoctortype.adapter as DoctorTypeListAdapter).setDoctortypeData(it.result)
        })
        //this observes list and assign to adapter
        searchDoctorViewmodel.doctorSpecialistResponseModel.observe(this, androidx.lifecycle.Observer {
            for (doctorspecialityList in it.result) {
                specialityItems.add(doctorspecialityList.type)
                println(doctorspecialityList.type)
            }

            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, specialityItems)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Set Adapter to Spinner
            spinner_speciality.adapter = aa
            spinner_speciality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    print("onItemSelected position = $position id = $id")
                    if (CommonUtils.checkIfConnectedToInternet(MainApplication.appContext)) {
                        searchDoctorViewmodel.getAllDoctorSubSpeciality(
                            doctorTypeItems.get(selectedPosition), specialityItems.get(position)
                        )
                    } else {
                        toast(getString(R.string.no_internet_error_message), Toast.LENGTH_SHORT);
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        })
        searchDoctorViewmodel.doctorSubspecialistResponseModel.observe(this, androidx.lifecycle.Observer {
            for (doctorSubspecialityList in it.result) {
                subSpecialityItems.add(doctorSubspecialityList.type)
                println(doctorSubspecialityList.type)
            }
            val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, subSpecialityItems)
            // Set layout to use when the list of choices appear
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner_subspeciality?.adapter = aa
            spinner_subspeciality.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    print(" spinner_subspeciality onItemSelected position = $position id = $id")
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        })
        searchDoctorViewmodel.searchByDoctorTypeResponseModel.observe(this, androidx.lifecycle.Observer {
            for (doctorSubspecialityList in it.data!!) {
                println(doctorSubspecialityList)
            }
            startActivity(
                Intent(this, DoctorList::class.java)
                    .putExtra("searchedlocation", searcheditemlist.location)
                    .putExtra("sercheditem", it)
            )
        })
    }


    override fun onDoctorTypeItemClick(position: Int, itemView: View) {
        selectedPosition = position
        if (CommonUtils.checkIfConnectedToInternet(MainApplication.appContext)) {
            searchDoctorViewmodel.getAllDoctorSpeciality(
                searchDoctorViewmodel.doctorTypeResponseModel.value?.result?.get(position)?.type
            )
        } else {
            toast(getString(R.string.no_internet_error_message), Toast.LENGTH_SHORT);
        }

    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        txt_distancemiles.text = String.format(progress.toString() + " " + getString(R.string.miles))
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
    }

    fun SearchDoctorBySpecialist(view: View) {
        searcheditemlist.speciality = spinner_speciality.selectedItem.toString()
        searcheditemlist.subSpeciality = spinner_subspeciality.selectedItem.toString()
        searcheditemlist.distance = distance_seekbar.progress
        searcheditemlist.type = doctorTypeItems.get(selectedPosition)
        searchDoctorViewmodel.getSearchedDoctorbyType(searcheditemlist)


    }

    fun backpressed(view: View) {
        finish()
    }

}