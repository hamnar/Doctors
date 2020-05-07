package com.docter.docter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSpinner
import com.docter.docter.adapter.CustomDropDownAdapter

class SampleSpinnerView : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }

    lateinit var spinner1: AppCompatSpinner
    lateinit var spinner2: AppCompatSpinner
    private lateinit var listItemsTxt: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.samplespinner)
        findviewID()
    }

    private fun findviewID() {
        spinner1 = findViewById(R.id.spinner_subspeciality)
        spinner2 = findViewById(R.id.spinner_speciality)

        listItemsTxt = arrayOf("Select Specialist", "Family Medicine ", "General Practice ", "Internal Medicine")

//        // Create an ArrayAdapter using a simple spinner layout and languages array
//        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, listItemsTxt)
//        // Set layout to use when the list of choices appear
//        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        // Set Adapter to Spinner
        var aa = CustomDropDownAdapter(baseContext!!, listItemsTxt)

        spinner1.adapter = aa
        spinner2.adapter = aa

        spinner2.onItemSelectedListener = this

        spinner1.onItemSelectedListener = this


    }

}
