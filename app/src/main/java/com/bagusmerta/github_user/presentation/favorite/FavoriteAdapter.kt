package com.bagusmerta.github_user.presentation.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.github_user.core.domain.model.FavoriteUser
import com.bagusmerta.github_user.databinding.ItemRowUsersBinding
import com.bagusmerta.github_user.presentation.detail.DetailActivity
import com.bumptech.glide.Glide

class FavoriteAdapter(private val context: Context) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>()  {
    private var items = mutableListOf<FavoriteUser>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.FavoriteViewHolder {
        val binding = ItemRowUsersBinding.inflate(LayoutInflater.from(context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount(): Int = items.size

    inner class FavoriteViewHolder(private var binding: ItemRowUsersBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FavoriteUser){
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .into(imgItem)
                tvItemName.text = item.username

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.USERNAME, item.username)
                    })
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<FavoriteUser>){
        this.items = data
        notifyDataSetChanged()
    }
}