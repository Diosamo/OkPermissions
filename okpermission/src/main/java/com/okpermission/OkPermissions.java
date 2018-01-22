package com.okpermission;

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
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * @author LiHongCheng
 * @version V1.0
 * @mail diosamolee2014@gmail.com
 * @date 2018/1/18 16:34
 * @github https://github.com/Diosamo
 */
public class OkPermissions {
    /**
     * Do you need to check permissions?
     */
    private static boolean needCheckPermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
    /**
     * Gets the permissions that need to be obtained.
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
     * open the app details and set the interface.
     * there is no need to judge resultCode in onActivityResult(), since the user can only return to our App by returning the key,
     * so resultCode is always RESULT_CANCEL, so it cannot be judged by the return code.
     * in onActivityResult(), you also need to judge the permissions because the user may return without authorization!
     */
    public static void startApplicationDetailsSettings(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }


    public static void requestPerssions(Activity activity, String... permissions) {
        requestPerssions(activity,0,permissions);
    }
    public static void requestPerssions(Fragment activity,String rationale, String... permissions) {
        requestPerssions(activity,0,permissions);
    }
    public static void requestPerssions(android.app.Fragment activity, String... permissions) {
        requestPerssions(activity,0,permissions);
    }

    public static void requestPerssions(Activity activity,int requestCode, String... permissions) {
        //是否需要权限,不需要直接调用接口方法
        if (!needCheckPermission()) {
            okPermission(activity);
            return ;
        }
        List<String> needPermissions = getNeedPermissions(activity, permissions);
        int size = needPermissions.size();
        if (size > 0){
            startRequestPermission(activity,requestCode,needPermissions.toArray(new String[size]));
        }else {
            okPermission(activity);
        }
    }

    public static void requestPerssions(Fragment fragment, int requestCode, String... permissions) {
        //是否需要权限,不需要直接调用接口方法
        if (!needCheckPermission()) {
            okPermission(fragment);
            return ;
        }
        List<String> needPermissions = getNeedPermissions(fragment.getActivity(), permissions);
        int size = needPermissions.size();
        if (size!=0){
            startRequestPermission(fragment,requestCode,needPermissions.toArray(new String[size]));
        }else {
            okPermission(fragment);
        }
    }

    public static void requestPerssions(android.app.Fragment fragment, int requestCode, String... permissions) {
        //是否需要权限,不需要直接调用接口方法
        if (!needCheckPermission()) {
            okPermission(fragment);
            return ;
        }
        List<String> needPermissions = getNeedPermissions(fragment.getActivity(), permissions);
        int size = needPermissions.size();
        if (size!=0){
            startRequestPermission(fragment,requestCode,needPermissions.toArray(new String[size]));
        }else {
            okPermission(fragment);
        }
    }


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


    private static void startRequestPermission(Activity activity, int requestCode, String... permissions) {
        realRequest(activity, requestCode, permissions);
    }
    private static void startRequestPermission(Fragment fragment, int requestCode, String... permissions) {
        FragmentActivity activity = fragment.getActivity();
        realRequest(activity, requestCode, permissions);
    }
    private static void startRequestPermission(android.app.Fragment fragment, int requestCode, String... permissions) {
        Activity activity = fragment.getActivity();
        realRequest(activity, requestCode, permissions);
    }

    /**
     * Request permission to
     * @param activity
     * @param requestCode
     * @param permissions
     */
    private static void realRequest(Activity activity, int requestCode, String[] permissions) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    /**
     * Handle the permissions callback, and then callback to the implementation of the corresponding method.
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
     * Verify that all permissions are checked and no longer asked.
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
     * Do you have permissions
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
     * The Activity and Fragment application permission callback must implement this interface
     */
    public interface OnRequestPermissionsResultCallbacks {
        /**
         * Apply for all permissions successfully.
         */
        void agreeAllPermissions();

        /**
         * Permission denied, as long as there is permission to request the callback method.
         */
        void onPermissionsDenied(int requestCode, List<String> perms);

        /**
         * All request permission checks are not asked.
         */
        void onPermissionsNeverAskDenied(int requestCode, List<String> perms);

    }
}