package com.pokedex.cache

import com.pokedex.entity.dto.PokemonsDTO

val cache = mutableMapOf<String, PokemonsDTO>()

private const val MAX_CACHE_SIZE = 1000

val clearCache = {
  cache.clear()
}

val isCacheTooLarge = {
  cache.size >= MAX_CACHE_SIZE
}