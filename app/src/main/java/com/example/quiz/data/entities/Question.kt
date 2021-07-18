package com.example.quiz.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey
    val id : String,
    val qno : Int,
    var text : String,
    val mcOptions : ArrayList<String>,
    val type : String,
    val marks : Int
)