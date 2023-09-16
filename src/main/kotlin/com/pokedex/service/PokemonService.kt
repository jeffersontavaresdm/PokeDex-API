package com.pokedex.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.pokedex.entity.Pokemons
import com.pokedex.entity.dto.PokemonsDTO
import com.pokedex.enums.SortType
import com.pokedex.util.sortPokemons
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class PokemonService(private val restTemplate: RestTemplate) {
  @Value("\${baseUrl}")
  private val baseUrl = ""

  private val logger = LoggerFactory.getLogger(this::class.java)
  private val objectMapper = ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  fun getPokemons(query: String?, sort: SortType): PokemonsDTO {
    val response = restTemplate.getForEntity("$baseUrl?limit=100", String::class.java)

    if (!response.statusCode.is2xxSuccessful) {
      logger.info("There was an error when trying to search for pokemons")
      return PokemonsDTO(emptyList())
    }

    return getPokemons(response, query, sort)
  }

  private fun getPokemons(response: ResponseEntity<String>, query: String?, sort: SortType): PokemonsDTO {
    val pokemons = objectMapper.readValue(response.body, Pokemons::class.java)
    val sortedPokemons = sortPokemons(sort, pokemons.results)
    val pokemonNames = sortedPokemons.map { pokemon -> pokemon.name }

    return if (query.isNullOrEmpty()) PokemonsDTO(pokemonNames)
    else PokemonsDTO(pokemonNames.filter { pokemonName -> pokemonName.contains(query, ignoreCase = true) })
  }
}