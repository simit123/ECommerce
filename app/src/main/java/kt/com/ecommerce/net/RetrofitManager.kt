package kt.com.ecommerce.net


import com.hazz.kotlinmvp.utils.Preference
import kt.com.ecommerce.MyApplication
import kt.com.ecommerce.api.ApiService
import kt.com.ecommerce.api.UriConstant
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 修改番号 INLS - NEW - 201810 - 003 修改简介 wuy 2018/11/30 ADD
 */
object RetrofitManager{

    private var retrofit: Retrofit? = null
    private var client: OkHttpClient? = null
    private var token:String by Preference("token","")
    val service: ApiService by lazy { getRetrofit()!!.create(ApiService::class.java)}

    private fun getRetrofit(): Retrofit? {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java){
                if (retrofit == null) {

                    //添加log拦截器
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    //设置请求的缓存大小和位置
                    var file = File(MyApplication.context.cacheDir, "cache")
                    var cache = Cache(file,1024*1024*50)

                    client = OkHttpClient.Builder()
                            .addInterceptor(addQueryParameterInterceptor())
                            .addInterceptor(httpLoggingInterceptor)
                            .addInterceptor(addHeaderInterceptor())
                            .cache(cache)
                            .connectTimeout(60L,TimeUnit.SECONDS)
                            .readTimeout(60L,TimeUnit.SECONDS)
                            .writeTimeout(60L,TimeUnit.SECONDS)
                            .build()

                    retrofit = Retrofit.Builder()
                            .baseUrl(UriConstant.BASE_URL)
                            .client(client)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }
        return retrofit
    }


    /**
     * 设置公共参数
     */
    private fun addQueryParameterInterceptor():Interceptor{

        return Interceptor{chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()
            request = originalRequest.newBuilder().url(modifiedUrl).build()
            chain.proceed(request)
        }
    }

    /**
     * 设置头
     */
    private fun addHeaderInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()
                    // Provide your custom header here
                    .header("token", token)
                    .method(originalRequest.method(), originalRequest.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }

}