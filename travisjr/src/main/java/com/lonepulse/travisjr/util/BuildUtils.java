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


import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.model.BuildInfo;

/**
 * <p>Utilities for common operations on {@link Build}s.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public final class BuildUtils {

	
	/**
	 * <p>Constructor visibility is restricted. Instantiation is nonsensical.
	 * 
	 * @since 1.1.0
	 */
	private BuildUtils() {}

	
	/**
	 * <p>Determines if a {@link Build} has terminated.
	 *
	 * @param build
	 * 			the {@link Build} whose termination status is to be determined
	 * 
	 * @return {@code true} if the {@link Build} has terminated
	 * 
	 * @since 1.1.0
	 */
	public static boolean isTerminated(Build build) {
		
		String stateFinished = Resources.key(R.string.key_state_finished);
		return (build.getResult() != null)? true :build.getState().equals(stateFinished)? true :false;
	}
	
	/**
	 * <p>Determines if a {@link Build} is still ongoing without any valid result.
	 *
	 * @param build
	 * 			the {@link Build} whose ongoing status is to be determined
	 * 
	 * @return {@code true} if the {@link Build} is ongoing
	 * 
	 * @since 1.1.0
	 */
	public static boolean isOngoing(Build build) {
		
		return (build.getResult() == null) && !BuildUtils.isTerminated(build);
	}
	
	/**
	 * <p>Determines the {@link BuildState} of the given {@link Build}.
	 *
	 * @param build
	 * 			the {@link Build} whose state is to be determined
	 * 
	 * @return the {@link BuildState} of the given {@link Build}
	 * 
	 * @since 1.1.0
	 */
	public static BuildState discoverState(Build build) {
		
		if(BuildUtils.isOngoing(build)) return BuildState.ONGOING;
		
		Short buildStatus = build.getResult();
		boolean terminated = BuildUtils.isTerminated(build);
		
		if(buildStatus == null && terminated) return BuildState.ERRORED;
		
		else if(buildStatus.shortValue() == 0) return BuildState.PASSED;
		
		else return BuildState.FAILED;
	}
	
	/**
	 * <p>Determines if a {@link BuildInfo} has terminated.
	 *
	 * @param buildInfo
	 * 			the {@link BuildInfo} whose termination status is to be determined
	 * 
	 * @return {@code true} if the {@link BuildInfo} has terminated
	 * 
	 * @since 1.1.0
	 */
	public static boolean isTerminated(BuildInfo buildInfo) {
		
		String stateFinished = Resources.key(R.string.key_state_finished);
		return (buildInfo.getResult() != null)? true :buildInfo.getState().equals(stateFinished)? true :false;
	}
	
	/**
	 * <p>Determines if a {@link BuildInfo} is still ongoing without any valid result.
	 *
	 * @param buildInfo
	 * 			the {@link BuildInfo} whose ongoing status is to be determined
	 * 
	 * @return {@code true} if the {@link BuildInfo} is ongoing
	 * 
	 * @since 1.1.0
	 */
	public static boolean isOngoing(BuildInfo buildInfo) {
		
		return (buildInfo.getResult() == null) && !BuildUtils.isTerminated(buildInfo);
	}
	
	/**
	 * <p>Determines the {@link BuildState} of the given {@link BuildInfo}.
	 *
	 * @param buildInfo
	 * 			the {@link BuildInfo} whose state is to be determined
	 * 
	 * @return the {@link BuildState} of the given {@link BuildInfo}
	 * 
	 * @since 1.1.0
	 */
	public static BuildState discoverState(BuildInfo buildInfo) {
		
		if(BuildUtils.isOngoing(buildInfo)) return BuildState.ONGOING;
		
		Short buildStatus = buildInfo.getResult();
		boolean terminated = BuildUtils.isTerminated(buildInfo);
		
		if(buildStatus == null && terminated) return BuildState.ERRORED;
		
		else if(buildStatus.shortValue() == 0) return BuildState.PASSED;
		
		else return BuildState.FAILED;
	}
}
