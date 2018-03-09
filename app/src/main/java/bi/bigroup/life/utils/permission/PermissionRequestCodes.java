package bi.bigroup.life.utils.permission;

import android.Manifest;

public class PermissionRequestCodes {
    public static final int STORAGE_PERMISSION_CODE = 4000;

    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static String PERM_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static String PERM_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
}