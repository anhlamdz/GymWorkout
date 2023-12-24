package io.strongapp.gymworkout.ui.guide.tab

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.FragmentRegisterBinding
import io.strongapp.gymworkout.ui.guide.GuideAct
import io.strongapp.gymworkout.ui.guide.viewmodel.GuideViewModel


class FragmentGuideAccount : BaseFragment<FragmentRegisterBinding>() {
	private lateinit var guideViewModel: GuideViewModel
	private var gender : String = ""
	private var goal : String = ""
	private var name : String = ""
	private var age : Int = 0
	private var height : Float= 0f
	private var tdee : Int = 0
	private var totalCalo :Int = 0
	private var weight :Float = 0f
	private var targetWeight : Float = 0f
	private lateinit var dbRef : DatabaseReference
	private lateinit var mAuth : FirebaseAuth


	override fun getLayoutRes(): Int {
		return R.layout.fragment_register
	}

	override fun initAction() {
		binding.btnNext.setOnClickListener {
			when(validateCredentials(binding.email.text.toString(),binding.pass.text.toString())) {
				true -> {
					(activity as? GuideAct)?.updateProgressBarAndNavigateNext()
					val id = dbRef.push().key!!
					val newUser = UserEntity(id,name,age,gender,goal,height,weight,tdee,totalCalo,targetWeight,binding.email.text.toString(),binding.pass.text.toString())
					dbRef.child(id).setValue(newUser)
						.addOnCompleteListener{
							Toast.makeText(requireContext(),resources.getText(R.string.sign_success),Toast.LENGTH_SHORT).show()
							Log.i("hahaha", "sucessfully")
						}
						.addOnFailureListener {
							Toast.makeText(requireContext(),"Lỗi ${it.message}",Toast.LENGTH_SHORT).show()
							Log.i("hahaha", "failded ${it.message}")
						}
					register(binding.email.text.toString(),binding.pass.text.toString())
					guideViewModel.insertUser(newUser)
				}
				false -> {
					Toast.makeText(requireContext(),resources.getText(R.string.warning_account),Toast.LENGTH_LONG).show()
				}
			}

		}
	}

	override fun initView() {
		dbRef = FirebaseDatabase.getInstance().getReference("User")
		mAuth = FirebaseAuth.getInstance()
		guideViewModel = ViewModelProvider(requireActivity())[GuideViewModel::class.java]
		getData()


	}
	fun getData() {
		guideViewModel.name.observe(this){_name ->
			this.name = _name
		}
		guideViewModel.age.observe(this){_age ->
			this.age = _age
		}
		guideViewModel.height.observe(this){_height ->
			this.height = _height
		}
		guideViewModel.weight.observe(this){_weight ->
			this.weight = _weight
		}
		guideViewModel.goal.observe(this){_goal ->
			this.goal = _goal
		}
		guideViewModel.gender.observe(this){_gender ->
			this.gender = _gender
		}
		guideViewModel.tdee.observe(this){_tdee ->
			this.tdee = _tdee
		}
		guideViewModel.totalCalo.observe(this){_totalCalo ->
			this.totalCalo = _totalCalo
		}
		guideViewModel.targetWeight.observe(this){_targetWeight ->
			this.targetWeight = _targetWeight
		}
	}
	fun validateCredentials(username: String, password: String): Boolean {
		if (username.isEmpty() || password.isEmpty()){
			return false
		}
		val emailRegex = "^[a-zA-Z0-9._-]+@gmail.com$".toRegex()
		if (!username.matches(emailRegex)) {
			return false
		}
		val passwordRegex = "^(?=.*[0-9])(?=.*[a-z]).{6,}$".toRegex()
		if (!password.matches(passwordRegex)) {
			return false
		}
		return true
	}
	fun register(username: String, password: String) {
		mAuth.createUserWithEmailAndPassword(username,password)
			.addOnCompleteListener {
			Toast.makeText(requireContext(),resources.getText(R.string.sign_success),Toast.LENGTH_SHORT).show()
		}
			.addOnFailureListener {
				Toast.makeText(requireContext(),"Lỗi ${it.message}",Toast.LENGTH_SHORT).show()
			}
	}


}