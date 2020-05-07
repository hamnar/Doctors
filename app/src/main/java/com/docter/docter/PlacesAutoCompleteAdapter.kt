package com.docter.docter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.docter.docter.responseModel.LocationSearchResponseModel
import com.docter.docter.responseModel.SearchedItemList
import com.docter.docter.viewmodel.HomeViewModel

class PlacesAutoCompleteAdapter(mContext: Context, val homeViewModel: HomeViewModel) :
    ArrayAdapter<SearchedItemList>(mContext, R.layout.autocomplete_list_item), Filterable {

    var resultList: ArrayList<SearchedItemList>? = ArrayList()
    private lateinit var searchedLocation: LocationSearchResponseModel

    override fun getCount(): Int {
        return when {
            resultList.isNullOrEmpty() -> 0
            else -> resultList?.size!!
        }
    }

    override fun getItem(position: Int): SearchedItemList? {
        return when {
            resultList.isNullOrEmpty() -> null
            else -> resultList!![position]
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(R.layout.autocomplete_list_item, parent, false)
            viewHolder.description = view?.findViewById(R.id.autocompleteText) as TextView
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val place = resultList!![position]
        bindView(viewHolder, place, position)
        return view
    }

    private fun bindView(viewHolder: ViewHolder, searchedItemList: SearchedItemList, position: Int) {
        if (!resultList.isNullOrEmpty()) {
            if (position != resultList!!.size - 1) {
                viewHolder.description?.text = searchedItemList.location
                viewHolder.description?.visibility = View.VISIBLE
            } else {
                viewHolder.description?.visibility = View.GONE
            }
        }
    }


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged()
                } else {
                    notifyDataSetInvalidated()
                }
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint != null) {
                    searchedLocation = homeViewModel.getSearchedLocation1(constraint.toString())!!
                    resultList?.addAll(searchedLocation.result)
                    resultList?.add(SearchedItemList("-1", "footer"))
                    filterResults.values = resultList
                    filterResults.count = resultList!!.size
                }
                return filterResults
            }
        }
    }

    internal class ViewHolder {
        var description: TextView? = null
    }

}
