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
 * <p>This entity provides detailed information about a {@link Build}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BuildInfo implements Serializable {


	private static final long serialVersionUID = -8755854557147767907L;
	

	/**
	 * <p>Identifies a {@link BuildInfo} uniquely.
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
	 * <p>Specifies whether the last build was <b>started</b> or <b>finished</b>.
	 */
	private Short status;

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
	 * <p>The timestamp which indicates when the commit was initiated.
	 */
	private String committed_at;
	
	/**
	 * <p>The name of the developer who created the repository.
	 */
	private String author_name;
	
	/**
	 * <p>The email of the developer who created the repository.
	 */
	private String author_email;
	
	/**
	 * <p>The name of the developer who committed the this build.
	 */
	private String committer_name;
	
	/**
	 * <p>The email of the developer who committed the this build.
	 */
	private String committer_email;
	
	/**
	 * <p>The GitHub url to compare the commit.
	 */
	private String compare_url;

	/**
	 * <p>The event which triggered this build - e.g. a {@code git push}.
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
	 * <p>Accessor for status.
	 *
	 * @return the status
	 */
	public Short getStatus() {
		return status;
	}

	/**
	 * <p>Mutator for status.
	 *
	 * @param status 
	 *			the status to set
	 */
	public void setStatus(Short status) {
		this.status = status;
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
	 * <p>Accessor for committed_at.
	 *
	 * @return the committed_at
	 */
	public String getCommitted_at() {
		return committed_at;
	}

	/**
	 * <p>Mutator for committed_at.
	 *
	 * @param committed_at 
	 *			the committed_at to set
	 */
	public void setCommitted_at(String committed_at) {
		this.committed_at = committed_at;
	}

	/**
	 * <p>Accessor for author_name.
	 *
	 * @return the author_name
	 */
	public String getAuthor_name() {
		return author_name;
	}

	/**
	 * <p>Mutator for author_name.
	 *
	 * @param author_name 
	 *			the author_name to set
	 */
	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	/**
	 * <p>Accessor for author_email.
	 *
	 * @return the author_email
	 */
	public String getAuthor_email() {
		return author_email;
	}

	/**
	 * <p>Mutator for author_email.
	 *
	 * @param author_email 
	 *			the author_email to set
	 */
	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}

	/**
	 * <p>Accessor for committer_name.
	 *
	 * @return the committer_name
	 */
	public String getCommitter_name() {
		return committer_name;
	}

	/**
	 * <p>Mutator for committer_name.
	 *
	 * @param committer_name 
	 *			the committer_name to set
	 */
	public void setCommitter_name(String committer_name) {
		this.committer_name = committer_name;
	}

	/**
	 * <p>Accessor for committer_email.
	 *
	 * @return the committer_email
	 */
	public String getCommitter_email() {
		return committer_email;
	}

	/**
	 * <p>Mutator for committer_email.
	 *
	 * @param committer_email 
	 *			the committer_email to set
	 */
	public void setCommitter_email(String committer_email) {
		this.committer_email = committer_email;
	}

	/**
	 * <p>Accessor for compare_url.
	 *
	 * @return the compare_url
	 */
	public String getCompare_url() {
		return compare_url;
	}

	/**
	 * <p>Mutator for compare_url.
	 *
	 * @param compare_url 
	 *			the compare_url to set
	 */
	public void setCompare_url(String compare_url) {
		this.compare_url = compare_url;
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
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuildInfo other = (BuildInfo) obj;
		if (id != other.id)
			return false;
		
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		
		StringBuilder builder = new StringBuilder();
		
		builder.append("BuildInfo [id=");
		builder.append(id);
		builder.append(", repository_id=");
		builder.append(repository_id);
		builder.append(", number=");
		builder.append(number);
		builder.append(", state=");
		builder.append(state);
		builder.append(", result=");
		builder.append(result);
		builder.append(", status=");
		builder.append(status);
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
		builder.append(", committed_at=");
		builder.append(committed_at);
		builder.append(", author_name=");
		builder.append(author_name);
		builder.append(", author_email=");
		builder.append(author_email);
		builder.append(", committer_name=");
		builder.append(committer_name);
		builder.append(", committer_email=");
		builder.append(committer_email);
		builder.append(", compare_url=");
		builder.append(compare_url);
		builder.append(", event_type=");
		builder.append(event_type);
		builder.append("]");
		
		return builder.toString();
	}
}
