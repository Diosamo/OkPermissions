# OkPermissions
OkPermissions is a very simple and lightweight library, which can help you to automatically remove the permission of duplicate application and only apply without permission. It is also easy to use!

[中文文档](./README-CN.md)  

## Installation

OkPermissions is installed by adding the following dependency to your `build.gradle` file:

```groovy
dependencies {
    implementation 'com.okpermission:OkPermission:1.0'
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
	
	OkPermissions.requestPerssions(this,perms);
    //Or other
    OkPermissions.requestPerssions(this,Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE);
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

