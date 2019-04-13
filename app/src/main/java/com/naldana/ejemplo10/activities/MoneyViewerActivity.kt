package com.naldana.ejemplo10.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.models.Coins
import kotlinx.android.synthetic.main.viewer_money.*

class MoneyViewerActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewer_money)

        setSupportActionBar(toolbarviewer)
        collapsingtoolbarviewer.setExpandedTitleTextAppearance(R.style.ExpandedAppBar)
        collapsingtoolbarviewer.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)

        val reciever: Coins = intent?.extras?.getParcelable("MOVIE") ?: Coins()
        init(reciever)
    }

    fun init(coin: Coins){
        Glide.with(this)
                .load(coin.img)
                .placeholder(R.drawable.ic_launcher_background)
                .into(app_bar_image_viewer)
        collapsingtoolbarviewer.title = coin.name
        country_viewer.text = coin.country
        plot_viewer.text = coin.review
        year_viewer.text = coin.year.toString()
        genre_viewer.text = coin.value.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {onBackPressed();true}
            else -> super.onOptionsItemSelected(item)
        }
    }
}