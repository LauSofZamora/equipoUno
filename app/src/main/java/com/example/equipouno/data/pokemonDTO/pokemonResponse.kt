package com.example.equipouno.data.pokemonDTO

import com.google.gson.annotations.SerializedName

data class pokemonResponse(
    @SerializedName("pokemon")
    val pokemonList: List<pokemonDTO>?
)
