package kt.com.ecommerce.mvp.Constract

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import kt.com.ecommerce.base.IBasePresenter
import kt.com.ecommerce.base.IBaseView

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/14 ADD
 */
class FollowConstract{
    interface IFollowV : IBaseView {
        fun showFindData(data: ArrayList<HomeBean.Issue.Item>)
    }

    interface IFollowP : IBasePresenter<IFollowV> {
        fun getFollowData()
    }
}