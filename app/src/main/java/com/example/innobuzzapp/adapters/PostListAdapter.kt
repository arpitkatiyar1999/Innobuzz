package com.example.innobuzzapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.innobuzzapp.databinding.ItemPostBinding
import com.example.innobuzzapp.listeners.PostListClickListener
import com.example.innobuzzapp.local_db.entity.PostEntity

class PostListAdapter(
    private val postList: ArrayList<PostEntity>,
    private val postListClickListener: PostListClickListener
) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = postList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position])
    }


    inner class ViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                postListClickListener.onPostClicked(postList[adapterPosition])
            }
        }

        fun bind(post: PostEntity) {
            with(binding) {
                titleTv.text = post.title
            }
        }

    }
}