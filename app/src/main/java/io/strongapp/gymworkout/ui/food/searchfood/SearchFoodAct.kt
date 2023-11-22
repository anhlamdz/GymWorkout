package io.strongapp.gymworkout.ui.food.searchfood

import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.api.ApiViewModel
import io.strongapp.gymworkout.data.api.RetrofitClient
import io.strongapp.gymworkout.data.api.StateApi
import io.strongapp.gymworkout.data.database.FoodResponse
import io.strongapp.gymworkout.data.database.entities.NutritionEntity
import io.strongapp.gymworkout.databinding.ActivitySearchFoodBinding
import io.strongapp.gymworkout.ui.food.searchfood.adapter.SearchFoodAdapter
import io.strongapp.gymworkout.ui.food.viewmodel.FoodViewModel
import java.util.Calendar
import java.util.Date

class SearchFoodAct : BaseActivity<ActivitySearchFoodBinding>(), SearchFoodAdapter.ListenerInsertFoodToMeal {
	private val searchFoodAdapter by lazy(LazyThreadSafetyMode.NONE) { SearchFoodAdapter(this) }
	private lateinit var foodViewModel: FoodViewModel
	private lateinit var spinnerMeal : Spinner
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
		spinnerMeal = binding.meal
		foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
		val mealOptions = arrayOf("Bữa sáng", "Bữa trưa", "Bữa tối", "Đồ ăn nhẹ")
		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mealOptions)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spinnerMeal.adapter = adapter

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
	fun getCurrentDate(): Triple<Int, Int, Int> {
		val currentDate = Calendar.getInstance()
		val year = currentDate.get(Calendar.YEAR)
		val month = currentDate.get(Calendar.MONTH) + 1  // Note: Months are zero-based
		val day = currentDate.get(Calendar.DAY_OF_MONTH)
		return Triple(year, month, day)
	}
	fun convertStringToDouble(input: String): Double {
		// Chuyển dấu "," thành dấu "."
		val sanitizedInput = input.replace(",", ".")

		// Chuyển chuỗi thành kiểu double
		return sanitizedInput.toDouble()
	}

	override fun onClick(foodResponse: FoodResponse) {
		val (year, month, day) = getCurrentDate()
		val date = "$day/$month/$year"
		val food = NutritionEntity(
			0,
			foodResponse.name,
			convertStringToDouble(foodResponse.fat),
			convertStringToDouble(foodResponse.carbonHydrates),
			convertStringToDouble(foodResponse.protein),
			foodResponse.calories.toInt(),
			spinnerMeal.selectedItem.toString(),
			foodResponse.weight,
			date
		)
		try {
			foodViewModel.insertFoodToMeal(food)
			Toast.makeText(this, "Food inserted successfully",Toast.LENGTH_LONG)
		} catch (e: Exception) {
			e.printStackTrace()
			Toast.makeText(this, "Error inserting food: ${e.message}",Toast.LENGTH_LONG)
		}
	}
}