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

import android.content.Context;

import com.lonepulse.icklebot.annotation.inject.ApplicationContract;


/**
 * <p>This contract specifies the global services offered by {@link TravisJr.Application}. 
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@ApplicationContract
public interface TravisJr {
	
	
	/**
	 * <p>The default implementation of {@link TravisJr} which is used as the 
	 * default {@link Application} instance as specified in the manifest.
	 * 
	 * @version 1.1.1
	 */
	public static final class Application extends android.app.Application implements TravisJr {
		
		
		/**
		 * <p>{@link Application#getApplicationContext()} offered in a static context.
		 * 
		 * @since 1.1.0
		 */
		private static volatile Context context;
		
		/**
		 * <p>The application context offered in a static context which conforms to the 
		 * {@link TravisJr} contract. 
		 * 
		 * @since 1.1.1 
		 */
		private static volatile TravisJr instance;
		
		/**
		 * <p>A flag which determines if a synchronization is already 
		 * taking place.
		 */
		private AtomicBoolean syncing = new AtomicBoolean(false);
		
		
		/**
		 * <p>Accessor for {@link #context}.
		 *
		 * @return {@link #context}
		 * 
		 * @since 1.1.1
		 */
		public static Context getContext() {
			
			return context;
		}

		/**
		 * <p>Accessor for {@link #instance}.
		 *
		 * @return {@link #instance}
		 * 
		 * @since 1.1.1
		 */
		public static TravisJr getInstance() {
			
			return instance;
		}

		@Override
		public void onCreate() {
		
			super.onCreate();
			
			Application.context = getApplicationContext();
			Application.instance = this;
		}
		
		@Override
		public synchronized boolean isSyncing() {
			
			return syncing.get();
		}
		
		@Override
		public synchronized void setSyncing(boolean isSyncing) {

			syncing.set(isSyncing);
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
}
