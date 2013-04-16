package com.lonepulse.travisjr.app;

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


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.lonepulse.travisjr.AuthenticationActivity;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.service.AccountService;
import com.lonepulse.travisjr.service.BasicAccountService;


/**
 * <p>This contract specifies the global services offered by {@link TravisJr.Application}. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public interface TravisJr {
	
	
	public static final class Application extends android.app.Application implements TravisJr {
		
		
		/**
		 * <p>{@link Application#getApplicationContext()} offered in a static context.
		 * 
		 * @since 1.1.0
		 */
		public static volatile Context CONTEXT;
		
		/**
		 * <p>An instance of {@link AccountService} which offers 
		 * services on the user account.
		 */
		private AccountService accountService;
		
		/**
		 * <p>An {@link Application} level {@link ReentrantLock} to manage 
		 * global race conditions.
		 */
		private final ReentrantLock lock = new ReentrantLock();
		
		/**
		 * <p>A flag which determines if a synchronization is already 
		 * taking place.
		 */
		private AtomicBoolean syncing = new AtomicBoolean(false);
		
		
		@Override
		public void onCreate() {
		
			super.onCreate();
			
			Application.CONTEXT = getApplicationContext();
			accountService = new BasicAccountService();
		}
		
		@Override
		public synchronized boolean isSyncing() {
			
			return syncing.get();
		}
		
		@Override
		public synchronized void setSyncing(boolean isSyncing) {

			syncing.set(isSyncing);
		}
		
		@Override
		public void purgeAccount(final Context context) {
			
			if(lock.tryLock()) {
			
				StringBuilder builder = new StringBuilder()
				.append(getAccountService().getGitHubUsername())
				.append(", ")
				.append(getString(R.string.conf_clear_account));
				
				new AlertDialog.Builder(context)
				.setTitle(getString(R.string.ttl_dialog_account))
				.setMessage(builder.toString())
				.setPositiveButton(getString(R.string.lbl_yes_uc), new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						accountService.setGitHubUsername("");
						AuthenticationActivity.start(context);
						
						lock.unlock();
					}
				})
				.setNegativeButton(getString(R.string.lbl_no_uc), new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						lock.unlock();
					}
				})
				.show();
			}
		}

		@Override
		public AccountService getAccountService() {
			
			return accountService;
		}
	}
	
	
	/**
	 * <p>Sets the sync state.
	 * 
	 * @param syncing
	 * 			the state to be set for syncing
	 * 
	 * @since 1.1.0
	 */
	void setSyncing(boolean syncing);
	
	/**
	 * <p>Specifies whether a sync operation is currently underway. 
	 *
	 * @return {@code true} if there is an ongoing sync
	 * 
	 * @since 1.1.0
	 */
	boolean isSyncing();
	
	/**
	 * <p>Clears all saved credentials and account details; navigates 
	 * to the {@link AuthenticationActivity}.
	 * 
	 * @param context
	 * 			the {@link Context} from which the user's 
	 * 			account is purged
	 *
	 * @since 1.1.0
	 */
	void purgeAccount(Context context);
	
	/**
	 * <p>Retrieves an implementation of {@link AccountService}. 
	 * 
	 * @return an instance of {@link AccountService}
	 *
	 * @since 1.1.0
	 */
	AccountService getAccountService();
}
