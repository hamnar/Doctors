package com.docter.docter.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.docter.docter.HomeView
import com.docter.docter.R
import com.docter.docter.responseModel.Data
import com.docter.docter.viewmodel.DoctorDetailListViewmodel
import kotlinx.android.synthetic.main.layout_header_nav.*

class DoctorListDetail : AppCompatActivity() {
    lateinit var doctorDetailListViewmodel: DoctorDetailListViewmodel
    lateinit var doctorprofiledetailBinding: com.docter.docter.databinding.DoctorprofiledetailBinding
    private lateinit var pNumber: String
    private lateinit var data: Data
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)

    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        doctorprofiledetailBinding = DataBindingUtil.setContentView(this, R.layout.doctorprofiledetail)
        doctorDetailListViewmodel = ViewModelProvider(this).get(DoctorDetailListViewmodel::class.java)
        doctorprofiledetailBinding.doctordetaillistviewmodel = doctorDetailListViewmodel

        setViewText()
        getIntentData((intent.getSerializableExtra("sercheditem") as? Data))


        user_icon.setOnClickListener(View.OnClickListener {
            finish()
            startActivity(
                Intent(this, HomeView::class.java)
            )
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            user_icon.setBackgroundDrawable(getDrawable(R.drawable.home_icon))
        }

    }

    private fun getIntentData(datatxt: Data?) {
        data = datatxt!!
        if (data.gender.equals("M")) {
            doctorprofiledetailBinding.imgDoctorprofile.setImageDrawable(getDrawable(R.drawable.profile_dummy_male))
            doctorprofiledetailBinding.valueGender.text = getString(R.string.male)
        } else {
            doctorprofiledetailBinding.imgDoctorprofile.setImageDrawable(getDrawable(R.drawable.profile_dummy_female))
            doctorprofiledetailBinding.valueGender.text = getString(R.string.female)
        }

        doctorprofiledetailBinding.valueSpeciality.text = data.speciality?.get(1)
        doctorprofiledetailBinding.txtProfilecalldoctor.text = data.address?.phone.toString()
        doctorprofiledetailBinding.txtProfiledoctorname.text = data.name.toString()
        doctorprofiledetailBinding.valueOfficelocation.text =
            data.address?.lineAddress.toString() + "\n" + data.address?.city.toString() + "\n" +
                    data.address?.state.toString() +" \n" +
                    data.address?.zip.toString()

    }

    fun phoneNumber(view: View) {
        callNumber(data.address?.phone.toString())
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

    fun googleMap(view: View) {
        val lat = data.address?.geoLocation?.lat
        val lng = data.address?.geoLocation?.lon
        callGoogleMaps(lat, lng)
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


    private fun setViewText() {
        back.visibility = View.VISIBLE
        toolbar_title.setText(getString(R.string.doctorprofile))
        user_icon.visibility = View.VISIBLE
        user_icon.setImageResource(R.drawable.home_icon)
    }


    override fun onStart() {
        super.onStart()
    }

    fun backpressed(view: View) {
        finish()
    }

}