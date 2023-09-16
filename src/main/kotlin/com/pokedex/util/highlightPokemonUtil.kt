package com.pokedex.util

import com.pokedex.entity.HighlightedPokemon

fun highlightSubstringInPokemons(pokemonNames: List<String>, query: String?): List<String> {
  return pokemonNames
    .map { pokemonName -> HighlightedPokemon(highlightSubstring(pokemonName, query)) }
    .map { highlightedPokemon -> highlightedPokemon.name }
}

private fun highlightSubstring(name: String, query: String?): String {
  if (query.isNullOrEmpty()) {
    return name
  }

  val lowerName = name.lowercase()
  val lowerQuery = query.lowercase()

  val startIndexes = mutableListOf<Int>()
  var currentIndex = lowerName.indexOf(lowerQuery)

  while (currentIndex != -1) {
    startIndexes.add(currentIndex)
    currentIndex = lowerName.indexOf(lowerQuery, currentIndex + 1)
  }

  if (startIndexes.isEmpty()) {
    return name
  }

  val highlightedBuilder = StringBuilder()
  var lastIndex = 0

  for (startIndex in startIndexes) {
    val endIndex = startIndex + query.length
    highlightedBuilder.append(name.substring(lastIndex, startIndex))
    highlightedBuilder.append("<pre>")
    highlightedBuilder.append(name.substring(startIndex, endIndex))
    highlightedBuilder.append("</pre>")
    lastIndex = endIndex
  }

  highlightedBuilder.append(name.substring(lastIndex))

  return highlightedBuilder.toString()
}