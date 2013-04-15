package com.lonepulse.travisjr.pref;

/*
 * #%L
 * Travis Jr.
 * %%
 * Copyright (C) 2013 Lonepulse
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;
import com.lonepulse.travisjr.dialog.LicensesActivity;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>This {@link PreferenceFragment} aggregates all individual preference 
 * fragments to provide a holistic view.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class SettingsFragment extends PreferenceFragment implements OnPreferenceClickListener {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
		
		findPreference(Resources.key(R.string.key_sign_out)).setOnPreferenceClickListener(this);
		findPreference(Resources.key(R.string.key_oss_licenses)).setOnPreferenceClickListener(this);
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		
		if(preference.getKey().equals(Resources.key(R.string.key_sign_out))) {
			
			((TravisJr)getActivity().getApplication()).purgeAccount(getActivity());
		}
		else if(preference.getKey().equals(Resources.key(R.string.key_oss_licenses))) {
			
			LicensesActivity.start(getActivity());
		}
		
		return true;
	}
}
