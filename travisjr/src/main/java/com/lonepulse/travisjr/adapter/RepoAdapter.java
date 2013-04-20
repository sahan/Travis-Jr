package com.lonepulse.travisjr.adapter;

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


import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.provider.ContactsContract.Contacts.Data;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.util.DateUtils;
import com.lonepulse.travisjr.util.Resources;
import com.lonepulse.travisjr.util.TextUtils;

/**
 * <p>An extension of {@link ArrayAdapter} which populates a {@link ListView} 
 * with {@link Repo} entities.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class RepoAdapter extends ArrayAdapter<Repo> {

	
	/**
	 * <p>Pre-references child views within the root list item layout. 
	 * 
	 * @version 1.1.0
	 */
	private static final class ViewHolder {
		
		public ViewGroup root;
		public ImageView status;
		public TextView repoName;
		public TextView buildNumber;
		public TextView startTime;
		public TextView startDate;
		public TextView duration;
		public TextView endTime;
		public TextView endDate;
		
		/**
		 * <p>Creates a new {@link ViewHolder} for the given view.
		 * 
		 * @param convertView
		 * 			the root view of the list item whose child views are 
		 * 			to be referenced
		 *
		 * @since 1.1.0
		 */
		public ViewHolder(View convertView) {
		
			root = (ViewGroup) convertView.findViewById(R.id.root);
			status = (ImageView) convertView.findViewById(R.id.status);
			repoName = (TextView) convertView.findViewById(R.id.repo_name);
			buildNumber = (TextView) convertView.findViewById(R.id.build_number);
			startTime = (TextView) convertView.findViewById(R.id.start_time);
			startDate = (TextView) convertView.findViewById(R.id.start_date);
			duration = (TextView) convertView.findViewById(R.id.duration);
			endTime = (TextView) convertView.findViewById(R.id.end_time);
			endDate = (TextView) convertView.findViewById(R.id.end_date);
		}
	}
	
	/**
	 * <p>The {@link Context} in which the adapter was instantiated. 
	 */
	private Context context;
	
	/**
	 * <p>The set of {@link Repo} entities to be consumed by this adapter. 
	 */
	private List<Repo> data;
	
	
	/**
	 * <p>Use {@link RepoAdapter#newInstance(Context, List)} instead.
	 */
	private RepoAdapter(Context context, List<Repo> data) {
		
		super(context, R.layout.list_item_repo, data);
		
		this.context = context;
		this.data = data;
	}

	/**
	 * <p>See {@link ArrayAdapter#getView(int, View, ViewGroup)}.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if(convertView == null) {
			
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item_repo, null);
			convertView.setTag(new ViewHolder(convertView));
		}
		
		Repo repo = data.get(position);
		
		processTheme(position, (ViewHolder)convertView.getTag());
		processStatus(position, repo, (ViewHolder)convertView.getTag());
		processInfo(repo, (ViewHolder)convertView.getTag());
		
		return convertView;
	}
	
	/**
	 * <p>Takes the root view of the list items and processes its layout if it is an 
	 * alternate list item.
	 * 
	 * @param position
	 * 			the index of the list item
	 * 
	 * @param viewHolder
	 * 			the {@link ViewHolder} of root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processTheme(int position, ViewHolder viewHolder) {

		if(position % 2 == 0)
			viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.bg_list_item_generic));
		
		else
			viewHolder.root.setBackgroundColor(context.getResources().getColor(R.color.bg_list_item_alt));
		
		return viewHolder.root;
	}
	
	/**
	 * <p>Processed the indicator on the repo list item to specify the build status.
	 * 
	 * @param position
	 * 			the index of the list item
	 * 
	 * @param repo
	 * 			the {@link Repo} whose build status is to be indicated
	 * 
	 * @param viewHolder
	 * 			the {@link ViewHolder} of the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processStatus(int position, Repo repo, ViewHolder viewHolder) {
	
		Short buildStatus = repo.getLast_build_status();
		
		String stateFinished = Resources.key(R.string.key_state_finished);
		boolean finished = (buildStatus != null)? true 
							:repo.getBuilds().get(0).getState().equals(stateFinished)? true :false;
		
		ImageView status = viewHolder.status;
		
		if(buildStatus == null && finished) {
			
			status.setImageResource(position % 2 == 0? R.drawable.gear_errored :R.drawable.gear_errored_alt);
		}
		else if(buildStatus == null && !finished) {
			
			status.setImageResource(position % 2 == 0? R.drawable.gear_started :R.drawable.gear_started_alt);
		}
		else if(buildStatus.shortValue() == 0) {
			
			status.setImageResource(position % 2 == 0? R.drawable.gear_passed :R.drawable.gear_passed_alt);
		}
		else {
			
			status.setImageResource(position % 2 == 0? R.drawable.gear_failed :R.drawable.gear_failed_alt);
		}
		
		return viewHolder.root;
	}
	
	/**
	 * <p>Processed the indicator on the repo list item to specify the build status.
	 * 
	 * @param repo
	 * 			the {@link Repo} whose information is to be processed
	 * 
	 * @param viewHolder
	 * 			the {@link ViewHolder} of the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processInfo(Repo repo, ViewHolder viewHolder) {

		viewHolder.repoName.setText(repo.getSlug());
		
		viewHolder.buildNumber.setText(String.valueOf(repo.getLast_build_number()));
		
		viewHolder.startTime
		.setText(TextUtils.isAvailable(DateUtils.formatTimeForDisplay(repo.getLast_build_started_at())));
		
		viewHolder.startDate
		.setText(TextUtils.isAvailable(DateUtils.formatDateForDisplay(repo.getLast_build_started_at())));
		
		viewHolder.duration
		.setText(String.valueOf(TextUtils.isAvailable(repo.getLast_build_duration())));
		
		TextView endTime = viewHolder.endTime;
		
		if(endTime != null)
			endTime.setText(TextUtils.isAvailable(DateUtils.formatTimeForDisplay(repo.getLast_build_finished_at())));
		
		TextView endDate = viewHolder.endDate;
		
		if(endDate != null)
			endDate.setText(TextUtils.isAvailable(DateUtils.formatDateForDisplay(repo.getLast_build_finished_at())));
		
		return viewHolder.root;
	}
	
	/**
	 * <p>Creates a new instance of {@link RepoAdapter} with the base {@link Context} 
	 * and the set of {@link Data} which it is to consume. It sorts the {@link Repo}s 
	 * by the last-build start date. 
	 *
	 * @param context
	 * 			the {@link Context} in which the adapter was instantiated
	 * 
	 * @param data
	 * 			the set of {@link Repo} entities to be consumed by this adapter
	 * 
	 * @return a new instance of {@link ArrayAdapter} of type {@link Repo}
	 * 
	 * @since 1.1.0
	 */
	public static ArrayAdapter<Repo> newInstance(Context context, List<Repo> data) {
		
		Collections.sort(data);
		return new RepoAdapter(context, data);
	}
}
