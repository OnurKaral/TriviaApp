package com.example.composetrivia.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composetrivia.data.DataOrException
import com.example.composetrivia.model.QuestionItem
import com.example.composetrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val questionRepository: QuestionRepository): ViewModel() {

    val data: MutableState<DataOrException<ArrayList<QuestionItem>,
            Boolean, Exception>> = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions(){
        viewModelScope.launch {
            data.value.isLoading = true
            data.value = questionRepository.getQuestions()
            if(data.value.data.toString().isNotEmpty()){
                data.value.isLoading = false
            }
        }
    }
}