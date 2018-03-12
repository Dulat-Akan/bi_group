package bi.bigroup.life.utils;

import static bi.bigroup.life.config.BiGroupConfig.API_BASE_URL;

public class Constants {
    // TEST users
    public static final String TEST_USERNAME = "test_bi_life";
    public static final String TEST_PWD = "opimr-BL2";

    // Constant keys
    public static final String OS_ANDROID = "android";
    public static final String KEY_USER = "user";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_BOOL = "bool";
    public static final String CACHE_TIME = "cache_time";
    public static final String KEY_CODE = "code";
    public static final String KEY_MAIN_IMAGE = "MainImage";
    public static final String KEY_SECONDARY_IMAGES = "SecondaryImages";
    public static final String KEY_ATTACHMENTS= "Attachments";

    // Mask, length, statics
    public static final String PHONE_MASK = "+7 ([000]) [000]-[00]-[00]";
    public static final int LENGTH_PHONE_NUMBER = 12;
    public static final int MIN_LENGHT_PASSWORD = 6;
    public static final int DEFAULT_MAX_LENGTH = 255;

    public static final int HELPER_ZERO = 0;
    public static final int HELPER_FIRST = 1;

    public static final int INITIAL_PAGE_NUMBER = 0;
    public static final int REQUEST_COUNT = 20;

    // Employees profile picture
    public static final String PROFILE_PICTURE = API_BASE_URL + "employees/%s/avatar/";

    public static final int LIMIT_SINGLE_FILE = 1;
    public static final int LIMIT_FILES = 5;

    public static String getProfilePicture(String employeeCode) {
        return String.format(PROFILE_PICTURE, employeeCode);
    }
}