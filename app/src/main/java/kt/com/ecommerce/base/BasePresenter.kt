package kt.com.ecommerce.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 修改番号 INLS-NEW-201811-002 修改简介 wuy 2019/3/13 ADD
 *
 * 处理presenter中共有逻辑，rxjava的disposable的释放等等
 */
open class BasePresenter<T : IBaseView> : IBasePresenter<T> {

    var mRootView: T? = null
        private set

   private var mDisposables = CompositeDisposable();


    override fun attachView(mRootView: T) {
        this.mRootView = mRootView
    }

    override fun detachView() {
        mRootView = null
        //取消rxjava的所有订阅
        if (!mDisposables.isDisposed) {
            mDisposables.clear()
        }

    }

    /**
     * 把Disposable加入mDisposables中，如果view销毁则清除所有的Disposable
     */
    fun addDisposable(d:Disposable){
        mDisposables.add(d)
    }

    /**
     * 检查View是否Attach，异步请求之前调用
     */
    fun checkViewAttached(){
        if (mRootView == null) {
            throw RuntimeException("Please call IPresenter.attachView(IBaseView) before\" + \" requesting data to the IPresenter")
        }
    }

}