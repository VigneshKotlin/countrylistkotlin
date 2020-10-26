package com.interviewtask.app.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CountryListDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCountryList(countryListEn: CountryListEn)

    @Query("select * from country_list")
    fun getCountryList(): List<CountryListEn>
}