package com.bagusmerta.github_user.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.github_user.core.domain.model.UserDetail
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.loadImage
import com.bagusmerta.github_user.databinding.ItemRowUsersBinding
import com.bagusmerta.github_user.presentation.detail.DetailActivity

class MainAdapter(private val context: Context) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var items = mutableListOf<UserDetail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(context), parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.MainViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

    inner class MainViewHolder(private var binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(item: UserDetail){
                binding.apply {
                    tvItemName.text = item.login
                    tvItemLocation.text = item.location
                    tvItemRepo.text = item.publicRepos.toString()
                    imgItem.loadImage(item.avatarUrl)

                    itemView.setOnClickListener {
                        context.startActivity(Intent(context, DetailActivity::class.java).apply {
                            putExtra(DetailActivity.USERNAME, item.login)
                        })
                    }
                }
            }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<UserDetail>){
        this.items = data
        notifyDataSetChanged()
    }

}