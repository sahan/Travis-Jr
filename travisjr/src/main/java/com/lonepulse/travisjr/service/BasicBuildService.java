package com.lonepulse.travisjr.service;

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


import java.util.Arrays;
import java.util.List;

import com.lonepulse.robozombie.core.annotation.Bite;
import com.lonepulse.robozombie.core.inject.Zombie;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.net.TravisCIEndpoint;

/**
 * <p>A basic implementation of {@link BuildService}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BasicBuildService implements BuildService {


	@Bite
	private TravisCIEndpoint travisCIEndpoint;
	{
		Zombie.infect(this);
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Build> getRecentBuilds(long repoId) {
	
		try {
		
			return Arrays.asList(travisCIEndpoint.getRecentBuilds(String.valueOf(repoId)));
		}
		catch(Exception e) {
			
			throw new BuildsUnavailableException(repoId);
		}
	}
}
