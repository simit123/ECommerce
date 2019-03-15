package kt.com.ecommerce.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment
import kt.com.ecommerce.mvp.Constract.FollowConstract
import kt.com.ecommerce.mvp.presenter.FollowPresenter


/**
 * A simple [Fragment] subclass.
 */
class FollowFragment : BaseFragment(), FollowConstract.IFollowV {


    private val mPresenter by lazy {
        FollowPresenter()
    }

    override fun showFindData(data: List<HomeBean.Issue.Item>) {

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

    }


}
