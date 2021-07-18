package com.example.quiz.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.quiz.data.entities.Question

@Dao
interface QuestionDao {

    @Query("SELECT * FROM questions")
    fun getAllQuestions() : LiveData<List<Question>>

    @Query("SELECT * FROM questions WHERE id = :id")
    fun getQuestion(id: Int): LiveData<Question>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<Question>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: Question)


}