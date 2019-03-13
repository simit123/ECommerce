package kt.com.ecommerce.ui.activity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import kt.com.ecommerce.R
import kt.com.ecommerce.base.BaseActivity
import me.weyye.hipermission.HiPermission
import me.weyye.hipermission.PermissionCallback
import me.weyye.hipermission.PermissionItem
import org.jetbrains.anko.toast

class FlashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        keepTime(3)
    }
    private fun keepTime(second:Int){
       var handlerTime = Handler()
        handlerTime.postDelayed({
            val intent = Intent(this@FlashActivity, HomeActivity::class.java)
            startActivity(intent)
        }, (second * 1000).toLong())
    }

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        checkPermission()
    }

    override fun initView() {
    }


    private fun checkPermission(){
        val permissionItems = arrayListOf<PermissionItem>()
        permissionItems.add(PermissionItem(Manifest.permission.READ_PHONE_STATE, "手机状态", R.drawable.permission_ic_phone))
        permissionItems.add(PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", R.drawable.permission_ic_storage))
        HiPermission.create(this)
                .title("你好！")
                .msg("为了能够更好的使用，请开启如下权限！")
                .permissions(permissionItems)
                .style(R.style.PermissionDefaultGreenStyle)
                .animStyle(R.style.PermissionDefaultGreenStyle)
                .checkMutiPermission(object : PermissionCallback {
                    override fun onFinish() {
                        toast("初始化完毕")
                        val intent = Intent(this@FlashActivity, HomeActivity::class.java)
                        startActivity(intent)
                    }

                    override fun onDeny(permission: String?, position: Int) {
                    }

                    override fun onGuarantee(permission: String?, position: Int) {
                    }

                    override fun onClose() {
                        toast("用户关闭了权限")
                    }

                })

    }
}
