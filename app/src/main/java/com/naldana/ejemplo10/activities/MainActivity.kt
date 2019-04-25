package com.naldana.ejemplo10.activities

import android.content.Intent
import android.content.res.Configuration
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import com.naldana.ejemplo10.AppConstants
import com.naldana.ejemplo10.Network.NetworkUtils
import com.naldana.ejemplo10.R
import com.naldana.ejemplo10.adapters.MoneyAdapter
import com.naldana.ejemplo10.fragments.MainContentFragment
import com.naldana.ejemplo10.fragments.MainListFragment
import com.naldana.ejemplo10.models.Coins
import com.naldana.ejemplo10.models.infoAllCoin
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainListFragment.SearchNewMovieListener {
    private lateinit var mainFragment : MainListFragment
    private lateinit var mainContentFragment: MainContentFragment
    private var moneyList = ArrayList<Coins>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.money_list_fragment)
        moneyList = savedInstanceState?.getParcelableArrayList(AppConstants.dataset_saveinstance_key) ?: ArrayList()

        initMainFragment()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(AppConstants.dataset_saveinstance_key, moneyList)
        super.onSaveInstanceState(outState)
    }

    /*private fun coinItemClicked(item: Coins) {
        val coinBundle = Bundle()
        coinBundle.putParcelable("COIN", item)
        startActivity(Intent(this, MoneyViewerActivity::class.java).putExtras(coinBundle))
    }

    private lateinit var viewAdapter: MoneyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    fun initRecycler(coins : ArrayList<Coins>) {

        //viewManager = LinearLayoutManager(this)
        if(this.resources.configuration.orientation == 2
            || this.resources.configuration.orientation == 4){
            viewManager = LinearLayoutManager(this)
        } else{
            viewManager = GridLayoutManager(this, 2)
        }

        viewAdapter = MoneyAdapter(coins, { coinItem: Coins -> coinItemClicked(coinItem) })

    }*/

    fun initMainFragment(){
        mainFragment = MainListFragment.newInstance(moneyList)

        val resource = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            R.id.main_fragment
        else {
            mainContentFragment = MainContentFragment.newInstance(Coins())
            changeFragment(R.id.land_main_cont_fragment, mainContentFragment)

            R.id.land_main_fragment
        }

        changeFragment(resource, mainFragment)
    }

    fun addMovieToList(movie: Coins) {
        moneyList.add(movie)
        mainFragment.updateMoviesAdapter(moneyList)
        Log.d("Number", moneyList.size.toString())
    }

    override fun searchMovie(movieName: String) {
        FetchMovie().execute(movieName)
    }

    override fun managePortraitItemClick(movie: Coins) {
        val movieBundle = Bundle()
        movieBundle.putParcelable("MOVIE", movie)
        startActivity(Intent(this, MoneyViewerActivity::class.java).putExtras(movieBundle))
    }

    private fun changeFragment(id: Int, frag: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(id, frag)
            .commit() }

    override fun manageLandscapeItemClick(movie: Coins) {
        mainContentFragment = MainContentFragment.newInstance(movie)
        changeFragment(R.id.land_main_cont_fragment, mainContentFragment)
    }

    /*private fun coinItemClicked(item: Coins) {
        val coinBundle = Bundle()
        coinBundle.putParcelable("COIN", item)
        startActivity(Intent(this, MoneyViewerActivity::class.java).putExtras(coinBundle))
    }

    private lateinit var viewAdapter: MoneyAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    fun initRecycler(coins : ArrayList<Coins>) {

        //viewManager = LinearLayoutManager(this)
        if(this.resources.configuration.orientation == 2
            || this.resources.configuration.orientation == 4){
            viewManager = LinearLayoutManager(this)
        } else{
            viewManager = GridLayoutManager(this, 2)
        }

        viewAdapter = MoneyAdapter(coins, { coinItem: Coins -> coinItemClicked(coinItem) })

        recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }*/

    private var lista : ArrayList<Coins> = ArrayList<Coins>()

    private inner class FetchMovie : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String): String {

            if (params.isNullOrEmpty()) return ""

            val movieName = params[0]
            val movieUrl = NetworkUtils().buildtSearchUrl(movieName)

            try {
                var result = NetworkUtils().getResponseFromHttpUrl(movieUrl)
                var gson : Gson = Gson()
                var element : infoAllCoin = gson.fromJson(result, infoAllCoin::class.java)
                for (i in 0 .. (element.datos.size-1)){
                    var dato = Coins(element.datos.get(i).name.toString(),element.datos.get(i).country.toString(), element.datos.get(i).value,
                        element.datos.get(i).value_us,
                        element.datos.get(i).year, element.datos.get(i).review, element.datos.get(i).isAvailable, element.datos.get(i).img)
                    lista.add(dato)
                }
                return result
            } catch (e: IOException) {
                e.printStackTrace()
                return ""
            }
        }

        override fun onPostExecute(movieInfo: String) {
            super.onPostExecute(movieInfo)
            if (!movieInfo.isEmpty()) {
                val movieJson = JSONObject(movieInfo)
                if (movieJson.getString("Response") == "True") {
                    val movie = Gson().fromJson<Coins>(movieInfo, Coins::class.java)
                    addMovieToList(movie)
                } else {
                    Toast.makeText(this@MainActivity, "No existe en la base de datos,", Toast.LENGTH_LONG).show()
                }
            }else
            {
                Toast.makeText(this@MainActivity, "A ocurrido un error,", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun searchForCountry(country : String){
        var listaS : ArrayList<Coins> = ArrayList<Coins>()
        for (i in 0 .. (lista.size-1)){
            if(lista.get(i).country.equals(country)){
                listaS.add(lista.get(i))
            }
            //initRecycler(listaS)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            // TODO (14.3) Los Id solo los que estan escritos en el archivo de MENU
            R.id.nav_all -> {
                //initRecycler(lista)
            }
            R.id.nav_sv -> {
                searchForCountry("El Salvador")
            }
            R.id.nav_mx -> {
                searchForCountry("Mexico")
            }
            R.id.nav_usa -> {
                searchForCountry("USA")
            }
            R.id.nav_vn -> {
                searchForCountry("Venezuela")
            }
            R.id.nav_gt -> {
                searchForCountry("Guatemala")
            }
        }

        // TODO (15) Cuando se da click a un opcion del menu se cierra de manera automatica
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
