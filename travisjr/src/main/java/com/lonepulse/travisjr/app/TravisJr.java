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


import android.content.Context;


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
		public static Context CONTEXT;
		
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onCreate() {
		
			super.onCreate();
			
			Application.CONTEXT = getApplicationContext();
		}
	}
}
