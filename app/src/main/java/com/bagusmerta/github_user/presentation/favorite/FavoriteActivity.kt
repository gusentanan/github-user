package com.bagusmerta.github_user.presentation.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.core.utils.LoadingState
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

        initRecyclerView()
        initStateObserver()

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
               handleUsersResult(it)
           }
       }
    }

    private fun handleUsersResult(data: List<FavoriteUser>){
        handleEmptyState(true)
        items.clear()
        items.addAll(data)
        favoriteAdapter.setItems(items)
    }

    private fun handleLoadingState(loading: LoadingState){
        binding.apply {
            if(loading is LoadingState.ShowLoading){
                progressBar.visibility = View.VISIBLE
                handleEmptyState(false)
                rvUsersFavorite.visibility = View.GONE
            } else {
                progressBar.visibility = View.VISIBLE
                handleEmptyState(false)
                rvUsersFavorite.visibility = View.GONE
            }
        }
    }

    private fun handleEmptyState(status: Boolean){
        binding.lottieView.root.visibility = if (status) View.VISIBLE else View.GONE
    }
}