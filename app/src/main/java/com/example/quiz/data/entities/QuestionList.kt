package com.example.quiz.data.entities

data class QuestionList(

    val assessmentId : String,
    val assessmentName : String,
    val subject : String,
    val duration : Int,
    val questions : List<Question>,
    val totalMarks : Int
)