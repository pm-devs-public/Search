package com.parkmobile.sample.search

import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.search_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    override fun onResume() {
        super.onResume()
        updateSearchViewWithLatestQuery()
    }

    private fun setupViews() {
        search_edit_text.inputType = InputType.TYPE_NULL

        search_edit_text.setOnClickListener {
            showSearchScreen()
        }
    }

    private fun showSearchScreen() {
        startActivity(SearchActivity.createStartIntent(this))
    }

    private fun updateSearchViewWithLatestQuery() {
        search_edit_text.setText(SearchService.latestQuery)
    }

}