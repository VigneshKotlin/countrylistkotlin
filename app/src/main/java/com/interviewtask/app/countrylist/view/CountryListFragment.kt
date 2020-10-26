package com.interviewtask.app.countrylist.view

import android.app.ProgressDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.interviewtask.app.R
import com.interviewtask.app.countrylist.Communicator
import com.interviewtask.app.countrylist.adapter.CountryListAdapter
import com.interviewtask.app.countrylist.model.CountryListModel
import com.interviewtask.app.countrylist.model.CountryListResponseModel
import com.interviewtask.app.countrylist.viewmodel.CountryListVM
import com.interviewtask.app.databinding.FragmentCountryListBinding
import com.interviewtask.app.db.CountryListDB
import com.interviewtask.app.db.CountryListEn
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CountryListFragment : Fragment(), CountryListAdapter.OnItemClickListener {
    private lateinit var binding: FragmentCountryListBinding
    private lateinit var countryListVM: CountryListVM
    private lateinit var countryList: List<CountryListEn>
    private lateinit var db: CountryListDB
    private lateinit var adapter: CountryListAdapter
    lateinit var progress: ProgressDialog
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

        db = CountryListDB.getInstance(activity!!.applicationContext)
        progress = ProgressDialog(activity, R.style.AppCompatAlertDialogStyle)
        progress.setTitle(getString(R.string.app_name))
        progress.setMessage("Loading, please wait")
        progress.show()
        callCountryService()
        binding.recyclerView!!.layoutManager = LinearLayoutManager(activity!!.applicationContext)
        countryListVM.response.observe(this,
            Observer<List<CountryListModel>> {
                progress.dismiss()
                loadLocalDB(it)

            })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.searchView.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                searchFilter(s.toString())
                /*adapter.filter.filter(s.toString())*/
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }
    fun searchFilter(searchVal: String){
        var tempArr = ArrayList<CountryListEn>()
        if(!searchVal.isEmpty()){
            countryList.forEach(){
                if(it.name!!.toLowerCase().contains(searchVal)) {
                    tempArr.add(it)
                }
            }
            adapter.updateList(tempArr)
        }else{
            var list = ArrayList<CountryListEn>()
            list.addAll(countryList)
            adapter.updateList(list)
        }
    }

    override fun onItemClick(countryList: CountryListEn) {
        comm = activity as Communicator
        comm.passDataCom(Gson().toJson(countryList))
    }

    private fun loadLocalDB(countryArr: List<CountryListModel>) {

        doAsync {
            var cnt: Int = 0
            countryArr.forEach() {
                var countryListEn = CountryListEn(
                    cnt++,
                    it.name,
                    it.capital,
                    it.currencies[0].name,
                    it.languages[0].name,
                    it.flag,
                    it.currencies[0].symbol,
                    it.population.toString()
                )
                db.countryListDao().insertCountryList(countryListEn)
            }
            cnt = 0
            countryList = db.countryListDao().getCountryList()
            uiThread {
                var list = ArrayList<CountryListEn>()
                list.addAll(countryList)
                adapter = activity?.let { it1 -> CountryListAdapter(list, it1, this@CountryListFragment) }!!
                binding.recyclerView!!.adapter = adapter
            }
        }

    }

    private fun callCountryService() {
        doAsync {
            countryList = db.countryListDao().getCountryList()

            if (countryList.isNotEmpty()) {
                uiThread {
                    progress.dismiss()
                    var list = ArrayList<CountryListEn>()
                    list.addAll(countryList)
                    adapter = activity?.let { it1 -> CountryListAdapter(list, it1, this@CountryListFragment) }!!
                    binding.recyclerView!!.adapter = adapter

                }
            }else{
                countryListVM.countryListAPICall()
            }
        }


    }

}