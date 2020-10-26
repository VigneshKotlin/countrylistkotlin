package com.interviewtask.app.db

import androidx.room.RoomDatabase

abstract  class CountryListDB : RoomDatabase() {
    abstract fun countryListDao(): CountryListDAO
}