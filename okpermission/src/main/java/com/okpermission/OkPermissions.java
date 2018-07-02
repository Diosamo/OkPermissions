package com.okpermission;

import android.app.Activity;
import android.app.Fragment;
import android.support.annotation.NonNull;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Package com.lcns.fmrt.widget
 * @Description: 用于请求权限和 放置功能需求权限的管理类  可以在Activity 和 Fragment 中使用
 * @mail diosamolee2014@gmail.com
 * @date 2018/1/18 20:55
 */
public class OkPermissions {

    /**
     * 传入Activity 和请求的权限数组
     *
     * @param activity
     * @param permissions
     */
    public static void apply(@NonNull Activity activity, @NonNull String rationale, @NonNull String... permissions) {
        PermissionsU.requestPerssions(activity, rationale, permissions);
    }

    /**
     * 传入Activity 和请求的权限数组 和请求码 可以在回调中获得
     *
     * @param activity
     * @param requestCode
     * @param permissions
     */
    public static void apply(@NonNull Activity activity, @NonNull String rationale, int requestCode, @NonNull String... permissions) {
        PermissionsU.requestPerssions(activity, rationale, requestCode, permissions);
    }

    /**
     * 传入Activity 和请求的权限数组
     *
     * @param fragment
     * @param permissions
     */
    public static void apply(@NonNull android.support.v4.app.Fragment fragment, @NonNull String rationale, @NonNull String... permissions) {
        PermissionsU.requestPerssions(fragment, rationale, permissions);
    }

    /**
     * 传入Activity 和请求的权限数组 和请求码 可以在回调中获得
     *
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void apply(@NonNull android.support.v4.app.Fragment fragment, @NonNull String rationale, int requestCode, @NonNull String... permissions) {
        PermissionsU.requestPerssions(fragment, rationale, requestCode, permissions);
    }

    /**
     * 传入Activity 和请求的权限数组
     *
     * @param fragment
     * @param permissions
     */
    public static void apply(@NonNull Fragment fragment, @NonNull String rationale, @NonNull String... permissions) {
        PermissionsU.requestPerssions(fragment, rationale, permissions);
    }

    /**
     * 传入Activity 和请求的权限数组 和请求码 可以在回调中获得
     *
     * @param fragment
     * @param requestCode
     * @param permissions
     */
    public static void apply(@NonNull Fragment fragment, @NonNull String rationale, int requestCode, @NonNull String... permissions) {
        PermissionsU.requestPerssions(fragment, rationale, requestCode, permissions);
    }

    /**
     * 接口回调权限请求结果并且回调不同函数
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     * @param callBack
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, @NonNull PermissionsU.OnRequestPermissionsResultCallbacks callBack) {
        PermissionsU.onRequestPermissionsResult(requestCode, permissions, grantResults, callBack);
    }
}
