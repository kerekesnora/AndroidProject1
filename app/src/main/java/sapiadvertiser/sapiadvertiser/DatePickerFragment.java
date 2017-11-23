package sapiadvertiser.sapiadvertiser;

import android.app.DatePickerDialog;
import android.app.Dialog;


import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.widget.DatePicker;


import java.util.Calendar;


public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = (c.get(Calendar.MONTH));
        int day = c.get(Calendar.DAY_OF_MONTH);
        Log.d("tesz3","jel: "+year);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Intent intent=new Intent("date");
        intent.putExtra("date",year+" "+(month+1)+" "+day);
        getActivity().sendBroadcast(intent);
    }



}
