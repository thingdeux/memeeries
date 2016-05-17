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

    public static void setIsUsingFirebase(boolean useFirebase, Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(API_TYPE, useFirebase)
                .apply();
    }

    public static boolean getIsUsingFirebase(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(API_TYPE, true);
    }
}
