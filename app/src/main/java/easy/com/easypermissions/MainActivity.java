package easy.com.easypermissions;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.okpermission.OkPermissions;
import com.okpermission.PermissionsU;
import com.okpermission.easypermissions.AppSettingsDialog;

import java.util.List;



public class MainActivity extends AppCompatActivity implements PermissionsU.OnRequestPermissionsResultCallbacks {


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




    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE};


    private void requstPermissions() {
        OkPermissions.apply(this,"如果第一次申请被拒绝第二次弹出的对话框描述",perms);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        OkPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }


    /**
     * 权限申请成功
     */
    @Override
    public void agreeAllPermissions() {
        Toast.makeText(this, "权限获取成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限部分失败
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show();
    }

    /**
     * 权限部分失败
     */
    @Override
    public void onPermissionsNeverAskDenied(int requestCode, List<String> perms) {
        new AppSettingsDialog.Builder(this).setTitle("提示").setRationale("需要前往设置中心手动打开申请权限").build().show();
    }

}
