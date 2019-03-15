package kt.com.ecommerce.api

import com.hazz.kotlinmvp.mvp.model.bean.CategoryBean
import com.hazz.kotlinmvp.mvp.model.bean.HomeBean
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * 修改番号 INLS - NEW - 201810 - 003 修改简介 wuy 2018/11/30 ADD
 */
/**
 * retrofit api 接口定义
 */
interface ApiService {

    @GET("v2/feed?")
    fun getSelectionData(@Query("num") num: Int): Observable<HomeBean>

    /**
     * 根据 nextPageUrl 请求数据下一页数据
     */
    @GET
    fun getMoreHomeData(@Url url: String): Observable<HomeBean>

    /**
     * //关注
     *  http://baobab.kaiyanapp.com/api/v4/tabs/follow
     */

    @GET("v4/tabs/follow")
    fun getFollowData():Observable<HomeBean.Issue>

    /**
     * //获取分类
     *   http://baobab.kaiyanapp.com/api/v4/categories
     */

    @GET("v4/categories")
    fun getCategoryData():Observable<ArrayList<CategoryBean>>

}