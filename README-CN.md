# OkPermissions 	[![Build Status][1]][2]
OkPermissions是一个非常简单和轻量级的库,基于Easypermissions1.2.0，它可以帮助您自动删除重复应用程序的权限，并仅在未经许可的情况下应用。针对Activity,与Fragment中做了处理兼容,它也很容易使用！






## 依赖

OkPermissions is installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    api 'com.okpermission:OkPermission:1.3.0'
}
```


## 使用

### 基本操作

您必须在 `Activity` (or `Fragment`) 重写 `onRequestPermissionsResult` 这个方法,然后加上OkPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)这行代码:

```java
public class MainActivity extends AppCompatActivity {

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to OkPermissions
        OkPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
}
```

### 请求权限


下面就用相机和位置权限的列子给大家示范一下:

```java

private void methodRequiresTwoPermission() {
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
	
	OkPermissions.apply(this,"如果第一次申请被拒绝第二次弹出的对话框描述",perms);
    //或者直接给予权限
    OkPermissions.apply(this,"如果第一次申请被拒绝第二次弹出的对话框描述",Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE);
}
```



你必须在对应请求权限的`Activities`/`Fragments` 实现 `OnRequestPermissionsResultCallbacks`这个接口 .

```java
public class MainActivity extends AppCompatActivity implements OkPermissions.OnRequestPermissionsResultCallbacks {

   @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to OkPermissions
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
```

### 需要的权限

在某些情况下，如果没有某些权限，你的应用程序将无法正常运行。如果用户
通过“永不再问”选项来拒绝这些权限，您将无法请求
用户的这些权限必须在应用程序设置中进行更改。您可以使用
方法“onPermissionsNeverAskDenied”来显示一个对话框
在这种情况下，用户可以直接将他们引导到系统设置屏幕上:

```java
/**
 *
 * @param requestCode
 * @param perms
 */
@Override
public void onPermissionsNeverAskDenied(int requestCode, List<String> perms) {

    //Use the dialog prompt to use after the user confirms.
    OkPermissions.startApplicationDetailsSettings(this,DEFAULT_SETTINGS_REQ_CODE);
}

/**
 * You can open the permissions and check whether the check permissions are open and then execute agreeAllPermissions() directly
 * @param requestCode
 * @param resultCode
 * @param data
 */
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == DEFAULT_SETTINGS_REQ_CODE) {
        if (OkPermissions.hasPermissions(this, perms)) {
            agreeAllPermissions();
        }
    }
}
```


## LICENSE

```
	Copyright 2018 Diosamo

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

```
[1]: https://travis-ci.org/Diosamo/OkPermissions.svg?branch=master
[2]: https://travis-ci.org/Diosamo/OkPermissions
