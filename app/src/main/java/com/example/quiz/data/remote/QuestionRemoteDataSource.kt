package com.example.quiz.data.remote

import javax.inject.Inject

class QuestionRemoteDataSource @Inject constructor(
    private val questionService: QuestionService
): BaseDataSource() {

    suspend fun getQuestions() = getResult { questionService.getAllQuestions() }
}