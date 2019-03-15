package kt.com.ecommerce.mvp.presenter

import kt.com.ecommerce.base.BasePresenter
import kt.com.ecommerce.mvp.Constract.CategoryConstract
import kt.com.ecommerce.mvp.model.CategoryModel

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/15 ADD
 */
class CategoryPresenter : BasePresenter<CategoryConstract.ICategoryV>(),CategoryConstract.ICategoryP{

    override fun getCategoryData() {
        checkViewAttached()
        addDisposable(CategoryModel.getCategoryData().subscribe({ data ->
            mRootView!!.showData(data)
        },{ error -> }))
    }

}