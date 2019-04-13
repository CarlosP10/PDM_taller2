package com.naldana.ejemplo10.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naldana.ejemplo10.AppConstants
import com.naldana.ejemplo10.MyMoneyAdapter
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.adapters.MoneyAdapter
import com.naldana.ejemplo10.adapters.MoneySimpleListAdapter
import com.naldana.ejemplo10.models.Coins
import kotlinx.android.synthetic.main.money_list_fragment.view.*

class MainListFragment : Fragment() {

    private lateinit var  moneys :ArrayList<Coins>
    private lateinit var moneyAdapter : MyMoneyAdapter
    var listenerTool :  SearchNewMovieListener? = null

    companion object {
        fun newInstance(dataset : ArrayList<Coins>): MainListFragment{
            val newFragment = MainListFragment()
            newFragment.moneys = dataset
            return newFragment
        }
    }

    interface SearchNewMovieListener{
        fun searchMovie(movieName: String)

        fun managePortraitItemClick(movie: Coins)

        fun manageLandscapeItemClick(movie: Coins)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.money_list_fragment, container, false)

        if(savedInstanceState != null) moneys = savedInstanceState.getParcelableArrayList<Coins>(AppConstants.MAIN_LIST_KEY)!!

        initRecyclerView(resources.configuration.orientation, view)
        initSearchButton(view)

        return view
    }

    fun initRecyclerView(orientation:Int, container:View){
        val linearLayoutManager = LinearLayoutManager(this.context)

        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            moneyAdapter = MoneyAdapter(moneys, { coin:Coins->listenerTool?.managePortraitItemClick(coin)})
            container.rv_main.adapter = moneyAdapter as MoneyAdapter
        }
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            moneyAdapter = MoneySimpleListAdapter(moneys, { coin:Coins->listenerTool?.manageLandscapeItemClick(coin)})
            container.rv_main.adapter = moneyAdapter as MoneySimpleListAdapter
        }

        container.rv_main.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
        }
    }
//TODO REvisar esta parte -------------------------
    fun initSearchButton(container:View) = container.main_ll.setOnClickListener {container}

    fun updateMoviesAdapter(moneyList: ArrayList<Coins>){ moneyAdapter.changeDataSet(moneyList) }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is SearchNewMovieListener) {
            listenerTool = context
        } else {
            throw RuntimeException("Se necesita una implementación de  la interfaz")
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.MAIN_LIST_KEY, moneys)
        super.onSaveInstanceState(outState)
    }

    override fun onDetach() {
        super.onDetach()
        listenerTool = null
    }
}
