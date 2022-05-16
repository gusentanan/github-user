package com.bagusmerta.github_user.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.core.utils.makeGone
import com.bagusmerta.github_user.core.utils.makeVisible
import com.bagusmerta.github_user.databinding.ActivityMainBinding
import com.bagusmerta.github_user.presentation.favorite.FavoriteActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val items = mutableListOf<UserDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSplashScreen()
        setContentView(binding.root)

        supportActionBar?.elevation = 0f
        initView()
        initRecyclerView()
        initSearchMenu()
        initStateObserver()
    }

    private fun initView() {
        binding.btnFavorite.setOnClickListener {
            startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
        }
    }

    private fun initSplashScreen(){
        installSplashScreen().apply {
            setKeepVisibleCondition {
                mainViewModel.splashState.value
            }
        }
    }

    private fun initSearchMenu(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.svSearch

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    items.clear()
                    mainViewModel.getUsersByUsername(query)
                    searchView.clearFocus()
                    handleEmptyState(false)
                }else{
                    searchView.clearFocus()
                    handleEmptyState(true)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean = false

        })
    }

    private fun initStateObserver(){
        with(mainViewModel){
            state.observe(this@MainActivity) {
                it?.let { handleLoadingState(it) }
            }
            result.observe(this@MainActivity) {
                it?.let { handleUsersResult(it) }
            }
        }
    }

    private fun initRecyclerView(){
        with(binding){
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = mainAdapter
        }
    }

    private fun handleUsersResult(result: List<UserDetail>) {
        items.clear()
        items.addAll(result)
        mainAdapter.setItems(items)
    }


    private fun handleLoadingState(loading: LoadingState) {
        binding.apply {
            if (loading is LoadingState.ShowLoading) {
                progressBar.makeVisible()
                lottieView.root.makeGone()
                rvUsers.makeGone()
            } else {
                progressBar.makeGone()
                lottieView.root.makeGone()
                rvUsers.makeVisible()
            }
        }
    }

    private fun handleEmptyState(status: Boolean) {
        binding.lottieView.root.let {
            if (status) it.makeVisible() else it.makeGone()
        }
    }
}
