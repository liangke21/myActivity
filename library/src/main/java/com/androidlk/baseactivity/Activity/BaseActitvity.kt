package com.androidlk.baseactivity.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.yanzhenjie.permission.Action
import com.yanzhenjie.permission.AndPermission


/**
 * 作者: QQ:1396797522
 * 时间: 2021/3/15 15:55
 * 描述:
 */

abstract class BaseActitvity : AppCompatActivity() {
    protected val CODE_WINDOW_PERMISSION = 1000

    /*获取布局*/
    abstract fun getLayoutId(): Int

    //获取标题
    abstract fun getTitleText(): String

    //是否显示返回键
    abstract fun isShowBack(): Boolean

    //初始化
    abstract fun initView()

   //多条权限初始化
    abstract fun Authorityname(): Array<String>

    //权限后执行代码
    abstract fun Authoritymonitor()

    // 是否开启悬浮窗
    abstract fun Whethertoopen(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        //当前版本大于等于
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportActionBar?.let {
                it.title = getTitleText()
                it.setDisplayHomeAsUpEnabled(isShowBack())//显示返回键
                it.elevation = 0f//透明值
            }
        }
        //活动初始化
        initView()
        //权限
        Authority(Authorityname()) { Authoritymonitor() }
        if (Whethertoopen()) {
            //窗口权限
            if (!checkWindowPermission()) {
                requestwindowPermission(packageName)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return true
    }

    /*===============权限请求=============================================*/
    //检查窗口权限
    protected fun checkWindowPermission(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.canDrawOverlays(this)
        }
        return true
    }

    //申请权限
    protected fun requestwindowPermission(packageName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startActivityForResult(
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName")
                ), CODE_WINDOW_PERMISSION
            )

        }
    }
    /*===============权限请求=============================================*/
/*===============权限请求=============================================*/


    private fun Authority(permission: Array<String>, executor: () -> Unit) {

        //动态权限
        if (checkPermission(permission)) {
            executor()
        } else {
            requestpermission(permission,
                Action<List<String>> { executor() })
        }


    }


    //权限判断
    private fun Authority(permission: String, executor: () -> Unit) {


        //申请权限

        if (chalkPermission(permission)) {
            Log.e("", "有权限运行")
            executor()

        } else {
            requestpermission(
                arrayOf(permission)
            ) {
                Log.e("", "没权限运行")
                executor()
            }
        }

    }

    //检查多个权限
    protected fun checkPermission(permission: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission.forEach {
                if (checkSelfPermission(it) == PackageManager.PERMISSION_DENIED) {
                    return false
                }
            }
        }
        return true
    }

    //检查权限
    protected fun chalkPermission(permission: String): Boolean {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //当前版本要大于等于23版本
            //  return   checkSelfPermission(permission) == PackageManager.PERMISSION_DENIED
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }

    //请求权限
    protected fun requestpermission(permission: Array<String>, granted: Action<List<String>>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndPermission.with(this)
                .runtime()
                .permission(permission)
                .onGranted(granted)
                .start()
        }
    }
/*===============权限请求=============================================*/
}

