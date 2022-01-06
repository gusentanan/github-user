package com.bagusmerta.github_user.presentation.viewpager

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bagusmerta.github_user.presentation.followers.FollowersFragment
import com.bagusmerta.github_user.presentation.following.FollowingFragment

class ViewPagerAdapter(
    activity: AppCompatActivity
): FragmentStateAdapter(activity){

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> FollowersFragment()
            1 -> FollowingFragment()
            else -> FollowersFragment()
        }
    }
}