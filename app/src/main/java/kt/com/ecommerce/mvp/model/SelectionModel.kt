package kt.com.ecommerce.mvp.model.bean

import kt.com.ecommerce.net.RetrofitManager
import kt.com.ecommerce.scheduler.SchedulerUtils

/**
 * 修改番号 INLS - NEW - 201810 - 003 修改简介 wuy 2018/11/30 ADD
 */
/**
 * model 只负责请求数据 至于请求到的数据怎么处理 交给 契约类实现（最终目的 是通过接口 把数据给view）
 */
class SelectionModel{

    companion object {
        fun getSelectionData(num:Int){
            RetrofitManager.service.getSelectionData(num)
                    .compose(SchedulerUtils.ioToMain())
        }
    }

}