# OkPermissions
OkPermissions 是一个用起来非常简单而且轻量级的库,它可以帮你自动去除重复申请的权限,只申请没有申请的权限.使用也比较简单方便!



## 依赖

OkPermissions is installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.okpermission:OkPermission:1.0'
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
	
	OkPermissions.requestPerssions(this,perms);
    //或者直接给予权限
    OkPermissions.requestPerssions(this,Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE);
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

