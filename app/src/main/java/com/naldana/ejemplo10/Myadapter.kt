package com.naldana.ejemplo10

import com.naldana.ejemplo10.models.Coins

object AppConstants{
    val dataset_saveinstance_key = "CLE"
    val MAIN_LIST_KEY = "key_list_movies"
}

interface MyMoneyAdapter {
    fun changeDataSet(newDataSet : List<Coins>)
}