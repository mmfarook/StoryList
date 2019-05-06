package com.example.storylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.storylist.R
import com.example.storylist.model.StoryItems
import com.example.storylist.repository.ItemType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.collection_item.view.*
import kotlinx.android.synthetic.main.collection_sub_item.view.*
import kotlinx.android.synthetic.main.story_item.view.*


class StoryAdapter(private val mItemClickListener: ItemClickListener?) :
    ListAdapter<StoryItems, StoryAdapter.ViewHolder>(diffCallback) {

    private val VIEWTYPE_COLLECTION = 1
    private val VIEWTYPE_COLLECTION_SUBSTORY = 2
    private val VIEWTYPE_STORY = 3

    interface ItemClickListener {
        fun onItemClick(storyItem: StoryItems, view: View)
    }

    abstract inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bindTo(position: Int, storyItem: StoryItems)
    }

    inner class CollectionViewHolder(itemView: View) : ViewHolder(itemView) {
        private val titleView: TextView = itemView.collection_title

        override fun bindTo(position: Int, storyItem: StoryItems) {
            titleView.text = storyItem.name
        }
    }

    open inner class StoryViewHolder(itemView: View) : ViewHolder(itemView) {
        protected lateinit var imageView: ImageView
        protected lateinit var headlineView: TextView
        protected lateinit var authorView: TextView

        override fun bindTo(position: Int, storyItem: StoryItems) {
            if (mItemClickListener != null) {
                itemView.setOnClickListener { mItemClickListener.onItemClick(storyItem, imageView) }
                ViewCompat.setTransitionName(imageView, storyItem.id)
            }
            headlineView.text = storyItem.story?.headline
            authorView.text = storyItem.story?.authorName
            Picasso.get().load(storyItem.story?.heroImage).placeholder(R.drawable.placeholder_image).into(imageView)
        }
    }

    inner class MainStoryViewHolder(itemView: View) : StoryViewHolder(itemView) {
        init {
            imageView = itemView.header_image
            headlineView = itemView.headline
            authorView = itemView.author_name
        }
    }

    inner class SubStoryViewHolder(itemView: View) : StoryViewHolder(itemView) {
        init {
            imageView = itemView.sub_header_image
            headlineView = itemView.sub_headline
            authorView = itemView.sub_author_name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View?
        if (viewType == VIEWTYPE_COLLECTION) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_item, parent, false)
            return CollectionViewHolder(itemView)
        } else if (viewType == VIEWTYPE_COLLECTION_SUBSTORY) {
            itemView = LayoutInflater.from(parent.context).inflate(R.layout.collection_sub_item, parent, false)
            return SubStoryViewHolder(itemView)
        }
        itemView = LayoutInflater.from(parent.context).inflate(R.layout.story_item, parent, false)
        return MainStoryViewHolder(itemView)
    }

    override fun onBindViewHolder(mholder: ViewHolder, position: Int) {
        val story = getItem(position)
        mholder.bindTo(position, story)
    }

    override fun getItemViewType(position: Int): Int {
        val story = getItem(position)
        if (ItemType.COLLECTION.toString() == story.subType) {
            return VIEWTYPE_COLLECTION
        } else if (ItemType.COLLECTION_SUB_STORY.toString() == story.subType) {
            return VIEWTYPE_COLLECTION_SUBSTORY
        }
        return VIEWTYPE_STORY
    }


    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<StoryItems>() {
            override fun areItemsTheSame(oldItem: StoryItems, newItem: StoryItems): Boolean {
                return oldItem.id.equals(newItem.id)
            }

            override fun areContentsTheSame(oldItem: StoryItems, newItem: StoryItems): Boolean {
                return areItemsTheSame(oldItem, newItem)

            }
        }
    }
}