package com.pokedex.cache

import com.pokedex.entity.dto.PokemonsDTO
import com.pokedex.cache.cache
import com.pokedex.cache.clearCache
import com.pokedex.cache.isCacheTooLarge
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CacheTest {

  @BeforeEach
  fun setUp() {
    clearCache()
  }

  @Test
  fun testClearCache() {
    cache["key1"] = PokemonsDTO(listOf("pokemon1"))
    cache["key2"] = PokemonsDTO(listOf("pokemon2"))

    clearCache()

    assertTrue(cache.isEmpty())
  }

  @Test
  fun testIsCacheTooLarge() {
    assertFalse(isCacheTooLarge())

    repeat(1001) { index ->
      cache["key$index"] = PokemonsDTO(listOf("pokemon$index"))
    }

    assertTrue(isCacheTooLarge())
  }
}