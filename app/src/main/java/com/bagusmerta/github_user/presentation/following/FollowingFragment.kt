package com.bagusmerta.github_user.presentation.following

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.LoadingState
import com.bagusmerta.github_user.core.utils.makeGone
import com.bagusmerta.github_user.core.utils.makeVisible
import com.bagusmerta.github_user.databinding.FragmentFollowingBinding
import com.bagusmerta.github_user.presentation.detail.DetailActivity
import com.bagusmerta.github_user.presentation.viewpager.ListFollowersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowingFragment : Fragment() {

    private val followingViewModel: FollowingViewModel by viewModel()
    private val binding: FragmentFollowingBinding by lazy { FragmentFollowingBinding.inflate(layoutInflater) }
    private val listAdapter: ListFollowersAdapter by lazy { ListFollowersAdapter(requireContext()) }
    private var listUsers = mutableListOf<UsersItemSearch>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailActivity = activity as DetailActivity
        val usersUsername = detailActivity.getUsernameUsers()
        usersUsername.let { followingViewModel.getUsersFollowing(usersUsername) }
        initStateObserver()
        initRecyclerView()
    }

    private fun initRecyclerView(){
        with(binding){
            rvViewPagerFollowing.layoutManager = LinearLayoutManager(context)
            rvViewPagerFollowing.adapter = listAdapter
        }
    }

    private fun initStateObserver(){
        with(followingViewModel){
            state.observe(viewLifecycleOwner){
                handleLoadingState(it)
            }
            resFollowing.observe(viewLifecycleOwner){
                it?.let { data -> handleUsersFollowing(data) }
            }
        }
    }

    private fun handleUsersFollowing(data: List<UsersItemSearch>) {
        handleEmptyResult(data)
        listUsers.clear()
        listUsers.addAll(data)
        listAdapter.setItems(listUsers)
    }

    private fun handleLoadingState(loadingState: LoadingState){
        binding.progressBar.let {
            if (loadingState is LoadingState.ShowLoading) it.makeVisible() else it.makeGone()
        }
    }

    private fun handleEmptyResult(data: List<UsersItemSearch>){
        if (data.isEmpty()){
            binding.tvNoData.makeVisible()
        } else {
            binding.tvNoData.makeGone()
        }
    }

}