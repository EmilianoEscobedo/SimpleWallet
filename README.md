# SimpleWallet

SimpleWallet is, as its name says, a simple two-pages project that mimics the withdrawal process in a digital wallet or bank, developed for ISTEA Mobile Applications course midterm exam. <br/>

The application has been built with **Kotlin and Jetpack Compose** for Android platform. 

## Features

- Display current wallet balance.  
- Enter withdrawal amount with numeric-only input.  
- Validate withdrawal amount against current balance.
- Store on in-memory data structure operations, simulating DB transactions.
- Show a receipt page with withdrawal details.  

## Run the app

### Run on emulator

#### Prerequisites

- [Android Studio installed](https://developer.android.com/studio).  
- [Android 11 SDK configured](https://developer.android.com/about/versions/11/setup-sdk).

#### Steps

1. Open the project in Android Studio.  
2. Build the project by selecting **Build > Make Project**.  
3. Run the emulator from **Tools > AVD Manager**.  
4. Click the **Run** button in Android Studio and select the emulator.  
5. The app will launch in the emulator, showing the balance page.  

### Run with adb on Android device

#### Prerequisites

- Android device with **USB debugging** enabled.  
- Android SDK platform tools installed (you can get it by installing Android Studio).  
- USB cable to connect device to PC.  

#### Steps

1. Build the APK from Android Studio: **Build > Build Bundle(s) / APK(s) > Build APK(s)**.  
2. Locate the generated APK in `app/build/outputs/apk/debug/app-debug.apk`.  
3. Connect your Android device via USB.  
4. Open a terminal and navigate to the folder containing the APK.  
5. Install the APK using adb:

```bash
adb install -r app-debug.apk
```

Once installed, open the app on your device to see the balance page and test withdrawals.
