package com.interviewtask.app.countrylist.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.interviewtask.app.R
import com.interviewtask.app.countrylist.model.CountryListModel
import com.interviewtask.app.db.CountryListEn
import kotlinx.android.synthetic.main.country_list_item.view.*
import java.nio.file.DirectoryStream
import java.util.*
import kotlin.collections.ArrayList

class CountryListAdapter(var items: ArrayList<CountryListEn>, val context: Activity, val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {
    var countryList = ArrayList<CountryListEn>()
    init{
        countryList = items
    }
    class CountryListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val flag: ImageView = view.flagImage
        val country: TextView = view.countryTv
        val capital: TextView = view.capitalTv
        val parent: RelativeLayout = view.parentItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryListViewHolder {
        return CountryListViewHolder(LayoutInflater.from(context).inflate(R.layout.country_list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.country.text = countryList[position].name
        holder.capital.text = countryList[position].capital
        SvgLoader.pluck()
            .with(context)
            .setPlaceHolder(R.drawable.ic_image, R.drawable.ic_image)
            .load(countryList[position].flag, holder.flag)
        holder.parent.setOnClickListener{
            itemClickListener.onItemClick(countryList[position])
        }
    }

    fun updateList(list: ArrayList<CountryListEn>){
        countryList = list
        this.notifyDataSetChanged()
    }



    interface OnItemClickListener{
        fun onItemClick(countryList: CountryListEn);
    }

}