package com.udacity.asteroidradar.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.HolderAsteroidBinding
import com.udacity.asteroidradar.models.domainmodels.Asteroid


class AsteroidRecyclerViewAdapter : RecyclerView.Adapter<AsteroidRecyclerViewAdapter.AsteroidViewHolder> (){

    inner class  AsteroidViewHolder(private val itemViewBinding: HolderAsteroidBinding): RecyclerView.ViewHolder(
            itemViewBinding.root
    ) {

        fun bindView(asteroidItem: Asteroid) {
            itemViewBinding.apply {
              asteroid= asteroidItem
            }

            itemViewBinding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(asteroidItem)

                }
            }
        }
    }

    private val differCallBack  = object : DiffUtil.ItemCallback<Asteroid>()
    {

        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return  oldItem.id== newItem.id
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return  oldItem==newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(

                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.holder_asteroid, parent, false
                )
        )
    }

    private var onItemClickListener: ((Asteroid) -> Unit)? = null
    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {

        val asteroidItem= differ.currentList[position]
        holder.bindView(asteroidItem)
    }




    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnItemClickListener(listener: (Asteroid) -> Unit) {
        onItemClickListener = listener

    }
}