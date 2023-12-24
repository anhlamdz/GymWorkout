package io.strongapp.gymworkout.ui.custom.detail

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseActivity
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.data.models.CustomTrainingEntity
import io.strongapp.gymworkout.data.models.TrainingEntity
import io.strongapp.gymworkout.databinding.ActivityDetailCustomBinding
import io.strongapp.gymworkout.ui.custom.customtraining.CustomTrainingAct
import io.strongapp.gymworkout.ui.custom.detail.adapter.DetailCustomAdapter
import io.strongapp.gymworkout.ui.custom.startcustomtraining.StartCustomTrainingAct
import io.strongapp.gymworkout.ui.custom.viewmodel.CustomViewModel
import io.strongapp.gymworkout.view.NameTrainingDialog
import kotlinx.coroutines.launch

class DetailCustomAct: BaseActivity<ActivityDetailCustomBinding>() {
	private lateinit var customEntity: CustomEntity
	private lateinit var customViewModel: CustomViewModel

	override fun initView() {
		customViewModel = ViewModelProvider(this)[CustomViewModel::class.java]
		customEntity = intent.getSerializableExtra("customTraining") as CustomEntity
		val data = customEntity.data.toCustomTrainingList()
		binding.nameTrainingCustom.text = customEntity.name
		binding.numberEx.text = data?.size.toString()
		val detailCustomAdapter = data?.let { DetailCustomAdapter(this, it) }
		binding.rcvEx.layoutManager = LinearLayoutManager(this)
		binding.rcvEx.adapter = detailCustomAdapter
	}

	override fun initAction() {
		binding.btnMore.setOnClickListener {
			when(binding.dropOption.visibility){
				View.VISIBLE ->{
					binding.dropOption.visibility =View.GONE
				}
				View.GONE -> {
					binding.dropOption.visibility = View.VISIBLE
				}
			}
		}
		binding.btnRename.setOnClickListener {
			showInputDialog(binding.nameTrainingCustom.text.toString())
		}
		binding.btnDelete.setOnClickListener {
			customViewModel.deleteWorkout(customEntity)
			val intent = Intent()
			setResult(Activity.RESULT_OK,intent)
			finish()
		}
		binding.btnBack.setOnClickListener {
			finish()
		}
		binding.btnFinish.setOnClickListener {
			val intent = Intent(this, StartCustomTrainingAct::class.java)
			intent.putExtra("customTraining",customEntity)
			startActivity(intent)
		}
		binding.btnEdit.setOnClickListener {
			val intent = Intent(this, CustomTrainingAct::class.java)
			intent.putExtra("edit", customEntity)
			startActivityForResult(intent, REQUEST_EDIT_CUSTOM_TRAINING)

		}
	}

	override fun getContentView(): Int {
		return R.layout.activity_detail_custom
	}

	override fun bindViewModel() {
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == REQUEST_EDIT_CUSTOM_TRAINING && resultCode == Activity.RESULT_OK){
			lifecycleScope.launch {
				customEntity = customViewModel.getCustomEntityById(customEntity.id)
				binding.nameTrainingCustom.text = customEntity.name
				val listNew = customEntity.data.toCustomTrainingList()
				binding.numberEx.text = listNew?.size.toString()
				val detailCustomAdapter = listNew?.let { DetailCustomAdapter(this@DetailCustomAct, it) }
				binding.rcvEx.adapter = detailCustomAdapter
			}
		}
	}

	fun String.toCustomTrainingList(): List<CustomTrainingEntity>? {
		return try {
			val gson = Gson()
			val listType = object : TypeToken<List<CustomTrainingEntity>>() {}.type
			gson.fromJson(this, listType)
		} catch (e: Exception) {
			e.printStackTrace()
			null
		}
	}
	private fun showInputDialog(name: String) {
		val nameTrainingDialog = NameTrainingDialog(this)
		nameTrainingDialog.setOnNameEnteredListener(object : NameTrainingDialog.OnNameEnteredListener {
			override fun onNameEntered(name: String) {
				binding.nameTrainingCustom.text = name
				val update = CustomEntity(customEntity.id,name ,customEntity.color,customEntity.data,customEntity.idUser)
				customViewModel.updateCustom(update)
			}
		})
		nameTrainingDialog.show(name)
	}

	companion object {
		const val REQUEST_EDIT_CUSTOM_TRAINING = 3
	}


}