package com.parkmobile.sample.search

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import kotlinx.android.synthetic.main.search_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (REQUEST_SEARCH == requestCode && data != null) {
            updateSearchViewWithResultData(data)
        }
    }

    private fun setupViews() {
        search_edit_text.inputType = InputType.TYPE_NULL

        search_edit_text.setOnClickListener {
            showSearchScreen()
        }
    }

    private fun showSearchScreen() {
        startActivityForResult(
            SearchActivity.createStartIntent(
                this,
                search_edit_text.text.toString()
            ),
            REQUEST_SEARCH,
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                Pair(search_card_view, "search")
            ).toBundle()
        )
    }

    private fun updateSearchViewWithResultData(data: Intent) {
        val query = data.getStringExtra(SearchActivity.EXTRA_QUERY)
        search_edit_text.setText(query)
    }

    companion object {

        private const val REQUEST_SEARCH = 1

    }

}