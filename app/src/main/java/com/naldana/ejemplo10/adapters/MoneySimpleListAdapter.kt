package com.naldana.ejemplo10.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.naldana.ejemplo10.MyMoneyAdapter
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.models.Coins
import kotlinx.android.synthetic.main.list_item_money.view.*

class MoneySimpleListAdapter(var coins:List<Coins>, val clickListener: (Coins) -> Unit): RecyclerView.Adapter<MoneySimpleListAdapter.ViewHolder>(), MyMoneyAdapter{

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_money, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = coins.size

    override fun onBindViewHolder(holder: ViewHolder, pos: Int) =holder.bind(coins[pos], clickListener)

    override fun changeDataSet(newDataSet: List<Coins>) {
        this.coins = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(coin: Coins, clickListener: (Coins) -> Unit) = with(itemView){
            name_list_item.text = coin.name
            country_list_item.text = coin.country
            year_list_item.text = coin.year.toString()
            this.setOnClickListener { clickListener(coin) }
        }
    }
}