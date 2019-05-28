package com.parkmobile.sample.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.panel_search_results.*
import kotlinx.android.synthetic.main.search_view.*

class SearchActivity : AppCompatActivity() {

    private val bottomSheetBehavior: BottomSheetBehavior<View> by lazy {
        BottomSheetBehavior.from(findViewById<View>(R.id.search_panel))
    }

    private val searchResultsAdapter: SearchResultsAdapter = SearchResultsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        overridePendingTransition(0, 0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setupSearchView(SearchService.latestQuery)
        setupBottomSheet()
        setupSearchResultsRecyclerView()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus) {
            showBottomSheet()
            requestFocusOnSearchView()
        }
    }

    override fun onBackPressed() {
        hideBottomSheet()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun setupSearchView(query: String) {
        search_edit_text.setText(query)
        search_edit_text.setSelection(search_edit_text.text.length)
        search_edit_text.addTextChangedListener(object : TextWatcherAdapter() {

            override fun afterTextChanged(s: Editable?) {
                searchResultsAdapter.updateSearchResults(SearchService.search(s.toString()))
            }

        })
    }

    private fun requestFocusOnSearchView() {
        search_edit_text.requestFocus()
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // No-op.
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    finish()
                }
            }

        })
    }

    private fun showBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun setupSearchResultsRecyclerView() {
        search_results_recycler_view.adapter = searchResultsAdapter
    }

    companion object {

        fun createStartIntent(context: Context): Intent =
                Intent(context, SearchActivity::class.java)

    }

}