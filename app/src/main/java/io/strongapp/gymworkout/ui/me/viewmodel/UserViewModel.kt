package io.strongapp.gymworkout.ui.me.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.ExerciseGymEntity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.repository.ExercisesRepository
import io.strongapp.gymworkout.data.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {
	val userRepository : UserRepository

	init {
		val dao = AppDatabase.getDatabase(application).useDao()
		userRepository = UserRepository(dao)

	}

	suspend fun getInfo(): UserEntity {
		return userRepository.getInfo()
	}
}