package com.lonepulse.travisjr.net;

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

import java.io.InputStream;
import java.security.KeyStore;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import android.util.Log;

import com.lonepulse.robozombie.proxy.Zombie;
import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.app.TravisJr;

/**
 * <p>Provides an {@link HttpClient} with a custom {@link SSLSocketFactory} using a keystore.</p>
 * 
 * @version 1.1.0
 * <br><br>
 * @since 0.1.2
 * <br><br>
 * @author <a href="mailto:sahan@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class ZombieConfig extends Zombie.Configuration {

	@Override
	public HttpClient httpClient() {
		
		HttpClient client = super.httpClient();
		
		try {
		
			KeyStore keyStore = KeyStore.getInstance("BKS");
			InputStream is = TravisJr.Application.getContext().getResources().openRawResource(R.raw.travisjr);
			
			try {
			
				keyStore.load(is, null);
			}
			finally {
				
				is.close();
			}
	
	        SSLSocketFactory sslSocketFactory = new SSLSocketFactory(keyStore);
	        sslSocketFactory.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
	        
	        SchemeRegistry schemeRegistry 
	        	= ((ThreadSafeClientConnManager)client.getConnectionManager()).getSchemeRegistry();
	        
	        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
		}
		catch(Exception e) {

			Log.e(getClass().getSimpleName(), "HttpClient configuration with a custom SSLSocketFactory failed.", e);
		}
		
		return client;
	}
}
