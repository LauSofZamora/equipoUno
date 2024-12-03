package com.example.equipouno.ServicesWeb

import com.example.equipouno.data.pokemonDTO.pokemonResponse
import com.example.equipouno.Utilitis.Constants.END_POINT
import retrofit2.Response
import retrofit2.http.GET

interface ServiceAPI {
    @GET(END_POINT)
    suspend fun getPokemons(): Response<pokemonResponse>
}