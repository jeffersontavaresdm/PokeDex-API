package com.pokedex.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class PokedexApiConfig {
  @Bean
  fun restTemplate(): RestTemplate {
    return RestTemplate()
  }
}