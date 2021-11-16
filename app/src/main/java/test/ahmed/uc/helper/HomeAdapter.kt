package test.ahmed.uc.helper

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.data_item.view.*
import test.ahmed.uc.R
import test.ahmed.uc.model.Result


class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private var resultList = ArrayList<Result>()
    private lateinit var context: Context


    constructor(
        resultList: ArrayList<Result>,
        context: Context,
    ) : this() {
        this.resultList = resultList
        this.context = context

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: Result = resultList[position]
        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(android.R.color.darker_gray)
            .error(android.R.color.darker_gray)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        try {
            Glide.with(context).load(model.media[0].mediaMetadata[0].url)
                .apply(options)
                .into(holder.image)
        } catch (e: Exception) {
            holder.image.setImageResource(android.R.color.darker_gray)
        }

        holder.title.text = model.title
        holder.description.text = model.abstract
        holder.date.text = model.publishedDate
        holder.title.text = model.title


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val image: ImageView = itemView.img
        val title: TextView = itemView.title
        val description: TextView = itemView.description
        val date: TextView = itemView.date


    }
}


