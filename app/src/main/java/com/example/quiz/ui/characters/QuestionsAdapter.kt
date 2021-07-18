package com.example.quiz.ui.characters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.quiz.R
import com.example.quiz.databinding.ItemQuestionBinding
import com.google.android.material.card.MaterialCardView
import katex.hourglass.`in`.mathlib.MathView
import kotlin.collections.ArrayList

class QuestionsAdapter(private val context: QuestionsFragment) :
    RecyclerView.Adapter<QuestionViewHolder>() {


    interface QuestionItemListener {
        fun onClickedAttachment(view: View)
        fun onClickedImage(view: View)
    }

    private val items = ArrayList<QuestionItemViewModel>()

    fun setItems(items: ArrayList<QuestionItemViewModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun getItems(): ArrayList<QuestionItemViewModel> {
        return this.items
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuestionBinding.inflate(inflater, parent, false)

        return QuestionViewHolder(binding, context)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(items[position])
        val item = items[position]

    }
}


class QuestionViewHolder(
    private val itemBinding: ItemQuestionBinding,
    private val context: QuestionsFragment
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    var index: Int = -1
    private val listener = context as QuestionsAdapter.QuestionItemListener
    private lateinit var item: QuestionItemViewModel
    val textTitle: MathView = itemBinding.questionMTv
    val mcOption1: MathView = itemBinding.option1
    val mcOption2: MathView = itemBinding.option2
    val mcOption3: MathView = itemBinding.option3
    val mcOption4: MathView = itemBinding.option4
    val image_view: ImageView = itemBinding.imageView

    init {
        itemBinding.option1.setOnClickListener(this)
        itemBinding.option2.setOnClickListener(this)
        itemBinding.option3.setOnClickListener(this)
        itemBinding.option3.setOnClickListener(this)
        itemBinding.imageView.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    fun bind(item: QuestionItemViewModel) {
        this.item = item
        itemBinding.viewModel = item

//        itemBinding.name.text = item.name
//        itemBinding.speciesAndStatus.text = """${item.species} - ${item.status}"""
        if (item.question.type.equals("MC")) {
            itemBinding.option1.setDisplayText(item.question.mcOptions!![0])
            itemBinding.option2.setDisplayText(item.question.mcOptions.get(1))
            itemBinding.option3.setDisplayText(item.question.mcOptions[2])
            itemBinding.option4.setDisplayText(item.question.mcOptions.get(3))
        }
        itemBinding.questionMTv.setDisplayText(this.item.questionText)
        if (this.item.question.type.equals("MC")) {
            itemBinding.option1.setDisplayText(this.item.question.mcOptions.get(0))
            itemBinding.option2.setDisplayText(this.item.question.mcOptions.get(1))
            itemBinding.option3.setDisplayText(this.item.question.mcOptions.get(2))
            itemBinding.option4.setDisplayText(this.item.question.mcOptions.get(3))
            setMCOptionSelected(this.item.mcAnswers.get()!!)
        }

    }

    override fun onClick(view: View?) {
        if (view!!.id.equals(itemBinding.imageView.id))
            listener.onClickedAttachment(view)
        else if (view!!.id.equals(itemBinding.imgProfile.id))
            listener.onClickedAttachment(view)
        else {
            itemBinding.apply {
           index = when (view!!.id) {
                option1.id -> 0
                option2.id -> 1
                option3.id -> 2
                option4.id -> 3
                else -> -1
            }}

            this.item.mcAnswers.set(index)
            setMCOptionSelected(index)

        }
    }

    fun setMCOptionSelected(index: Int) {
        itemBinding.apply {
            when (index) {
                0 -> {
                    onOptionsSelected(
                        option1Cv, option2Cv, option3Cv, option4Cv
                    )
                }
                1 -> {
                    onOptionsSelected(
                        option2Cv, option1Cv, option3Cv, option4Cv
                    )
                }
                2 -> {
                    onOptionsSelected(
                        option3Cv, option1Cv, option2Cv, option4Cv
                    )
                }
                3 -> {
                    onOptionsSelected(
                        option4Cv, option1Cv, option2Cv, option3Cv
                    )
                }
                else -> { // Note the block
                    print("error")
                }
            }
        }
    }

    fun onOptionsSelected(
        card1: MaterialCardView,
        card2: MaterialCardView,
        card3: MaterialCardView,
        card4: MaterialCardView
    ) {
        card1.background =
            context.resources.getDrawable(R.drawable.rounded_rect_background)
        card2.background =
            context.resources.getDrawable(R.drawable.rounded_selected_rect_background)
        card3.background =
            context.resources.getDrawable(R.drawable.rounded_selected_rect_background)
        card4.background =
            context.resources.getDrawable(R.drawable.rounded_selected_rect_background)
    }
}

