package com.example.appfootprint.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appfootprint.R
import com.example.appfootprint.db.UserRecollect
import kotlinx.android.synthetic.main.list_recollection.view.*

class RecollectAdapter: RecyclerView.Adapter<RecollectAdapter.RecollectViewHolder>() {


        inner class RecollectViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)


        private val differCallback = object : DiffUtil.ItemCallback<UserRecollect>() {

        override fun areItemsTheSame(oldItem: UserRecollect, newItem: UserRecollect): Boolean {
            return oldItem.cantidad == newItem.cantidad
        }

        override fun areContentsTheSame(oldItem: UserRecollect, newItem: UserRecollect): Boolean {
            return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecollectViewHolder {
            return RecollectViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.list_recollection,
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: RecollectViewHolder, position: Int) {
            val recollect = differ.currentList[position]
            holder.itemView.apply {
                materialTextView.text = recollect.material
                cantMaterialTextView.text = String.format(context.getString(R.string.cant_material), recollect.cantidad)
                dateRecollectTextView.text = recollect.fecha
                kgCo2TextView.text = recollect.co2.toString()
            }
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }
    }


