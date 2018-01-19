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

import java.util.List;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Package easy.com.easypermissions
 * @Description: TODO
 * @mail diosamolee2014@gmail.com
 * @date 2018/1/19 14:08
 */
public class MyFragment extends Fragment implements PermissionsUtils.OnRequestPermissionsResultCallbacks {


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
        PermissionsUtils.requestPerssions(this,"里面的权限",perms);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsUtils.onRequestPermissionsResult(requestCode,permissions, grantResults,this);
    }

    @Override
    public void agreeAllPermissions() {
        Log.d(TAG, "开始工作");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        for (String perm : perms) {
            Log.d(TAG, "onPermissionsDenied: 拒绝 "+perm);
        }
    }

    @Override
    public void onPermissionsNeverAskDenied(int requestCode, List<String> perms) {
        for (String perm : perms) {
            Log.d(TAG, "onPermissionsDenied: 勾选不在询问拒绝 "+perm);
        }
    }
}
