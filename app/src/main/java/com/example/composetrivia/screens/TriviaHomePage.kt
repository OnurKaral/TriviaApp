package com.example.composetrivia.screens


import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.composetrivia.components.Questions

@Composable
fun TriviaHomePage(viewModel: QuestionsViewModel = hiltViewModel()) {
    Questions(viewModel)
}


