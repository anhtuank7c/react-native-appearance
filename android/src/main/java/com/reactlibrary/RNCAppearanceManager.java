package com.reactlibrary;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

public class RNCAppearanceManager extends SimpleViewManager<RNCAppearanceProvider> {
    public static final String CLASS_NAME = "RNCAppearanceProvider";
    @NonNull
    @Override
    public String getName() {
        return CLASS_NAME;
    }

    @NonNull
    @Override
    protected RNCAppearanceProvider createViewInstance(@NonNull ThemedReactContext reactContext) {
        return new RNCAppearanceProvider(reactContext);
    }
}
