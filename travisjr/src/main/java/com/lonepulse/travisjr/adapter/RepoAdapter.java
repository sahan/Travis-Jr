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
import android.widget.AbsListView.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.Repo;
import com.lonepulse.travisjr.util.TextUtils;
import com.lonepulse.travisjr.util.TimestampUtils;

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
		
		super(context, 0, data);
		
		this.context = context;
		this.data = data;
	}

	/**
	 * <p>See {@link ArrayAdapter#getView(int, View, ViewGroup)}.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if(convertView == null) {
			
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.list_item_repo, null);
			
			View root = convertView.findViewById(R.id.root);
			
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 110);
			root.setLayoutParams(params);
			root.setPadding(0, 0, 5, 0);
		}
		
		Repo repo = data.get(position);
		
		processTheme(position, convertView);
		processStatus(position, repo, convertView);
		processInfo(repo, convertView);
		
		return convertView;
	}
	
	/**
	 * <p>Takes the root view of the list items and processes its 
	 * layout if it is an alternate list item.
	 * 
	 * @param position
	 * 			the index of the list item
	 * 
	 * @param convertView
	 * 			the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processTheme(int position, View convertView) {

		if(position % 2 == 0) {
			
			convertView.findViewById(R.id.root)
			.setBackgroundColor(context.getResources().getColor(R.color.bg_list_item_generic));
		}
		else {
			
			convertView.findViewById(R.id.root)
			.setBackgroundColor(context.getResources().getColor(R.color.bg_list_item_alt));
		}
		
		return convertView;
	}
	
	/**
	 * <p>Processed the indicator on the repo list item to 
	 * specify the build status.
	 * 
	 * @param position
	 * 			the index of the list item
	 * 
	 * @param repo
	 * 			the {@link Repo} whose build status is to be indicated
	 * 
	 * @param convertView
	 * 			the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processStatus(int position, Repo repo, View convertView) {
	
		Short buildStatus = repo.getLast_build_status();
		
		ImageView status = ((ImageView)convertView.findViewById(R.id.status));
		
		if(buildStatus == null) 
			status.setImageResource(position % 2 == 0? R.drawable.gear_pending :R.drawable.gear_pending_alt);
		
		else if(buildStatus.shortValue() == 0)
			status.setImageResource(position % 2 == 0? R.drawable.gear_passed :R.drawable.gear_passed_alt);
			
		else
			status.setImageResource(position % 2 == 0? R.drawable.gear_failed :R.drawable.gear_failed_alt);
		
		return convertView;
	}
	
	/**
	 * <p>Processed the indicator on the repo list item to 
	 * specify the build status.
	 * 
	 * @param repo
	 * 			the {@link Repo} whose information is to be processed
	 * 
	 * @param convertView
	 * 			the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processInfo(Repo repo, View convertView) {

		((TextView)convertView.findViewById(R.id.repo_name))
		.setText(repo.getSlug());
		
		((TextView)convertView.findViewById(R.id.build_number))
		.setText(String.valueOf(repo.getLast_build_number()));
		
		((TextView)convertView.findViewById(R.id.start_time))
		.setText(TimestampUtils.formatForDisplay(TextUtils.isAvailable(repo.getLast_build_started_at())));
		
		((TextView)convertView.findViewById(R.id.duration))
		.setText(String.valueOf(TextUtils.isAvailable(repo.getLast_build_duration())));
		
		return convertView;
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
