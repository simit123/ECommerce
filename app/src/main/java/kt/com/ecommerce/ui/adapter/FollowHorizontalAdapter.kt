package kt.com.ecommerce.ui.adapter

import android.content.Context
import android.media.Image
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.durationFormat
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kt.com.ecommerce.R

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/15 ADD
 */
class FollowHorizontalAdapter(val mContext: Context, var mList: ArrayList<HomeBean.Issue.Item>) : RecyclerView.Adapter<FollowHorizontalAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mList.get(position).data.let {
            Glide.with(mContext)
                    .load(mList.get(position).data!!.cover.feed)
//                .placeholder(R.drawable.placeholder_banner)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.ivCoverFeed)
            holder.tvTitle.setText(mList.get(position).data!!.title)
            // 格式化时间
            val timeFormat = durationFormat(mList.get(position).data?.duration)
            holder.tvTitle.setText(timeFormat)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_follow_horizontal, parent, false))
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ivCoverFeed = itemView.findViewById<ImageView>(R.id.iv_cover_feed)
        var tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        var tvTag = itemView.findViewById<TextView>(R.id.tv_tag)

    }

}