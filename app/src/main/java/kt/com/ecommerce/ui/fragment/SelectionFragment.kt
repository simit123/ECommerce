package kt.com.ecommerce.ui.fragment


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import com.hazz.kotlinmvp.utils.StatusBarUtil
import com.scwang.smartrefresh.header.MaterialHeader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_selection.*
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment
import kt.com.ecommerce.net.RetrofitManager
import kt.com.ecommerce.ui.activity.SearchActivity
import kt.com.ecommerce.ui.adapter.SelectionAdapter
import java.text.SimpleDateFormat
import java.util.*


class SelectionFragment : BaseFragment() {


    private var mTitle: String? = null
    private var bannerHomeBean: HomeBean? = null
    private var nextPageUrl: String? = null
    private var selectionAdapter: SelectionAdapter? = null
    private var mMaterialHeader: MaterialHeader? = null

    companion object {
        fun getInstance(title: String): SelectionFragment {
            var fragment = SelectionFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    private val linearLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }


    fun getColor(colorId: Int): Int {
        return resources.getColor(colorId)
    }


    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun initView() {
        initStatusBar()
        init()
    }

    override fun initData() {
        requestHomeData()


//        initRV()
    }

    private fun init(){
        //设置内容跟随偏移
        mRefreshLayout.setEnableHeaderTranslationContent(true)
        mRefreshLayout.setOnRefreshListener({
            requestHomeData()
        })
        mMaterialHeader = mRefreshLayout.refreshHeader as MaterialHeader?
        //打开下拉刷新区域块背景
        mMaterialHeader?.setShowBezierWave(true)
        //设置下拉刷新主题颜色
        mRefreshLayout.setPrimaryColorsId(R.color.color_light_black, R.color.color_title_bg)



        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentVisibleItemPosition = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    toolbar.setBackgroundColor(getColor(R.color.color_translucent))
                    iv_search.setImageResource(R.mipmap.ic_action_search_white)
                    tv_header_title.text = ""
                } else {
                    if (selectionAdapter?.data!!.size > 1) {
                        toolbar.setBackgroundColor(getColor(R.color.color_title_bg))
                        iv_search.setImageResource(R.mipmap.ic_action_search_black)
                        val itemList = selectionAdapter!!.data
                        val item = itemList[currentVisibleItemPosition + selectionAdapter!!.bannerSize - 1]
                        if (item.type == "textHeader") {
                            tv_header_title.text = item.data?.text
                        } else {
                            tv_header_title.text = simpleDateFormat.format(item.data?.date)
                        }
                    }
                }

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val childCount = mRecyclerView.childCount
                    val childItem = mRecyclerView.layoutManager.itemCount
                    val firstVisibleItem = (mRecyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    if (childCount + firstVisibleItem > (childItem - 2)) {
                        loadMoreData()
                    }
                }
            }
        })

        iv_search.setOnClickListener {
            openSearchActivity()
        }

    }

    private fun initRV(homeBean: HomeBean) {
        mRefreshLayout.finishRefresh()
        selectionAdapter = SelectionAdapter(activity!!, homeBean.issueList[0].itemList)
        selectionAdapter!!.setBannerSize1(homeBean.issueList[0].count)
        mRecyclerView.adapter = selectionAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun initStatusBar() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity!!)
        StatusBarUtil.setPaddingSmart(activity!!, toolbar)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_selection
    }

    fun loadData() {
        RetrofitManager.service.getSelectionData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ homeBean: HomeBean? ->
                    Log.i(tag, "数据类型" + homeBean.toString())

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeBean!!.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }

                    bannerHomeBean = homeBean

                    homeBean?.let {
                        initRV(it)
                    }

                }, { error ->
                    Log.i(tag, "错误类型" + error)
                })
    }

    fun requestHomeData() {
        RetrofitManager.service.getSelectionData(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ homeBean ->
                    Log.i(tag, "数据类型" + homeBean.toString())

                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val bannerItemList = homeBean!!.issueList[0].itemList

                    bannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        bannerItemList.remove(item)
                    }

                    bannerHomeBean = homeBean
                    bannerHomeBean!!.nextPageUrl?.let { RetrofitManager.service.getMoreHomeData(it).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }

                })
                .subscribe({ homeBean ->
                    nextPageUrl = homeBean.nextPageUrl
                    //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                    val newBannerItemList = homeBean.issueList[0].itemList

                    newBannerItemList.filter { item ->
                        item.type == "banner2" || item.type == "horizontalScrollCard"
                    }.forEach { item ->
                        //移除 item
                        newBannerItemList.remove(item)
                    }
                    // 重新赋值 Banner 长度
                    bannerHomeBean!!.issueList[0].count = bannerHomeBean!!.issueList[0].itemList.size

                    //赋值过滤后的数据 + banner 数据
                    bannerHomeBean?.issueList!![0].itemList.addAll(newBannerItemList)
                    bannerHomeBean?.let {
                        initRV(it)
                    }
                }, { error ->
                    Log.i(tag, "错误类型123" + error)
                })
    }

    fun loadMoreData() {
        nextPageUrl?.let {
            RetrofitManager.service.getMoreHomeData(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ homeBean ->
                        //过滤掉 Banner2(包含广告,等不需要的 Type), 具体查看接口分析
                        val newItemList = homeBean.issueList[0].itemList

                        newItemList.filter { item ->
                            item.type == "banner2" || item.type == "horizontalScrollCard"
                        }.forEach { item ->
                            //移除 item
                            newItemList.remove(item)
                        }

                        nextPageUrl = homeBean.nextPageUrl
                        selectionAdapter?.let { it.addItemData(newItemList) }
                    })

        }

    }

    private fun openSearchActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!, iv_search, iv_search.transitionName)
            startActivity(Intent(activity, SearchActivity::class.java), options.toBundle())
        } else {
            startActivity(Intent(activity, SearchActivity::class.java))
        }
    }
}
