package kt.com.ecommerce.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kotlinx.android.synthetic.main.fragment_follow.*
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment
import kt.com.ecommerce.mvp.Constract.FollowConstract
import kt.com.ecommerce.mvp.presenter.FollowPresenter
import kt.com.ecommerce.ui.adapter.FollowAdapter


/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : BaseFragment(), FollowConstract.IFollowV {


    private val mPresenter by lazy {
        FollowPresenter()
    }
    private var itemList = ArrayList<HomeBean.Issue.Item>()

    private val mAdapter by lazy { FollowAdapter(activity!!,itemList) }

    override fun showFindData(data: ArrayList<HomeBean.Issue.Item>) {
        mAdapter.addData(data)
    }


    override fun dismissProgress() {
    }


    override fun showProgress() {
    }

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): FollowFragment {
            var fragment = FollowFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }


    override fun initData() {
        mPresenter.attachView(this)
        mPresenter.getFollowData()
    }

    override fun layoutId(): Int {
        return R.layout.fragment_follow
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        mRecyclerView.layoutManager = LinearLayoutManager(activity)
        mRecyclerView.adapter = mAdapter
    }

}
