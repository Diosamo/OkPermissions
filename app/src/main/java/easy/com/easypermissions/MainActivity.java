package easy.com.easypermissions;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class MainActivity extends AppCompatActivity implements EasyPermissionsProxy.OnRequestPermissionsCallbacks {

    private static final String TAG = "dada";

    private Button mBt1;
    private FrameLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBt1 = (Button) findViewById(R.id.bt1);
        fragment = (FrameLayout) findViewById(R.id.fragment);
        mBt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                EasyPermissions.requestPermissions(new PermissionRequest.Builder(MainActivity.this, 1, perms)
//                        .setRationale("提示")
//                        .setPositiveButtonText("dada")
//                        .setNegativeButtonText("papa")
//                        .setTheme(R.style.my_fancy_style)
//                        .build());
                requstPermissions();

            }
        });
        MyFragment myFragment = new MyFragment();

        FragmentManager supportFragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment,myFragment);
        fragmentTransaction.show(myFragment);
        fragmentTransaction.commit();

    }

    private void requstPermissions() {
        EasyPermissionsProxy.getInstance(MainActivity.this).requestPermissions(1,perms,this);

    }

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE};




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
//        PermissionsUtils.onRequestPermissionsResult(requestCode,permissions,grantResults,this);


    }

    @Override
    public void onAgreeAllPermissions() {
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

//    @Override
//    public void agreeAllPermissions() {
//    }
//
//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perms) {
//
//    }
//
//    @Override
//    public void onPermissionsNeverAskDenied(int requestCode, List<String> perms) {
//
//    }


//    @Override
//    public void onPermissionsGranted(int requestCode, List<String> perms) {
////        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
//
//        //(可选)检查用户是否拒绝了任何权限，并检查“不再询问”。
//        //这将显示一个对话框，引导他们在应用程序设置中启用权限。
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
//            //打开设置的log
////            Log.d(TAG, "onPermissionsDenied: hasPermissions"+ true);
//        }
//    }

//    @Override
//    public void onPermissionsDenied(int requestCode, List<String> perm1) {
//        for (String perm : perm1) {
////            Log.d(TAG, "失败权限onPermissionsDenied: "+perm);
//        }
//
//        if (EasyPermissions.somePermissionPermanentlyDenied(this, perm1)) {
//            //这个方法有个前提是，用户点击了“不再询问”后，才判断权限没有被获取到
//            new AppSettingsDialog.Builder(this)
//                    .setRationale("没有该权限，此应用程序可能无法正常工作。打开应用设置界面以修改应用权限")
//                    .setTitle("必需权限")
//                    .build()
//                    .show();
//        }
//
//        if (!EasyPermissions.hasPermissions(this, perms)) {
//            //这里响应的是除了AppSettingsDialog这个弹出框，剩下的两个弹出框被拒绝或者取消的效果
////            Log.d(TAG, "onPermissionsDenied: hasPermissions"+ true);
//        }
//
//    }



//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
//            if (!EasyPermissions.hasPermissions(this, perms)) {
////                Log.d(TAG, "onActivityResult: hasPermissions"+ true);
//            }
//        }
//    }
}
