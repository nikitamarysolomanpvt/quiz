package com.example.quiz.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questionAnswere")
data class QuestionAnswere(
    @PrimaryKey
    val qno : Int,
    val text : String,
    val answere : String
)