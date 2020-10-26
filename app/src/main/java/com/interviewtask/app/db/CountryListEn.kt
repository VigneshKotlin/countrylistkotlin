package com.interviewtask.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "country_list")
data class CountryListEn (
  @PrimaryKey
  var id: Int,
  @ColumnInfo(name = "name")
  var name: String,
  @ColumnInfo(name = "capital")
  var capital: String,
  @ColumnInfo(name = "currency")
  var currency: String,
  @ColumnInfo(name = "language")
  var language: String,
  @ColumnInfo(name = "flag")
  var flag: String,
  @ColumnInfo(name = "currency_symbol")
  var currencySymbol: String ?= null,
  @ColumnInfo(name = "population")
  var population: String)