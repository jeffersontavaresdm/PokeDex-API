package com.pokedex.controller

import com.pokedex.entity.dto.PokemonsDTO
import com.pokedex.enums.SortType
import com.pokedex.service.PokemonService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pokemons")
class PokemonController(private val service: PokemonService) {

  @GetMapping
  fun getPokemons(
    @RequestParam query: String?,
    @RequestParam(defaultValue = "ALPHABETICAL") sort: SortType,
    @RequestParam(defaultValue = "1500") limit: Int
  ): PokemonsDTO = service.getPokemons(query, sort, limit)

  @GetMapping("/highlight")
  fun getHighlightedPokemons(
    @RequestParam query: String?,
    @RequestParam(defaultValue = "ALPHABETICAL") sort: SortType,
    @RequestParam(defaultValue = "1500") limit: Int
  ): PokemonsDTO = service.getHightLightedPokemon(query, sort, limit)
}