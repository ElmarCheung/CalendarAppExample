package codewithcal.au.calendarappexample;

import androidx.appcompat.app.AppCompatActivity;

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
import java.time.LocalDate;

public class BookingConfirm extends AppCompatActivity{
    private Spinner bookTime;
    private String[] paths = {};
    public ArrayList<LocalDate> dateToBook_morning = (ArrayList<LocalDate>) getIntent().getSerializableExtra("1");
    public ArrayList<LocalDate> dateToBook_afternoon = (ArrayList<LocalDate>) getIntent().getSerializableExtra("2");
    public ArrayList<LocalDate> dateToCancel_morning = (ArrayList<LocalDate>) getIntent().getSerializableExtra("3");
    public ArrayList<LocalDate> dateToCancel_afternoon = (ArrayList<LocalDate>) getIntent().getSerializableExtra("4");
    public ArrayList<Integer> bookingInfo_morning = (ArrayList<Integer>) getIntent().getSerializableExtra("5");
    public ArrayList<Integer> bookingInfo_afternoon = (ArrayList<Integer>) getIntent().getSerializableExtra("6");
    Intent intent = getIntent();
    public String spinner_item = intent.getExtras().getString("7");
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookTime = findViewById(R.id.bookTime);
        if(spinner_item == "Make Booking"){
            if(dateToBook_morning == null){
                paths = new String[]{"Afternoon"};
            }
            else if(dateToBook_afternoon == null){
                paths = new String[]{"Morning"};
            }
            else{
                paths = new String[]{"Both", "Morning", "Afternoon"};
            }
        }

        if(spinner_item == "Cancel Booking"){
            if(dateToCancel_morning == null){
                paths = new String[]{"Afternoon"};
            }
            else if(dateToCancel_afternoon == null){
                paths = new String[]{"Morning"};
            }
            else{
                paths = new String[]{"Both", "Morning", "Afternoon"};
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BookingConfirm.this,
                android.R.layout.simple_spinner_item, paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookTime.setAdapter(adapter);
    }


    public void confirmBooking (View view) {
        String bookTime_item = bookTime.getSelectedItem().toString();
        if(spinner_item == "Make Booking"){
            if(bookTime_item == "Both"){
                for (int i = 0; i < dateToBook_morning.size(); i++) {
                    bookingInfo_morning.set((Integer.valueOf((dateToBook_morning.get(i)).getDayOfMonth()) - 1), 1);
                }
                for (int i = 0; i < dateToBook_afternoon.size(); i++) {
                    bookingInfo_afternoon.set((Integer.valueOf((dateToBook_afternoon.get(i)).getDayOfMonth()) - 1), 1);
                }
            }
            else if(bookTime_item == "Morning"){
                for (int i = 0; i < dateToBook_morning.size(); i++) {
                    bookingInfo_morning.set((Integer.valueOf((dateToBook_morning.get(i)).getDayOfMonth()) - 1), 1);
                }
            }
            else if(bookTime_item == "Afternoon"){
                for (int i = 0; i < dateToBook_afternoon.size(); i++) {
                    bookingInfo_afternoon.set((Integer.valueOf((dateToBook_afternoon.get(i)).getDayOfMonth()) - 1), 1);
                }
            }
        }

        if(spinner_item == "Cancel Booking"){
            if(bookTime_item == "Both"){
                for (int i = 0; i < dateToCancel_afternoon.size(); i++) {
                    bookingInfo_afternoon.set((Integer.valueOf((dateToCancel_afternoon.get(i)).getDayOfMonth()) - 1), 0);
                }
                for (int i = 0; i < dateToCancel_morning.size(); i++) {
                    bookingInfo_morning.set((Integer.valueOf((dateToCancel_morning.get(i)).getDayOfMonth()) - 1), 0);
                }
            }
            else if(bookTime_item == "Morning"){
                for (int i = 0; i < dateToCancel_morning.size(); i++) {
                    bookingInfo_morning.set((Integer.valueOf((dateToCancel_morning.get(i)).getDayOfMonth()) - 1), 0);
                }
            }
            else if(bookTime_item == "Afternoon"){
                for (int i = 0; i < dateToCancel_afternoon.size(); i++) {
                    bookingInfo_afternoon.set((Integer.valueOf((dateToCancel_afternoon.get(i)).getDayOfMonth()) - 1), 0);
                }
            }
        }

        Intent goBack = new Intent(this, MainActivity.class);
        intent.putExtra("5", bookingInfo_morning);
        intent.putExtra("6", bookingInfo_afternoon);
        startActivity(goBack);

        Intent toCalendar = new Intent(this, MainActivity.class);
        startActivity(toCalendar);
    }

}
