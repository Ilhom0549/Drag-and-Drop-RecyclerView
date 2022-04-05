package uz.ilkhomkhuja.draganddroprecyclerview.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.ActivityScoped
import uz.ilkhomkhuja.draganddroprecyclerview.R
import uz.ilkhomkhuja.draganddroprecyclerview.databinding.ItemSongBinding
import uz.ilkhomkhuja.draganddroprecyclerview.models.SongData
import javax.inject.Inject

@ActivityScoped
class SongAdapter @Inject constructor() : ListAdapter<SongData, SongAdapter.Vh>(SongDiffUtil()) {

    var onLongClickListener: ((RecyclerView.ViewHolder) -> Unit)? = null

    inner class Vh(var itemSongBinding: ItemSongBinding) :
        RecyclerView.ViewHolder(itemSongBinding.root) {
        fun onBind(songData: SongData) {
            itemSongBinding.apply {
                authorNameTv.text = songData.authorName
                songNameTv.text = songData.songName
                val animation =
                    AnimationUtils.loadAnimation(root.context, R.anim.item_animation)
                root.animation = animation
                root.setOnLongClickListener {
                    onLongClickListener?.invoke(this@Vh)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemSongBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }


    class SongDiffUtil : DiffUtil.ItemCallback<SongData>() {
        override fun areItemsTheSame(oldItem: SongData, newItem: SongData): Boolean {
            return oldItem.authorName == newItem.authorName
        }

        override fun areContentsTheSame(oldItem: SongData, newItem: SongData): Boolean {
            return oldItem == newItem
        }
    }
}