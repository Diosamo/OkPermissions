package easy.com.easypermissions.version1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Package com.lcns.fmrt.utils
 * @Description: 用于去除重复请求权限并且回调到OnRequestPermissionsResultCallbacks 里面, 尽量使用PermissionsManager 申请管理权限
 * @mail diosamolee2014@gmail.com
 * @date 2018/1/18 16:34
 */
public class PermissionsUtils {
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
        if (!deniedPermissions.isEmpty()) {
            return deniedPermissions;
        }
        return null;
    }

    /**
     * 是否拥有权限
     */
    public static boolean hasPermissons(@NonNull Activity activity, @NonNull String... permissions) {
        if (!needCheckPermission()) {
            return true;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否拒绝了再次申请权限的请求（点击了不再询问）
     */
    public static boolean deniedRequestPermissonsAgain(@NonNull Activity activity, @NonNull String... permissions) {
        if (!needCheckPermission()) {
            return false;
        }
        List<String> deniedPermissions = getNeedPermissions(activity, permissions);
        for (String permission : deniedPermissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_DENIED) {

                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    //当用户之前已经请求过该权限并且拒绝了授权这个方法返回true
                    return true;
                }
            }
        }

        return false;
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
     *
     * @return 是否已经获取权限
     */
    public static void requestPerssions(Activity activity, String... permissions) {
        requestPerssions(activity,1,permissions);
    }


    public static void requestPerssions(Activity activity, int requestCode, String... permissions) {

        if (!needCheckPermission()) {
            if (null!= activity && activity instanceof OnRequestPermissionsResultCallbacks){
                ((OnRequestPermissionsResultCallbacks) activity).agreeAllPermissions();
            }
            return ;
        }

        if (!hasPermissons(activity, permissions)) {
                List<String> deniedPermissions = getNeedPermissions(activity, permissions);
                if (deniedPermissions != null) {
                    ActivityCompat.requestPermissions(activity, deniedPermissions.toArray(new String[deniedPermissions.size()]), requestCode);
                }
            return ;
        }
        if (null!= activity && activity instanceof OnRequestPermissionsResultCallbacks) {
            ((OnRequestPermissionsResultCallbacks) activity).agreeAllPermissions();
        }
        return ;
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
                if (hasNeverAskPermissions((Activity) callBack,(String[])denied.toArray(new String[denied.size()]))) {
                    callBack.onPermissionsNeverAskDenied(requestCode, denied);
                } else {
                    callBack.onPermissionsDenied(requestCode, denied);
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