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
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.util.TextUtils;

/**
 * <p>An extension of {@link ArrayAdapter} which populates a {@link ListView} 
 * with {@link Build} entities.
 * 
 * @version 1.1.0
 * <br><br>
 * @author <a href="mailto:lahiru@lonepulse.com">Lahiru Sahan Jayasinghe</a>
 */
public class BuildAdapter extends ArrayAdapter<Build> {

	
	/**
	 * <p>The {@link Context} in which the adapter was instantiated. 
	 */
	private Context context;
	
	/**
	 * <p>The set of {@link Build} entities to be consumed by this adapter. 
	 */
	private List<Build> data;
	
	
	/**
	 * <p>Use {@link BuildAdapter#newInstance(Context, List)} instead.
	 */
	private BuildAdapter(Context context, List<Build> data) {
		
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
			convertView = inflater.inflate(R.layout.list_item_build, null);
			
			View root = convertView.findViewById(R.id.root);
			
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 85);
			root.setLayoutParams(params);
			root.setPadding(0, 0, 5, 0);
		}
		
		Build repo = data.get(position);
		
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
	 * <p>Processed the indicator on the build list item to 
	 * specify the build status.
	 * 
	 * @param position
	 * 			the index of the list item
	 * 
	 * @param build
	 * 			the {@link Build} whose status is to be indicated
	 * 
	 * @param convertView
	 * 			the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processStatus(int position, Build build, View convertView) {
	
		Short buildStatus = build.getResult();
		
		boolean finished = (buildStatus != null)? true :build.getState().equals("finished")? true :false;
		
		ImageView status = ((ImageView)convertView.findViewById(R.id.status));
		
		if(buildStatus == null && finished) 
			status.setImageResource(position % 2 == 0? R.drawable.gear_errored :R.drawable.gear_errored_alt);
		
		else if(buildStatus == null && !finished) 
			status.setImageResource(position % 2 == 0? R.drawable.gear_started :R.drawable.gear_started_alt);
		
		else if(buildStatus.shortValue() == 0)
			status.setImageResource(position % 2 == 0? R.drawable.gear_passed :R.drawable.gear_passed_alt);
			
		else
			status.setImageResource(position % 2 == 0? R.drawable.gear_failed :R.drawable.gear_failed_alt);
		
		return convertView;
	}
	
	/**
	 * <p>Plugs data into the information placeholders on the build list item. 
	 * 
	 * @param build
	 * 			the {@link Build} whose information is to be processed
	 * 
	 * @param convertView
	 * 			the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processInfo(Build build, View convertView) {

		((TextView)convertView.findViewById(R.id.build_number))
		.setText(String.valueOf(build.getNumber()));
		
		((TextView)convertView.findViewById(R.id.duration))
		.setText(String.valueOf(TextUtils.isAvailable(build.getDuration())));
		
		((TextView)convertView.findViewById(R.id.branch))
		.setText(String.valueOf(TextUtils.isAvailable(build.getBranch())));
		
		((TextView)convertView.findViewById(R.id.event))
		.setText(String.valueOf(TextUtils.isAvailable(build.getEvent_type())));
		
		return convertView;
	}
	
	/**
	 * <p>Creates a new instance of {@link BuildAdapter} with the base {@link Context} 
	 * and the set of {@link Data} which it is to consume. It sorts the {@link Build}s 
	 * by the last-build start date. 
	 *
	 * @param context
	 * 			the {@link Context} in which the adapter was instantiated
	 * 
	 * @param data
	 * 			the set of {@link Build} entities to be consumed by this adapter
	 * 
	 * @return a new instance of {@link ArrayAdapter} of type {@link Build}
	 * 
	 * @since 1.1.0
	 */
	public static ArrayAdapter<Build> newInstance(Context context, List<Build> data) {
		
		Collections.sort(data);
		return new BuildAdapter(context, data);
	}
}
