package com.pokedex.util

import com.pokedex.entity.Pokemon
import com.pokedex.enums.SortType

val sortPokemons = { sort: SortType, pokemons: List<Pokemon> ->
  if (pokemons.size >= 2) {
    val sortedPokemons = pokemons.toMutableList()

    for (i in 1 until sortedPokemons.size) {
      val currentPokemon = sortedPokemons[i]
      val insertionPosition = findPosition(sort, i, currentPokemon, sortedPokemons)

      sortedPokemons[insertionPosition + 1] = currentPokemon
    }

    sortedPokemons
  } else pokemons
}

private fun findPosition(sort: SortType, i: Int, currentPokemon: Pokemon, sortedPokemons: MutableList<Pokemon>): Int {
  var insertionPosition = i - 1

  while (shouldInsertBefore(sort, insertionPosition, currentPokemon, sortedPokemons)) {
    sortedPokemons[insertionPosition + 1] = sortedPokemons[insertionPosition]
    insertionPosition = insertionPosition.dec()
  }

  return insertionPosition
}

private fun shouldInsertBefore(
  sort: SortType,
  insertionPosition: Int,
  currentPokemon: Pokemon,
  sortedPokemons: MutableList<Pokemon>
) = when (sort) {
  SortType.ALPHABETICAL -> insertionPosition >= 0 && currentPokemon.name < sortedPokemons[insertionPosition].name
  SortType.LENGTH -> insertionPosition >= 0 && currentPokemon.name.length < sortedPokemons[insertionPosition].name.length
}