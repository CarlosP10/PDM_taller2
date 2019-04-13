package com.naldana.ejemplo10.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.models.Coins
import kotlinx.android.synthetic.main.main_content_fragment_layout.view.*

class MainContentFragment: Fragment() {
    var coin = Coins()

    companion object {
        fun newInstance(coin: Coins): MainContentFragment{
            val newFragment = MainContentFragment()
            newFragment.coin = coin
            return newFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.main_content_fragment_layout, container, false)

        bindData(view)

        return view
    }

    fun bindData(view: View){
        view.coin_title_main_content_fragment.text = coin.name
        view.country_main_content_fragment.text = coin.country
        view.value_main_content_fragment.text = coin.value.toString()
        view.value_us_main_content_fragment.text = coin.value_us.toString()
        view.year_main_content_fragment.text = coin.year.toString()
        view.is_available_main_content_fragment.text = coin.isAvailable
        Glide.with(view).load(coin.img)
            .placeholder(R.drawable.ic_launcher_background)
            .into(view.image_main_content_fragment)
    }

}