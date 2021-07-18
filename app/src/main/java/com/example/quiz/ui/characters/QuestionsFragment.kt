package com.example.quiz.ui.characters

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cn.iwgang.countdownview.CountdownView
import com.example.quiz.R
import com.example.quiz.data.entities.Question
import com.example.quiz.data.entities.QuestionAnswere
import com.example.quiz.databinding.QuestionsFragmentBinding
import com.example.quiz.utils.Resource
import com.example.quiz.utils.autoCleared
import com.example.quiz.utils.setLocalImage
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.constant.ImageProvider
import com.github.dhaval2404.imagepicker.util.IntentUtils
import kotlinx.android.synthetic.main.item_question.*

const val PROFILE_IMAGE_REQ_CODE = 101
@AndroidEntryPoint
class QuestionsFragment : Fragment(), QuestionsAdapter.QuestionItemListener , CountdownView.OnCountdownEndListener {

    private var binding: QuestionsFragmentBinding by autoCleared()
    private val viewModel: QuestionsViewModel by viewModels()
    private val viewAnswereModel: QuestionAnswereViewModel by viewModels()

    private lateinit var adapter: QuestionsAdapter
    private val questionList = ArrayList<QuestionItemViewModel>()
    private var mProfileUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = QuestionsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        binding.submitBtn.setOnClickListener {
            populateQuestionAnswers(adapter.getItems())
        }


    }

    private fun populateQuestionAnswers(questionItemViewModel: ArrayList<QuestionItemViewModel>) {
        var questionAnswereList = ArrayList<QuestionAnswere>()
        if (questionItemViewModel.isNotEmpty())
            questionItemViewModel.forEach {
                var answere: String = when (it.question.type) {
                    "MC" ->
                        if (it.mcAnswers.get() == -1) "Not attempted"
                        else it.question.mcOptions.get(it.mcAnswers.get()!!)
                    "SA" -> it.answers.get() as String
                    else -> ""
                }
                val questionAnswere = QuestionAnswere(
                    it.question.qno,
                    it.questionText,
                    answere
                )
                questionAnswereList.add(questionAnswere)
            }
        viewAnswereModel.insert(questionAnswereList)
        onClickedSubmitQuestion()
    }

    private fun setupRecyclerView() {
        adapter = QuestionsAdapter(this)

        with(binding) {
            questionsRv.layoutManager = LinearLayoutManager(requireContext())
            questionsRv.adapter = adapter
            questionsRv.itemAnimator!!.setChangeDuration(0)

        }
    }

    private fun setupObservers() {
        viewModel.questions.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    binding.mCvCountdownView.start(TimeUnit.MINUTES.toMillis(30))
                    if (!it.data.isNullOrEmpty())
                        populateRecyclerView(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }

    private fun populateRecyclerView(questions: List<Question>) {
        questionList.clear()
        if (questions.isNotEmpty())
            questions.forEach {
                val questionItemViewModel = QuestionItemViewModel(
                    it
                )
                questionList.add(questionItemViewModel)
            }
        adapter.setItems(questionList)
    }


    fun onClickedSubmitQuestion() {
        findNavController().navigate(
            R.id.action_charactersFragment_to_characterDetailFragment
        )
    }
    @Suppress("UNUSED_PARAMETER")
    fun pickProfileImage(view: View) {
        ImagePicker.with(this)
            // Crop Square image
            .provider(ImageProvider.BOTH)
            .cropSquare()
            .setImageProviderInterceptor { imageProvider -> // Intercept ImageProvider
            }
            .setDismissListener {
            }
            // Image resolution will be less than 512 x 512
            .maxResultSize(200, 200)
            .start(PROFILE_IMAGE_REQ_CODE)
    }
    override fun onClickedAttachment(view: View) {
        pickProfileImage(view)
    }

    override fun onClickedImage(view: View) {
        showImage(view)
    }

    override fun onEnd(cv: CountdownView?) {
       binding.submitBtn.performClick()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            // Uri object will not be null for RESULT_OK
            val uri: Uri = data?.data!!
            when (requestCode) {
                PROFILE_IMAGE_REQ_CODE -> {
                    mProfileUri = uri
                    imgProfile.visibility=View.VISIBLE
                    imgProfile.setLocalImage(uri, true)
                }
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this.context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this.context, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }

    }
    fun showImage(view: View) {
        val uri = when (view) {
            imgProfile -> mProfileUri
            else -> null
        }

        uri?.let {
            startActivity(this.context?.let { it1 -> IntentUtils.getUriViewIntent(it1, uri) })
        }
    }

}
