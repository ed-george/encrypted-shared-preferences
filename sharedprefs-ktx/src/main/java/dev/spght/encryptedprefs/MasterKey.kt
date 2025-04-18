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

/**
 * Creates a [MasterKey] with the provided parameters.
 *
 * * Based on MasterKey.kt from AndroidX Crypto Ktx - v1.1.0-alpha07
 * * Permalink: [e50caac](https://android.googlesource.com/platform/frameworks/support/+/e50caacef9794c6c1d05ed647347a01b06b96930/security/security-crypto-ktx/src/main/java/androidx/security/crypto/MasterKey.kt)
 *
 * @param context The context to work with.
 * @param keyAlias The alias to use for the `MasterKey`.
 * @param keyScheme The [MasterKey.KeyScheme] to have the `MasterKey` use.
 * @param authenticationRequired `true` if the user must authenticate for the `MasterKey` to be
 *   used.
 * @param userAuthenticationValidityDurationSeconds Duration in seconds that the `MasterKey` is
 *   valid for after the user has authenticated. Must be a value > 0.
 * @param requestStrongBoxBacked `true` if the key should be stored in Strong Box, if possible.
 */
public fun MasterKey(
  context: Context,
  keyAlias: String = MasterKey.DEFAULT_MASTER_KEY_ALIAS,
  keyScheme: MasterKey.KeyScheme = MasterKey.KeyScheme.AES256_GCM,
  authenticationRequired: Boolean = false,
  userAuthenticationValidityDurationSeconds: Int =
    MasterKey.getDefaultAuthenticationValidityDurationSeconds(),
  requestStrongBoxBacked: Boolean = false,
): MasterKey = MasterKey.Builder(context, keyAlias)
  .setKeyScheme(keyScheme)
  .setUserAuthenticationRequired(
    authenticationRequired,
    userAuthenticationValidityDurationSeconds,
  )
  .setRequestStrongBoxBacked(requestStrongBoxBacked)
  .build()
