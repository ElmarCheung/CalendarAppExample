package codewithcal.au.calendarappexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static codewithcal.au.calendarappexample.CalendarUtils.daysInMonthArray;
import static codewithcal.au.calendarappexample.CalendarUtils.monthYearFromDate;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener, AdapterView.OnItemSelectedListener {
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private Button BookingButton;

    public static ArrayList<LocalDate> dateToBook_morning = new ArrayList<LocalDate>();
    public static ArrayList<LocalDate> dateToCancel_morning = new ArrayList<LocalDate>();
    public static ArrayList<LocalDate> dateToBook_afternoon = new ArrayList<LocalDate>();
    public static ArrayList<LocalDate> dateToCancel_afternoon = new ArrayList<LocalDate>();
    public static ArrayList<Integer> bookingInfo_morning = new ArrayList<>(Arrays.asList(1, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
    public static ArrayList<Integer> bookingInfo_afternoon = new ArrayList<>(Arrays.asList(1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

    private Spinner spinner;
    private static final String[] paths = {" -- ", "Make Booking", "Cancel Booking"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        CalendarUtils.selectedDate = LocalDate.now();
        setMonthView();
        BookingButton = findViewById(R.id.BookingButton);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                dateToBook_morning.clear();
                dateToBook_afternoon.clear();
                dateToCancel_morning.clear();
                dateToCancel_afternoon.clear();
                BookingButton.setBackgroundColor(Color.parseColor("#d5d0d0"));
                setMonthView();
                break;

            case 1:
                dateToCancel_morning.clear();
                dateToCancel_afternoon.clear();
                if(dateToBook_morning == null && dateToBook_afternoon == null){
                    BookingButton.setBackgroundColor(Color.parseColor("#d5d0d0"));
                }
                setMonthView();
                break;

            case 2:
                dateToBook_morning.clear();
                dateToBook_afternoon.clear();
                if(dateToCancel_morning == null && dateToCancel_afternoon == null){
                    BookingButton.setBackgroundColor(Color.parseColor("#d5d0d0"));
                }
                setMonthView();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(CalendarUtils.selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, dateToBook_morning, dateToBook_afternoon, bookingInfo_morning, dateToCancel_morning, dateToCancel_afternoon, bookingInfo_afternoon, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);

    }

    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        if (date != null) {
            if (String.valueOf(date.getDayOfWeek()) != "SUNDAY" && String.valueOf(date.getDayOfWeek()) != "SATURDAY" && currentDate.compareTo(date) <= 0) {
                String spinner_item = spinner.getSelectedItem().toString();
                if (spinner_item == "Make Booking") {
                    if (bookingInfo_morning.get(Integer.valueOf(date.getDayOfMonth()) - 1) == 0) {
                        if (dateToBook_morning != null) {
                            int repFlag = 0;
                            for (int i = 0; i < dateToBook_morning.size(); i++) {
                                if (date.compareTo(dateToBook_morning.get(i)) == 0) {
                                    dateToBook_morning.remove(date);
                                    repFlag++;
                                }
                            }
                            if (repFlag == 0) {
                                dateToBook_morning.add(date);
                                BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                            }
                        }
                        else {
                            dateToBook_morning.add(date);
                            BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                        }
                    }

                    if (bookingInfo_afternoon.get(Integer.valueOf(date.getDayOfMonth()) - 1) == 0) {
                        if (dateToBook_afternoon != null) {
                            int repFlag = 0;
                            for (int i = 0; i < dateToBook_afternoon.size(); i++) {
                                if (date.compareTo(dateToBook_afternoon.get(i)) == 0) {
                                    dateToBook_afternoon.remove(date);
                                    repFlag++;
                                }
                            }
                            if (repFlag == 0) {
                                dateToBook_afternoon.add(date);
                                BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                            }
                        } else {
                            dateToBook_afternoon.add(date);
                            BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                        }
                    }

                }

                else if (spinner_item == "Cancel Booking") {
                    if (bookingInfo_morning.get(Integer.valueOf(date.getDayOfMonth()) - 1) == 1) {
                        if (dateToCancel_morning != null) {
                            int repFlag = 0;
                            for (int i = 0; i < dateToCancel_morning.size(); i++) {
                                if (date.compareTo(dateToCancel_morning.get(i)) == 0) {
                                    dateToCancel_morning.remove(date);
                                    repFlag++;
                                }
                            }
                            if (repFlag == 0) {
                                dateToCancel_morning.add(date);
                                BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                            }
                        } else {
                            dateToCancel_morning.add(date);
                            BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                        }
                    }

                    if (bookingInfo_afternoon.get(Integer.valueOf(date.getDayOfMonth()) - 1) == 1) {
                        if (dateToCancel_afternoon != null) {
                            int repFlag = 0;
                            for (int i = 0; i < dateToCancel_afternoon.size(); i++) {
                                if (date.compareTo(dateToCancel_afternoon.get(i)) == 0) {
                                    dateToCancel_afternoon.remove(date);
                                    repFlag++;
                                }
                            }
                            if (repFlag == 0) {
                                dateToCancel_afternoon.add(date);
                                BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                            }
                        }

                        else {
                            dateToCancel_afternoon.add(date);
                            BookingButton.setBackgroundColor(Color.parseColor("#ffbb6b"));
                        }
                    }
                }
                setMonthView();
            }
        }
    }

    public void BookingButtonAction (View view) {
        if(dateToBook_morning != null || dateToBook_afternoon!= null ||
                dateToCancel_morning != null ||dateToCancel_afternoon != null) {

            String spinner_item = spinner.getSelectedItem().toString();
            Intent intent = new Intent(MainActivity.this, BookingConfirm.class);
            intent.putExtra("1", dateToBook_morning);
            intent.putExtra("2", dateToBook_afternoon);
            intent.putExtra("3", dateToCancel_morning);
            intent.putExtra("4", dateToCancel_afternoon);
            intent.putExtra("5", bookingInfo_morning);
            intent.putExtra("6", bookingInfo_afternoon);
            startActivity(intent);

            Intent i = new Intent(this, BookingConfirm.class);
            i.putExtra("7", spinner_item);
            startActivity(i);

            Intent toConfirmPage = new Intent(this, BookingConfirm.class);
            startActivity(toConfirmPage);

            dateToBook_morning.clear();
            dateToBook_afternoon.clear();
            dateToCancel_morning.clear();
            dateToCancel_afternoon.clear();
        }

    }

}








