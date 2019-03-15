package kt.com.ecommerce.mvp.model

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import io.reactivex.Observable
import io.reactivex.Scheduler
import kt.com.ecommerce.net.RetrofitManager
import kt.com.ecommerce.scheduler.SchedulerUtils

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/15 ADD
 */
class CategoryModel{
    companion object {
        fun getCategoryData():Observable<ArrayList<CategoryBean>>{
            return RetrofitManager.service.getCategoryData().compose(SchedulerUtils.ioToMain())
        }
    }
}