package com.maxdexter.myrecipe


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.maxdexter.myrecipe.View.bottomsheet.BottomsheetFragment
import com.maxdexter.myrecipe.View.detail.DetailFragment
import com.maxdexter.myrecipe.View.notelist.NoteListFragment
import com.maxdexter.myrecipe.database.AppDatabase
import com.maxdexter.myrecipe.databinding.ActivityMainBinding
import com.maxdexter.myrecipe.repository.NoteRepository
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(bottom_app_bar)
        val fab = binding.fab
        fab.setOnClickListener { supportFragmentManager.beginTransaction().add(R.id.navHostFragment,DetailFragment.newInstance()).addToBackStack("detailNote").commit() }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.app_bar_search -> Toast.makeText(this, "clicked search", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> Toast.makeText(this, "clicked settings", Toast.LENGTH_SHORT).show()
            android.R.id.home -> {val bottomsheetFragment = BottomsheetFragment()
                bottomsheetFragment.show(supportFragmentManager,"BottomSheetTAG")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}