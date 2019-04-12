package com.naldana.ejemplo10

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.naldana.ejemplo10.models.Coins
import kotlinx.android.synthetic.main.item_list_content.view.*

class MoneyAdapter(
    private val parentActivity: AppCompatActivity,
    private val values: List<Coins>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<MoneyAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as Coins
            if (twoPane) {
                val fragment = ItemDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.name)
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.country)
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.value.toString())
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.value_us.toString())
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.year.toString())
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.review.toString())
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.isAvailable.toString())
                        putString(ItemDetailFragment.ARG_ITEM_ID, item.img)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_content, fragment)
                    .commit()
            } else {
              /*  val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.name)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.country)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.value)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.value_us)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.year)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.review)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.isAvailable)
                    putExtra(ItemDetailFragment.ARG_ITEM_ID, item.img)
                }
                v.context.startActivity(intent)*/
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.name.text = item.name
        holder.country.text = item.country
        holder.value.text = item.value.toString()
        holder.value_us.text = item.value_us.toString()
        holder.year.text = item.year.toString()
        holder.review.text = item.review
        holder.isAvailable.text = item.isAvailable.toString()

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.item_list_content_TextView_name
        val country: TextView = view.item_list_content_TextView_country
        val value: TextView = view.item_list_content_TextView_value
        val value_us: TextView = view.item_list_content_TextView_value_us
        val year: TextView = view.item_list_content_TextView_year
        val review: TextView = view.item_list_content_TextView_review
        val isAvailable: TextView = view.item_list_content_TextView_isAvailable
        val img: ImageView = view.item_list_content_ImageView_img

    }
}