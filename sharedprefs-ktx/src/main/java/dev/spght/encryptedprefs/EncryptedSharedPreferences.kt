/*
 * Copyright 2025 (C) Ed Holloway-George • spght.dev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.spght.encryptedprefs

import android.content.Context
import android.content.SharedPreferences
import dev.spght.encryptedprefs.EncryptedSharedPreferences.PrefKeyEncryptionScheme
import dev.spght.encryptedprefs.EncryptedSharedPreferences.PrefValueEncryptionScheme

/**
 * Opens an instance of encrypted SharedPreferences
 *
 * * Based on EncryptedSharedPreferences.kt from AndroidX Crypto Ktx - v1.1.0-alpha07
 * * Permalink: [e50caac](https://android.googlesource.com/platform/frameworks/support/+/e50caacef9794c6c1d05ed647347a01b06b96930/security/security-crypto-ktx/src/main/java/androidx/security/crypto/EncryptedSharedPreferences.kt)
 *
 * @param fileName The name of the file to open; can not contain path separators.
 * @param masterKey The master key to use.
 * @param prefKeyEncryptionScheme The scheme to use for encrypting keys.
 * @param prefValueEncryptionScheme The scheme to use for encrypting values.
 * @return The SharedPreferences instance that encrypts all data.
 */
@Suppress("FunctionNaming", "ktlint:standard:function-naming")
public fun EncryptedSharedPreferences(
  context: Context,
  fileName: String,
  masterKey: MasterKey,
  prefKeyEncryptionScheme: PrefKeyEncryptionScheme = PrefKeyEncryptionScheme.AES256_SIV,
  prefValueEncryptionScheme: PrefValueEncryptionScheme = PrefValueEncryptionScheme.AES256_GCM,
): SharedPreferences = EncryptedSharedPreferences.create(
  context,
  fileName,
  masterKey,
  prefKeyEncryptionScheme,
  prefValueEncryptionScheme,
)
