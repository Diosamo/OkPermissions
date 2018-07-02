# OkPermissions    [![Build Status][1]][2]
OkPermissions是一个非常简单和轻量级的库,基于Easypermissions1.2.0，它可以帮助您自动删除重复应用程序的权限，并仅在未经许可的情况下应用。针对Activity,与Fragment中做了处理兼容,它也很容易使用！
OkPermissions is a very simple and lightweight library, based on Easypermissions1.2.0, which can help you automatically delete the permissions of a repeat application and apply it only without permission. It's also easy to use!

[中文文档](./README-CN.md)  

## Installation

OkPermissions is installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    api 'com.okpermission:OkPermission:1.3.0'
}
```


## Usage

### Basic

To begin using OkPermissions, have your `Activity` (or `Fragment`) override the `onRequestPermissionsResult` method:

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

### Request Permissions

The example below shows how to request permissions for a method that requires both
`CAMERA` and `ACCESS_FINE_LOCATION` permissions. There are a few things to note:

 

```java

private void methodRequiresTwoPermission() {
    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};
	
	OkPermissions.apply(this,"A first request to be rejected second times",perms);
    //Or other
    OkPermissions.apply(this,"A first request to be rejected second times",Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE);
}
```



To better control, you must implement the `OnRequestPermissionsResultCallbacks` interface with your `Activities`/`Fragments`.

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
		new AppSettingsDialog.Builder(this).setTitle("hint").setRationale("setting").build().show();
    }
}
```

### Required Permissions

In some cases your app will not function properly without certain permissions. If the user
denies these permissions with the "Never Ask Again" option, you will be unable to request
these permissions from the user and they must be changed in app settings. You can use the
method `onPermissionsNeverAskDenied` to display a dialog to the
user in this situation and direct them to the system setting screen for your app:

```java
/**
 * The denied permission is all checked out (if there are any other permissions that are not checked, the onPermissionsDenied)
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
