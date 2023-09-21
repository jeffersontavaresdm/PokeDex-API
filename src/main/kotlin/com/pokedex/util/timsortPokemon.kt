package com.pokedex.util

import com.pokedex.entity.Pokemon
import com.pokedex.enums.SortType
import kotlin.math.min

fun timsort(pokemons: MutableList<Pokemon>, sortType: SortType) {
  val minRun = calculateMinRun(pokemons.size)

  var i = 0
  while (i < pokemons.size) {
    val runSize = min(minRun, pokemons.size - i)
    insertionSort(pokemons, i, i + runSize, sortType)
    i += runSize
  }

  mergeRuns(pokemons, minRun, sortType)
}

private fun calculateMinRun(n: Int): Int {
  var r = 0
  var x = n

  while (x >= 64) {
    r = r or (x and 1)
    x = x shr 1
  }

  return x + r
}

private fun insertionSort(pokemons: MutableList<Pokemon>, start: Int, end: Int, sortType: SortType) {
  for (i in start + 1 until end) {
    val current = pokemons[i]
    var j = i - 1

    while (j >= start && compare(pokemons[j], current, sortType) > 0) {
      pokemons[j + 1] = pokemons[j]
      j--
    }

    pokemons[j + 1] = current
  }
}

private fun compare(p1: Pokemon, p2: Pokemon, sortType: SortType): Int {
  return when (sortType) {
    SortType.ALPHABETICAL -> p1.name.compareTo(p2.name)
    SortType.LENGTH -> p1.name.length.compareTo(p2.name.length)
  }
}

private fun mergeRuns(pokemons: MutableList<Pokemon>, minRun: Int, sortType: SortType) {
  val stack = mutableListOf<Pair<Int, Int>>() // Pilha para rastrear os runs

  var start = 0
  var end = 0

  while (end < pokemons.size) {
    end = min(start + minRun, pokemons.size)
    insertionSort(pokemons, start, end, sortType)

    stack.add(Pair(start, end))

    mergeAdjacentRuns(pokemons, stack, sortType)

    start = end
  }
}

private fun mergeAdjacentRuns(pokemons: MutableList<Pokemon>, stack: MutableList<Pair<Int, Int>>, sortType: SortType) {
  while (stack.size >= 2) {
    val (run1Start, run1End) = stack[stack.size - 2]
    val (run2Start, run2End) = stack[stack.size - 1]

    if (run1End <= run2Start) {
      val mergedRun = mutableListOf<Pokemon>()
      var i = run1Start
      var j = run2Start

      while (i < run1End && j < run2End) {
        if (compare(pokemons[i], pokemons[j], sortType) <= 0) {
          mergedRun.add(pokemons[i])
          i++
        } else {
          mergedRun.add(pokemons[j])
          j++
        }
      }

      while (i < run1End) {
        mergedRun.add(pokemons[i])
        i++
      }

      while (j < run2End) {
        mergedRun.add(pokemons[j])
        j++
      }

      for (k in run1Start until run2End) {
        pokemons[k] = mergedRun[k - run1Start]
      }

      stack.removeAt(stack.size - 1)
      stack.removeAt(stack.size - 1)
      stack.add(Pair(run1Start, run2End))
    } else {
      break
    }
  }
}