package com.parkmobile.sample.search

import kotlin.math.max

object SearchService {

    var latestQuery: String = ""

    fun search(query: String): List<String> {
        latestQuery = query

        val numberOfResults = max(0, 10 - query.length)

        return when {
            numberOfResults > 0 ->
                (0..numberOfResults).fold(mutableListOf()) { searchResults, _ ->
                    searchResults.add(query)
                    searchResults
                }
            else -> listOf()
        }
    }

}