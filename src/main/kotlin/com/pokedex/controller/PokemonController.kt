package com.pokedex.controller

import com.pokedex.entity.HighlightedPokemon
import com.pokedex.entity.Pokemon
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
    @RequestParam(defaultValue = "ALPHABETICAL") sort: SortType
  ): PokemonsDTO = service.getPokemons(query, sort)

  @GetMapping("/highlight")
  fun getHighlightedPokemons(
    @RequestParam query: String?,
    @RequestParam(defaultValue = "alphabetical") sort: String
  ): List<HighlightedPokemon> {
    // Dentro do método getHighlightedPokemons
    val pokemons: List<Pokemon> = emptyList()// Faça a chamada à PokéAPI para buscar os Pokémon

    // Implemente a ordenação aqui com base no parâmetro 'sort'

    // Implemente a lógica de destaque para o endpoint /pokemons/highlight

    // Retorne a lista de Pokémon destacados
    return emptyList()
  }
}