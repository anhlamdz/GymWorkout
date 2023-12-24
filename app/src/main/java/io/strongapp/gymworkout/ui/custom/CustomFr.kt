package io.strongapp.gymworkout.ui.main_v2.custom

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import io.strongapp.gymworkout.R
import io.strongapp.gymworkout.base.BaseFragment
import io.strongapp.gymworkout.data.database.entities.CustomEntity
import io.strongapp.gymworkout.databinding.FragmentCustomBinding
import io.strongapp.gymworkout.ui.custom.adapter.CustomAdapter
import io.strongapp.gymworkout.ui.custom.customtraining.CustomTrainingAct
import io.strongapp.gymworkout.ui.custom.viewmodel.CustomViewModel


class CustomFr  : BaseFragment<FragmentCustomBinding>() {
	private lateinit var customViewModel: CustomViewModel
	private lateinit var customAdapter: CustomAdapter
	override fun getLayoutRes(): Int {
		return R.layout.fragment_custom
	}

	override fun initAction() {
		binding.btnAddEx.setOnClickListener {
			val intent = Intent(requireContext(), CustomTrainingAct::class.java)
			startActivity(intent)
		}
		binding.btnNew.setOnClickListener {
			val intent = Intent(requireContext(),CustomTrainingAct::class.java)
			startActivity(intent)
		}
	}

	override fun initView() {
		customViewModel = ViewModelProvider(this)[CustomViewModel::class.java]
		customAdapter = CustomAdapter(requireContext(), mutableListOf())
		binding.rcvCustom.layoutManager = LinearLayoutManager(requireContext())
		binding.rcvCustom.adapter = customAdapter


		customViewModel.customWorkoutEntity.observe(this,{
			if (!it.isEmpty()) {
				binding.layout1.visibility = View.GONE
				binding.layout2.visibility = View.VISIBLE
				customAdapter.setWorkoutList(it)
			} else {
				binding.layout1.visibility = View.VISIBLE
				binding.layout2.visibility = View.GONE
			}
		})
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (resultCode == REQUEST_DETAIL_CUSTOM && resultCode == Activity.RESULT_OK){
			customViewModel.customWorkoutEntity.observe(this,{
				if (!it.isEmpty()) {
					binding.layout1.visibility = View.GONE
					binding.layout2.visibility = View.VISIBLE
					customAdapter.setWorkoutList(it)
				} else {
					binding.layout1.visibility = View.VISIBLE
					binding.layout2.visibility = View.GONE
				}
			})
		}
	}

	companion object {
		const val REQUEST_DETAIL_CUSTOM = 1001
	}
}