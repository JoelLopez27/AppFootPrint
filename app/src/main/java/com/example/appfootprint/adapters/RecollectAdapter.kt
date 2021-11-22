package com.example.appfootprint.adapters

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.appfootprint.R
import com.example.appfootprint.db.UserRecollect
import kotlinx.android.synthetic.main.list_recollection.view.*
import java.security.AccessController.getContext

class RecollectAdapter: RecyclerView.Adapter<RecollectAdapter.RecollectViewHolder>() {


    private var mUserRecollectList: MutableList<UserRecollect>? = null

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

             /*   val recollectCircle = (holder.itemView.kgCo2TextView.background) as GradientDrawable
                val recollectColor = getRecollectolor(recollect.co2.toFloat())
                recollectCircle.setColor(recollectColor)  */
                }

            }



         private fun getRecollectolor(co2: Float): Int {

        return when (co2) {

            in 1f..15f -> R.color.severe_thinness

            in 15.1f..16f -> R.color.moderate_thinness

            in 17f..18.5f -> R.color.mild_thinness

            in 18.5f..25f -> R.color.normal

            in 25.1f..30f -> R.color.overweight

            in 30f..35f -> R.color.Obese_class_1

            in 35.1f..40f -> R.color.Obese_class_2

            in 40.1f..40f -> R.color.Obese_class_3

            else -> R.color.principal
        }
    }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        fun swapList(userUserList: MutableList<UserRecollect>) {
            mUserRecollectList = userUserList
            notifyDataSetChanged()
        }


}


