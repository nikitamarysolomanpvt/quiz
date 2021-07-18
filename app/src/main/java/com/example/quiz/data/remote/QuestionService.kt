package com.example.quiz.data.remote

import com.example.quiz.data.entities.QuestionList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionService {
    @GET("/exam/exam.json")
    suspend fun getAllQuestions() : Response<QuestionList>


}