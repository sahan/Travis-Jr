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
import java.util.Arrays;

import com.lonepulse.icklebot.annotation.bind.BindText;
import com.lonepulse.icklebot.annotation.bind.Model;
import com.lonepulse.travisjr.R;

/**
 * <p>This entity provides detailed information about a {@link Build}.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
@Model
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
	@BindText(R.id.build_number)
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
	@BindText(R.id.duration)
	private Integer duration;
	
	/**
	 * <p>The commit hash for the current build.
	 */
	@BindText(R.id.commit)
	private String commit;
	
	/**
	 * <p>The branch to which this commit was made.
	 */
	@BindText(R.id.branch)
	private String branch;
	
	/**
	 * <p>The message provided with this commit.
	 */
	@BindText(R.id.commit_message)
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
	@BindText(R.id.committer_name)
	private String committer_name;
	
	/**
	 * <p>The email of the developer who committed the this build.
	 */
	@BindText(R.id.committer_email)
	private String committer_email;
	
	/**
	 * <p>The GitHub url to compare the commit.
	 */
	private String compare_url;

	/**
	 * <p>The event which triggered this build - e.g. a {@code git push}.
	 */
	@BindText(R.id.event)
	private String event_type;

	/**
	 * <p>The build matrix which contains information on all the individual <i>build jobs</i>. 
	 */
	private BuildJob[] matrix;
	
	/**
	 * <p>The formatted start time of the build.
	 */
	@BindText(R.id.start_time)
	private String start_time;
	
	/**
	 * <p>The formatted start date of the build.
	 */
	@BindText(R.id.start_date)
	private String start_date;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getRepository_id() {
		return repository_id;
	}

	public void setRepository_id(Long repository_id) {
		this.repository_id = repository_id;
	}

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Short getResult() {
		return result;
	}

	public void setResult(Short result) {
		this.result = result;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getStarted_at() {
		return started_at;
	}

	public void setStarted_at(String started_at) {
		this.started_at = started_at;
	}

	public String getFinished_at() {
		return finished_at;
	}

	public void setFinished_at(String finished_at) {
		this.finished_at = finished_at;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getCommit() {
		return commit;
	}

	public void setCommit(String commit) {
		this.commit = commit;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCommitted_at() {
		return committed_at;
	}

	public void setCommitted_at(String committed_at) {
		this.committed_at = committed_at;
	}

	public String getAuthor_name() {
		return author_name;
	}

	public void setAuthor_name(String author_name) {
		this.author_name = author_name;
	}

	public String getAuthor_email() {
		return author_email;
	}

	public void setAuthor_email(String author_email) {
		this.author_email = author_email;
	}

	public String getCommitter_name() {
		return committer_name;
	}

	public void setCommitter_name(String committer_name) {
		this.committer_name = committer_name;
	}

	public String getCommitter_email() {
		return committer_email;
	}

	public void setCommitter_email(String committer_email) {
		this.committer_email = committer_email;
	}

	public String getCompare_url() {
		return compare_url;
	}

	public void setCompare_url(String compare_url) {
		this.compare_url = compare_url;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	
	public BuildJob[] getMatrix() {
		return matrix;
	}

	public void setMatrix(BuildJob[] matrix) {
		this.matrix = matrix;
	}
	
	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String startTime) {
		this.start_time = startTime;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String startDate) {
		this.start_date = startDate;
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
		builder.append(", matrix=");
		builder.append(Arrays.toString(matrix));
		builder.append(", start_time=");
		builder.append(start_time);
		builder.append(", start_date=");
		builder.append(start_date);
		builder.append("]");
		return builder.toString();
	}
}
