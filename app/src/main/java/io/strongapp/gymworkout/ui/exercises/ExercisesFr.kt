package io.strongapp.gymworkout.ui.exercises

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.api.ApiViewModel
import io.strongapp.gymworkout.data.api.RetrofitClient
import io.strongapp.gymworkout.data.api.StateApi
import io.strongapp.gymworkout.data.database.ExerciseResponse
import io.strongapp.gymworkout.databinding.FragmentExercisesBinding
import io.strongapp.gymworkout.ui.exercises.adpter.ExercisesAdapter
import io.strongapp.gymworkout.ui.exercises.viewmodel.ExercisesViewModel


class ExercisesFr constructor() : BaseFragment<FragmentExercisesBinding>() {
    private lateinit var exercisesViewModel: ExercisesViewModel
    private lateinit var edtSearch: EditText
    private lateinit var inputMethodManager: InputMethodManager
    private val exercisesAdapter by lazy(LazyThreadSafetyMode.NONE) { ExercisesAdapter() }
    private val viewModel by viewModels<ApiViewModel>(
        factoryProducer = {
            viewModelFactory {
                addInitializer(ApiViewModel::class) {
                    ApiViewModel(RetrofitClient.apiService)
                }
            }
        }
    )

    override fun getLayoutRes(): Int {
        return R.layout.fragment_exercises
    }

    override fun initAction() {
        val slideUpAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_slide_up)
        val slideDownAnimation = AnimationUtils.loadAnimation(context, R.anim.anim_slide_down)
        binding.ivSearch.setOnClickListener {
            binding.constraintLayout.visibility = View.INVISIBLE
            binding.searchLayout.visibility = View.VISIBLE
            binding.searchLayout.startAnimation(slideDownAnimation)
            binding.constraintLayout.startAnimation(slideUpAnimation)
        }
        binding.btnClose.setOnClickListener {
            binding.constraintLayout.visibility = View.VISIBLE
            binding.searchLayout.visibility = View.INVISIBLE
            binding.searchLayout.startAnimation(slideUpAnimation)
            binding.constraintLayout.startAnimation(slideDownAnimation)
            edtSearch.text.clear()
            hideKeyboard()
        }


    }

    override fun initView() {
        edtSearch = binding.searchEdt
        inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        exercisesViewModel = ViewModelProvider(requireActivity())[ExercisesViewModel::class.java]
        binding.rcvExercises.layoutManager = LinearLayoutManager(requireContext())
        binding.rcvExercises.adapter = exercisesAdapter
        viewModel.getAllExercises()
        observer()
    }

    private fun observer() {
        viewModel.todoLiveData.observe(this) {
            when (it) {
                is StateApi.Loading -> {

                }

                is StateApi.Success -> {
                    exercisesAdapter.submitList(it.exerciseResponse)
                    filter(it.exerciseResponse)
                }
                is StateApi.SuccessSingle -> {

                }
                is StateApi.Failed -> {

                }
            }
        }
    }

	private fun filter(list: List<ExerciseResponse>) {
		edtSearch.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			}

			override fun onTextChanged(key: CharSequence?, p1: Int, p2: Int, p3: Int) {

				val filteredList = list.filter {
					it.name.toLowerCase().contains(key.toString())
				}
				exercisesAdapter.submitList(filteredList)
			}

			override fun afterTextChanged(p0: Editable?) {

			}
		})
	}

    private fun hideKeyboard() {
        inputMethodManager.hideSoftInputFromWindow(edtSearch.windowToken, 0)
    }
}
