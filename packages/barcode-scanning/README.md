# @capacitor-mlkit/barcode-scanning

Unofficial Capacitor plugin for [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning).[^1][^2]

## Features

- 🧩 Optional ready-to-use interface without webview customizations
- 🏎️ Extremely fast
- 📷 Scan multiple barcodes at once
- ⏺️ Define detection area
- 🏞️ Reading barcodes from images
- 🔦 Torch and Autofocus support
- 🔋 Supports Android and iOS

For a complete list of **supported barcodes**, see [BarcodeFormat](#barcodeformat).

## Demo

A working example can be found here: [https://github.com/robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

| Android                                                                                                                         |
| ------------------------------------------------------------------------------------------------------------------------------- |
| <img src="https://user-images.githubusercontent.com/13857929/224423595-b7e97595-8b2b-4cd7-b500-b30e10e11dfc.gif" width="324" /> |

## Guides

- [How to build an Ionic Barcode Scanner with Capacitor](https://ionic.io/blog/how-to-build-an-ionic-barcode-scanner-with-capacitor)

## Installation

```bash
npm install @capacitor-mlkit/barcode-scanning
npx cap sync
```

### Android

#### Permissions

This API requires the following permissions be added to your `AndroidManifest.xml` before the `application` tag:

```xml
<!-- To get access to the camera. -->
<uses-permission android:name="android.permission.CAMERA" />
<!-- To get access to the flashlight. -->
<uses-permission android:name="android.permission.FLASHLIGHT"/>
```

You also need to add the following meta data **in** the `application` tag in your `AndroidManifest.xml`:

```xml
<meta-data android:name="com.google.mlkit.vision.DEPENDENCIES" android:value="barcode_ui"/>
<!-- To use multiple models: android:value="face,model2,model3" -->
```

#### Data Binding

Enable the databinding library by setting the `dataBinding` and `enabled` build options to `true` in your module-level (app-level) Gradle file (usually `android/app/build.gradle`):

```diff
android {
    ...
+    buildFeatures {
+        dataBinding true
+    }
+    dataBinding {
+        enabled = true
+    }
}
```

#### Variables

This plugin will use the following project variables (defined in your app’s `variables.gradle` file):

- `$androidxCameraCamera2Version` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$androidxCameraCoreVersion` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$androidxCameraLifecycleVersion` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$androidxCameraViewVersion` version of `com.google.mlkit:barcode-scanning` (default: `1.1.0`)
- `$mlkitBarcodeScanningVersion` version of `com.google.mlkit:barcode-scanning` (default: `17.1.0`)
- `$playServicesCodeScannerVersion` version of `com.google.mlkit:barcode-scanning` (default: `16.0.0`)

### iOS

Add the `NSCameraUsageDescription` key to the `ios/App/App/Info.plist` file, which tells the user why the app needs to use the camera:

```diff
+ <key>NSCameraUsageDescription</key>
+ <string>The app enables the scanning of various barcodes.</string>
```

## Configuration

No configuration required for this plugin.

## Demo

A working example can be found here: [robingenz/capacitor-mlkit-plugin-demo](https://github.com/robingenz/capacitor-mlkit-plugin-demo)

## Usage

```typescript
import {
  BarcodeScanner,
  BarcodeFormat,
  LensFacing,
} from '@capacitor-mlkit/barcode-scanning';

const startScan = async () => {
  // The camera is visible behind the WebView, so that you can customize the UI in the WebView.
  // However, this means that you have to hide all elements that should not be visible.
  // You can find an example in our demo repository.
  // In this case we set a class `barcode-scanner-active`, which then contains certain CSS rules for our app.
  document.querySelector('body')?.classList.add('barcode-scanner-active');

  // Add the `barcodeScanned` listener
  const listener = await BarcodeScanner.addListener(
    'barcodeScanned',
    async result => {
      console.log(result.barcode);
    },
  );

  // Start the barcode scanner
  await BarcodeScanner.startScan();
};

const stopScan = async () => {
  // Make all elements in the WebView visible again
  document.querySelector('body')?.classList.remove('barcode-scanner-active');

  // Remove all listeners
  await BarcodeScanner.removeAllListeners();

  // Stop the barcode scanner
  await BarcodeScanner.stopScan();
};

const scanSingleBarcode = async () => {
  return new Promise(async resolve => {
    document.querySelector('body')?.classList.add('barcode-scanner-active');

    const listener = await BarcodeScanner.addListener(
      'barcodeScanned',
      async result => {
        await listener.remove();
        document
          .querySelector('body')
          ?.classList.remove('barcode-scanner-active');
        await BarcodeScanner.stopScan();
        resolve(result.barcode);
      },
    );

    await BarcodeScanner.startScan();
  });
};

const scan = async () => {
  const { barcodes } = await BarcodeScanner.scan({
    formats: [BarcodeFormat.QrCode],
  });
  return barcodes;
};

const isSupported = async () => {
  const { supported } = await BarcodeScanner.isSupported();
  return supported;
};

const enableTorch = async () => {
  await BarcodeScanner.enableTorch();
};

const disableTorch = async () => {
  await BarcodeScanner.disableTorch();
};

const toggleTorch = async () => {
  await BarcodeScanner.toggleTorch();
};

const isTorchEnabled = async () => {
  const { enabled } = await BarcodeScanner.isTorchEnabled();
  return enabled;
};

const isTorchAvailable = async () => {
  const { available } = await BarcodeScanner.isTorchAvailable();
  return available;
};

const setZoomRatio = async () => {
  await BarcodeScanner.setZoomRatio({ zoomRatio: 0.5 });
};

const getZoomRatio = async () => {
  const { zoomRatio } = await BarcodeScanner.getZoomRatio();
  return zoomRatio;
};

const getMinZoomRatio = async () => {
  const { zoomRatio } = await BarcodeScanner.getMinZoomRatio();
  return zoomRatio;
};

const getMaxZoomRatio = async () => {
  const { zoomRatio } = await BarcodeScanner.getMaxZoomRatio();
  return zoomRatio;
};

const openSettings = async () => {
  await BarcodeScanner.openSettings();
};

const isGoogleBarcodeScannerModuleAvailable = async () => {
  const { available } =
    await BarcodeScanner.isGoogleBarcodeScannerModuleAvailable();
  return available;
};

const installGoogleBarcodeScannerModule = async () => {
  await BarcodeScanner.installGoogleBarcodeScannerModule();
};

const checkPermissions = async () => {
  const { camera } = await BarcodeScanner.checkPermissions();
  return camera;
};

const requestPermissions = async () => {
  const { camera } = await BarcodeScanner.requestPermissions();
  return camera;
};
```

An example of the CSS class `barcode-scanner-active` **with** Ionic Framework could be:

```css
// Hide all elements
body.barcode-scanner-active {
  visibility: hidden;
  --background: transparent;
  --ion-background-color: transparent;
}

// Show only the barcode scanner modal
.barcode-scanner-modal {
  visibility: visible;
}

@media (prefers-color-scheme: dark) {
  .barcode-scanner-modal {
    --background: transparent;
    --ion-background-color: transparent;
  }
}
```

An example of the CSS class `barcode-scanner-active` **without** Ionic Framework could be:

```css
// Hide all elements
body.barcode-scanner-active {
  visibility: hidden;
}

// Show only the barcode scanner modal
.barcode-scanner-modal {
  visibility: visible;
}
```

If you can't see the camera view, make sure all elements in the DOM are not visible or have a transparent background to debug the issue.

## API

<docgen-index>

* [`startScan(...)`](#startscan)
* [`stopScan()`](#stopscan)
* [`takePhoto()`](#takephoto)
* [`readBarcodesFromImage(...)`](#readbarcodesfromimage)
* [`scan(...)`](#scan)
* [`isSupported()`](#issupported)
* [`enableTorch()`](#enabletorch)
* [`disableTorch()`](#disabletorch)
* [`toggleTorch()`](#toggletorch)
* [`isTorchEnabled()`](#istorchenabled)
* [`isTorchAvailable()`](#istorchavailable)
* [`setZoomRatio(...)`](#setzoomratio)
* [`getZoomRatio()`](#getzoomratio)
* [`getMinZoomRatio()`](#getminzoomratio)
* [`getMaxZoomRatio()`](#getmaxzoomratio)
* [`openSettings()`](#opensettings)
* [`isGoogleBarcodeScannerModuleAvailable()`](#isgooglebarcodescannermoduleavailable)
* [`installGoogleBarcodeScannerModule()`](#installgooglebarcodescannermodule)
* [`checkPermissions()`](#checkpermissions)
* [`requestPermissions()`](#requestpermissions)
* [`addListener('barcodeScanned', ...)`](#addlistenerbarcodescanned)
* [`addListener('barcodesScanned', ...)`](#addlistenerbarcodesscanned)
* [`addListener('scanError', ...)`](#addlistenerscanerror)
* [`addListener('googleBarcodeScannerModuleInstallProgress', ...)`](#addlistenergooglebarcodescannermoduleinstallprogress)
* [`removeAllListeners()`](#removealllisteners)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### startScan(...)

```typescript
startScan(options?: StartScanOptions | undefined) => Promise<void>
```

Start scanning for barcodes.

Only available on Android and iOS.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#startscanoptions">StartScanOptions</a></code> |

**Since:** 0.0.1

--------------------


### stopScan()

```typescript
stopScan() => Promise<void>
```

Stop scanning for barcodes.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### takePhoto()

```typescript
takePhoto() => Promise<Photo>
```

Read barcodes from an image.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#photo">Photo</a>&gt;</code>

**Since:** 0.0.1

--------------------


### readBarcodesFromImage(...)

```typescript
readBarcodesFromImage(options: ReadBarcodesFromImageOptions) => Promise<ReadBarcodesFromImageResult>
```

take a photo and return it as a string

Only available on Android

| Param         | Type                                                                                  |
| ------------- | ------------------------------------------------------------------------------------- |
| **`options`** | <code><a href="#readbarcodesfromimageoptions">ReadBarcodesFromImageOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#readbarcodesfromimageresult">ReadBarcodesFromImageResult</a>&gt;</code>

**Since:** 6.3.10

--------------------


### scan(...)

```typescript
scan(options?: ScanOptions | undefined) => Promise<ScanResult>
```

Scan a barcode with a ready-to-use interface without WebView customization.

On **Android**, this method is only available on devices with Google Play Services
installed. Therefore, no camera permission is required.

**Attention:** Before using this method on *Android*, first check if the Google <a href="#barcode">Barcode</a> Scanner module is available
by using `isGoogleBarcodeScannerModuleAvailable()`.

Only available on Android and iOS.

| Param         | Type                                                |
| ------------- | --------------------------------------------------- |
| **`options`** | <code><a href="#scanoptions">ScanOptions</a></code> |

**Returns:** <code>Promise&lt;<a href="#scanresult">ScanResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isSupported()

```typescript
isSupported() => Promise<IsSupportedResult>
```

Returns whether or not the barcode scanner is supported.

Available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#issupportedresult">IsSupportedResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### enableTorch()

```typescript
enableTorch() => Promise<void>
```

Enable camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### disableTorch()

```typescript
disableTorch() => Promise<void>
```

Disable camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### toggleTorch()

```typescript
toggleTorch() => Promise<void>
```

Toggle camera's torch (flash) during a scan session.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### isTorchEnabled()

```typescript
isTorchEnabled() => Promise<IsTorchEnabledResult>
```

Returns whether or not the camera's torch (flash) is enabled.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#istorchenabledresult">IsTorchEnabledResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### isTorchAvailable()

```typescript
isTorchAvailable() => Promise<IsTorchAvailableResult>
```

Returns whether or not the camera's torch (flash) is available.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#istorchavailableresult">IsTorchAvailableResult</a>&gt;</code>

**Since:** 0.0.1

--------------------


### setZoomRatio(...)

```typescript
setZoomRatio(options: SetZoomRatioOptions) => Promise<void>
```

Set the zoom ratio of the camera.

Only available on Android and iOS.

| Param         | Type                                                                |
| ------------- | ------------------------------------------------------------------- |
| **`options`** | <code><a href="#setzoomratiooptions">SetZoomRatioOptions</a></code> |

**Since:** 5.4.0

--------------------


### getZoomRatio()

```typescript
getZoomRatio() => Promise<GetZoomRatioResult>
```

Get the zoom ratio of the camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getzoomratioresult">GetZoomRatioResult</a>&gt;</code>

**Since:** 5.4.0

--------------------


### getMinZoomRatio()

```typescript
getMinZoomRatio() => Promise<GetMinZoomRatioResult>
```

Get the minimum zoom ratio of the camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getminzoomratioresult">GetMinZoomRatioResult</a>&gt;</code>

**Since:** 5.4.0

--------------------


### getMaxZoomRatio()

```typescript
getMaxZoomRatio() => Promise<GetMaxZoomRatioResult>
```

Get the maximum zoom ratio of the camera.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#getmaxzoomratioresult">GetMaxZoomRatioResult</a>&gt;</code>

**Since:** 5.4.0

--------------------


### openSettings()

```typescript
openSettings() => Promise<void>
```

Open the settings of the app so that the user can grant the camera permission.

Only available on Android and iOS.

**Since:** 0.0.1

--------------------


### isGoogleBarcodeScannerModuleAvailable()

```typescript
isGoogleBarcodeScannerModuleAvailable() => Promise<IsGoogleBarcodeScannerModuleAvailableResult>
```

Check if the Google <a href="#barcode">Barcode</a> Scanner module is available.

If the Google <a href="#barcode">Barcode</a> Scanner module is not available, you can install it by using `installGoogleBarcodeScannerModule()`.

Only available on Android.

**Returns:** <code>Promise&lt;<a href="#isgooglebarcodescannermoduleavailableresult">IsGoogleBarcodeScannerModuleAvailableResult</a>&gt;</code>

**Since:** 5.1.0

--------------------


### installGoogleBarcodeScannerModule()

```typescript
installGoogleBarcodeScannerModule() => Promise<void>
```

Install the Google <a href="#barcode">Barcode</a> Scanner module.

**Attention**: This only starts the installation.
The `googleBarcodeScannerModuleInstallProgress` event listener will
notify you when the installation is complete.

Only available on Android.

**Since:** 5.1.0

--------------------


### checkPermissions()

```typescript
checkPermissions() => Promise<PermissionStatus>
```

Check camera permission.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### requestPermissions()

```typescript
requestPermissions() => Promise<PermissionStatus>
```

Request camera permission.

Only available on Android and iOS.

**Returns:** <code>Promise&lt;<a href="#permissionstatus">PermissionStatus</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('barcodeScanned', ...)

```typescript
addListener(eventName: 'barcodeScanned', listenerFunc: (event: BarcodeScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when a barcode is scanned.

Available on Android and iOS.

| Param              | Type                                                                                    |
| ------------------ | --------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'barcodeScanned'</code>                                                           |
| **`listenerFunc`** | <code>(event: <a href="#barcodescannedevent">BarcodeScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('barcodesScanned', ...)

```typescript
addListener(eventName: 'barcodesScanned', listenerFunc: (event: BarcodesScannedEvent) => void) => Promise<PluginListenerHandle>
```

Called when barcodes are scanned.

Available on Android and iOS.

| Param              | Type                                                                                      |
| ------------------ | ----------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'barcodesScanned'</code>                                                            |
| **`listenerFunc`** | <code>(event: <a href="#barcodesscannedevent">BarcodesScannedEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 6.2.0

--------------------


### addListener('scanError', ...)

```typescript
addListener(eventName: 'scanError', listenerFunc: (event: ScanErrorEvent) => void) => Promise<PluginListenerHandle>
```

Called when an error occurs during the scan.

Available on Android and iOS.

| Param              | Type                                                                          |
| ------------------ | ----------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanError'</code>                                                      |
| **`listenerFunc`** | <code>(event: <a href="#scanerrorevent">ScanErrorEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 0.0.1

--------------------


### addListener('googleBarcodeScannerModuleInstallProgress', ...)

```typescript
addListener(eventName: 'googleBarcodeScannerModuleInstallProgress', listenerFunc: (event: GoogleBarcodeScannerModuleInstallProgressEvent) => void) => Promise<PluginListenerHandle>
```

Called when the Google <a href="#barcode">Barcode</a> Scanner module is installed.

Available on Android.

| Param              | Type                                                                                                                                          |
| ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'googleBarcodeScannerModuleInstallProgress'</code>                                                                                      |
| **`listenerFunc`** | <code>(event: <a href="#googlebarcodescannermoduleinstallprogressevent">GoogleBarcodeScannerModuleInstallProgressEvent</a>) =&gt; void</code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt;</code>

**Since:** 5.1.0

--------------------


### removeAllListeners()

```typescript
removeAllListeners() => Promise<void>
```

Remove all listeners for this plugin.

**Since:** 0.0.1

--------------------


### Interfaces


#### StartScanOptions

| Prop             | Type                                              | Description                                                                              | Since |
| ---------------- | ------------------------------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`**    | <code>BarcodeFormat[]</code>                      | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |
| **`lensFacing`** | <code><a href="#lensfacing">LensFacing</a></code> | Configure the camera (front or back) to use.                                             | 0.0.1 |


#### Photo

| Prop      | Type                                      | Description                                | Since  |
| --------- | ----------------------------------------- | ------------------------------------------ | ------ |
| **`img`** | <code><a href="#string">String</a></code> | The <a href="#photo">Photo</a> in base 64. | 6.3.10 |


#### String

Allows manipulation and formatting of text strings and determination and location of substrings within strings.

| Prop         | Type                | Description                                                  |
| ------------ | ------------------- | ------------------------------------------------------------ |
| **`length`** | <code>number</code> | Returns the length of a <a href="#string">String</a> object. |

| Method                | Signature                                                                                                                      | Description                                                                                                                                   |
| --------------------- | ------------------------------------------------------------------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------- |
| **toString**          | () =&gt; string                                                                                                                | Returns a string representation of a string.                                                                                                  |
| **charAt**            | (pos: number) =&gt; string                                                                                                     | Returns the character at the specified index.                                                                                                 |
| **charCodeAt**        | (index: number) =&gt; number                                                                                                   | Returns the Unicode value of the character at the specified location.                                                                         |
| **concat**            | (...strings: string[]) =&gt; string                                                                                            | Returns a string that contains the concatenation of two or more strings.                                                                      |
| **indexOf**           | (searchString: string, position?: number \| undefined) =&gt; number                                                            | Returns the position of the first occurrence of a substring.                                                                                  |
| **lastIndexOf**       | (searchString: string, position?: number \| undefined) =&gt; number                                                            | Returns the last occurrence of a substring in the string.                                                                                     |
| **localeCompare**     | (that: string) =&gt; number                                                                                                    | Determines whether two strings are equivalent in the current locale.                                                                          |
| **match**             | (regexp: string \| <a href="#regexp">RegExp</a>) =&gt; <a href="#regexpmatcharray">RegExpMatchArray</a> \| null                | Matches a string with a regular expression, and returns an array containing the results of that search.                                       |
| **replace**           | (searchValue: string \| <a href="#regexp">RegExp</a>, replaceValue: string) =&gt; string                                       | Replaces text in a string, using a regular expression or search string.                                                                       |
| **replace**           | (searchValue: string \| <a href="#regexp">RegExp</a>, replacer: (substring: string, ...args: any[]) =&gt; string) =&gt; string | Replaces text in a string, using a regular expression or search string.                                                                       |
| **search**            | (regexp: string \| <a href="#regexp">RegExp</a>) =&gt; number                                                                  | Finds the first substring match in a regular expression search.                                                                               |
| **slice**             | (start?: number \| undefined, end?: number \| undefined) =&gt; string                                                          | Returns a section of a string.                                                                                                                |
| **split**             | (separator: string \| <a href="#regexp">RegExp</a>, limit?: number \| undefined) =&gt; string[]                                | Split a string into substrings using the specified separator and return them as an array.                                                     |
| **substring**         | (start: number, end?: number \| undefined) =&gt; string                                                                        | Returns the substring at the specified location within a <a href="#string">String</a> object.                                                 |
| **toLowerCase**       | () =&gt; string                                                                                                                | Converts all the alphabetic characters in a string to lowercase.                                                                              |
| **toLocaleLowerCase** | (locales?: string \| string[] \| undefined) =&gt; string                                                                       | Converts all alphabetic characters to lowercase, taking into account the host environment's current locale.                                   |
| **toUpperCase**       | () =&gt; string                                                                                                                | Converts all the alphabetic characters in a string to uppercase.                                                                              |
| **toLocaleUpperCase** | (locales?: string \| string[] \| undefined) =&gt; string                                                                       | Returns a string where all alphabetic characters have been converted to uppercase, taking into account the host environment's current locale. |
| **trim**              | () =&gt; string                                                                                                                | Removes the leading and trailing white space and line terminator characters from a string.                                                    |
| **substr**            | (from: number, length?: number \| undefined) =&gt; string                                                                      | Gets a substring beginning at the specified location and having the specified length.                                                         |
| **valueOf**           | () =&gt; string                                                                                                                | Returns the primitive value of the specified object.                                                                                          |


#### RegExpMatchArray

| Prop        | Type                |
| ----------- | ------------------- |
| **`index`** | <code>number</code> |
| **`input`** | <code>string</code> |


#### RegExp

| Prop             | Type                 | Description                                                                                                                                                          |
| ---------------- | -------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`source`**     | <code>string</code>  | Returns a copy of the text of the regular expression pattern. Read-only. The regExp argument is a Regular expression object. It can be a variable name or a literal. |
| **`global`**     | <code>boolean</code> | Returns a Boolean value indicating the state of the global flag (g) used with a regular expression. Default is false. Read-only.                                     |
| **`ignoreCase`** | <code>boolean</code> | Returns a Boolean value indicating the state of the ignoreCase flag (i) used with a regular expression. Default is false. Read-only.                                 |
| **`multiline`**  | <code>boolean</code> | Returns a Boolean value indicating the state of the multiline flag (m) used with a regular expression. Default is false. Read-only.                                  |
| **`lastIndex`**  | <code>number</code>  |                                                                                                                                                                      |

| Method      | Signature                                                                     | Description                                                                                                                   |
| ----------- | ----------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| **exec**    | (string: string) =&gt; <a href="#regexpexecarray">RegExpExecArray</a> \| null | Executes a search on a string using a regular expression pattern, and returns an array containing the results of that search. |
| **test**    | (string: string) =&gt; boolean                                                | Returns a Boolean value that indicates whether or not a pattern exists in a searched string.                                  |
| **compile** | () =&gt; this                                                                 |                                                                                                                               |


#### RegExpExecArray

| Prop        | Type                |
| ----------- | ------------------- |
| **`index`** | <code>number</code> |
| **`input`** | <code>string</code> |


#### ReadBarcodesFromImageResult

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### Barcode

| Prop               | Type                                                                                  | Description                                                                                                                                                | Since |
| ------------------ | ------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------- | ----- |
| **`bytes`**        | <code>number[]</code>                                                                 | Raw bytes as it was encoded in the barcode.                                                                                                                | 0.0.1 |
| **`cornerPoints`** | <code>[[number, number], [number, number], [number, number], [number, number]]</code> | The four corner points of the barcode in clockwise order starting with top-left. This property is currently only supported by the `startScan(...)` method. | 0.0.1 |
| **`displayValue`** | <code>string</code>                                                                   | The barcode value in a human readable format.                                                                                                              | 0.0.1 |
| **`format`**       | <code><a href="#barcodeformat">BarcodeFormat</a></code>                               | The barcode format.                                                                                                                                        | 0.0.1 |
| **`rawValue`**     | <code>string</code>                                                                   | The barcode value in a machine readable format.                                                                                                            | 0.0.1 |
| **`valueType`**    | <code><a href="#barcodevaluetype">BarcodeValueType</a></code>                         | The barcode value type.                                                                                                                                    | 0.0.1 |


#### ReadBarcodesFromImageOptions

| Prop          | Type                         | Description                                                                              | Since |
| ------------- | ---------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`** | <code>BarcodeFormat[]</code> | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |
| **`path`**    | <code>string</code>          | The local path to the image file.                                                        | 0.0.1 |


#### ScanResult

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 0.0.1 |


#### ScanOptions

| Prop          | Type                         | Description                                                                              | Since |
| ------------- | ---------------------------- | ---------------------------------------------------------------------------------------- | ----- |
| **`formats`** | <code>BarcodeFormat[]</code> | Improve the speed of the barcode scanner by configuring the barcode formats to scan for. | 0.0.1 |


#### IsSupportedResult

| Prop            | Type                 | Description                                                                             | Since |
| --------------- | -------------------- | --------------------------------------------------------------------------------------- | ----- |
| **`supported`** | <code>boolean</code> | Whether or not the barcode scanner is supported by checking if the device has a camera. | 0.0.1 |


#### IsTorchEnabledResult

| Prop          | Type                 | Description                          | Since |
| ------------- | -------------------- | ------------------------------------ | ----- |
| **`enabled`** | <code>boolean</code> | Whether or not the torch is enabled. | 0.0.1 |


#### IsTorchAvailableResult

| Prop            | Type                 | Description                            | Since |
| --------------- | -------------------- | -------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the torch is available. | 0.0.1 |


#### SetZoomRatioOptions

| Prop            | Type                | Description            | Since |
| --------------- | ------------------- | ---------------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The zoom ratio to set. | 5.4.0 |


#### GetZoomRatioResult

| Prop            | Type                | Description     | Since |
| --------------- | ------------------- | --------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The zoom ratio. | 5.4.0 |


#### GetMinZoomRatioResult

| Prop            | Type                | Description             | Since |
| --------------- | ------------------- | ----------------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The minimum zoom ratio. | 5.4.0 |


#### GetMaxZoomRatioResult

| Prop            | Type                | Description             | Since |
| --------------- | ------------------- | ----------------------- | ----- |
| **`zoomRatio`** | <code>number</code> | The maximum zoom ratio. | 5.4.0 |


#### IsGoogleBarcodeScannerModuleAvailableResult

| Prop            | Type                 | Description                                                                           | Since |
| --------------- | -------------------- | ------------------------------------------------------------------------------------- | ----- |
| **`available`** | <code>boolean</code> | Whether or not the Google <a href="#barcode">Barcode</a> Scanner module is available. | 5.1.0 |


#### PermissionStatus

| Prop         | Type                                                                    | Since |
| ------------ | ----------------------------------------------------------------------- | ----- |
| **`camera`** | <code><a href="#camerapermissionstate">CameraPermissionState</a></code> | 0.0.1 |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


#### BarcodeScannedEvent

| Prop          | Type                                        | Description         | Since |
| ------------- | ------------------------------------------- | ------------------- | ----- |
| **`barcode`** | <code><a href="#barcode">Barcode</a></code> | A detected barcode. | 0.0.1 |


#### BarcodesScannedEvent

| Prop           | Type                   | Description            | Since |
| -------------- | ---------------------- | ---------------------- | ----- |
| **`barcodes`** | <code>Barcode[]</code> | The detected barcodes. | 6.2.0 |


#### ScanErrorEvent

| Prop          | Type                | Description        | Since |
| ------------- | ------------------- | ------------------ | ----- |
| **`message`** | <code>string</code> | The error message. | 0.0.1 |


#### GoogleBarcodeScannerModuleInstallProgressEvent

| Prop           | Type                                                                                                      | Description                                                    | Since |
| -------------- | --------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------- | ----- |
| **`state`**    | <code><a href="#googlebarcodescannermoduleinstallstate">GoogleBarcodeScannerModuleInstallState</a></code> | The current state of the installation.                         | 5.1.0 |
| **`progress`** | <code>number</code>                                                                                       | The progress of the installation in percent between 0 and 100. | 5.1.0 |


### Type Aliases


#### CameraPermissionState

<code><a href="#permissionstate">PermissionState</a> | 'limited'</code>


#### PermissionState

<code>'prompt' | 'prompt-with-rationale' | 'granted' | 'denied'</code>


### Enums


#### BarcodeFormat

| Members          | Value                      | Description                        | Since |
| ---------------- | -------------------------- | ---------------------------------- | ----- |
| **`Aztec`**      | <code>'AZTEC'</code>       | Only available on Android and iOS. | 0.0.1 |
| **`Codabar`**    | <code>'CODABAR'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`Code39`**     | <code>'CODE_39'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`Code93`**     | <code>'CODE_93'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`Code128`**    | <code>'CODE_128'</code>    | Only available on Android and iOS. | 0.0.1 |
| **`DataMatrix`** | <code>'DATA_MATRIX'</code> | Only available on Android and iOS. | 0.0.1 |
| **`Ean8`**       | <code>'EAN_8'</code>       | Only available on Android and iOS. | 0.0.1 |
| **`Ean13`**      | <code>'EAN_13'</code>      | Only available on Android and iOS. | 0.0.1 |
| **`Itf`**        | <code>'ITF'</code>         | Only available on Android and iOS. | 0.0.1 |
| **`Pdf417`**     | <code>'PDF_417'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`QrCode`**     | <code>'QR_CODE'</code>     | Only available on Android and iOS. | 0.0.1 |
| **`UpcA`**       | <code>'UPC_A'</code>       | Only available on Android and iOS. | 0.0.1 |
| **`UpcE`**       | <code>'UPC_E'</code>       | Only available on Android and iOS. | 0.0.1 |


#### LensFacing

| Members     | Value                | Since |
| ----------- | -------------------- | ----- |
| **`Front`** | <code>'FRONT'</code> | 0.0.1 |
| **`Back`**  | <code>'BACK'</code>  | 0.0.1 |


#### BarcodeValueType

| Members              | Value                          | Since |
| -------------------- | ------------------------------ | ----- |
| **`CalendarEvent`**  | <code>'CALENDAR_EVENT'</code>  | 0.0.1 |
| **`ContactInfo`**    | <code>'CONTACT_INFO'</code>    | 0.0.1 |
| **`DriversLicense`** | <code>'DRIVERS_LICENSE'</code> | 0.0.1 |
| **`Email`**          | <code>'EMAIL'</code>           | 0.0.1 |
| **`Geo`**            | <code>'GEO'</code>             | 0.0.1 |
| **`Isbn`**           | <code>'ISBN'</code>            | 0.0.1 |
| **`Phone`**          | <code>'PHONE'</code>           | 0.0.1 |
| **`Product`**        | <code>'PRODUCT'</code>         | 0.0.1 |
| **`Sms`**            | <code>'SMS'</code>             | 0.0.1 |
| **`Text`**           | <code>'TEXT'</code>            | 0.0.1 |
| **`Url`**            | <code>'URL'</code>             | 0.0.1 |
| **`Wifi`**           | <code>'WIFI'</code>            | 0.0.1 |
| **`Unknown`**        | <code>'UNKNOWN'</code>         | 0.0.1 |


#### GoogleBarcodeScannerModuleInstallState

| Members               | Value          | Since |
| --------------------- | -------------- | ----- |
| **`UNKNOWN`**         | <code>0</code> | 5.1.0 |
| **`PENDING`**         | <code>1</code> | 5.1.0 |
| **`DOWNLOADING`**     | <code>2</code> | 5.1.0 |
| **`CANCELED`**        | <code>3</code> | 5.1.0 |
| **`COMPLETED`**       | <code>4</code> | 5.1.0 |
| **`FAILED`**          | <code>5</code> | 5.1.0 |
| **`INSTALLING`**      | <code>6</code> | 5.1.0 |
| **`DOWNLOAD_PAUSED`** | <code>7</code> | 5.1.0 |

</docgen-api>

## Common Issues

### `NullPointerException` during `startScan(...)`

The following error may occur when calling the `startScan(...)` method: `Attempt to invoke virtual method 'void androidx.camera.view.PreviewView.setScaleType(androidx.camera.view.PreviewView$ScaleType)' on a null object reference`.
In this case, make sure that the databinding library is enabled (see [Data Binding](#data-binding)).

## Terms & Privacy

This plugin uses the [Google ML Kit](https://developers.google.com/ml-kit):

- [Terms & Privacy](https://developers.google.com/ml-kit/terms)
- [Android Data Disclosure](https://developers.google.com/ml-kit/android-data-disclosure)
- [iOS Data Disclosure](https://developers.google.com/ml-kit/ios-data-disclosure)

## Changelog

See [CHANGELOG.md](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/barcode-scanning/CHANGELOG.md).

## License

See [LICENSE](https://github.com/capawesome-team/capacitor-mlkit/blob/main/packages/barcode-scanning/LICENSE).

[^1]: This project is not affiliated with, endorsed by, sponsored by, or approved by Google LLC or any of their affiliates or subsidiaries.
[^2]: `QR Code` is a registered trademark of DENSO WAVE INCORPORATED.
