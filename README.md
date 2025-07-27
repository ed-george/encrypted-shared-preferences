# EncryptedSharedPreferences

<p align="center">
  <a href="https://github.com/ed-george/encrypted-shared-preferences/releases"><img alt="GitHub Release" src="https://img.shields.io/github/v/release/ed-george/encrypted-shared-preferences"></a>
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://developer.android.com/about/versions/marshmallow"><img alt="API" src="https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/ed-george/encrypted-shared-preferences/actions/workflows/build.yml?query=branch%3Amain"><img alt="Main branch status" src="https://img.shields.io/github/checks-status/ed-george/encrypted-shared-preferences/main">
</a>
</p>

**An updated fork of the [AndroidX Crypto library](https://developer.android.com/jetpack/androidx/releases/security#1.1.0-alpha07) (also known as JetSec) for on-going EncryptedSharedPreferences and EncryptedFile support post-deprecation.**

This library is *unofficial* and is not produced, endorsed, supported, or affiliated with Google or the original JetSec maintainers in any way.

The full source of this fork is released under the [Apache License 2.0](https://github.com/ed-george/encrypted-shared-preferences/blob/main/LICENSE) and was based on JetSec's [1.1.0-alpha07 code](https://android.googlesource.com/platform/frameworks/support/+/e50caacef9794c6c1d05ed647347a01b06b96930/security/security-crypto/).

## Background

In April 2025, the `androidx.security:security-crypto` library [was deprecated](https://developer.android.com/jetpack/androidx/releases/security#1.1.0-alpha07) at version `1.1.0-alpha07`. This deprecation had the knock-on effect of  causing a lack of on-going support for popular classes provided by the libraries such as `EncryptedSharedPreferences` and `EncryptedFile`.

This library forks both the `androidx.security:security-crypto` and the `androidx.security:security-crypto-ktx` libraries to provide continuous updates to core dependencies such as [tink](https://developers.google.com/tink) to allow existing JetSec users to continue to use the functionality securely.

### Should I be using this library?

**TL;DR - No, probably not.**

The existance of `EncryptedSharedPreferences` has misled many developers by implying that there's an inherent insecurity with `SharedPreferences`, which is simply not true.

As of Android 10, file-based encryption is enforced on device and through Android's app sandbox model, an attack-vector in which a `SharedPreferences` is compromised would be difficult to perform and likely require physical access to a device to do so.

Additionally, using `EncryptedSharedPreferences` also indirectly encourages developers to ignore security-best practises by storing sensitive data on-device. You should always avoid this whenever possible.

However, there are a small number of valid applications for this library or you may just wish to continue to use it based on your own requirements.

For more information and further reading - see my blog post ["Securing the Future: Navigating the Deprecation of Encrypted Shared Preferences"](https://www.spght.dev/articles/28-05-2024/jetsec-deprecation) to help determine if this library is right for you.

## Usage

Add the following dependency to your module-level `build.gradle` or `build.gradle.kts` file:
```groovy
repositories {
  mavenCentral()
}

dependencies {
  // Java
  implementation("dev.spght:encryptedprefs-core:<latest version>")
  // Kotlin  
  implementation("dev.spght:encryptedprefs-ktx:<latest version>")
}
```

You can then create an `EncryptedSharedPreferences` instance via the existing JetSec APIs

**Java**:

```java
MasterKey masterKey = new MasterKey.Builder(context)
  .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
  .build();

SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
  context,
  "secret_shared_prefs",
  masterKey,
  EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
  EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
);

// Use the shared preferences and editor as you normally would
SharedPreferences.Editor editor = sharedPreferences.edit();
```

**Kotlin:**
```kotlin
val masterKey = MasterKey(context = context)

val sharedPreferences: SharedPreferences = EncryptedSharedPreferences(
  context = context,
  fileName = "secret_shared_prefs",
  masterKey = masterKey,
)

// Use the shared preferences and editor as you normally would
sharedPreferences.edit {
  // etc...
}
```

### JetSec Migration Guide

If your project previously used the official 1.1.x JetSec implementation, it should be possible to migrate to this library without breaking existing functionality via the following actions:

* For Java projects, replace your existing `androidx.security:security-crypto` dependency with `dev.spght:encryptedprefs-core:<latest version>`
* For Kotlin projects, replace your existing `androidx.security:security-crypto-ktx` dependency with `dev.spght:encryptedprefs-ktx:<latest version>`

Within your code, replace any existing instances (e.g. imports) of `androidx.security.crypto` with `dev.spght.encryptedprefs`

### Known Issues

* Any encrypted shared preference file(s) or encrypted file(s) should _not_ be backed up with Auto Backup as, when restoring, it is likely the key used to encrypt it will no longer be present and will cause runtime crashes. You should therefore exclude all `EncryptedSharedPreference` or `EncryptedFile` from a backup using [backup rules](https://developer.android.com/guide/topics/data/autobackup#IncludingFiles).

#### Min SDK 21 Support
As of Tink Android version 1.18.0, support for Android SDK version 21 (Android 5.0 Lollipop) was removed and replaced with a min SDK version of 23 (Android 6.0 Marshmallow).

This change is present in this library's 1.1.x (and above) releases and therefore removes ongoing support for SDK 21 in favour of keeping dependencies up to date.

However, to gauge the impact on users of this library, if this change is problematic for your application please do [raise an issue](https://github.com/ed-george/encrypted-shared-preferences/issues/new) to highlight this.

For more information see https://github.com/ed-george/encrypted-shared-preferences/pull/24

## License
```xml
Copyright 2025 (C) Ed Holloway-George • spght.dev

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```