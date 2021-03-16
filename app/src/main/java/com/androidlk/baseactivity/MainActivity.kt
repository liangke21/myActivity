package com.androidlk.baseactivity

import android.Manifest
import android.widget.Toast
import com.androidlk.baseactivity.Activity.BaseActitvity

class MainActivity : BaseActitvity() {
    //获取布局id
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    //标题设置
    override fun getTitleText(): String {
        return "我是标题"
    }

    //是否有返回键
    override fun isShowBack(): Boolean {
        return true
    }

    //活动初始化
    override fun initView() {

    }

    //多条权限
    override fun Authorityname(): Array<String> {
        val permission = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,

            )
        return permission
    }

    //权限后执行代码
    override fun Authoritymonitor() {
        Toast.makeText(this, "权限通过", Toast.LENGTH_SHORT).show()
    }


    //是否开启悬浮窗
    override fun Whethertoopen(): Boolean {
        return true
    }
}