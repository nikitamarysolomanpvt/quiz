package com.example.quiz.data.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.quiz.data.entities.QuestionAnswere
import com.example.quiz.data.local.QuestionAnswerDao
import com.example.quiz.data.local.QuestionDao
import com.example.quiz.data.remote.QuestionRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


    class QuestionAnswereRepository @Inject constructor(
        private val localDataSource: QuestionAnswerDao
    ) {
    // Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
//    class WordRepository(private val wordDao: WordDao) {

        // Room executes all queries on a separate thread.
        // Observed Flow will notify the observer when the data has changed.
        val questionAnswers= localDataSource.getAllQuestionAnswers()

        // By default Room runs suspend queries off the main thread, therefore, we don't need to
        // implement anything else to ensure we're not doing long running database work
        // off the main thread.
        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(questionAnsweres: List<QuestionAnswere>) {
            localDataSource.insertAll(questionAnsweres)
        }
    }