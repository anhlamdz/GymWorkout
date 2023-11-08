package io.strongapp.gymworkout.ui.food.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FoodViewModel(application: Application) : AndroidViewModel(application) {
	val userRepository : UserRepository

	init {
		val dao = AppDatabase.getDatabase(application).useDao()
		userRepository = UserRepository(dao)

	}

	suspend fun getInfo(): UserEntity {
		return viewModelScope.async(Dispatchers.IO) {
			userRepository.getInfo()
		}.await()
	}


}