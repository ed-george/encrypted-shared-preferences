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
package dev.spght.encryptedprefs.sample

import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Telephony.Mms.Part.FILENAME
import androidx.activity.ComponentActivity
import androidx.core.content.edit
import dev.spght.encryptedprefs.EncryptedSharedPreferences
import dev.spght.encryptedprefs.MasterKey
import dev.spght.encryptedprefs.sample.databinding.MainActivityBinding

class MainActivity : ComponentActivity() {

  private lateinit var binding: MainActivityBinding

  // Don't actually set these here like this!
  // This is for demonstrative purposes only
  private val masterKey by lazy { MasterKey(context = applicationContext) }
  private val prefs: SharedPreferences by lazy {
    EncryptedSharedPreferences(
      context = applicationContext,
      fileName = FILENAME,
      masterKey = masterKey,
    )
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = MainActivityBinding.inflate(layoutInflater)
    val view = binding.root
    setContentView(view)

    binding.saveButton.setOnClickListener { savePreference() }
    fetchPreference()
  }

  private fun savePreference() {
    prefs.edit {
      putString(PREF_KEY, binding.preferenceEdit.text.toString())
    }
    fetchPreference()
  }

  @Suppress("SetTextI18n")
  private fun fetchPreference() {
    val currentPref = prefs.getString(PREF_KEY, "null")
    binding.preferenceValue.text = "Current Pref Value: $currentPref"
  }

  companion object {
    const val PREF_KEY = "ExamplePref"
  }
}
