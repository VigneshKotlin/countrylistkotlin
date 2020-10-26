package com.interviewtask.app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CountryListEn::class], version = 1)
abstract  class CountryListDB : RoomDatabase() {
    abstract fun countryListDao(): CountryListDAO
    companion object {
        private var INSTANCE: CountryListDB? = null
        fun getInstance(context: Context): CountryListDB {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    CountryListDB::class.java,
                    "country_db")
                    .build()
            }
            return INSTANCE as CountryListDB
        }
    }
}