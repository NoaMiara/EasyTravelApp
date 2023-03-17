package com.app.easy.travel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.R
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.model.Image

class FileRecyclerViewAdapter(
    private val recyclerViewInterface: RecyclerViewInterface,
    private val fileList: ArrayList<Image> = ArrayList()
) : RecyclerView.Adapter<FileRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileViewModel = fileList[position]

        holder.imageView.setImageBitmap(fileViewModel.image)

    }

    override fun getItemCount(): Int {
        return fileList.size
    }


    fun updateFile(image: List<Image>) {
        this.fileList.clear()
        this.fileList.addAll(image)
        notifyDataSetChanged()
    }

    fun removeFile(position: Int) {
        notifyItemRemoved(position)
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener,
        View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.ticket_image)

        init {
            view.setOnLongClickListener(this)
            view.setOnClickListener(this)
        }

        override fun onLongClick(p0: View?): Boolean {
            val pos = bindingAdapterPosition
            recyclerViewInterface.onItemClick(true, pos)
            return true
        }

        override fun onClick(p0: View?) {
            val pos = bindingAdapterPosition
            recyclerViewInterface.onItemClick(false, pos)
        }


    }
}