	package io.strongapp.gymworkout.ui.guide.viewmodel

	import android.app.Application
	import androidx.lifecycle.AndroidViewModel
	import androidx.lifecycle.LiveData
	import androidx.lifecycle.MutableLiveData
	import androidx.lifecycle.viewModelScope
	import io.strongapp.gymworkout.data.database.AppDatabase
	import io.strongapp.gymworkout.data.database.entities.UserEntity
	import io.strongapp.gymworkout.data.repository.UserRepository
	import kotlinx.coroutines.launch

	class GuideViewModel(application: Application) : AndroidViewModel(application) {
		val userRepository : UserRepository
		private val _gender = MutableLiveData<String>()
		private val _goal = MutableLiveData<String>()
		private val _name = MutableLiveData<String>()
		private val _age = MutableLiveData<Int>()
		private val _height = MutableLiveData<Float>()
		private val _weight = MutableLiveData<Float>()
		private val _bmi = MutableLiveData<Float>()
		private val _tdee = MutableLiveData<Int>()
		private val _totalCalo = MutableLiveData<Int>()

		val weight : LiveData<Float>
			get() = _weight
		val height : LiveData<Float>
			get() = _height
		val bmi : LiveData<Float>
			get() = _bmi
		val gender : LiveData<String>
			get() = _gender
		val goal : LiveData<String>
			get() = _goal
		val name : LiveData<String>
			get() = _name
		val age : LiveData<Int>
			get() = _age
		val tdee : LiveData<Int>
			get() = _tdee
		val totalCalo : LiveData<Int>
			get() = _totalCalo
		init {
			val daoUser = AppDatabase.getDatabase(application).useDao()
			userRepository = UserRepository(daoUser)
		}
		fun setGender(gender : String){
			_gender.value = gender
		}
		fun setWeight(weight :Float){
			_weight.value = weight
		}
		fun setHeight(height : Float){
			_height.value = height
		}
		fun setBmi(bmi : Float){
			_bmi.value = bmi
		}
		fun setName(name : String){
			_name.value = name
		}
		fun setAge(age : Int){
			_age.value = age
		}
		fun setGoal(goal : String){
			_goal.value = goal
		}
		fun setTdee(tdee : Int){
			_tdee.value = tdee
		}
		fun setTotalCalo(totalCalo : Int){
			_totalCalo.value = totalCalo
		}

		fun insertUser(userEntity: UserEntity) = viewModelScope.launch {
			userRepository.insertUser(userEntity)
		}
		suspend fun getInfo() : UserEntity{
			return  userRepository.getInfo()
		}
	}