package com.example.quiz.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiz.data.entities.Question
import com.example.quiz.data.entities.QuestionAnswere

@Dao
interface QuestionAnswerDao {

    @Query("SELECT * FROM questionAnswere")
    fun getAllQuestionAnswers() : LiveData<List<QuestionAnswere>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionAnswere>)

}