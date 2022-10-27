package com.aldhykohar.first_submission_intermediate.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aldhykohar.first_submission_intermediate.data.model.list_story.ListStoryItem
import com.aldhykohar.first_submission_intermediate.databinding.ItemListStoryBinding
import com.squareup.picasso.Picasso

/**
 * Created by aldhykohar on 4/1/2021.
 */
class ListStoryAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<ListStoryAdapter.ItemViewHolder>() {

    private var data = ArrayList<ListStoryItem>()

    fun setData(item: List<ListStoryItem>) {
        if (item.isEmpty()) return
        val oldPos = data.size
        data.addAll(item)
        notifyItemRangeInserted(oldPos, data.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemListStoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class ItemViewHolder(private val binding: ItemListStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ListStoryItem) {
            with(binding) {
                nameTV.text = item.name
                Picasso.get().load(item.photoUrl).into(circleImageView)

                root.setOnClickListener { onClick(item.id ?: "") }
            }
        }
    }
}