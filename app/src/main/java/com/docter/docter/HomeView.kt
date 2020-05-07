package com.docter.docter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.docter.docter.databinding.HomeBinding
import com.docter.docter.responseModel.SearchedItemList
import com.docter.docter.ui.splash.SearchDoctor
import com.docter.docter.ui.splash.SearchDoctorByName
import com.docter.docter.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.layout_header_nav.*


class HomeView : AppCompatActivity() {
    lateinit var homeViewModel: HomeViewModel
    lateinit var homeBinding: HomeBinding
    lateinit var searcheditemlist: SearchedItemList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBindings(savedInstanceState)

    }

    private fun setupBindings(savedInstanceState: Bundle?) {
        homeBinding = DataBindingUtil.setContentView(this, R.layout.home)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeBinding.homeviewmodel = homeViewModel
        back.visibility = View.VISIBLE
        user_icon.visibility = View.VISIBLE
        //this observes list and assign to adapter
        homeBinding.searchLocation.setAdapter(
            baseContext?.let { PlacesAutoCompleteAdapter(it, homeViewModel) }
        )
        homeBinding.searchLocation.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                searcheditemlist = parent.getItemAtPosition(position) as SearchedItemList
                homeBinding.searchLocation.setText(searcheditemlist.location)
                homeBinding.searchLocation.clearFocus()
            }
    }

    fun findBYDoctor(view: View) {
        startActivity(
            Intent(this, SearchDoctorByName::class.java)
                .putExtra("sercheditem", searcheditemlist)
        )
    }

    fun findBYSpecialist(view: View) {
        startActivity(
            Intent(this, SearchDoctor::class.java)
                .putExtra("sercheditem", searcheditemlist)
        )
    }

}
