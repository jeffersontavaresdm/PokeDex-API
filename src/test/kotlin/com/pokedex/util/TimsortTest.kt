package com.pokedex.util

import com.pokedex.entity.Pokemon
import com.pokedex.enums.SortType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

class TimsortTest {

  @Test
  fun testTimsortAlphabeticalSorting() {
    val pokemons = mutableListOf(
      Pokemon("Charizard"),
      Pokemon("Bulbasaur"),
      Pokemon("Squirtle"),
      Pokemon("Pikachu"),
      Pokemon("Eevee")
    )

    timsort(pokemons, SortType.ALPHABETICAL)

    val expected = listOf(
      Pokemon("Bulbasaur"),
      Pokemon("Charizard"),
      Pokemon("Eevee"),
      Pokemon("Pikachu"),
      Pokemon("Squirtle")
    )

    assertEquals(expected, pokemons)
  }

  @Test
  fun testTimsortLengthSorting() {
    val pokemons = mutableListOf(
      Pokemon("Charizard"),
      Pokemon("Bulbasaur"),
      Pokemon("Squirtle"),
      Pokemon("Pikachu"),
      Pokemon("Eevee")
    )

    timsort(pokemons, SortType.LENGTH)

    val expected = listOf(
      Pokemon("Eevee"),
      Pokemon("Pikachu"),
      Pokemon("Squirtle"),
      Pokemon("Charizard"),
      Pokemon("Bulbasaur")
    )

    assertEquals(expected, pokemons)
  }

  @Test
  fun testTimsortEmptyList() {
    val pokemons = mutableListOf<Pokemon>()

    timsort(pokemons, SortType.ALPHABETICAL)

    val expected = emptyList<Pokemon>()

    assertEquals(expected, pokemons)
  }

  @Test
  fun testTimsortSinglePokemon() {
    val pokemons = mutableListOf(Pokemon("Pikachu"))

    timsort(pokemons, SortType.ALPHABETICAL)

    val expected = listOf(Pokemon("Pikachu"))

    assertEquals(expected, pokemons)
  }
}