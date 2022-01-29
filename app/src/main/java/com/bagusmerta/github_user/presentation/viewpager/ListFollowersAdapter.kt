package com.bagusmerta.github_user.presentation.viewpager

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.github_user.core.domain.model.UsersItemSearch
import com.bagusmerta.github_user.core.utils.loadImage
import com.bagusmerta.github_user.databinding.ItemRowUsersBinding

class ListFollowersAdapter(private val context: Context): RecyclerView.Adapter<ListFollowersAdapter.ViewHolder>()  {

    private var items = mutableListOf<UsersItemSearch>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFollowersAdapter.ViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFollowersAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(private var binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UsersItemSearch){
            binding.apply {
                item.avatar?.let { imgItem.loadImage(it) }
                tvItemName.text = item.login
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<UsersItemSearch>){
        this.items = data
        notifyDataSetChanged()
    }

}