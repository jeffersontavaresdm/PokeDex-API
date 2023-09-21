package com.pokedex.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.pokedex.entity.Pokemon
import com.pokedex.entity.Pokemons
import com.pokedex.entity.dto.PokemonsDTO
import com.pokedex.enums.SortType
import com.pokedex.cache.cache
import com.pokedex.cache.clearCache
import com.pokedex.cache.isCacheTooLarge
import com.pokedex.util.highlightSubstringInPokemons
import com.pokedex.util.timsort
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

  fun getPokemons(query: String?, sortType: SortType, limit: Int): PokemonsDTO {
    val cacheKey = "$query-$sortType-$limit"

    if (cache.containsKey(cacheKey)) return cache[cacheKey]!!

    if (isCacheTooLarge()) {
      clearCache()
    }

    val response = restTemplate.getForEntity("$baseUrl?limit=${limit}", String::class.java)

    if (!response.statusCode.is2xxSuccessful) {
      logger.info("There was an error when trying to search for pokémons")
      return PokemonsDTO(emptyList())
    }

    val pokemons = objectMapper.readValue(response.body, Pokemons::class.java)
    val filteredPokemons = filterPokemonsByQuery(pokemons.results, query)
    val pokemonsMutableList = filteredPokemons.toMutableList()

    timsort(pokemonsMutableList, sortType)

    val result = PokemonsDTO(pokemonsMutableList.map { pokemon -> pokemon.name })

    cache[cacheKey] = result

    return result
  }

  fun getHightLightedPokemon(query: String?, sort: SortType, limit: Int): PokemonsDTO {
    val pokemons = getPokemons(query, sort, limit)
    val highlightedPokemons = highlightSubstringInPokemons(pokemons.pokemonNames, query)

    return PokemonsDTO(highlightedPokemons)
  }

  private fun getPokemonsFromPokeAPI(limit: Int) = restTemplate.getForEntity("$baseUrl?limit=${limit}", String::class.java)

  private fun filterPokemonsByQuery(pokemons: List<Pokemon>, query: String?): List<Pokemon> {
    return if (query.isNullOrEmpty()) {
      pokemons
    } else {
      pokemons.filter { pokemon -> pokemon.name.contains(query, ignoreCase = true) }
    }
  }
}