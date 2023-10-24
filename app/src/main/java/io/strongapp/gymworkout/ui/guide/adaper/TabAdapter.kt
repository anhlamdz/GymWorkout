package io.strongapp.gymworkout.ui.guide.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabAdapter(fragmentActivity: FragmentActivity, private val fragments: List<Fragment>) :
	FragmentStateAdapter(fragmentActivity) {

	override fun getItemCount(): Int {
		return fragments.size
	}

	override fun createFragment(position: Int): Fragment {
		return fragments[position]
	}
}
