package com.parkmobile.sample.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.ViewHolder>() {

    private var entries: List<Item> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.item_search_result,
                            parent,
                            false
                    )
            )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]

        (holder.itemView as TextView).text = when (entry) {
            is Item.SearchResult -> entry.searchResult
            else -> "No Results"
        }
    }

    override fun getItemCount(): Int = entries.size

    override fun getItemViewType(position: Int): Int {
        return when (entries[position]) {
            is Item.SearchResult -> TYPE_SEARCH_RESULT
            else -> TYPE_NO_RESULT
        }
    }

    fun updateSearchResults(newSearchResults: List<String>) {
        if (newSearchResults.isNotEmpty()) {
            entries = newSearchResults.fold(mutableListOf()) { newEntries, searchResult ->
                newEntries.add(Item.SearchResult(searchResult))
                newEntries
            }
        } else {
            entries = listOf(Item.NoResult)
        }

        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    companion object {

        private const val TYPE_SEARCH_RESULT = 0

        private const val TYPE_NO_RESULT = 1

    }

    private sealed class Item {

        data class SearchResult(val searchResult: String) : Item()

        object NoResult : Item()

    }

}