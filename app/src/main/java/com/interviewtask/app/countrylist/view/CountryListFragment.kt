package com.interviewtask.app.countrylist.view

import CountryListRes
import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.ArrayMap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.Gson
import com.interviewtask.app.R
import com.interviewtask.app.countrylist.Communicator
import com.interviewtask.app.countrylist.adapter.CountryListAdapter
import com.interviewtask.app.countrylist.viewmodel.CountryListVM
import com.interviewtask.app.databinding.FragmentCountryListBinding
import com.interviewtask.app.countrylist.view.HomeParentActivity
import com.interviewtask.app.db.CountryListDB
import com.interviewtask.app.db.CountryListEn

class CountryListFragment : Fragment(), CountryListAdapter.OnItemClickListener {
    private lateinit var binding: FragmentCountryListBinding
    private lateinit var countryListVM: CountryListVM
    private lateinit var countryList: List<CountryListRes>
    //private lateinit var db: CountryListDB
    private lateinit var adapter: CountryListAdapter
    lateinit var comm: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootview = inflater!!.inflate(R.layout.fragment_country_list, container, false)
        binding = FragmentCountryListBinding.bind(rootview)
        init()
        return binding.root
    }

    private fun init(){

        countryListVM = ViewModelProviders.of(this).get(CountryListVM::class.java)
        //db = Room.databaseBuilder(activity!!.applicationContext, CountryListDB::class.java, "CountyDB").build()
        //callCountryService()
        val progressDialog = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
        callAPI(progressDialog)
        binding.recyclerView!!.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        countryListVM.response.observe(this,
            Observer<List<CountryListRes>> {
               // Log.e("Fragment", ">>"+ Gson().toJson(it))
                progressDialog.dismiss()
                countryList = it
                adapter = CountryListAdapter(countryList, this.requireActivity(), this)
                binding.recyclerView!!.adapter = adapter

            })



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                searchFilter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
    fun searchFilter(searchVal: String){
        var tempArr: List<CountryListRes> = ArrayList()
        if(!searchVal.isEmpty()){
            countryList.forEach(){
                if(it.name.toLowerCase().contains(searchVal)) {
                    tempArr += it
                }
            }
            adapter.updateList(tempArr)
        }else{
            adapter.updateList(countryList)
        }
    }

    override fun onItemClick(countryList: CountryListRes) {
        comm = activity as Communicator
        comm.passDataCom(Gson().toJson(countryList))
    }

    /*private fun loadLocalDB(countryArr: List<CountryListRes>){
        //var countryListEn = CountryListEn(1,

        val thread = Thread{
           countryArr.forEach(){
               var countryListEn = CountryListEn(1,
                   it.name,
                   it.capital,
                   it.currencies[0].name,
                   it.languages[0].name,
                   it.flag,
                   it.currencies[0].symbol,
                   it.population.toString())
               db.countryListDao().insertCountryList(countryListEn)
           }
        }
        thread.start()
        var countryList = db.countryListDao().getCountryList()
        binding.recyclerView!!.adapter = CountryListAdapter(countryList, this.requireActivity())
    }

    private fun callCountryService(){
        var countryList = db.countryListDao().getCountryList()
        if(countryList.isNotEmpty()){
            binding.recyclerView!!.adapter = CountryListAdapter(countryList, this.requireActivity())
        }else{
            countryListVM.countryListAPICall()
        }
    }*/

    fun callAPI(progressDialog: ProgressDialog){
        progressDialog.setTitle(getString(R.string.app_name))
        progressDialog.setMessage("Loading, please wait")
        progressDialog.show()
        countryListVM.countryListAPICall()
    }
}