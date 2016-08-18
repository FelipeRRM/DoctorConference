package com.feliperrm.doctororganizer.Fragments;


import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feliperrm.doctororganizer.Activities.MainActivity;
import com.feliperrm.doctororganizer.Adapters.ConferenceCalendarAdapter;
import com.feliperrm.doctororganizer.Models.Conference;
import com.feliperrm.doctororganizer.R;
import com.feliperrm.doctororganizer.Utils.Geral;
import com.p_v.flexiblecalendar.FlexibleCalendarView;
import com.p_v.flexiblecalendar.entity.Event;
import com.p_v.flexiblecalendar.entity.SelectedDateItem;
import com.p_v.flexiblecalendar.view.BaseCellView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends BaseFragment {

    FlexibleCalendarView calendarView;
    ImageView leftArrow;
    ImageView rightArrow;
    TextView monthTextView;
    LinearLayout calendarTopBar;
    RecyclerView recyclerView;
    TextView selectedDateTxt;
    Button suggest;
    LinearLayout parentLayout;
    RelativeLayout loaderLayout;
    int day, month, year;
    ProgressBar progressBar;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        findViews(v);
        setUpViews();
        return v;
    }

    private void findViews(View v){
        calendarView = (FlexibleCalendarView) v.findViewById(R.id.calendarView);
        leftArrow = (ImageView) v.findViewById(R.id.left_arrow);
        rightArrow = (ImageView) v.findViewById(R.id.right_arrow);
        monthTextView = (TextView) v.findViewById(R.id.month_text_view);
        calendarTopBar = (LinearLayout) v.findViewById(R.id.calendarTopBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewConferencesCalendar);
        selectedDateTxt = (TextView) v.findViewById(R.id.selectedDateTxt);
        suggest = (Button) v.findViewById(R.id.idSuggest);
        parentLayout = (LinearLayout) v.findViewById(R.id.parentLayout);
        loaderLayout = (RelativeLayout) v.findViewById(R.id.loaderLayout);
        progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
    }

    public void setUpViews(){
        progressBar.getIndeterminateDrawable().setColorFilter(MainActivity.TAB1_COLOR, PorterDuff.Mode.MULTIPLY);
        customizeCalendar();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        suggest.setBackgroundColor(MainActivity.TAB1_COLOR);
        suggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateEventFragment.newInstance(day,month,year).show(getFragmentManager(), null);
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                parentLayout.setVisibility(View.VISIBLE);
                loaderLayout.setVisibility(View.GONE);
            }
        },800);
    }

    private void customizeCalendar(){
        calendarView.setStartDayOfTheWeek(Calendar.MONDAY);
        calendarTopBar.setBackgroundColor(MainActivity.TAB1_COLOR);

        final Calendar cal = Calendar.getInstance();
        cal.set(calendarView.getSelectedDateItem().getYear(), calendarView.getSelectedDateItem().getMonth(), 1);
        monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                Calendar.LONG, Locale.ENGLISH) + " " + calendarView.getSelectedDateItem().getYear());

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToPreviousMonth();
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.moveToNextMonth();
            }
        });
        calendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, 1);
                monthTextView.setText(cal.getDisplayName(Calendar.MONTH,
                        Calendar.LONG, Locale.ENGLISH) + " " + year);
                calendarView.onDateClick(new SelectedDateItem(year,month,1));
            }
        });
        calendarView.setShowDatesOutsideMonth(false);

        calendarView.setCalendarView(new FlexibleCalendarView.CalendarView() {
            @Override
            public BaseCellView getCellView(int position, View convertView, ViewGroup parent, int cellType) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar1_date_cell_view, null);
                }
                return cellView;
            }

            @Override
            public BaseCellView getWeekdayCellView(int position, View convertView, ViewGroup parent) {
                BaseCellView cellView = (BaseCellView) convertView;
                if (cellView == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    cellView = (BaseCellView) inflater.inflate(R.layout.calendar3_week_cell_view, null);
                    cellView.setBackgroundColor(MainActivity.TAB1_COLOR);
                    cellView.setTextColor(getResources().getColor(android.R.color.white));
                    cellView.setTextSize(18);
                }
                return cellView;
            }

            @Override
            public String getDayOfWeekDisplayValue(int dayOfWeek, String defaultValue) {
                return null;
            }
        });

        calendarView.setOnDateClickListener(new FlexibleCalendarView.OnDateClickListener() {
            @Override
            public void onDateClick(int year, int month, int day) {
                CalendarFragment.this.year = year;
                CalendarFragment.this.month = month;
                CalendarFragment.this.day = day;

                selectedDateTxt.setText(getString(R.string.conferences_for) + " " + Geral.getMonth(month) + " "+ day + ", " +  year);
                selectedDateTxt.setTextSize(15);
                suggest.setText(getString(R.string.suggest_conference) + " for " + Geral.getMonth(month) + " " + day + ", " + year);
                List<Conference> conferences = Conference.getConferences(day,month,year);
                if(conferences!=null && conferences.size()>0) {
                    recyclerView.setAdapter(new ConferenceCalendarAdapter(getContext(), conferences));
                    recyclerView.setVisibility(View.VISIBLE);
                }
                else {
                    selectedDateTxt.setText(getString(R.string.no_conferences_for) + " " + Geral.getMonth(month) + " " + day + ", " + year);
                    selectedDateTxt.setTextSize(13);
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        calendarView.setEventDataProvider(new FlexibleCalendarView.EventDataProvider() {
            @Override
            public List<? extends Event> getEventsForTheDay(int year, int month, int day) {
                return Conference.getConferences(day,month,year);
            }
        });

        calendarView.onDateClick(new SelectedDateItem(Geral.getTodayYear(), Geral.getTodayMonth(), Geral.getDayOfMonth()));


    }


}
