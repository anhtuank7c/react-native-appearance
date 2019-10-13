
# react-native-appearance


Polyfill for `Appearance` API to detect preferred color scheme (light/dark) in React Native 0.59, 0.60 and perhaps more (ymmv outside of these two!). The `Appearance` API will likely be available in `react-native@>=0.61`.

This library is currently iOS only, and on iOS < 13 the color scheme will always be `'no-preference'`.

## Installation

Installation instructions vary depending on whether you're using a managed Expo project or a bare React Native project.

### Managed Expo project

This library is supported in Expo SDK 35+.

```sh
expo install react-native-appearance
```

### Bare React Native project

Install the library using either Yarn:

```sh
yarn add react-native-appearance
```

or npm:

```sh
npm install react-native-appearance
```

  

## Mostly automatic install with react-native  

- **React Native 0.60+**


[CLI autolink feature](https://github.com/react-native-community/cli/blob/master/docs/autolinking.md) links the module while building the app. 


- **React Native <= 0.59**
```bash
$ react-native link @react-native-appearance
```


*Note* For `iOS` using `cocoapods`, run:

```bash
$ cd ios/ && pod install
```

### Manual installation


#### iOS

  

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`

2. Go to `node_modules` ➜ `react-native-appearance` and add `Appearance.xcodeproj`

3. In XCode, in the project navigator, select your project. Add `libAppearance.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`

4. Run your project (`Cmd+R`)<

 

#### Android

  

1. Open up `android/app/src/main/java/[...]/MainApplication.java`

- Add `import com.reactlibrary.AppearancePackage;` to the imports at the top of the file

- Add `new AppearancePackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:

```

include ':react-native-appearance'

project(':react-native-appearance').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-appearance/android')

```

3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

```

implementation project(':react-native-appearance')

```

  
### Configuration (required)

**Android**

Implement `onConfigurationChanged` method in `MainActivity.java`

```java
    import android.content.Intent; // <--- import
    import android.content.res.Configuration; // <--- import

    public class MainActivity extends ReactActivity {
      ......
      @Override
      public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Intent intent = new Intent("onAppearanceConfigurationChanged");
        intent.putExtra("newConfig", newConfig);
        sendBroadcast(intent);
    }

      ......

    }
```

## Usage

First, you need to wrap your app in the `AppearanceProvider`. At the root of your app, do the following:

```js
import { AppearanceProvider } from 'react-native-appearance';

export default () => (
  <AppearanceProvider>
    <App />
  </AppearanceProvider>
);
```

Now you can use `Appearance` and `useColorScheme` anywhere in your app.

```js
import { Appearance, useColorScheme } from 'react-native-appearance';

/**
 * Get the current color scheme
 */
Appearance.getColorScheme();

/**
 * Subscribe to color scheme changes with a hook
 */
function MyComponent() {
  let colorScheme = useColorScheme();
  if (colorScheme === 'dark') {
    // render some dark thing
  } else {
    // render some light thing
  }
}

/**
 * Subscribe to color scheme without a hook
 */
let subscription = Appearance.addChangeListener(({ colorScheme }) => {
  // do something with color scheme
});

// Remove the subscription at some point
subscription.remove()
```

## Attribution

This was mostly written by Facebook for inclusion in React Native core. It is provided here as a separate module so people can opt-in to using it without updating entirely to the newest React Native version.
