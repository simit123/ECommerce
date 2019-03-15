package kt.com.ecommerce.base

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/13 ADD
 */

/**
 * basePresenter的基类接口，定义子presenter中共有的方法
 */
interface IBasePresenter <V:IBaseView>{

    fun attachView(mRootView:V)
    fun detachView()

}