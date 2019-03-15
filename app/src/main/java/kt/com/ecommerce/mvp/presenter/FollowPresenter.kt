package kt.com.ecommerce.mvp.presenter

import android.util.Log
import kt.com.ecommerce.base.BasePresenter
import kt.com.ecommerce.mvp.Constract.FollowConstract
import kt.com.ecommerce.mvp.model.FollowModel

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/14 ADD
 */
class FollowPresenter : BasePresenter<FollowConstract.IFollowV>(), FollowConstract.IFollowP {


    override fun getFollowData() {
        checkViewAttached()
        addDisposable(FollowModel.getFollowData().subscribe({ items ->
            if (items != null) {
                mRootView!!.showFindData(items.itemList)
            }
        }, { err ->
            Log.i("",err.toString())
        })
        )
    }
}