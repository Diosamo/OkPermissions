package easy.com.easypermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * EasyPermissions代理类
 * Created by Zoyix on 2018/1/19.
 */

public class EasyPermissionsProxy implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "EasyPermissionsProxyDebug";

    /**
     * 申请的权限的个数
     */
    private int size = 0;

    private OnRequestPermissionsCallbacks callbacks = null;

    private final Activity activity;


    private EasyPermissionsProxy(Activity activity) {
        this.activity = activity;
    }

    /**
     * 简单工厂方法获得EasyPermissionsProxy实例
     *
     * @param activity
     * @return
     */
    public static EasyPermissionsProxy getInstance(@NonNull Activity activity) {
        return new EasyPermissionsProxy(activity);
    }

    /**
     * 请求权限
     *
     * @param requestCode 请求码  主要在同一个代理类多次调用requestPermissions时用来判断
     * @param permissions 需要请求的权限
     * @param callbacks   需要的回调
     */
    @SuppressLint("NewApi")
    public void requestPermissions(int requestCode, @NonNull String[] permissions, @NonNull OnRequestPermissionsCallbacks callbacks) {
        //为什么不用@AfterPermissionGranted()注解，因为这个注解的方法必须是无参的,而我们需要传参
        //该注解的内部会通过反射找到这个方法，其中要用请求码确定是哪个方法。


        //过滤已经同意了的权限
        ArrayList<String> permissionList = new ArrayList<>();
        for (String permission : permissions) {
            //检查该权限是否已经申请
            int tag = activity.checkSelfPermission(permission);
            if (tag != PackageManager.PERMISSION_GRANTED) {
                permissionList.add(permission);
            }
        }

        this.size = permissionList.size();
        this.callbacks = callbacks;

        //得到要申请的权限中未申请的部分
        String[] realPermissions = permissionList.toArray(new String[size]);

        //这里不再用EasyPermissions.hasPermissions(activity, realPermissions)来判断
        if (size == 0) {
            callbacks.onAgreeAllPermissions();
        } else {
            //第二个参数是被拒绝后再次申请该权限的解释
            //第三个参数是请求码
            //第四个参数是要申请的权限
            EasyPermissions.requestPermissions(activity, "必要的权限", requestCode, realPermissions);
        }
    }

    //下面两个方法是实现EasyPermissions的EasyPermissions.PermissionCallbacks接口

    /**
     * 返回授权成功的权限
     *
     * @param requestCode
     * @param perms
     */

    @SuppressLint("LongLogTag")
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.i(TAG, "获取成功的权限:" + perms);

        //当申请的权限的个数等于获取成功的个数
        if (size == perms.size()) {
            if (callbacks != null) {
                callbacks.onAgreeAllPermissions();
            }
        }
    }

    /**
     * 返回授权失败的权限
     *
     * @param requestCode 请求码
     * @param perms       失败的权限
     */
    @SuppressLint("LongLogTag")
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.i(TAG, "获取失败的权限:" + perms);

        if (callbacks != null) {
            callbacks.onPermissionsDenied(requestCode, perms);
        }

        //检查被拒绝的权限列表中是否有至少一个权限被永久拒绝（用户点击“永不再询问”）
        if (EasyPermissions.somePermissionPermanentlyDenied(activity, perms)) {
            if (callbacks != null) {
                callbacks.onPermissionsNeverAskDenied(requestCode, perms);
            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //把申请权限的回调交由EasyPermissions处理
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 当用户从其它程序返回后执行的某些操作,例如Toast
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //当用户从应用程序设置里返回后执行某些操作,例如Toast
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            Toast.makeText(activity, "返回到app", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 申请权限返回接口
     */
    public interface OnRequestPermissionsCallbacks {
        /**
         * 申请所有权限成功
         */
        void onAgreeAllPermissions();


        /**
         *  拒绝一个（包括一个）以上权限回调
         * @param requestCode
         * @param perms
         */
        void onPermissionsDenied(int requestCode, List<String> perms);


        //例如：
//        new AppSettingsDialog.Builder(activity)
//                .setRationale("不同意权限将无法运行程序，是否打开设置修改权限？")
//                .build()
//                .show();
        /**
         * * 请求权限勾选不在询问后回调 例如写上面的代码，会弹出一个dialog，点同意就到设置页面
         * @param requestCode
         * @param perms
         */
        void onPermissionsNeverAskDenied(int requestCode, List<String> perms);

    }

}
