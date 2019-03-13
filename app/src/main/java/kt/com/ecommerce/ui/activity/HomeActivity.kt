package kt.com.ecommerce.ui.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import kotlinx.android.synthetic.main.activity_home.*
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseActivity
import kt.com.ecommerce.mvp.model.bean.TabEntity
import kt.com.ecommerce.ui.fragment.FindFragment
import kt.com.ecommerce.ui.fragment.HotFragment
import kt.com.ecommerce.ui.fragment.MineFragment
import kt.com.ecommerce.ui.fragment.SelectionFragment

/**
 *加载精选 发现 热门 我的 四个fragment
 * 首先下面四个按钮的点击状态变更
 */
class HomeActivity : BaseActivity() {

    //fragment 角标
    private var mIndex = 0

    //定义4个图标
    private val tab = arrayOf("每日精选", "find", "hot", "mine")

    private val mTabEntities = ArrayList<CustomTabEntity>()
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)

    private var mSelectionFragment: SelectionFragment? = null
    private var mFindFragment: FindFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {

        if (savedInstanceState != null) {
            mIndex = savedInstanceState.getInt("currTabIndex")
        }
        super.onCreate(savedInstanceState)
        mIndex = tab_layout1.currentTab
        initTab()
        switchFragment(mIndex)
    }


    override fun layoutId(): Int {
        return R.layout.activity_home
    }

    override fun initData() {


    }

    override fun initView() {
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (tab_layout1 != null) {
            outState.putInt("currTabIndex", mIndex)
        }
    }


    private fun initTab() {

//        until 半闭区间 表示[0,tab.size)  mapTo:把T转换成对象
        (0 until tab.size).mapTo(mTabEntities) {
            TabEntity(mIconSelectIds[it], tab[it], mIconUnSelectIds[it])
        }
        tab_layout1.setTabData(mTabEntities)
        tab_layout1.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                //在这里切换各种fragment
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {

            }

        })
    }

    /**
     * let: object.let { }表示object不为null的条件下，才会去执行let函数体
     */
    private fun hideFragment(fs: FragmentTransaction) {
        mSelectionFragment?.let {
            fs.hide(it)
        }
        mMineFragment?.let {
            fs.hide(it)
        }
        mHotFragment?.let {
            fs.hide(it)
        }
        mFindFragment?.let {
            fs.hide(it)
        }
    }

    /**
     * 切换fragment
     */
    private fun switchFragment(pos: Int) {
        var transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when (pos) {
            0 -> mSelectionFragment?.let {
                transaction.show(it)
            } ?: SelectionFragment.getInstance(tab[pos]).let {
                mSelectionFragment = it
                transaction.add(R.id.fm_container, it, "Selection")
            }
            1 -> mFindFragment?.let {
                transaction.show(it)
            }?:FindFragment.getInstance(tab[pos]).let {
                mFindFragment = it
                transaction.add(R.id.fm_container, it, "Find")
            }
            2 -> mHotFragment?.let {
                transaction.show(it)
            }?:HotFragment.getInstance(tab[pos]).let {
                mHotFragment = it
                transaction.add(R.id.fm_container, it, "Hot")
            }
            3-> mMineFragment?.let {
                transaction.show(it)
            }?:MineFragment.getInstance(tab[pos]).let {
                mMineFragment = it
                transaction.add(R.id.fm_container, it, "Mine")
            }
            else -> {

            }
        }
        mIndex = pos
        tab_layout1.currentTab = pos
        transaction.commitAllowingStateLoss()
    }

}
