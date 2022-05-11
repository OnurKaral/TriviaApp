package com.example.composetrivia.repository

import com.example.composetrivia.data.DataOrException
import com.example.composetrivia.model.QuestionItem
import com.example.composetrivia.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject  constructor(
    private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {

        try {

            dataOrException.isLoading  = true
            dataOrException.data = api.getQuestions()
           if (dataOrException.data.toString().isNotEmpty()) dataOrException.isLoading = false


        }catch (exception: Exception){
            dataOrException.exception = exception
        }
        return dataOrException
}
}