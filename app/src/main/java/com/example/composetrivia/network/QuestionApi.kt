package com.example.composetrivia.network

import com.example.composetrivia.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getQuestions(): Question

}