package com.example.quiz.ui.characters

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.quiz.data.repository.QuestionRepository

class QuestionsViewModel @ViewModelInject constructor(
    private val repository: QuestionRepository
) : ViewModel() {

    val questions = repository.getQuestions()

}
