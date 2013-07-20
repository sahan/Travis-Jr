package com.lonepulse.travisjr.adapter;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;
import com.lonepulse.travisjr.model.Repo;

/**
 * <p>An extension of {@link ArrayAdapter} which is used to populate the navigation items 
 * for an {@link ActionBar} with a navigation mode of {@link ActionBar#NAVIGATION_MODE_LIST}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class NavigationAdapter extends ArrayAdapter<String> {


	/**
	 * <p>Use {@link RepoNavigationAdapter#newInstance(Context, int, int, String...)} instead.
	 */
	private NavigationAdapter(Context context, int resourceId, int textViewResourceId, String... navigationItems) {
		
		super(context, resourceId, textViewResourceId, navigationItems);
	}
	
	/**
	 * <p>Updates the subtitle (identified by {@code R.id.subtitle}) to the GitHub username.
	 * 
	 * @since 1.1.0
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		View view = super.getView(position, convertView, parent);
		TextView subtitle = (TextView) view.findViewById(R.id.subtitle);
		
		if(subtitle != null) {
			
			subtitle.setText(TravisJr.Application.getInstance().getAccountService().getGitHubUsername());
		}
		
		return view;
	}
	
	/**
	 * <p>Creates a new instance of {@link NavigationAdapter} using the given parameters and initializes 
	 * the drop down view resource with the given id.
	 *
	 * @param context
	 * 			the {@link Context} in which the adapter was instantiated
	 * 
	 * @param resourceId
	 * 			the ID of the layout resource to use
	 * 
	 * @param textViewResourceId
	 * 			the ID of the {@link TextView} within layout to set the navigation title 
	 * 
	 * @param dropDownViewResource
	 * 			the ID of the view resource to be used for displaying the drop down navigation items
	 * 
	 * @param navigationItems
	 * 			the array of titles for the navigation items
	 * 
	 * @return a new instance of {@link ArrayAdapter} of type {@link Repo}
	 * 
	 * @since 1.1.0
	 */
	public static ArrayAdapter<String> newInstance(Context context, int resourceId, int textViewResourceId, 
												   int dropDownViewResource, String... navigationItems) {
		
		ArrayAdapter<String> adapter = new NavigationAdapter(context, resourceId, textViewResourceId, navigationItems);
		adapter.setDropDownViewResource(dropDownViewResource);
		
		return adapter;
	}
}
