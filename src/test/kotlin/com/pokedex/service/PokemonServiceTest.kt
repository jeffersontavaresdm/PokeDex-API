package com.pokedex.service

import com.pokedex.enums.SortType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

class PokemonServiceTests {

  private lateinit var pokemonService: PokemonService
  private val restTemplate = Mockito.mock(RestTemplate::class.java)

  @BeforeEach
  fun setUp() {
    pokemonService = PokemonService(restTemplate)
  }

  @Test
  fun testGetPokemons() {
    val apiResponse = """
            {
                "count": 3,
                "results": [
                    {"name": "Ivysaur"},
                    {"name": "Bulbasaur"},
                    {"name": "Venusaur"}
                ]
            }
        """

    val responseEntity = ResponseEntity(apiResponse, HttpStatus.OK)
    Mockito.`when`(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String::class.java))).thenReturn(responseEntity)

    val result = pokemonService.getPokemons(null, SortType.ALPHABETICAL, 3)

    assertEquals(listOf("Bulbasaur", "Ivysaur", "Venusaur"), result.pokemonNames)
  }

  @Test
  fun testGetHightLightedPokemon() {
    val apiResponse = """
            {
                "count": 3,
                "results": [
                    {"name": "Ivysaur"},
                    {"name": "Bulbasaur"},
                    {"name": "Venusaur"}
                ]
            }
        """

    val responseEntity = ResponseEntity(apiResponse, HttpStatus.OK)
    Mockito.`when`(restTemplate.getForEntity(Mockito.anyString(), Mockito.eq(String::class.java))).thenReturn(responseEntity)

    val result = pokemonService.getHightLightedPokemon("Bul", SortType.ALPHABETICAL, 3)

    assertEquals(listOf("<pre>Bul</pre>basaur"), result.pokemonNames)
  }
}