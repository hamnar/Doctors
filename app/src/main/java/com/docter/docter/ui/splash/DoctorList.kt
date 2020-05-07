package com.docter.docter.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.docter.docter.HomeView
import com.docter.docter.R
import com.docter.docter.adapter.DoctorListAdapter
import com.docter.docter.responseModel.SearchByDoctorNameResponseModel
import com.docter.docter.viewmodel.DoctorListViewmodel
import kotlinx.android.synthetic.main.doctorlist.*
import kotlinx.android.synthetic.main.layout_header_nav.*


class DoctorList : AppCompatActivity() {
    lateinit var doctorListViewmodel: DoctorListViewmodel
    lateinit var doctorlistBinding: com.docter.docter.databinding.DoctorlistBinding
    private lateinit var searchByDoctornameResponse: SearchByDoctorNameResponseModel
    private lateinit var pNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)

    }


    private fun setupBindings(savedInstanceState: Bundle?) {
        doctorlistBinding = DataBindingUtil.setContentView(this, R.layout.doctorlist)
        doctorListViewmodel = ViewModelProvider(this).get(DoctorListViewmodel::class.java)
        doctorlistBinding.doctorlistviewmodel = doctorListViewmodel
        back.visibility = View.VISIBLE
        toolbar_title.text = getString(R.string.doctors)
        user_icon.visibility = View.VISIBLE
        user_icon.setImageResource(R.drawable.home_icon)
        user_icon.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(
                Intent(this, HomeView::class.java)
            )
        })
        searchByDoctornameResponse = (intent.getSerializableExtra("sercheditem") as? SearchByDoctorNameResponseModel)!!
        txt_doctorfound.text =
            searchByDoctornameResponse.data?.size.toString() + "Doctors Found in " + " " + intent.getStringExtra(
                "searchedlocation"
            )
        doctorlistBinding.doctorlists.run {
            adapter = DoctorListAdapter(this@DoctorList, context)
            (adapter as DoctorListAdapter).onItemClick = { pos, view ->
                val Address = searchByDoctornameResponse.data?.get(pos)?.address
                if (view.id == R.id.txt_calldoctor) {
                    callNumber(Address?.phone.toString())
                } else if (view.id == R.id.txt_getdirection) {
                    val lat = Address?.geoLocation?.lat
                    val lng = Address?.geoLocation?.lon
                    callGoogleMaps(lat, lng)
                } else if (view.id == R.id.doctorlistsclick) {
                    startActivity(
                        Intent(this@DoctorList, DoctorListDetail::class.java).putExtra(
                            "sercheditem",
                            searchByDoctornameResponse.data?.get(pos)
                        )
                    )
                }

            }
            (doctorlistBinding.doctorlists.adapter as DoctorListAdapter).setDoctorListdata(searchByDoctornameResponse)

        }
    }


    private fun callNumber(phoneTo: String) {
        pNumber = phoneTo
        if (checkPermission(android.Manifest.permission.CALL_PHONE)) {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneTo")))
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 0)
        }
    }

    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun callGoogleMaps(lat: Double?, lng: Double?) {
        val strUri = "http://maps.google.com/maps?q=loc:$lat,$lng (Label which you want)"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strUri))

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")

        startActivity(intent)

    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$pNumber")))
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun backpressed(view: View) {
        finish()
    }
}