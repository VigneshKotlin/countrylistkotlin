package com.interviewtask.app.countrylist.adapter

import CountryListRes
import android.app.Activity
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.ahmadrosid.svgloader.SvgLoader
import com.interviewtask.app.R
import com.interviewtask.app.db.CountryListEn
import kotlinx.android.synthetic.main.country_list_item.view.*

class CountryListAdapter(var items: List<CountryListRes>, val context: Activity, val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<CountryListAdapter.CountryListViewHolder>() {

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
        return items.size
    }

    override fun onBindViewHolder(holder: CountryListViewHolder, position: Int) {
        holder.country.text = items[position].name
        holder.capital.text = items[position].capital
        SvgLoader.pluck()
            .with(context)
            .setPlaceHolder(R.drawable.ic_image, R.drawable.ic_image)
            .load(items[position].flag, holder.flag)
        holder.parent.setOnClickListener{
            itemClickListener.onItemClick(items[position])
        }
    }

    fun updateList(list: List<CountryListRes>){
        items = list
        notifyDataSetChanged()
    }
    interface OnItemClickListener{
        fun onItemClick(countryList: CountryListRes);
    }
}