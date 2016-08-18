package com.feliperrm.doctororganizer.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feliperrm.doctororganizer.Models.Conference;
import com.feliperrm.doctororganizer.R;

import java.util.List;

/**
 * Created by felip on 17/08/2016.
 */
public class ConferenceCalendarAdapter extends RecyclerView.Adapter<ConferenceCalendarAdapter.ConferenceCalendarViewHolder> {

    Context context;
    List<Conference> conferences;

    public ConferenceCalendarAdapter(Context context, List<Conference> conferences) {
        this.context = context;
        this.conferences = conferences;
    }

    @Override
    public ConferenceCalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_confence_calendar_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //
        ConferenceCalendarViewHolder vh = new ConferenceCalendarViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ConferenceCalendarViewHolder holder, int position) {
        Conference atual = conferences.get(position);
        holder.speaker.setText(atual.getUser().getName());
    }

    @Override
    public int getItemCount() {
        return conferences.size();
    }

    public static class ConferenceCalendarViewHolder extends RecyclerView.ViewHolder {

        TextView speaker;

        public ConferenceCalendarViewHolder(View itemView) {
            super(itemView);
            speaker = (TextView) itemView.findViewById(R.id.textSpeaker);
        }

    }

}
