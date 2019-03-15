package kt.com.ecommerce.mvp.Constract

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import kt.com.ecommerce.base.IBasePresenter
import kt.com.ecommerce.base.IBaseView

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/15 ADD
 */
class CategoryConstract{
    interface ICategoryV : IBaseView{
        fun showData(mList: ArrayList<CategoryBean>)
    }

    interface ICategoryP : IBasePresenter<ICategoryV>{
        fun getCategoryData();
    }
}