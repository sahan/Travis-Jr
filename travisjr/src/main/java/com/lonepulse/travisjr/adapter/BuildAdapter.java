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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lonepulse.travisjr.R;
import com.lonepulse.travisjr.model.Build;
import com.lonepulse.travisjr.util.Resources;
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
	 * <p>This enum specifies the different type of list item layouts 
	 * being used by the {@link BuildAdapter}.
	 * 
	 * @version 1.1.0
	 */
	private static enum ViewTypes {
		
		FINISHED(0, R.layout.list_item_build),
		ONGOING(1, R.layout.list_item_build_started);
		
		/**
		 * <p>The integer ID by which identifies this type.
		 */
		private int id;
		
		/**
		 * <p>The layout ID by which this type's layout.
		 */
		private int layoutId;
		
		
		/**
		 * <p>Initializes {@link #getId()}.
		 */
		private ViewTypes(int id, int layoutId) {
			
			this.id = id;
			this.layoutId = layoutId;
		}
	}
	
	/**
	 * <p>Pre-references child views within the root list item layout. 
	 * 
	 * @version 1.1.0
	 */
	private static final class ViewHolder {
		
		public ViewGroup root;
		public ImageView status;
		public ImageView statusStarted;
		public TextView buildNumber;
		public TextView duration;
		public TextView branch;
		public TextView event;
		public TextView message;
		
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
			statusStarted = (ImageView) convertView.findViewById(R.id.status_started);
			buildNumber = (TextView) convertView.findViewById(R.id.build_number);
			duration = (TextView) convertView.findViewById(R.id.duration);
			branch = (TextView) convertView.findViewById(R.id.branch);
			event = (TextView) convertView.findViewById(R.id.event);
			message = (TextView) convertView.findViewById(R.id.message);
		}
	}
	
	/**
	 * <p>The {@link Context} in which the adapter was instantiated. 
	 */
	private Context context;
	
	/**
	 * <p>The set of {@link Build} entities to be consumed by this adapter. 
	 */
	private List<Build> data;
	
	/**
	 * <p>This animation is set on the indicator for ongoing builds.
	 */
	private Animation rotate;
	
	
	/**
	 * <p>Use {@link BuildAdapter#newInstance(Context, List)} instead.
	 */
	private BuildAdapter(Context context, List<Build> data) {
		
		super(context, 0, data);
		
		this.context = context;
		this.data = data;
		this.rotate = AnimationUtils.loadAnimation(context, R.anim.rotate_2x_min);
	}
	
	/**
	 * <p>See {@link ArrayAdapter#getItemViewType(int)}. 
	 */
	@Override
	public int getItemViewType(int position) {
	
		Build build = data.get(position);
		boolean finished = isFinished(build);
		
		return ((build.getResult() == null) && !finished)? ViewTypes.ONGOING.id :ViewTypes.FINISHED.id;
	}
	
	/**
	 * <p>See {@link ArrayAdapter#getViewTypeCount()}.
	 */
	@Override
	public int getViewTypeCount() {
		
		return ViewTypes.values().length;
	}

	/**
	 * <p>See {@link ArrayAdapter#getView(int, View, ViewGroup)}.
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
		if(convertView == null) {
			
			int type = getItemViewType(position);
			
			int layoutId = (type == ViewTypes.ONGOING.id)? 
				ViewTypes.ONGOING.layoutId :ViewTypes.FINISHED.layoutId; 
			
			convertView = LayoutInflater.from(context).inflate(layoutId, null);
			
			ViewHolder viewHolder = new ViewHolder(convertView);
			convertView.setTag(viewHolder);
			
			if(type == ViewTypes.ONGOING.id)
				viewHolder.statusStarted.setAnimation(rotate);
		}
		
		Build build = data.get(position);
		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		
		processTheme(position, viewHolder);
		processStatus(position, build, viewHolder);
		processInfo(build, viewHolder);
		
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
	 * 			the {@link ViewHolder} of the root view to be processed
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
	 * <p>Processed the indicator on the build list item to specify the build status.
	 * 
	 * @param position
	 * 			the index of the list item
	 * 
	 * @param build
	 * 			the {@link Build} whose status is to be indicated
	 * 
	 * @param viewHolder
	 * 			the {@link ViewHolder} of the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processStatus(int position, Build build, ViewHolder viewHolder) {
	
		if(viewHolder.statusStarted != null) {
			
			ImageView status = viewHolder.statusStarted;
			status.setImageResource(position % 2 == 0? R.drawable.gear_started :R.drawable.gear_started_alt);
		}
		else {
			
			Short buildStatus = build.getResult();
			boolean finished = isFinished(build);
			
			ImageView status = viewHolder.status;
			
			if(buildStatus == null && finished)
				status.setImageResource(position % 2 == 0? R.drawable.gear_errored :R.drawable.gear_errored_alt);
			
			else if(buildStatus.shortValue() == 0)
				status.setImageResource(position % 2 == 0? R.drawable.gear_passed :R.drawable.gear_passed_alt);
			
			else
				status.setImageResource(position % 2 == 0? R.drawable.gear_failed :R.drawable.gear_failed_alt);
		}
		
		return viewHolder.root;
	}
	
	/**
	 * <p>Plugs data into the information placeholders on the build list item. 
	 * 
	 * @param build
	 * 			the {@link Build} whose information is to be processed
	 * 
	 * @param viewHolder
	 * 			the {@link ViewHolder} of the root view to be processed
	 * 
	 * @return the processed root view
	 */
	private View processInfo(Build build, ViewHolder viewHolder) {

		viewHolder.buildNumber.setText(String.valueOf(build.getNumber()));
		viewHolder.duration.setText(String.valueOf(TextUtils.isAvailable(build.getDuration())));
		viewHolder.branch.setText(String.valueOf(TextUtils.isAvailable(build.getBranch())));
		viewHolder.event.setText(String.valueOf(TextUtils.isAvailable(build.getEvent_type())));
		viewHolder.message.setText(String.valueOf(TextUtils.isAvailable(build.getMessage())));
		
		return viewHolder.root;
	}
	
	/**
	 * <p>Determines if the given {@link Build} has run to completion. 
	 *
	 * @param build
	 * 			the {@link Build} whose state is to discovered
	 * 
	 * @return {@code true} if the {@link Build} is complete 
	 */
	private boolean isFinished(Build build) {
	
		String stateFinished = Resources.key(R.string.key_state_finished);
		return (build.getResult() != null)? true :build.getState().equals(stateFinished)? true :false;
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
