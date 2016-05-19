package josh.land.meemeries.MemeBrowser.Utils;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Shared pref. manager - just basic getting and setting of keys.
 *
 * Created by Josh on 5/16/16.
 */
public abstract class SharedPrefManager {
    private static String USERNAME = "josh.land.shared.pref.username";
    private static String API_TYPE = "josh.land.shared.pref.api.type";

    public enum ApiType {
        Firebase,
        ApperyIO,
        Backendless
    }

    public static void setUsername(String username, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(USERNAME, username)
                .apply();
    }

    public static String getUsername(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(USERNAME, null);
    }

    public static void setApiType(ApiType apiType, Context context) {
        String toSet;
        switch (apiType) {
            case ApperyIO:
                toSet = "appery.io";
                break;
            case Firebase:
                toSet = "firebase";
                break;
            case Backendless:
                toSet = "backendless";
                break;
            default:
                toSet = "firebase";
                break;
        }


        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(API_TYPE, toSet)
                .apply();
    }

    public static ApiType getApiType(Context context) {
        String currentAPI = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(API_TYPE, "appery.io");

        switch (currentAPI) {
            case "appery.io":
                return ApiType.ApperyIO;
            case "backendless":
                return ApiType.Backendless;
            default:
                return ApiType.Firebase;
        }
    }
}
