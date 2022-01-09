package com.bagusmerta.github_user.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.utils.Constants.EXTRA_USERNAME
import com.bagusmerta.github_user.core.utils.DataMapper
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

    private var favoriteUser: FavoriteUser? = null
    private var detailUser: UserDetail? = null
    private var favoriteState = false


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
        favoriteButtonState(false)
        binding.fabFavorite.setOnClickListener {
            setFavoriteUser()
        }
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

    private fun setFavoriteUser(){
        if (favoriteState){
            favoriteUser?.let { detailViewModel.deleteFavoriteUser(it) }
            favoriteButtonState(false)
        }else{
            val fav = detailUser?.let { DataMapper.mapDetailUserToFavoriteUser(it) }
            fav?.let { detailViewModel.addFavoriteUser(it) }
            favoriteButtonState(true)
        }
    }

    private fun initStateObserver(){
        val username = intent.getSerializableExtra(USERNAME) as String
        detailViewModel.getFavoriteUserByUsername(username)
        with(detailViewModel){
            getDetailUser(username)
            state.observe(this@DetailActivity){
                handleLoadingState(it)
            }
            result.observe(this@DetailActivity){
                it?.let { data -> handleUserDetailResult(data) }
            }
            resFavorite.observe(this@DetailActivity){
                it?.let { favoriteUser = it }
                if (favoriteUser != null){
                    favoriteState = true
                    favoriteButtonState(favoriteState)
                }
            }
            insertState.observe(this@DetailActivity){
                handleInsertState(it)
            }
            deleteState.observe(this@DetailActivity){
                handleDeleteState(it)
            }
        }
    }

    private fun handleLoadingState(loadingState: LoadingState){
        binding.progressBar.visibility = if(loadingState is LoadingState.ShowLoading) VISIBLE else GONE
    }

    private fun handleUserDetailResult(userData: UserDetail){
        detailUser = userData
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

    private fun favoriteButtonState(state: Boolean){
        if(state){
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
        }else{
            binding.fabFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    private fun handleInsertState(state: Boolean){
        if (state){
            Toast.makeText(this, getString(R.string.add_to_favorite_success), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, getString(R.string.add_to_favorite_failed), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleDeleteState(state: Boolean){
        if (state){
            Toast.makeText(this, getString(R.string.delete_favorite_success), Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, getString(R.string.delete_favorite_failed), Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val USERNAME = EXTRA_USERNAME
    }
}