package com.bagusmerta.github_user.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.utils.Constants.EXTRA_USERNAME
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.databinding.ActivityDetailBinding
import com.bagusmerta.github_user.presentation.viewpager.ViewPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()
    private lateinit var viewPager: ViewPager2
    private lateinit var tabs: TabLayout
    private lateinit var viewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewPager = binding.viewPager
        tabs = binding.tabs

        initActionBar()
        initViewPager()
        initStateObserver()
    }

    fun getUsernameUsers(): String {
        return intent.getSerializableExtra(USERNAME) as String
    }

    private fun initActionBar(){
        supportActionBar?.title = resources.getString(R.string.detail_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViewPager(){
        supportActionBar?.elevation = 0f
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager.adapter = viewPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when(position){
                0 -> tab.text = getString(R.string.followers)
                1 -> tab.text = getString(R.string.following)
            }
        }.attach()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initStateObserver(){
        val username = intent.getSerializableExtra(USERNAME) as String
        with(detailViewModel){
            getDetailUser(username)
            state.observe(this@DetailActivity){
                handleLoadingState(it)
            }
            result.observe(this@DetailActivity){
                it?.let { data -> handleUserDetailResult(data) }
            }
        }

    }

    private fun handleLoadingState(loadingState: LoadingState){
        binding.progressBar.visibility = if(loadingState is LoadingState.ShowLoading) VISIBLE else GONE
    }

    private fun handleUserDetailResult(userData: UserDetail){
        binding.apply {

            Glide.with(applicationContext)
                .load(userData.avatarUrl)
                .into(imgProfile)

            profileName.text = userData.name
            profileUsername.text = userData.login
            profileCompany.text = userData.company
            profileLocation.text = userData.location
            profileFollowers.text = userData.followers.toString()
            profileFollowing.text = userData.following.toString()
            profileRepository.text = userData.publicRepos.toString()
        }
    }

    companion object {
        const val USERNAME = EXTRA_USERNAME
    }
}