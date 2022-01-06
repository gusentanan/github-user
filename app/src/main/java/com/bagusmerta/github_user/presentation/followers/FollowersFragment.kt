package com.bagusmerta.github_user.presentation.followers

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
import com.bagusmerta.github_user.databinding.FragmentFollowersBinding
import com.bagusmerta.github_user.presentation.detail.DetailActivity
import com.bagusmerta.github_user.presentation.viewpager.ListFollowersAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowersFragment : Fragment() {

    private val followersVieModel: FollowersViewModel by viewModel()
    private val binding: FragmentFollowersBinding by lazy { FragmentFollowersBinding.inflate(layoutInflater) }
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
        val username = detailActivity.getUsernameUsers()
        followersVieModel.getUsersFollowers(username)
        initStateObserver()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        with(binding){
            rvViewPagerFollowers.layoutManager = LinearLayoutManager(context)
            rvViewPagerFollowers.adapter = listAdapter
        }
    }

    private fun initStateObserver() {
        with(followersVieModel){
            state.observe(viewLifecycleOwner){
                handleLoadingState(it)
            }
            resFollowers.observe(viewLifecycleOwner){
                it?.let { data -> handleUsersFollowers(data) }
            }
        }
    }

    private fun handleUsersFollowers(data: List<UsersItemSearch>) {
        handleEmptyResult(data)
        listUsers.clear()
        listUsers.addAll(data)
        listAdapter.setItems(listUsers)
    }

    private fun handleLoadingState(loadingState: LoadingState){
        binding.progressBar.visibility = if(loadingState is LoadingState.ShowLoading) VISIBLE else GONE
    }

    private fun handleEmptyResult(data: List<UsersItemSearch>){
        if (data.isEmpty()){
            binding.tvNoData.visibility = VISIBLE
        } else {
            binding.tvNoData.visibility = GONE
        }
    }
}