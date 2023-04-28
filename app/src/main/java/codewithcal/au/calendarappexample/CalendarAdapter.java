package codewithcal.au.calendarappexample;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;
    private static ArrayList<LocalDate> dateToBook_morning;
    private static ArrayList<LocalDate> dateToCancel_morning;
    private static ArrayList<LocalDate> dateToBook_afternoon;
    private static ArrayList<LocalDate> dateToCancel_afternoon;
    private final ArrayList<Integer> bookingInfo_morning;
    private final ArrayList<Integer> bookingInfo_afternoon;

    public CalendarAdapter(ArrayList<LocalDate> days, ArrayList<LocalDate> dateToBook_morning, ArrayList<LocalDate> dateToBook_afternoon, ArrayList<Integer> bookingInfo_morning, ArrayList<LocalDate> dateToCancel_morning, ArrayList<LocalDate> dateToCancel_afternoon, ArrayList<Integer> bookingInfo_afternoon, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
        this.dateToBook_morning = dateToBook_morning;
        this.dateToBook_afternoon = dateToBook_afternoon;
        this.dateToCancel_morning = dateToCancel_morning;
        this.dateToCancel_afternoon = dateToCancel_afternoon;
        this.bookingInfo_morning = bookingInfo_morning;
        this.bookingInfo_afternoon = bookingInfo_afternoon;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);

        return new CalendarViewHolder(view, onItemListener, days);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        final LocalDate currentDate = LocalDate.now();
        if(date == null) {
            holder.dayOfMonth.setText("");
            holder.outline.setBackgroundResource(0);
        }
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            holder.triangle.setBackgroundResource(R.drawable.triangle_white);
            if(date.compareTo(CalendarUtils.selectedDate) < 0){
                holder.parentView.setBackgroundColor(Color.LTGRAY);
                holder.triangle.setBackgroundResource(R.drawable.triangle_gray);
            }
            if(bookingInfo_afternoon.get(Integer.valueOf(date.getDayOfMonth())-1) == 1){
                holder.parentView.setBackgroundColor(Color.parseColor("#4e92f8"));
            }
            if(dateToBook_afternoon != null){
                for(int i = 0; i < dateToBook_afternoon.size(); i++){
                    if(date.compareTo(dateToBook_afternoon.get(i)) == 0){
                        holder.parentView.setBackgroundColor(Color.GREEN);
                    }
                }
            }

            if(dateToCancel_afternoon != null){
                for(int i = 0; i < dateToCancel_afternoon.size(); i++){
                    if(date.compareTo(dateToCancel_afternoon.get(i)) == 0){
                        holder.parentView.setBackgroundColor(Color.parseColor("#0000ff"));
                    }
                }
            }
            if(bookingInfo_morning.get(Integer.valueOf(date.getDayOfMonth())-1) == 1){
                holder.triangle.setBackgroundResource(R.drawable.triangle_lt_blue);
            }
            if(dateToBook_morning != null){
                for(int i = 0; i < dateToBook_morning.size(); i++){
                    if(date.compareTo(dateToBook_morning.get(i)) == 0){
                        holder.triangle.setBackgroundResource(R.drawable.triangle_green);
                    }
                }
            }

            if(dateToCancel_morning != null){
                for(int i = 0; i < dateToCancel_morning.size(); i++){
                    if(date.compareTo(dateToCancel_afternoon.get(i)) == 0){
                        holder.triangle.setBackgroundResource(R.drawable.triangle_dk_blue);
                    }
                }
            }

        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface  OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
