package com.docter.docter.ui.splash

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.cartus.broker2broker.utils.CommonUtils
import com.docter.docter.R
import com.docter.docter.main.MainApplication
import com.docter.docter.utils.CustomProgressDialog
import com.docter.docter.utils.toast
import com.docter.docter.viewmodel.StateWiseViewmodel
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class Statewise : AppCompatActivity() {
    lateinit var stateWiseViewmodel: StateWiseViewmodel
    lateinit var statewiseBinding: com.docter.docter.databinding.StatewiseBinding
    private lateinit var progressDialog: Dialog  // Progress Dialog
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)

    }


    private fun setupBindings(savedInstanceState: Bundle?) {
        statewiseBinding = DataBindingUtil.setContentView(this, R.layout.statewise)
        stateWiseViewmodel = ViewModelProvider(this).get(StateWiseViewmodel::class.java)
        progressDialog = CustomProgressDialog.progressDialog(this)
        initObservers()
//        statewiseBinding.run {
//            adapter = DoctorTypeListAdapter(this@Statewise, context)
//            addItemDecoration(
//                RecyclerCustomDecorator(
//                    resources.getDimension(R.dimen.vertical_padding).toInt()
//                )
//            )
//        }
        statewiseBinding.statewisebtn.setOnClickListener {
            if (CommonUtils.checkIfConnectedToInternet(MainApplication.appContext)) {
//                run("https://api.covid19india.org/states_daily.json")
                stateWiseViewmodel.getAllStatewise()
            } else {
                toast(getString(R.string.no_internet_error_message), Toast.LENGTH_SHORT);
            }

        }

    }

    fun run(url: String) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            override fun onResponse(call: Call, response: Response) {
                var str_response = response.body()!!.string()
                //creating json object
                val json_contact: JSONObject = JSONObject(str_response)
                //creating json array
//                var jsonarray_info: JSONArray = json_contact.getJSONArray("info")
//                var i: Int = 0
//                var size: Int = jsonarray_info.length()
//                arrayList_details = ArrayList();
//                for (i in 0..size - 1) {
//                    var json_objectdetail: JSONObject = jsonarray_info.getJSONObject(i)
//                    var model: Model = Model();
//                    model.id = json_objectdetail.getString("id")
//                    model.name = json_objectdetail.getString("name")
//                    model.email = json_objectdetail.getString("email")
//                    arrayList_details.add(model)
//                }
            }
        })
    }

    private fun initObservers() {
        //To initiate all live data observers
        stateWiseViewmodel.enableProgressBar.observe(this, androidx.lifecycle.Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })

        //this observes list and assign to adapter
        stateWiseViewmodel.statewiseSuccess.observe(this, androidx.lifecycle.Observer {

        })

    }

}