package com.pokedex.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.pokedex.entity.Pokemon
import com.pokedex.entity.Pokemons
import com.pokedex.entity.dto.PokemonsDTO
import com.pokedex.enums.SortType
import com.pokedex.util.highlightSubstringInPokemons
import com.pokedex.util.sortPokemons
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PokemonService(private val restTemplate: RestTemplate) {
  @Value("\${baseUrl}")
  private val baseUrl = ""

  private val logger = LoggerFactory.getLogger(this::class.java)
  private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  fun getPokemons(query: String?, sort: SortType, limit: Int): PokemonsDTO {
    val response = restTemplate.getForEntity("$baseUrl?limit=${limit}", String::class.java)

    if (!response.statusCode.is2xxSuccessful) {
      logger.info("There was an error when trying to search for pokemons")
      return PokemonsDTO(emptyList())
    }

    val pokemons = objectMapper.readValue(response.body, Pokemons::class.java)
    val filteredPokemons = filterPokemonsByQuery(pokemons.results, query)
    val sortedPokemonNames = sortPokemons(sort, filteredPokemons)

    return PokemonsDTO(sortedPokemonNames)
  }

  fun getHightLightedPokemon(query: String?, sort: SortType, limit: Int): PokemonsDTO {
    val pokemons = getPokemons(query, sort, limit)
    val highlightedPokemons = highlightSubstringInPokemons(pokemons.pokemonNames, query)

    return PokemonsDTO(highlightedPokemons)
  }

  private fun filterPokemonsByQuery(pokemons: List<Pokemon>, query: String?): List<Pokemon> {
    return if (query.isNullOrEmpty()) {
      pokemons
    } else {
      pokemons.filter { pokemon -> pokemon.name.contains(query, ignoreCase = true) }
    }
  }
}