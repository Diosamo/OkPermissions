package easy.com.easypermissions;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Package com.lcns.fmrt.utils
 * @Description: 用于去除重复请求权限并且回调到OnRequestPermissionsResultCallbacks 里面, 尽量使用PermissionsManager 申请管理权限
 * @mail diosamolee2014@gmail.com
 * @date 2018/1/18 16:34
 */
public class PermissionsUtils{
    /**
     * 是否需要检查权限
     */
    private static boolean needCheckPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    /**
     * 获取需要获取到的权限
     * @param activity
     * @param permissions
     * @return
     */
    public static List<String> getNeedPermissions(@NonNull Activity activity, @NonNull String... permissions) {
        if (!needCheckPermission()) {
            return null;
        }
        List<String> deniedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }
        return deniedPermissions;
    }

    /**
     * 打开app详细设置界面
     * 在 onActivityResult() 中没有必要对 resultCode 进行判断，因为用户只能通过返回键才能回到我们的 App 中，
     * 所以 resultCode 总是为 RESULT_CANCEL，所以不能根据返回码进行判断。
     * 在 onActivityResult() 中还需要对权限进行判断，因为用户有可能没有授权就返回了！
     */
    public static void startApplicationDetailsSettings(@NonNull Activity activity, int requestCode) {
        Toast.makeText(activity, "点击权限，并打开全部权限", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 申请权限
     * 使用onRequestPermissionsResult方法，实现回调结果或者自己普通处理
     * @return 是否已经获取权限
     */
    public static void requestPerssions(Activity activity,String rationale, String... permissions) {
        requestPerssions(activity,rationale,0,permissions);
    }
    public static void requestPerssions(Fragment activity,String rationale, String... permissions) {
        requestPerssions(activity,rationale,0,permissions);
    }
    public static void requestPerssions(android.app.Fragment activity, String rationale, String... permissions) {
        requestPerssions(activity,rationale,0,permissions);
    }

    public static void requestPerssions(Activity activity, String rationale,int requestCode, String... permissions) {
        //是否需要权限,不需要直接调用接口方法
        if (!needCheckPermission()) {
            okPermission(activity);
            return ;
        }
        List<String> needPermissions = getNeedPermissions(activity, permissions);
        int size = needPermissions.size();
        if (size!=0){
            startEasyPermission(activity,rationale,requestCode,needPermissions.toArray(new String[size]));
        }else {
            okPermission(activity);
        }
    }

    public static void requestPerssions(Fragment fragment, String rationale, int requestCode, String... permissions) {
        //是否需要权限,不需要直接调用接口方法
        if (!needCheckPermission()) {
            okPermission(fragment);
            return ;
        }
        List<String> needPermissions = getNeedPermissions(fragment.getActivity(), permissions);
        int size = needPermissions.size();
        if (size!=0){
            startEasyPermission(fragment,rationale,requestCode,needPermissions.toArray(new String[size]));
        }else {
            okPermission(fragment);
        }
    }

    public static void requestPerssions(android.app.Fragment fragment, String rationale, int requestCode, String... permissions) {
        //是否需要权限,不需要直接调用接口方法
        if (!needCheckPermission()) {
            okPermission(fragment);
            return ;
        }
        List<String> needPermissions = getNeedPermissions(fragment.getActivity(), permissions);
        int size = needPermissions.size();
        if (size!=0){
            startEasyPermission(fragment,rationale,requestCode,needPermissions.toArray(new String[size]));
        }else {
            okPermission(fragment);
        }
    }

    /**
     * 权限ok直接调用成功方法
     * @param activity
     */
    private static void okPermission(Activity activity) {
        if (null!= activity && activity instanceof OnRequestPermissionsResultCallbacks){
            ((OnRequestPermissionsResultCallbacks) activity).agreeAllPermissions();
        }
    }
    private static void okPermission(android.app.Fragment fragment) {
        if (null!= fragment && fragment instanceof OnRequestPermissionsResultCallbacks){
            ((OnRequestPermissionsResultCallbacks) fragment).agreeAllPermissions();
        }
    }
    private static void okPermission(Fragment fragment) {
        if (null!= fragment && fragment instanceof OnRequestPermissionsResultCallbacks){
            ((OnRequestPermissionsResultCallbacks) fragment).agreeAllPermissions();
        }
    }

    /**
     * 使用EasyPermissions 申请权限
     * @param activity
     * @param requestCode
     * @param permissions
     */
    @AfterPermissionGranted(1)
    private static void startEasyPermission(Activity activity,String rationale, int requestCode, String... permissions) {
        if (EasyPermissions.hasPermissions(activity,permissions )) {
            ((OnRequestPermissionsResultCallbacks) activity).agreeAllPermissions();
        } else {
            EasyPermissions.requestPermissions(activity,rationale,requestCode ,permissions);
        }
    }
    @AfterPermissionGranted(1)
    private static void startEasyPermission(Fragment fragment,String rationale, int requestCode, String... permissions) {
        if (EasyPermissions.hasPermissions(fragment.getActivity(),permissions )) {
            ((OnRequestPermissionsResultCallbacks) fragment).agreeAllPermissions();
        } else {
            EasyPermissions.requestPermissions(fragment,rationale,requestCode ,permissions);
        }
    }
    @AfterPermissionGranted(1)
    private static void startEasyPermission(android.app.Fragment fragment, String rationale, int requestCode, String... permissions) {
        if (EasyPermissions.hasPermissions(fragment.getActivity(),permissions )) {
            ((OnRequestPermissionsResultCallbacks) fragment).agreeAllPermissions();
        } else {
            EasyPermissions.requestPermissions(fragment,rationale,requestCode ,permissions);
        }
    }

    /**
     * 申请权限返回方法
     */
    public static void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                  @NonNull int[] grantResults, @NonNull OnRequestPermissionsResultCallbacks callBack) {
        List<String> granted = new ArrayList<>();
        List<String> denied = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {
            String perm = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                granted.add(perm);
            } else {
                denied.add(perm);
            }
        }

        if (null != callBack) {
            if (!granted.isEmpty()) {
                if (denied.isEmpty()) {
                    callBack.agreeAllPermissions();
                }
            }
            if (!denied.isEmpty()) {
                if (callBack instanceof Activity ){
                    if (hasNeverAskPermissions((Activity) callBack,(String[])denied.toArray(new String[denied.size()]))) {
                        callBack.onPermissionsNeverAskDenied(requestCode, denied);
                    } else {
                        callBack.onPermissionsDenied(requestCode, denied);
                    }
                }else if (callBack instanceof Fragment){
                    if (hasNeverAskPermissions(((Fragment) callBack).getActivity(),(String[])denied.toArray(new String[denied.size()]))) {
                        callBack.onPermissionsNeverAskDenied(requestCode, denied);
                    } else {
                        callBack.onPermissionsDenied(requestCode, denied);
                    }
                }else {
                    if (hasNeverAskPermissions(((android.app.Fragment) callBack).getActivity(),(String[])denied.toArray(new String[denied.size()]))) {
                        callBack.onPermissionsNeverAskDenied(requestCode, denied);
                    } else {
                        callBack.onPermissionsDenied(requestCode, denied);
                    }
                }

            }
        }
    }

    /**
     * 校验是否所有权限都是勾选不再询问
     * @param activity
     * @param permissons
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean hasNeverAskPermissions(Activity activity, String... permissons) {
        if (null!=activity && null !=permissons  && permissons.length >0){
            for (int i = 0; i < permissons.length; i++) {
                if (activity.shouldShowRequestPermissionRationale(permissons[i])) {
                    return false;
                }
            }
            return true;
        }else {
            return false;
        }
    }

    /**
     * 申请权限返回
     */
    public interface OnRequestPermissionsResultCallbacks {
        /**
         * 申请所有权限成功
         */
        void agreeAllPermissions();

        /**
         * 权限拒绝,只要有可以请求的权限就会回调这个方法
         */
        void onPermissionsDenied(int requestCode, List<String> perms);

        /**
         * 所有请求权限勾选不在询问
         */
        void onPermissionsNeverAskDenied(int requestCode, List<String> perms);

    }
}