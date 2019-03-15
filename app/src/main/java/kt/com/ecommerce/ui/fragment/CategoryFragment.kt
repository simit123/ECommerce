package kt.com.ecommerce.ui.fragment
import android.os.Bundle
import android.support.v4.app.Fragment
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class CategoryFragment : BaseFragment() {

    private var mTitle:String? = null

    companion object {
        fun getInstance(title:String):CategoryFragment{
            var fragment = CategoryFragment()
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

    override fun layoutId(): Int = R.layout.fragment_category

}
