package com.example.composetrivia.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composetrivia.model.QuestionItem
import com.example.composetrivia.screens.QuestionsViewModel

@Composable
fun Questions(viewModel: QuestionsViewModel) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex = remember {
        mutableStateOf(0)
    }
    if (viewModel.data.value.isLoading == true){

    }else{
        val question = try {
            questions?.get(questionIndex.value)
        }catch (ex: Exception){
            null
        }
        if (questions != null) {
            QuestionDisplay(question = question!!,questionIndex,viewModel){
                questionIndex.value = questionIndex.value+1
            }
        }
    }



}

@Composable
fun QuestionDisplay(question: QuestionItem,
                    questionIndex: MutableState<Int>,
                    viewModel: QuestionsViewModel,
                    onNextClick: (Int) -> Unit = {
                        question
                    }) {

    val choicesState = remember(question) {
        question.choices.toMutableList()

    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }

    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.primaryVariant
    ) {

        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker(counter = questionIndex.value)
            Line(pathEffect = pathEffect)

            Column {
                Text(
                    text = question.question, fontSize = 17.sp,
                    modifier = Modifier
                        .padding(6.dp)
                        .align(
                            alignment = Alignment.Start
                        )
                        .fillMaxHeight(0.3f),
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp,
                    color = Color.White,
                )


                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp, brush = Brush.linearGradient(
                                    colors = listOf(Color.White, Color.White)
                                ), shape = RoundedCornerShape(10.dp)
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomStartPercent = 50,
                                    bottomEndPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(16.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor =
                                if (correctAnswerState.value == true && answerState.value == index)
                                    Color.Green.copy(alpha = 0.2f)
                                else {
                                    Color.Red.copy(alpha = 0.2f)
                                }
                            )
                        )

                        val annatatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Light,
                                    color = if (correctAnswerState.value == true && answerState.value == index) {
                                        Color.Green
                                    } else if (correctAnswerState.value == false && answerState.value == index) {
                                        Color.Red
                                    } else {
                                        Color.White
                                    }, fontSize = 17.sp
                                )
                            ) {
                                append(answerText)
                            }
                        }

                        Text(text = annatatedString, modifier = Modifier.padding(6.dp))

                    }
                }
                androidx.compose.material3.Button(
                    onClick = { onNextClick(questionIndex.value) }, modifier = Modifier
                        .padding(3.dp)
                        .align(alignment = Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(

                    )
                ) {
                    Text(
                        text = "Next",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp
                    )
                }

            }
        }
    }
}
    @Composable
    fun Line(pathEffect: PathEffect) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        ) {
            drawLine(
                color = Color.White,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect
            )

        }
    }

    @Composable
    fun QuestionTracker(counter: Int = 10, outOff: Int = 100) {

        Text(text = buildAnnotatedString {
            withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray, fontWeight = FontWeight.Bold,
                        fontSize = 27.sp
                    )
                ) {

                    append("Question $counter/")
                    withStyle(
                        style = SpanStyle(
                            color = Color.LightGray, fontWeight = FontWeight.Light,
                            fontSize = 14.sp
                        )
                    ) {
                        append("$outOff")
                    }
                }
            }
        }, modifier = Modifier.padding(20.dp))

    }
