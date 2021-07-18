package com.example.quiz.data.repository

import com.example.quiz.data.local.QuestionDao
import com.example.quiz.data.remote.QuestionRemoteDataSource
import com.example.quiz.utils.performGetOperation
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val remoteDataSource: QuestionRemoteDataSource,
    private val localDataSource: QuestionDao
) {

    fun getQuestion(id: Int) = performGetOperation(
        databaseQuery = { localDataSource.getQuestion(id) }

    )

    fun getQuestions() = performGetOperation(
        databaseQuery = { localDataSource.getAllQuestions() },
        networkCall = { remoteDataSource.getQuestions() },
        saveCallResult = { localDataSource.insertAll(it.questions) }
    )
}