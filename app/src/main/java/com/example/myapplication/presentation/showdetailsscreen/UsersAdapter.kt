package com.example.myapplication.presentation.showdetailsscreen

import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.User
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.ArrayList

class UsersAdapter(
    private val usersList: ArrayList<User>
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_expandable_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var expandStatus = false
        val user = usersList[position]
        if (!expandStatus) {
            holder.expandView.layoutParams.height = 0
        }

        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING

        holder.userName.text = user.name
        holder.userAge.text = user.age
        holder.userGender.text = user.gender
        holder.userJobTitle.text = user.jobTitle

        holder.userItemView.setOnClickListener {
            expandStatus = if (expandStatus) {
                changeViewSizeWithAnimation(holder.expandView, 0, 200L)
                false
            } else {
                changeViewSizeWithAnimation(holder.expandView, 300, 500L)
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val userName: TextView = itemView.findViewById(R.id.user_name)
        val userAge: TextView = itemView.findViewById(R.id.age)
        val userGender: TextView = itemView.findViewById(R.id.gender)
        val userJobTitle: TextView = itemView.findViewById(R.id.jobTitle)
        val expandView: ConstraintLayout = itemView.findViewById(R.id.expand_view)
        val userItemView: ConstraintLayout = itemView.findViewById(R.id.user_item_view)
    }

    private fun changeViewSizeWithAnimation(view: View, viewSize: Int, duration: Long) {
        val startViewSize = view.measuredHeight
        val endViewSize =
            if (viewSize < startViewSize) (viewSize) else (view.measuredHeight + viewSize)
        val valueAnimator =
            ValueAnimator.ofInt(startViewSize, endViewSize)
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

}