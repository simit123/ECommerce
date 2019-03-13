package kt.com.ecommerce.ui.activity

import com.google.android.flexbox.FlexboxLayoutManager
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseActivity

class SearchActivity : BaseActivity() {

    var flexboxLayoutManager = FlexboxLayoutManager(this)


    override fun layoutId(): Int {
        return R.layout.activity_search
    }

    override fun initData() {
    }

    override fun initView() {
    }

}
