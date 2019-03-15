package kt.com.ecommerce.ui.fragment

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_category.*
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment
import kt.com.ecommerce.mvp.Constract.CategoryConstract
import kt.com.ecommerce.mvp.presenter.CategoryPresenter
import kt.com.ecommerce.ui.adapter.CategoryAdapter


/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : BaseFragment(), CategoryConstract.ICategoryV {
    override fun showProgress() {
    }

    override fun dismissProgress() {
    }

    private var mData = ArrayList<CategoryBean>()

    private val mAdapter: CategoryAdapter by lazy {
        CategoryAdapter(activity!!, mData)
    }

    private val mPresenter by lazy {
        CategoryPresenter()
    }


    override fun showData(mList: ArrayList<CategoryBean>) {
        mAdapter.addData(mList)

    }

    private var mTitle: String? = null

    companion object {
        fun getInstance(title: String): CategoryFragment {
            var fragment = CategoryFragment()
            var bundle = Bundle()
            fragment.arguments = bundle
            fragment.mTitle = title
            return fragment
        }
    }

    override fun initView() {
        initRecycler()
    }

    override fun initData() {
        mPresenter.attachView(this)
        mPresenter.getCategoryData()
    }

    override fun layoutId(): Int = R.layout.fragment_category

    private fun initRecycler() {
        mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                val position = parent!!.getChildPosition(view)
                val offset = DisplayManager.dip2px(2f)!!
                outRect!!.set(if (position % 2 == 0) 0 else offset, offset, if (position / 2 == 0) 0 else offset
                        , offset)
            }
        })
    }

}
