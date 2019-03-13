package kt.com.ecommerce.ui.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class FindFragment : BaseFragment() {

    private var mTitle:String? = null

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
    }

    override fun initData() {
    }

    override fun layoutId(): Int {
        return R.layout.fragment_find
    }

}
