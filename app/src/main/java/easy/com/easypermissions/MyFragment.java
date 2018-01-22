package easy.com.easypermissions;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.okpermission.OkPermissions;

import java.util.List;

import static pub.devrel.easypermissions.AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Package easy.com.easypermissions
 * @Description: TODO
 * @mail diosamolee2014@gmail.com
 * @date 2018/1/19 14:08
 */
public class MyFragment extends Fragment implements OkPermissions.OnRequestPermissionsResultCallbacks {


    private static final String TAG = "dada2";
    private Button mBt1;
    private Activity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mContext = getActivity();
        View inflate = View.inflate(mContext, R.layout.fragment, null);
        mBt1 = inflate.findViewById(R.id.bt1);
        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPermisison();
            }
        });
        return inflate;
    }
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE};
    private void startPermisison() {
        OkPermissions.requestPerssions(this,perms);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OkPermissions.onRequestPermissionsResult(requestCode,permissions, grantResults,this);
    }


    /**
     * All permissions are allowed to start the next step here.
     */
    @Override
    public void agreeAllPermissions() {
        // Already have permission, do the thing

    }

    /**
     * Permission denied
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /**
     * The denied permission is all checked out (if there are any other permissions that are not checked, the onPermissionsDenied)
     * @param requestCode
     * @param perms
     */
    @Override
    public void onPermissionsNeverAskDenied(int requestCode, List<String> perms) {

        //Use the dialog prompt to use after the user confirms.
    }
}
