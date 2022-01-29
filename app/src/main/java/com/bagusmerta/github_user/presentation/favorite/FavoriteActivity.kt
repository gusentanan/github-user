package com.bagusmerta.github_user.presentation.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.utils.makeGone
import com.bagusmerta.github_user.core.utils.makeVisible
import com.bagusmerta.github_user.databinding.ActivityFavoriteBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private val binding: ActivityFavoriteBinding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private val favoriteAdapter: FavoriteAdapter by lazy { FavoriteAdapter(this) }
    private var items = mutableListOf<FavoriteUser>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_users)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initRecyclerView()
        initStateObserver()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initRecyclerView() {
        with(binding){
            rvUsersFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsersFavorite.setHasFixedSize(true)
            rvUsersFavorite.adapter = favoriteAdapter
        }
    }

    private fun initStateObserver() {
       with(favoriteViewModel){
           getAllFavoriteUsers().observe(this@FavoriteActivity){
               if(it.isNotEmpty()) {
                   handleEmptyState(false)
                   handleUsersResult(it)
               } else {
                   handleEmptyState(true)
                   handleUsersResult(it)
               }
           }
       }
    }

    private fun handleUsersResult(data: List<FavoriteUser>){
        items.clear()
        items.addAll(data)
        favoriteAdapter.setItems(items)
    }

    private fun handleEmptyState(status: Boolean){
        binding.lottieView.root.let {
            if (status) it.makeVisible() else it.makeGone()
        }
    }
}