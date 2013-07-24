package com.lonepulse.travisjr.service;

/*
 * #%L
 * Travis Jr. App
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


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;
import com.lonepulse.travisjr.util.Resources;

/**
 * <p>This enum declares the modes by which an individual can use the application. 
 * As an <b>member</b>, the user could filter out the repositories which he's created 
 * and those which he has contributed to. As an <b>organization</b>, the user is 
 * provided a single unified view of the entire set of repositories which are maintained 
 * by the organization.
 * 
 * @since 1.1.0
 * <br><br>
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public enum UserMode {

	/**
	 * <p>Identifies a user who has a personal account on GitHub.
	 * 
	 * @since 1.1.0
	 */
	MEMBER(R.string.key_member),
	
	/**
	 * <p>Identifies a GitHub organization.
	 * 
	 * @since 1.1.0
	 */
	ORGANIZATION(R.string.key_organization);

	
	
	/**
	 * <p>A string which identifies this user mode uniquely.
	 */
	private String key;
	
	
	/**
	 * <p>Initializes this user mode with the unique {@link #key} which 
	 * identifies it <i>textually</i>.
	 * 
	 * @param keyStringResourceId
	 * 			the {@link String} resource ID of this user mode's textual key 
	 *
	 * @since 1.1.0
	 */
	private UserMode(int keyStringResourceId) {
		
		this.key = TravisJr.Application.getContext().getString(keyStringResourceId);
	}

	/**
	 * <p>Accessor for {@link #key}.
	 *
	 * @return the {@link #key}
	 * 
	 * @since 1.1.0
	 */
	public String getKey() {
		
		return key;
	}
	
	/**
	 * <p>Retrieves the {@link UserMode} for the authenticated user.
	 *
	 * @return the {@link UserMode} for the authenticated user
	 * 
	 * @since 1.1.0
	 */
	public static UserMode getCurrent() {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(TravisJr.Application.getContext());
		String currentKey = prefs.getString(Resources.key(R.string.key_user_mode), MEMBER.key);
		
		return currentKey.equals(MEMBER.key)? MEMBER :ORGANIZATION;
	}
	
	/**
	 * <p>Updates the user mode of the authenticated user to the given {@link UserMode}.
	 *
	 * @param userMode
	 * 			the {@link UserMode} to update that of the authenticated user 
	 * 
	 * @since 1.1.0
	 */
	public static void setCurrent(UserMode userMode) {
		
		Context context = TravisJr.Application.getContext();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		
		editor.putString(context.getString(R.string.key_user_mode), userMode.key);
		editor.commit();
	}
	
	/**
	 * <p>Returns {@code true} if the authenticated has the given {@link UserMode}.
	 *
	 * @return {@code true} if the authenticated has the given {@link UserMode}
	 * 
	 * @since 1.1.0
	 */
	public static boolean isCurrent(UserMode userMode) {
		
		return UserMode.getCurrent().equals(userMode);
	}

	/**
	 * <p>Retrieves the unique {@link #key} which identifies this user mode.
	 * 
	 * @return a unique key which identifies this user mode
	 * 
	 * @since 1.1.0
	 */
	@Override
	public String toString() {
		
		return key;
	}
}
