package com.bagusmerta.github_user.presentation.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.utils.*
import com.bagusmerta.github_user.core.utils.Constants.EXTRA_USERNAME
import com.bagusmerta.github_user.databinding.ActivityDetailBinding
import com.bagusmerta.github_user.presentation.viewpager.ViewPagerAdapter
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
            favoriteState = false
            favoriteButtonState(favoriteState)
        }else{
            val fav = detailUser?.let { DataMapper.mapDetailUserToFavoriteUser(it) }
            fav?.let { detailViewModel.addFavoriteUser(it) }
            favoriteState = true
            favoriteButtonState(favoriteState)
        }
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
            insertState.observe(this@DetailActivity){
                handleInsertState(it)
            }
            deleteState.observe(this@DetailActivity){
                handleDeleteState(it)
            }
            getFavoriteUserByUsername(username).observe(this@DetailActivity){
                it?.let {
                    favoriteUser = it
                    favoriteState = true
                    favoriteButtonState(favoriteState)
                }
            }
        }
    }

    private fun handleUserDetailResult(userData: UserDetail){
        detailUser = userData
        binding.apply {
            userData.avatarUrl?.let { imgProfile.loadImage(it) }

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
        binding.fabFavorite.let {
            if (state){
                it.setImageResource(R.drawable.ic_baseline_favorite_24)
            }else {
                it.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
        }
    }

    private fun handleInsertState(state: Boolean){
        this@DetailActivity.let {
            if (state){
                it.makeToast(getString(R.string.add_to_favorite_success))
            }else{
                it.makeToast(getString(R.string.add_to_favorite_failed))
            }
        }
    }

    private fun handleDeleteState(state: Boolean){
        this@DetailActivity.let {
            if (state){
                it.makeToast(getString(R.string.delete_favorite_success))
            }else{
                it.makeToast(getString(R.string.delete_favorite_failed))
            }
        }
    }

    private fun handleLoadingState(loadingState: LoadingState){
        binding.progressBar.let {
            if (loadingState is LoadingState.ShowLoading) it.makeVisible() else it.makeGone()
        }
    }

    companion object {
        const val USERNAME = EXTRA_USERNAME
    }
}