package io.strongapp.gymworkout.ui.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.strongapp.gymworkout.data.database.AppDatabase
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {
	val userRepository : UserRepository


	init {
		val daoUser = AppDatabase.getDatabase(application).useDao()
		userRepository = UserRepository(daoUser)
	}


	fun insertUser(userEntity: UserEntity) = viewModelScope.launch {
		userRepository.insertUser(userEntity)
	}

}