package kt.com.ecommerce.ui.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kt.com.ecommerce.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setListener()
    }


    private fun checkEmpty(et:EditText):Boolean {
        val text = et.text.trim().length
        return text > 0
    }

    private fun setListener(){
        login.setOnClickListener {
            if (!checkEmpty(username)) {
                Toast.makeText(this,"请输入用户名！",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (!checkEmpty(password)) {
                Toast.makeText(this,"请输入密码！",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            //登录成功
            successDialog()
        }
    }

    private fun successDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("尊敬的用户：")
        builder.setMessage("您已登录成功！")
        builder.setPositiveButton("继续"){
            //跳转下一个页面
            dialog, which -> val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        val alert = builder.create()
        alert.show()
    }

    // 点击空白处 关闭软键盘
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (currentFocus != null && currentFocus.windowToken != null) {
                closeInputMethod()
            }
        }
        return super.onTouchEvent(event)
    }

    private fun closeInputMethod() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
