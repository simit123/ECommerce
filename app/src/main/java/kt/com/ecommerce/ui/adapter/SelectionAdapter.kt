package kt.com.ecommerce.ui.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.hazz.kotlinmvp.durationFormat
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import kt.com.ecommerce.R


/**
 * 修改番号 INLS - NEW - 201810 - 003 修改简介 wuy 2018/11/29 ADD
 */
class SelectionAdapter(val mContext: Context, var data: ArrayList<HomeBean.Issue.Item>) : RecyclerView.Adapter<SelectionAdapter.ViewHolder>() {


    private var mInflater: LayoutInflater? = null

    var bannerSize: Int = 0

    init {
        mInflater = LayoutInflater.from(mContext)
    }

    companion object {
        private val ITEM_TYPE_BANNER = 1 //banner
        private val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private val ITEM_TYPE_CONTENT = 3    //item

    }

    /**
     * 添加更多数据
     */
    fun addItemData(itemList: ArrayList<HomeBean.Issue.Item>) {
        this.data.addAll(itemList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> ITEM_TYPE_BANNER
            data[position + bannerSize - 1].type == "textHeader" -> ITEM_TYPE_TEXT_HEADER
            else -> {
                ITEM_TYPE_CONTENT
            }
        }
    }

    fun setBannerSize1(count: Int) {
        bannerSize = count
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            ITEM_TYPE_BANNER ->
                ViewHolder(inflaterView(R.layout.selection_item_banner, parent))
            ITEM_TYPE_TEXT_HEADER ->
                ViewHolder(inflaterView(R.layout.selection_item_header, parent))
            else ->
                ViewHolder(inflaterView(R.layout.selection_item_content, parent))

        }

    }

    override fun getItemCount(): Int {
        return data.size - bannerSize - 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ITEM_TYPE_BANNER -> {
                var bannerItemData = data.take(bannerSize).toCollection(ArrayList())
                val bannerFeedList = ArrayList<String>()
                val bannerTitleList = ArrayList<String>()
                //取出banner 要显示的image 和 文字
                Observable.fromIterable(bannerItemData)
                        .subscribe({ list ->
                            bannerFeedList.add(list.data?.cover?.feed ?: "")
                            bannerTitleList.add(list.data?.title ?: "")
                        })
//                设置banner
                with(holder) {
                    holder.banner.run {
                        setAutoPlayAble(bannerFeedList.size > 1)
                        setData(bannerFeedList, bannerTitleList)
                        setAdapter(object : BGABanner.Adapter<ImageView, String> {
                            override fun fillBannerItem(p0: BGABanner?, p1: ImageView?, p2: String?, p3: Int) {
                                Glide.with(mContext)
                                        .load(p2)
                                        .transition(DrawableTransitionOptions().crossFade())
//                                        .placeholder(R.drawable.placeholder_banner)
                                        .into(p1)

                            }

                        })
                    }
                }
            }
            ITEM_TYPE_TEXT_HEADER -> {
                holder.header.text = data[position + bannerSize - 1].data?.text ?: ""
            }
            else -> {
                setVideoItem(holder, data[position + bannerSize - 1])
            }

//            else ->
//                holder?.content?.text = data[position].type

        }

    }

    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        var inflate = mInflater?.inflate(mLayoutId, parent, false)
        return inflate!! // !!告诉编译器确定此对象不为空
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //            init {
        var banner = itemView.findViewById<BGABanner>(R.id.banner)
        var header = itemView.findViewById<TextView>(R.id.tvHeader)
        var ivCoverFeed = itemView.findViewById<ImageView>(R.id.iv_cover_feed)
        var ivAvatar = itemView.findViewById<ImageView>(R.id.iv_avatar)
        var tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        var tvTag = itemView.findViewById<TextView>(R.id.tv_tag)
        var tvCategory = itemView.findViewById<TextView>(R.id.tv_category)

//            }
    }


    private fun setVideoItem(holder: ViewHolder, item: HomeBean.Issue.Item) {
        val itemData = item.data

        val defAvatar = R.mipmap.default_avatar
        val cover = itemData?.cover?.feed
        var avatar = itemData?.author?.icon
        var tagText: String? = "#"
        // 作者出处为空，就显获取提供者的信息
        if (avatar.isNullOrEmpty()) {
            avatar = itemData?.provider?.icon
        }
        // 加载封页图
        Glide.with(mContext)
                .load(cover)
//                .placeholder(R.drawable.placeholder_banner)
                .transition(DrawableTransitionOptions().crossFade())
                .into(holder.ivCoverFeed)

        // 如果提供者信息为空，就显示默认
        if (avatar.isNullOrEmpty()) {
            Glide.with(mContext)
                    .load(defAvatar)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.ivAvatar)

        } else {
            Glide.with(mContext)
                    .load(avatar)
                    .transition(DrawableTransitionOptions().crossFade())
                    .into(holder.ivAvatar)
        }
        holder.tvTitle.text = itemData?.title ?: ""

        //遍历标签
        itemData?.tags?.take(4)?.forEach {
            tagText += (it.name + "/")
        }
        // 格式化时间
        val timeFormat = durationFormat(itemData?.duration)

        tagText += timeFormat

        holder.tvTag.text = tagText

        holder.tvCategory.text = "#" + itemData?.category
    }

}