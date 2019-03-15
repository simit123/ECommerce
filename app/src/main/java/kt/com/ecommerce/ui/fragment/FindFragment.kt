package kt.com.ecommerce.ui.fragment


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import com.hazz.kotlinmvp.base.BaseFragmentAdapter
import com.hazz.kotlinmvp.utils.StatusBarUtil
import com.hazz.kotlinmvp.view.TabLayoutHelper
import kotlinx.android.synthetic.main.fragment_find.*

import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class FindFragment : BaseFragment() {

    private var mTitle:String? = null

    private var tabList = arrayListOf<String>()
    private var fragmentList = arrayListOf<BaseFragment>()

    companion object {
        fun getInstance(title:String):FindFragment{
            var fragment = FindFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.darkMode(activity as Activity)
        StatusBarUtil.setPaddingSmart(activity as Activity, toolbar)
    }

    override fun initData() {
        tabList.add("关注")
        tabList.add("分类")
        fragmentList.add(FollowFragment.getInstance("关注"))
        fragmentList.add(CategoryFragment.getInstance("分类"))
        mViewPager.adapter = BaseFragmentAdapter(childFragmentManager, fragmentList, tabList)
        mTabLayout.setupWithViewPager(mViewPager)
        TabLayoutHelper.setUpIndicatorWidth(mTabLayout)

    }

    override fun layoutId(): Int {
        return R.layout.fragment_find
    }
}
