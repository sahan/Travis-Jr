package com.lonepulse.travisjr.test;

/*
 * #%L
 * Travis Jr. Integration Tests
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


import org.junit.runners.model.InitializationError;

import android.app.Application;

import com.lonepulse.travisjr.app.TravisJr;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.res.RobolectricPackageManager;
import com.xtremelabs.robolectric.shadows.ShadowContextWrapper;

/**
 * <p>A custom extension {@link RobolectricTestRunner} which is used to serve 
 * an instance of {@link TravisJr} as the application instance for unit tests.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class TravisJrTestRunner extends RobolectricTestRunner {
	
	
	public TravisJrTestRunner(Class<?> testClass) throws InitializationError {

		super(testClass);
	}

	@Override
	protected Application createApplication() {
		
		TravisJr.Application application = new TravisJr.Application();
		
		ShadowContextWrapper shadowApp = Robolectric.shadowOf(application);
	    shadowApp.setPackageName("com.lonepulse.travisjr.app");
	    shadowApp.setPackageManager(new RobolectricPackageManager(application, robolectricConfig));
	    application.onCreate();
	    
	    return application;    
	}
}
