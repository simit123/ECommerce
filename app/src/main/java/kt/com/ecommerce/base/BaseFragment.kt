package kt.com.ecommerce.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 修改番号 INLS - NEW - 201810 - 003 修改简介 wuy 2018/11/28 ADD
 */
abstract class BaseFragment : Fragment(){

//    var mPresenter:T? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(layoutId(),null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        mPresenter = getP()
//        if (mPresenter != null) {
//            mPresenter?.attachView(this)
//        }


        initView()
        initData()
    }

    abstract fun initView()
    abstract fun initData()
    abstract fun layoutId():Int
//    abstract fun getP(): T?

    override fun onDestroy() {
        super.onDestroy()
//        if (mPresenter != null) {
//            mPresenter?.detachView()
//        }
    }
}