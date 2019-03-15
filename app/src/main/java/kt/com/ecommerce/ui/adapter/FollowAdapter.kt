package kt.com.ecommerce.ui.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.MediaController
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kt.com.ecommerce.R

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/15 ADD
 */
class FollowAdapter(val mContext: Context, var mList: ArrayList<HomeBean.Issue.Item>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>() {


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        mList.get(position).data!!.header.let {

            Glide.with(mContext)
                    .load(mList.get(position).data!!.header.icon)
//                .placeholder(R.drawable.placeholder_banner)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.ivAvatar)

            holder.tvTitle.setText(mList.get(position).data!!.header.title)
            holder.tvDesc.setText(mList.get(position).data!!.header.description)
            holder.flRecyclerView.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)
            holder.flRecyclerView.adapter = FollowHorizontalAdapter(mContext, mList.get(position).data!!.itemList)

        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_follow,parent,false));
    }

    class ViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        var ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
        var tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        var tvDesc = itemView.findViewById<TextView>(R.id.tv_desc)
        var tvFollow = itemView.findViewById<TextView>(R.id.tv_follow)
        var flRecyclerView = itemView.findViewById<RecyclerView>(R.id.fl_recyclerView)
    }
    fun addData(dataList: ArrayList<HomeBean.Issue.Item>) {
        mList.addAll(dataList)
        notifyDataSetChanged()
    }
}