package com.interviewtask.app.countrylist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ahmadrosid.svgloader.SvgLoader
import com.google.gson.Gson
import com.interviewtask.app.R
import com.interviewtask.app.databinding.FragmentCountryDetailBinding
import com.interviewtask.app.db.CountryListEn

class CountryDetailFragment : Fragment() {
    private lateinit var binding: FragmentCountryDetailBinding
    var countryVal: String? = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootview = inflater!!.inflate(R.layout.fragment_country_detail, container, false)

        countryVal = arguments?.getString("country_data")
        binding = FragmentCountryDetailBinding.bind(rootview)
        init()
        return binding.root
    }

    private fun init(){
        val countryData = Gson().fromJson(countryVal, CountryListEn::class.java)
        binding.capitalTv.text = countryData.capital
        binding.nameTv.text = countryData.name
        binding.currencyTv.text = countryData.name +" "+ countryData.currencySymbol
        binding.languageTv.text = countryData.language
        binding.populationTv.text = countryData.population.toString()
        SvgLoader.pluck()
            .with(activity)
            .setPlaceHolder(R.drawable.ic_image, R.drawable.ic_image)
            .load(countryData.flag, binding.flagImage)
    }
}