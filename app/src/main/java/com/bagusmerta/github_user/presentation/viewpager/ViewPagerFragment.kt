package com.bagusmerta.github_user.presentation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bagusmerta.github_user.R
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.databinding.FragmentViewPagerBinding
import com.bagusmerta.github_user.presentation.detail.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ViewPagerFragment : Fragment() {

    private val binding: FragmentViewPagerBinding by lazy { FragmentViewPagerBinding.inflate(layoutInflater) }
    private val detailViewModel: DetailViewModel by viewModel()
    private var items = mutableListOf<UsersItemSearch>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }
}