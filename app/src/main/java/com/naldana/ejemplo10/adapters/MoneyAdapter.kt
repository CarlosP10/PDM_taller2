package com.naldana.ejemplo10.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.MyMoneyAdapter
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.models.Coins
import kotlinx.android.synthetic.main.linear_layout_money.view.*

class MoneyAdapter(var coins: List<Coins>, val clickListener: (Coins) -> Unit) :RecyclerView.Adapter<MoneyAdapter.ViewHolder>(), MyMoneyAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.linear_layout_money, parent, false)
        return ViewHolder(view)
    }
    override fun getItemCount() = coins.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(coins[position], clickListener)

    override fun changeDataSet(newDataSet: List<Coins>) {
        this.coins = newDataSet
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: Coins, clickListener: (Coins) -> Unit) = with(itemView){
            Glide.with(itemView.context)
                .load(item.img)
                .placeholder(R.drawable.ic_launcher_background)
                .into(item_list_content_ImageView_img)

            item_list_content_TextView_name.text = item.name
            this.setOnClickListener { clickListener(item) }
        }
    }
}