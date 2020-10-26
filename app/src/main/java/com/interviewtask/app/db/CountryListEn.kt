package com.interviewtask.app.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_list")
data class CountryListEn (
  @PrimaryKey(autoGenerate = true)
  val id: Int,
  @ColumnInfo(name = "name")
  val name: String,
  @ColumnInfo(name = "capital")
  val capital: String,
  @ColumnInfo(name = "currency")
  val currency: String,
  @ColumnInfo(name = "language")
  val language: String,
  @ColumnInfo(name = "flag")
  val flag: String,
  @ColumnInfo(name = "currency_symbol")
  val currencySymbol: String,
  @ColumnInfo(name = "population")
  val population: String)
