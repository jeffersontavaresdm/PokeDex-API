package com.pokedex.entity

import com.fasterxml.jackson.annotation.JsonProperty

data class Pokemon(@JsonProperty("name") val name: String)
data class HighlightedPokemon(@JsonProperty("name") val name: String)
data class Pokemons(@JsonProperty("results") val results: List<Pokemon>)