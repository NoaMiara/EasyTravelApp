package com.app.easy.travel.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.app.easy.travel.R
import com.app.easy.travel.intarface.RecyclerViewInterface
import com.app.easy.travel.model.Image
import java.util.ArrayList

class ImageRecyclerViewAdapter(
    private val imageBitmap: ArrayList<Image>,
    private val recyclerViewInterface: RecyclerViewInterface
) :
    RecyclerView.Adapter<ImageRecyclerViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to ‚hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = imageBitmap[position]
        // sets the text to the textview from our itemHolder class
        holder.imageView.setImageBitmap(itemsViewModel.image)

    }

    override fun getItemCount(): Int {
        return imageBitmap.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnLongClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.ticket_image)
        init {
            view.setOnLongClickListener(this)
        }
        override fun onLongClick(p0: View?): Boolean {
            val pos = bindingAdapterPosition
            recyclerViewInterface.onItemClick(true, pos)
            return true;

        }
    }


}