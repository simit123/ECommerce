package kt.com.ecommerce.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kt.com.ecommerce.R

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/15 ADD
 */
class CategoryAdapter(val mContext:Context,var mList:ArrayList<CategoryBean>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_category,parent,false))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var data:CategoryBean = mList.get(position)
        data.let {

            Glide.with(mContext)
                    .load(data.bgPicture)
//                .placeholder(R.drawable.placeholder_banner)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.ivCategory)
            holder.tvCategoryName.setText(data.name)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivCategory = itemView.findViewById<ImageView>(R.id.iv_category)
        var tvCategoryName = itemView.findViewById<TextView>(R.id.tv_category_name)
    }

    fun addData(dataList: ArrayList<CategoryBean>) {
        mList.addAll(dataList)
        notifyDataSetChanged()
    }
}