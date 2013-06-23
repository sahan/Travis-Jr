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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

import com.lonepulse.robozombie.core.annotation.Bite;
import com.lonepulse.robozombie.core.inject.Zombie;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.BuildInfo;
import com.lonepulse.travisjr.model.BuildJob;
import com.lonepulse.travisjr.net.AmazonS3Endpoint;
import com.lonepulse.travisjr.net.TravisCIEndpoint;

/**
 * <p>A basic implementation of {@link BuildService}.
 * 
 * @version 1.1.2
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BasicBuildService implements BuildService {


	@Bite
	private TravisCIEndpoint travisCIEndpoint;
	
	@Bite
	private AmazonS3Endpoint amazonS3Endpoint;
	
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
			
			throw new BuildsUnavailableException(repoId, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BuildInfo getBuildInfo(String owner, String repository, long buildId) {
		
		try {
			
			return travisCIEndpoint.getBuildInfo(owner, repository, String.valueOf(buildId));
		}
		catch(Exception e) {
			
			throw new BuildInfoUnavailableException(owner, repository, buildId, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<BuildJob, StringBuilder> getJobLogs(BuildInfo buildInfo) {
		
		try {
			
			Map<BuildJob, StringBuilder> logMap = new HashMap<BuildJob, StringBuilder>();
			BuildJob[] buildJobs = buildInfo.getMatrix();
			
			for (BuildJob buildJob : buildJobs) {
			
				try {
					
					String log = amazonS3Endpoint.getJobLog(String.valueOf(buildJob.getId()));
					logMap.put(buildJob, new StringBuilder(log));
				}
				catch(Exception e) {
					
					Log.e(getClass().getName(), "Failed to fetch build job log.", 
						  new JobLogUnavailableException(buildJob, e));
				}
			}
			
			return logMap;
		}
		catch(Exception e) {
			
			throw new FetchingLogsFailedException(buildInfo, e);
		}
	}
}
