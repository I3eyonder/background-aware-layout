package com.hieupt.backgroundawareconstraintlayoutdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

/**
 * Created by HieuPT on 10/19/2020.
 */
class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    private val viewTypes = mutableListOf<Boolean>()

    init {
        repeat(itemCount) {
            viewTypes.add(Random.nextBoolean())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        LayoutInflater.from(parent.context).run {
            when (viewType) {
                R.id.view_type_owner -> ItemViewHolder.OwnerViewHolder(
                    inflate(
                        R.layout.item_owner,
                        parent,
                        false
                    )
                )
                else -> ItemViewHolder.OtherViewHolder(inflate(R.layout.item_other, parent, false))
            }
        }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (viewTypes[position]) R.id.view_type_owner else R.id.view_type_other
    }

    override fun getItemCount(): Int = 100

    sealed class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        abstract fun bind()

        class OwnerViewHolder(itemView: View) : ItemViewHolder(itemView) {

            private val tvContent: TextView = itemView.findViewById(R.id.tvContent)

            override fun bind() {
                tvContent.text = "This message from owner $adapterPosition"
            }
        }

        class OtherViewHolder(itemView: View) : ItemViewHolder(itemView) {

            private val tvContent: TextView = itemView.findViewById(R.id.tvContent)

            override fun bind() {
                tvContent.text = "This message from other $adapterPosition"
            }
        }
    }
}