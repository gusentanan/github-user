package com.bagusmerta.github_user.presentation.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val binding: ActivityDetailBinding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initActionBar()
        initStateObserver()
    }

    private fun initActionBar(){
        supportActionBar?.title = resources.getString(R.string.detail_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initStateObserver(){
        val username = intent.getSerializableExtra(USERNAME) as String
        with(detailViewModel){
            getDetailUser(username = username)
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
        const val USERNAME = "username"
    }
}