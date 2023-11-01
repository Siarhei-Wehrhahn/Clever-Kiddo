package com.example.kiinderlernapp.ui.vegetable

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.kiinderlernapp.R
import com.example.kiinderlernapp.data.Questions
import com.example.kiinderlernapp.databinding.FragmentVegetableBinding
import com.example.kiinderlernapp.ui.MainViewModel

class VegetableFragment : Fragment() {

    private lateinit var binding: FragmentVegetableBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVegetableBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setText()
        viewModel.quiz.observe(viewLifecycleOwner) {
            setText()
        }
    }

    private fun setText() {
        binding.textQuestion3.text = viewModel.quiz.value?.question

        binding.imageAnswereA.setImageResource(viewModel.quiz.value!!.answerA)

        binding.imageAnswereB.setImageResource(viewModel.quiz.value!!.answerB)

        binding.imageAnswereC.setImageResource(viewModel.quiz.value!!.answerC)

        binding.imageAnswereD.setImageResource(viewModel.quiz.value!!.answerD)

        var answereList =
            listOf(binding.imageAnswereA, binding.imageAnswereB, binding.imageAnswereC, binding.imageAnswereD)
        answereList.forEach {
            it.setOnClickListener {
                var answere = viewModel.checkAnswere(answereList.indexOf(it) + 1)
                if (answere) {
                    viewModel.nextQuestion()
                } else {
                    viewModel.textToSpeach("Leider Falsch")
                }
            }

        }
    }
}