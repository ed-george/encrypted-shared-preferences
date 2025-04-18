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
package dev.spght.encryptedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.MediumTest;
import java.io.File;
import java.security.KeyStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * This test checks that any migration from androidx.security.crypto.* to dev.spght.encryptedprefs.*
 * is functioning correctly
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
@SuppressWarnings("deprecation")
public class MigrationTest {

    private Context mContext;

    private static final String PREFS_FILE = "test_migration_shared_prefs";

    @Before
    public void setup() throws Exception {
        mContext = ApplicationProvider.getApplicationContext();

        // Delete all previous keys and shared preferences.
        String filePath =
                mContext.getFilesDir().getParent()
                        + "/shared_prefs/"
                        + "__androidx_security__crypto_encrypted_prefs__";
        File deletePrefFile = new File(filePath);
        deletePrefFile.delete();

        filePath = mContext.getFilesDir().getParent() + "/shared_prefs/" + PREFS_FILE;
        deletePrefFile = new File(filePath);
        deletePrefFile.delete();

        // Delete MasterKeys
        KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
        keyStore.load(null);
        keyStore.deleteEntry("_androidx_security_master_key_");
    }

    @Test
    public void testMigrationFromLegacyLibrary() throws Exception {

        // String Test
        final String stringTestKey = "StringTest";
        final String stringTestValue = "THIS IS A TEST STRING";

        final SharedPreferences.OnSharedPreferenceChangeListener listener =
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(
                            SharedPreferences sharedPreferences, String key) {
                        Assert.assertEquals(
                                stringTestValue, sharedPreferences.getString(stringTestKey, null));
                    }
                };

        // Generate old encrypted prefs
        androidx.security.crypto.MasterKey legacyMasterKey =
                new androidx.security.crypto.MasterKey.Builder(mContext)
                        .setKeyScheme(androidx.security.crypto.MasterKey.KeyScheme.AES256_GCM)
                        .build();

        SharedPreferences legacyEncryptedSharedPrefs =
                androidx.security.crypto.EncryptedSharedPreferences.create(
                        mContext,
                        PREFS_FILE,
                        legacyMasterKey,
                        androidx.security.crypto.EncryptedSharedPreferences.PrefKeyEncryptionScheme
                                .AES256_SIV,
                        androidx.security.crypto.EncryptedSharedPreferences
                                .PrefValueEncryptionScheme.AES256_GCM);

        legacyEncryptedSharedPrefs.registerOnSharedPreferenceChangeListener(listener);

        // Store string via legacy library
        SharedPreferences.Editor editor = legacyEncryptedSharedPrefs.edit();
        editor.putString(stringTestKey, stringTestValue);
        editor.commit();

        legacyEncryptedSharedPrefs.unregisterOnSharedPreferenceChangeListener(listener);

        // Generate new encrypted prefs
        MasterKey masterKey =
                new MasterKey.Builder(mContext)
                        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                        .build();

        SharedPreferences sharedPreferences =
                EncryptedSharedPreferences.create(
                        mContext,
                        PREFS_FILE,
                        masterKey,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

        // Check access remains the same with new library
        Assert.assertEquals(
                stringTestKey + " has the wrong value",
                stringTestValue,
                sharedPreferences.getString(stringTestKey, null));
    }
}
