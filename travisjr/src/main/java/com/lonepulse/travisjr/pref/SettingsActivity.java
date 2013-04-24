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

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.View;
import android.widget.TextView;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;

/**
 * <p>This {@link PreferenceActivity} displays all settings and configurations 
 * for TravisJr.</p>
 * 
 * <p>The following is a list of included {@link PreferenceFragment}s:</p>
 * <ul>
 * 	<li>{@link AccountSettingsFragment}</li>
 * </ul> 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class SettingsActivity extends PreferenceActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		setTheme(android.R.style.Theme_Holo_Light);
		
		super.onCreate(savedInstanceState);
		
		String title = getString(R.string.ttl_act_settings);
		String subtitle = ((TravisJr)getApplication()).getAccountService().getGitHubUsername();
		
		View header = getLayoutInflater().inflate(R.layout.action_view_title, null);
		((TextView)header.findViewById(R.id.title)).setText(title);
		((TextView)header.findViewById(R.id.subtitle)).setText(subtitle);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_actionbar));
		actionBar.setIcon(R.drawable.ic_action_bar);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(header);
		
		getFragmentManager().beginTransaction()
		.replace(android.R.id.content, new SettingsFragment()).commit();
		
		getListView().setBackgroundColor(getResources().getColor(R.color.bg_lightest));
	}
	
	/**
	 * <p>Starts {@link SettingsActivity} with all preference fragments.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context) {
		
		context.startActivity(new Intent(context, SettingsActivity.class));
	}
}
