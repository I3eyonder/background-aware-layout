package com.hieupt.backgroundawareconstraintlayoutdemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.hieupt.view.BackgroundAwareMode
import com.hieupt.view.extensions.backgroundAware
import com.hieupt.view.extensions.backgroundAwareMode
import com.hieupt.view.extensions.backgroundAwarePathCreator
import com.hieupt.view.graphic.RoundRectClipPathCreator
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
            private val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)

            override fun bind() {
                tvContent.text = "This message from owner $adapterPosition"
                val radius = tvContent.context.resources.getDimension(R.dimen.item_corner_radius)
                tvContent.backgroundAwarePathCreator =
                    RoundRectClipPathCreator(0f, radius, 0f, radius)
                ivIcon.setOnClickListener {
                    if (it.isActivated) {
                        it.backgroundAware = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.ic_thumb_up_black_24dp
                        )
                    } else {
                        it.backgroundAware =
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.ic_thumb_down_alt_black_24dp
                            )
                    }
                    it.isActivated = !it.isActivated
                }
            }
        }

        class OtherViewHolder(itemView: View) : ItemViewHolder(itemView) {

            private val tvContent: TextView = itemView.findViewById(R.id.tvContent)

            override fun bind() {
                tvContent.text = "This message from other $adapterPosition"
                tvContent.setOnClickListener {
                    val newMode = if (it.backgroundAwareMode == BackgroundAwareMode.TINT) {
                        BackgroundAwareMode.CLEAR
                    } else {
                        BackgroundAwareMode.TINT
                    }
                    it.backgroundAwareMode = newMode
                }
            }
        }
    }
}