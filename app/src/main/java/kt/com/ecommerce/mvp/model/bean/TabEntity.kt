package kt.com.ecommerce.mvp.model.bean

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * 修改番号 INLS - NEW - 201810 - 003 修改简介 wuy 2018/11/28 ADD
 */
class TabEntity(var selectIcon:Int,var title:String,var unSelectIcon:Int) : CustomTabEntity{
    override fun getTabSelectedIcon(): Int {
        return selectIcon
    }

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectIcon
    }

}