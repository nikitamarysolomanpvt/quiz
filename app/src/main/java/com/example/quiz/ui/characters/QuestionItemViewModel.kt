package com.example.quiz.ui.characters

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quiz.data.entities.Question


class QuestionItemViewModel(
    val question: Question
) : ViewModel() {
    val questionText = if(!question.text.startsWith("<p>"))
        "<p>"+question.text+"</p>" else question.text
    val questionNumberText = "Question no.: " + question.qno.toString()
    val marksText = "Marks: " + question.marks.toString()
    val answers = ObservableField<String>( "Not attempted")
    val mcAnswers = ObservableField<Int>(-1)


    fun onTextChanged(answer: CharSequence, start: Int, before: Int, count: Int) {
        val answerText = answer.toString().trim()
        val isAnswerTextAvailable = answerText.isNotEmpty()
        if (isAnswerTextAvailable) {
            answers!!.set(answerText)

        }
    }

}