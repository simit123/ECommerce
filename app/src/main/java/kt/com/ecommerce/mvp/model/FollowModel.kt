package kt.com.ecommerce.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import kt.com.ecommerce.net.RetrofitManager
import kt.com.ecommerce.scheduler.SchedulerUtils

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/14 ADD
 */
class FollowModel{
    companion object {
        fun getFollowData():Observable<HomeBean.Issue>{
            return RetrofitManager.service.getFollowData().compose(SchedulerUtils.ioToMain())
        }
    }
}