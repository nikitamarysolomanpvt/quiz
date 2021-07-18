package com.example.quiz.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.quiz.R
import com.example.quiz.data.entities.Question
import com.example.quiz.data.entities.QuestionAnswere
import com.example.quiz.databinding.CharacterDetailFragmentBinding
import com.example.quiz.ui.characters.QuestionAnswereViewModel
import com.example.quiz.utils.Resource
import com.example.quiz.utils.autoCleared
import dagger.hilt.android.AndroidEntryPoint
import katex.hourglass.`in`.mathlib.MathView

@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    private var binding: CharacterDetailFragmentBinding by autoCleared()
    private val viewModel:    QuestionAnswereViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CharacterDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getInt("id")?.let {  }
//        bindCharacter( viewModel.questionAnswers.value!!)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.questionAnswers.observe(viewLifecycleOwner, Observer {
            bindCharacter(it!!)
        })
    }

    private fun bindCharacter(questionAnswere: List<QuestionAnswere>) {
        val N = questionAnswere.size // total number of textviews to add

        val layout = binding.rootLayout


        questionAnswere.forEach {
            // create a new textview
            val rowTextView = TextView(this.activity)
            val context= this.activity?.applicationContext
            val mathView = MathView(context)
            mathView.isClickable = true
            mathView.setTextSize(16)
            mathView.setTextColor(
                ContextCompat.getColor(
                    context!!,
                    R.color.colorPrimaryDark
                )
            )
            mathView.setDisplayText("<B><BR>"+"Question No.</B>:"+it.qno+it.text+"<B><BR>Answere: </B>"+it.answere+"<HR>")

            // setting height and width
            rowTextView.layoutParams= LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            // set some properties of rowTextView or something

            // add the textview to the linearlayout
            layout?.addView(mathView)

        }

    }
}
