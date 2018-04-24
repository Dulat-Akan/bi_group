package bi.bigroup.life.utils;

import static bi.bigroup.life.config.BiGroupConfig.API_BASE_URL;
import static bi.bigroup.life.config.BiGroupConfig.BASE_URL;

public class Constants {
    // TEST users
    public static final String TEST_USERNAME = "test_bi_life";
    public static final String TEST_PWD = "opimr-BL2";
    public static final String TEST_USERNAME_2 = "dinislam_b";
    public static final String TEST_PWD_2 = "Qweasd123";
    public static final String HELP_PHONE = "8 (7172) 918989";

    // Constant keys
    public static final String OS_ANDROID = "android";
    public static final String KEY_USER = "user";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_PARAMS = "params";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_ROLES = "roles";
    public static final String KEY_BOOL = "bool";
    public static final String KEY_TYPE = "type";
    public static final String CACHE_TIME = "cache_time";
    public static final String KEY_CODE = "code";
    public static final String KEY_MAIN_IMAGE = "MainImage";
    public static final String KEY_SECONDARY_IMAGES = "SecondaryImages";
    public static final String KEY_ATTACHMENTS = "Attachments";

    // Mask, length, statics
    public static final String PHONE_MASK = "+7 ([000]) [000]-[00]-[00]";
    public static final int LENGTH_PHONE_NUMBER = 12;
    public static final int MIN_LENGHT_PASSWORD = 6;
    public static final int DEFAULT_MAX_LENGTH = 255;

    public static final int HELPER_ZERO = 0;
    public static final int HELPER_FIRST = 1;

    public static final int INITIAL_PAGE_NUMBER = 0;
    public static final int REQUEST_COUNT = 20;

    public final static int TOP_3 = 3;

    // Employees profile picture
    public static final String PROFILE_PICTURE = API_BASE_URL + "employees/%s/avatar/";

    public static final int LIMIT_SINGLE_FILE = 1;
    public static final int LIMIT_FILES = 5;

    // Databases
    public static final String DB_NAME = "bi_group_db";

    // Share urls
    public static final String SHARE_SUGGESTIONS = "suggestions";
    public static final String SHARE_QUESTIONNAIRES = "questionnaires";
    public static final String SHARE_NEWS = "news";
    public static final String shareUrlFormat = BASE_URL + "%s/%s";
    public static final String streamUrlFormat = API_BASE_URL + "files/%s?width=?&height=?";

    public static String buildShareUrl(String type, String id) {
        return String.format(shareUrlFormat, type, id);
    }

    public static String buildStreamUrl(String streamId) {
        return String.format(streamUrlFormat, streamId);
    }

    public static String getProfilePicture(String employeeCode) {
        return String.format(PROFILE_PICTURE, employeeCode);
    }
}