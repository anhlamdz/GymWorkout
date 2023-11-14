package io.strongapp.gymworkout.ui.food.searchfood

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.api.ApiViewModel
import io.strongapp.gymworkout.data.api.RetrofitClient
import io.strongapp.gymworkout.data.api.StateApi
import io.strongapp.gymworkout.data.database.FoodResponse
import io.strongapp.gymworkout.databinding.ActivitySearchFoodBinding
import io.strongapp.gymworkout.ui.food.searchfood.adapter.SearchFoodAdapter

class SearchFoodAct : BaseActivity<ActivitySearchFoodBinding>() {
	private val searchFoodAdapter by lazy(LazyThreadSafetyMode.NONE) { SearchFoodAdapter() }
	private val viewModel by viewModels<ApiViewModel>(
		factoryProducer = {
			viewModelFactory {
				addInitializer(ApiViewModel::class) {
					ApiViewModel(RetrofitClient.apiService)
				}
			}
		}
	)

	override fun initView() {
		val mealOptions = arrayOf("Bữa sáng", "Bữa trưa", "Bữa tối", "Đồ ăn nhẹ")
		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mealOptions)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.meal.adapter = adapter

		val meal = intent.getStringExtra("nameMeal")
		binding.meal.setSelection(adapter.getPosition(meal))
		binding.rcvFood.layoutManager = LinearLayoutManager(this)
		binding.rcvFood.adapter = searchFoodAdapter
		viewModel.getAllFood()
		observer()
	}

	override fun initAction() {
		binding.btnBack.setOnClickListener {
			finish()
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_search_food
	}

	override fun bindViewModel() {

	}

	private fun observer() {
		viewModel.todoLiveData.observe(this) {
			when (it) {
				is StateApi.Loading -> {

				}

				is StateApi.Success -> {

				}

				is StateApi.SuccessFood -> {
					searchFoodAdapter.submitList(it.foodResponse)
					filter(it.foodResponse)

				}
				is StateApi.Failed -> {

				}
			}
		}
	}

	private fun filter(list: List<FoodResponse>) {
		binding.searchEdt.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			}

			override fun onTextChanged(key: CharSequence?, p1: Int, p2: Int, p3: Int) {

				val filteredList = list.filter {
					it.name.toLowerCase().contains(key.toString())
				}
				searchFoodAdapter.submitList(filteredList)
			}

			override fun afterTextChanged(p0: Editable?) {

			}
		})
	}
}