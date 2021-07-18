package com.example.quiz.ui.characters

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.quiz.data.entities.QuestionAnswere
import com.example.quiz.data.repository.QuestionAnswereRepository
import com.example.quiz.data.repository.QuestionRepository
import kotlinx.coroutines.launch


class QuestionAnswereViewModel @ViewModelInject constructor(
    private val repository: QuestionAnswereRepository
) : ViewModel() {
    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val questionAnswers: LiveData<List<QuestionAnswere>> = repository.questionAnswers

    //    val questionAnswersList=questionAnswers.value!!
    val bLiveData: LiveData<List<QuestionAnswere>> =
        MediatorLiveData<List<QuestionAnswere>>().apply {
            addSource(questionAnswers) { aData ->
                value  //value is backing property for getValue()/setValue() method, use postValue() explicitly upon bg operation
            }
        }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(word: List<QuestionAnswere>) = viewModelScope.launch {
        repository.insert(word)
    }
}

class QuestionAnswereViewModelFactory(private val repository: QuestionAnswereRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionAnswereViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuestionAnswereViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}