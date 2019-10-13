package com.reactlibrary;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.common.logging.FLog;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class AppearanceModule extends ReactContextBaseJavaModule implements LifecycleEventListener {
    public static final String REACT_CLASS = "Appearance";
    private BroadcastReceiver receiver = null;

    public AppearanceModule(@NonNull ReactApplicationContext reactContext) {
        super(reactContext);
        // only android 10+ support dark mode
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            final ReactApplicationContext ctx = reactContext;
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    Configuration newConfig = intent.getParcelableExtra("newConfig");
                    sendEvent(reactContext, "appearanceChanged", getColorScheme(newConfig));
                }
            };
            ctx.addLifecycleEventListener(this);
        }
    }

    @NonNull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    /**
     * getColorScheme
     *
     * @param config
     * @return colorScheme
     */
    private WritableMap getColorScheme(Configuration config) {
        WritableMap colorScheme = Arguments.createMap();

        // only android 10+ support dark mode
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            int currentNightMode = config.uiMode & Configuration.UI_MODE_NIGHT_MASK;
            switch (currentNightMode) {
                case Configuration.UI_MODE_NIGHT_NO:
                    colorScheme.putString("colorScheme", "light");
                    break;
                case Configuration.UI_MODE_NIGHT_YES:
                    colorScheme.putString("colorScheme", "dark");
                    break;
            }
        } else {
            colorScheme.putString("colorScheme", "no-preference");
        }
        return colorScheme;
    }

    @androidx.annotation.Nullable
    @Override
    public Map<String, Object> getConstants() {
        HashMap<String, Object> constants = new HashMap();
        constants.put("initialPreferences", getColorScheme(getReactApplicationContext().getResources().getConfiguration()));
        return constants;
    }

    /**
     * sendEvent
     *
     * @param reactContext
     * @param eventName
     * @param params
     */
    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        if (reactContext.hasActiveCatalystInstance()) {
            FLog.i("sendEvent", eventName + ": " + params.toString());
            reactContext
                    .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }

    }

    @Override
    public void onHostResume() {
        final Activity activity = getCurrentActivity();

        if (activity == null) {
            FLog.e(ReactConstants.TAG, "no activity to register receiver");
            return;
        }
        activity.registerReceiver(receiver, new IntentFilter("onAppearanceConfigurationChanged"));
    }

    @Override
    public void onHostPause() {
        final Activity activity = getCurrentActivity();
        if (activity == null) return;
        try  {
            activity.unregisterReceiver(receiver);
        } catch (java.lang.IllegalArgumentException e) {
            FLog.e(ReactConstants.TAG, "receiver already unregistered", e);
        }
    }

    @Override
    public void onHostDestroy() {

    }
}
