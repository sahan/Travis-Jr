package com.lonepulse.travisjr.util;

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
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * <p>Utilities for launching {@link Intent}s with predefined <b>actions</b>.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class IntentUtills {

	
	/**
	 * <p>Constructor visibility is restricted. Instantiation is non-sensical.
	 * 
	 * @since 1.1.0
	 */
	private IntentUtills() {}

	
	/**
	 * <p>Fires an intent with the action {@link Intent#ACTION_SEND}.
	 *
	 * @param context
	 * 			the {@link Context} from which this intent is fired
	 * 
	 * @param to
	 * 			the email addresses of the recipients
	 * 
	 * @param subject
	 * 			the title or the subject of the message
	 * 
	 * @param body
	 * 			the content of the message
	 * 
	 * @param chooserTitle
	 * 			the title to be displayed on the choose dialog; if 
	 * 			you wish to use the default title pass {@code null}
	 * 
	 * @since 1.1.0
	 */
	public static void send(Context context, String[] to, String subject, String body, String chooserTitle) {
		
		Intent intent = new Intent(Intent.ACTION_SEND);
		
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_EMAIL, to);
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);

		if(TextUtils.isEmpty(chooserTitle)) {
			
			context.startActivity(intent);
		}
		else {
			
			context.startActivity(Intent.createChooser(intent, chooserTitle));
		}
	}
	
	/**
	 * <p>Fires an {@link Intent} with the action {@link Intent#ACTION_VIEW} for 
	 * a URI which points to the given repository on GitHub.
	 *
	 * @param context
	 * 			the {@link Context} from which this intent is fired
	 * 
	 * @param owner
	 * 			the GitHub user which created this repository. 
	 * 
	 * @param repo
	 * 			the name of the repository
	 * 
	 * @since 1.1.0
	 */
	public static void viewRepo(Context context, String owner, String repo) {
		
		String uri = "https://github.com/" + owner + "/" + repo; 
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(uri));
		
		context.startActivity(Intent.createChooser(intent, "View Repository"));
	}
	
	/**
	 * <p>Fires an {@link Intent} with the action {@link Intent#ACTION_VIEW} for 
	 * a URI which points to commit of the given repository on GitHub.
	 *
	 * @param context
	 * 			the {@link Context} from which this intent is fired
	 * 
	 * @param owner
	 * 			the GitHub user which created this repository. 
	 * 
	 * @param repo
	 * 			the name of the repository
	 * 
	 * @since 1.1.0
	 */
	public static void viewCommit(Context context, String owner, String repo, String commitHash) {
		
		String uri = "https://github.com/" + owner + "/" + repo + "/commit/" + commitHash;
		
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setData(Uri.parse(uri));
		
		context.startActivity(Intent.createChooser(intent, "View Commit"));
	}
}
