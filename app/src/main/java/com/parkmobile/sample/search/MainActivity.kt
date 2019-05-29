package com.parkmobile.sample.search

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.panel_search_results.*
import kotlinx.android.synthetic.main.search_view.*

class MainActivity : AppCompatActivity() {

    private val bottomSheetBehavior: BottomSheetBehavior<View> by lazy {
        BottomSheetBehavior.from(findViewById<View>(R.id.search_panel))
    }

    private val searchResultsAdapter: SearchResultsAdapter = SearchResultsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSearchView()
        disableSearchView()
        setupBottomSheet()
        setupSearchResultsRecyclerView()
    }

    override fun onBackPressed() {
        if (isBottomSheetExpanded()) {
            hideBottomSheet()
        } else {
            super.onBackPressed()
        }
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.setBottomSheetCallback(
            object : BottomSheetBehavior.BottomSheetCallback() {

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    // No-op.
                }

                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when (bottomSheetBehavior.state) {
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            enableSearchView()
                            requestFocusOnSearchView()
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            disableSearchView()
                            clearFocusOnSearchView()
                            hideSoftKeyboard(this@MainActivity, search_edit_text)
                        }
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

    private fun isBottomSheetExpanded(): Boolean =
        bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED

    private fun setupSearchView() {
        search_edit_text.setSelection(search_edit_text.text.length)
        search_edit_text.addTextChangedListener(object : TextWatcherAdapter() {

            override fun afterTextChanged(s: Editable?) {
                searchResultsAdapter.updateSearchResults(SearchService.search(s.toString()))
            }

        })
    }

    private fun enableSearchView() {
        search_edit_text.inputType = InputType.TYPE_CLASS_TEXT
    }

    private fun disableSearchView() {
        search_edit_text.inputType = InputType.TYPE_NULL
        search_edit_text.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                showBottomSheet()
                showSoftKeyboard(this, search_edit_text)
            }
            false
        }
    }

    private fun requestFocusOnSearchView() {
        search_edit_text.requestFocus()
    }

    private fun clearFocusOnSearchView() {
        search_edit_text.clearFocus()
    }

    private fun setupSearchResultsRecyclerView() {
        search_results_recycler_view.adapter = searchResultsAdapter
    }

}