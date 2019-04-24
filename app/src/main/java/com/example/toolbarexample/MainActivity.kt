package com.example.toolbarexample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.SearchView
import android.widget.Toast
import android.support.v4.view.MenuItemCompat.getActionView
import android.util.Log
import android.support.v7.widget.ShareActionProvider


class MainActivity : AppCompatActivity() {

    var toolbar:Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)

        val btnIr = findViewById<Button>(R.id.btnIr)
        btnIr.setOnClickListener {
            val intent = Intent(this, PantallaDos::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val itemBusqueda = menu?.findItem(R.id.busqueda)
        var vistaBusqueda = itemBusqueda?.actionView as SearchView

        val itemShare = menu?.findItem(R.id.share)
        val shareActionProvider = MenuItemCompat.getActionProvider(itemShare) as ShareActionProvider
        compartirIntent(shareActionProvider)

        vistaBusqueda.queryHint = "Escribe tu nombre..."

        vistaBusqueda.setOnQueryTextFocusChangeListener { v, hasFocus ->
            Log.d("LISTENERFOCUS", hasFocus.toString())
        }

        vistaBusqueda.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("OnQueryTextChange", newText)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("OnQueryTextSubmit", query)
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.btnFav -> {
                Toast.makeText(this, "Elemento añadido como favorito", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun compartirIntent(shareActionProvider: ShareActionProvider) {
        if(shareActionProvider != null) {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, "Este es un mensaje compartido")
            shareActionProvider.setShareIntent(intent)
        }
    }
}
