package com.lonepulse.travisjr.model;

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


import java.io.Serializable;

/**
 * <p>This entity represents a single build which was executed under 
 * continuous integration for a specific repository.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class Build implements Serializable, Comparable<Build>, Cloneable {


	private static final long serialVersionUID = -3466293008201679790L;

	
	/**
	 * <p>Identifies a {@link Build} uniquely.
	 */
	private long id;
	
	/**
	 * <p>The id of repository which this build belongs to.
	 */
	private Long repository_id;
	
	/**
	 * <p>The number assigned to this build from all builds 
	 * executed in order.
	 */
	private Long number;
	
	/**
	 * <p>This can be either started, failed, errored or passed.
	 */
	private String state;
	
	/**
	 * <p>The exit status of the build script. {@code 0} for success, 
	 * any other value for failure and {@code null} for unknown.
	 */
	private Short result;

	/**
	 * <p>The timestamp at which this build was started.
	 */
	private String started_at;
	
	/**
	 * <p>The timestamp at which this build terminated.
	 */
	private String finished_at;
	
	/**
	 * <p>The total time taked by this build.
	 */
	private Integer duration;
	
	/**
	 * <p>The commit hash for the current build.
	 */
	private String commit;
	
	/**
	 * <p>The branch to which this commit was made.
	 */
	private String branch;
	
	/**
	 * <p>The message provided with this commit.
	 */
	private String message;

	/**
	 * <p>The event which triggered this build - e.g. 
	 * a {@code git push}.
	 */
	private String event_type;

	/**
	 * <p>Accessor for id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * <p>Mutator for id.
	 *
	 * @param id 
	 *			the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * <p>Accessor for repository_id.
	 *
	 * @return the repository_id
	 */
	public Long getRepository_id() {
		return repository_id;
	}

	/**
	 * <p>Mutator for repository_id.
	 *
	 * @param repository_id 
	 *			the repository_id to set
	 */
	public void setRepository_id(Long repository_id) {
		this.repository_id = repository_id;
	}

	/**
	 * <p>Accessor for number.
	 *
	 * @return the number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * <p>Mutator for number.
	 *
	 * @param number 
	 *			the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * <p>Accessor for state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * <p>Mutator for state.
	 *
	 * @param state 
	 *			the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * <p>Accessor for result.
	 *
	 * @return the result
	 */
	public Short getResult() {
		return result;
	}

	/**
	 * <p>Mutator for result.
	 *
	 * @param result 
	 *			the result to set
	 */
	public void setResult(Short result) {
		this.result = result;
	}

	/**
	 * <p>Accessor for started_at.
	 *
	 * @return the started_at
	 */
	public String getStarted_at() {
		return started_at;
	}

	/**
	 * <p>Mutator for started_at.
	 *
	 * @param started_at 
	 *			the started_at to set
	 */
	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}

	/**
	 * <p>Accessor for finished_at.
	 *
	 * @return the finished_at
	 */
	public String getFinished_at() {
		return finished_at;
	}

	/**
	 * <p>Mutator for finished_at.
	 *
	 * @param finished_at 
	 *			the finished_at to set
	 */
	public void setFinished_at(String finished_at) {
		this.finished_at = finished_at;
	}

	/**
	 * <p>Accessor for duration.
	 *
	 * @return the duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * <p>Mutator for duration.
	 *
	 * @param duration 
	 *			the duration to set
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * <p>Accessor for commit.
	 *
	 * @return the commit
	 */
	public String getCommit() {
		return commit;
	}

	/**
	 * <p>Mutator for commit.
	 *
	 * @param commit 
	 *			the commit to set
	 */
	public void setCommit(String commit) {
		this.commit = commit;
	}

	/**
	 * <p>Accessor for branch.
	 *
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * <p>Mutator for branch.
	 *
	 * @param branch 
	 *			the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * <p>Accessor for message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * <p>Mutator for message.
	 *
	 * @param message 
	 *			the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * <p>Accessor for event_type.
	 *
	 * @return the event_type
	 */
	public String getEvent_type() {
		return event_type;
	}

	/**
	 * <p>Mutator for event_type.
	 *
	 * @param event_type 
	 *			the event_type to set
	 */
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Build other = (Build) obj;
		if (id != other.id) return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("Build [id=");
		builder.append(id);
		builder.append(", repository_id=");
		builder.append(repository_id);
		builder.append(", number=");
		builder.append(number);
		builder.append(", state=");
		builder.append(state);
		builder.append(", result=");
		builder.append(result);
		builder.append(", started_at=");
		builder.append(started_at);
		builder.append(", finished_at=");
		builder.append(finished_at);
		builder.append(", duration=");
		builder.append(duration);
		builder.append(", commit=");
		builder.append(commit);
		builder.append(", branch=");
		builder.append(branch);
		builder.append(", message=");
		builder.append(message);
		builder.append(", event_type=");
		builder.append(event_type);
		builder.append("]");
		
		return builder.toString();
	}

	/**
	 * <p>Compares {@link Build}s by their build number.
	 */
	@Override
	public int compareTo(Build another) {
		
		if(another == null) return -1;
		return another.getNumber().compareTo(this.getNumber());
	}

	/**
	 * <p>Performs a deep clone for this {@link Build} entity.
	 */
	@Override
	public Build clone() throws CloneNotSupportedException {
		
		Build build = new Build();
		
		build.setBranch(branch);
		build.setCommit(commit);
		build.setDuration(duration);
		build.setEvent_type(event_type);
		build.setFinished_at(finished_at);
		build.setId(id);
		build.setMessage(message);
		build.setNumber(number);
		build.setRepository_id(repository_id);
		build.setResult(result);
		build.setStarted_at(started_at);
		build.setState(state);
		
		return build;
	}
}
