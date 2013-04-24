package com.lonepulse.travisjr.dialog;

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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.lonepulse.travisjr.R;

/**
 * <p>Displays a list of open source libraries which are being used and 
 * their license information.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class LicensesActivity extends ListActivity implements OnItemClickListener {
	
	
	private String[] libraries;
	private String[] libraryLicenses;
	private String[] libraryUrls;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		libraries = getResources().getStringArray(R.array.libraries);
		libraryLicenses = getResources().getStringArray(R.array.library_licenses);
		libraryUrls = getResources().getStringArray(R.array.library_urls);
		
		String key_library = "library";
		String key_license = "license";
		
		String[] from = new String[] {key_library, key_license};
		int[] to = new int[] {android.R.id.text1, android.R.id.text2};
		
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		
		for (int i = 0; i < libraries.length; i++) {
			
			Map<String, String> model = new HashMap<String, String>();
			model.put(key_library, libraries[i]);
			model.put(key_license, libraryLicenses[i]);
			
			data.add(model);
		}
		
		setListAdapter(new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, from, to));
		getListView().setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(libraryUrls[position]));
		
		startActivity(intent);
	}
	
	/**
	 * <p>Starts {@link LicensesActivity} themed as a dialog.
	 *
	 * @param context
	 * 			the {@link Context} of initiation
	 * 
	 * @since 1.1.0
	 */
	public static final void start(Context context) {
		
		context.startActivity(new Intent(context, LicensesActivity.class));
	}
}
