package io.strongapp.gymworkout.ui.login

import android.content.Intent
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.ColumnInfo
import com.google.firebase.auth.ActionCodeResult.ActionDataKey
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.UserEntity
import io.strongapp.gymworkout.databinding.ActivityLoginBinding
import io.strongapp.gymworkout.ui.MainActivity
import io.strongapp.gymworkout.ui.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginAct : BaseActivity<ActivityLoginBinding>() {
	private lateinit var mAuth : FirebaseAuth
	private lateinit var loginViewModel: LoginViewModel
	private val firebaseDatabase = FirebaseDatabase.getInstance()
	var isPasswordVisible = false
	override fun initView() {
		mAuth = FirebaseAuth.getInstance()
		loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
	}

	override fun initAction() {
		binding.btnNext.setOnClickListener {
			when(validateCredentials(binding.email.text.toString(),binding.pass.text.toString())){
				true -> {
					mAuth.signInWithEmailAndPassword(binding.email.text.toString(),binding.pass.text.toString())
						.addOnCompleteListener {
							loadUserDataToRoom()
						}
						.addOnFailureListener {
							Toast.makeText(this,"Lỗi ${it.message}", Toast.LENGTH_SHORT).show()
						}
				}
				false -> {
					Toast.makeText(this,resources.getText(R.string.is_empty),Toast.LENGTH_SHORT).show()
				}
			}
		}
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.showPass.setOnClickListener {
			isPasswordVisible = !isPasswordVisible
			if (isPasswordVisible) {
				binding.pass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
				binding.showPass.setImageResource(R.drawable.baseline_visibility_24)
			} else {
				binding.pass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
				binding.showPass.setImageResource(R.drawable.baseline_visibility_off_24)
			}
			binding.pass.textSize = 23f
			binding.pass.text?.let { it1 -> binding.pass.setSelection(it1.length) }
		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_login
	}

	override fun bindViewModel() {
	}

	fun validateCredentials(username: String, password: String): Boolean {
		if (username.isEmpty() || password.isEmpty()){
			return false
		}
		return true
	}
	private fun loadUserDataToRoom() {
			firebaseDatabase.getReference("User").addListenerForSingleValueEvent(object : ValueEventListener {
				override fun onDataChange(snapshot: DataSnapshot) {
					for (userSnap in snapshot.children) {
						val email = userSnap.child("email").getValue(String::class.java)
						if (binding.email.text.toString() == email) {
							Log.d("FirebaseData", "Found userSnap with uid: Hmosmfk49hW6FM37Hl5f0lgwrAQ2")
							val userData = userSnap.getValue(UserEntity::class.java)
							userData?.let {
								Log.d("FirebaseData", "userData: $userData")
								lifecycleScope.launch {
									loginViewModel.insertUser(userData)
									Log.d("FirebaseData", "User inserted into Room Database")
									goToHome()
								}
							}
							break // Dừng vòng lặp khi tìm thấy userSnap cần
						}
					}

				}

				override fun onCancelled(error: DatabaseError) {
					Log.d("FirebaseData", "lỗi ${error.message}")
				}
			})

	}

	fun goToHome(){
		val intent = Intent(this, MainActivity::class.java)
		startActivity(intent)
	}
}