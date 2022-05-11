package com.example.composetrivia.components

import androidx.compose.runtime.Composable
import com.example.composetrivia.screens.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
}
