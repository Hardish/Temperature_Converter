package com.example.temperature_converter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    RadioButton toc;
    RadioButton tof;
    EditText temp;
    private TextView firstTextView;
    private  TextView secondTextView;
    private SharedPreferences selectionPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = (EditText)findViewById(R.id.txtinput);

        firstTextView = (TextView)findViewById(R.id.txtview1);
        secondTextView = (TextView)findViewById(R.id.txtview2);;
        toc = (RadioButton) findViewById(R.id.rbtnFaToce);
        tof = (RadioButton) findViewById(R.id.rbtnCeToFa);
       // selectionPrefs = getSharedPreferences("SELECTION", Context.MODE_PRIVATE);
        selectionPrefs = getSharedPreferences("SELECTION",MODE_PRIVATE);
        String myData = selectionPrefs.getString("OPTION","ONE");

        if(myData.equals("ONE"))
        {
            toc.setChecked(true);
            firstTextView.setText("Fahrenheit Degrees:");
            secondTextView.setText("Celsius Degrees:");

        }
        else if(myData.equals("TWO"))
        {
            firstTextView.setText("Celsius Degrees:");
            secondTextView.setText("Fahrenheit Degrees:");
            tof.setChecked(true);
        }

    }


    public void conversionSelected(View v)
    {
        SharedPreferences.Editor editor = selectionPrefs.edit();
     /*   TextView firstTextView = (TextView)findViewById(R.id.txtview1);
        TextView secondTextView = (TextView)findViewById(R.id.txtview2);*/
        switch (v.getId())
        {
            case R.id.rbtnFaToce:

                firstTextView.setText("Fahrenheit Degrees:");
                secondTextView.setText("Celsius Degrees:");
                editor.putString("OPTION","ONE");


                break;

            case R.id.rbtnCeToFa:

                Log.d(TAG, "conversionSelected: Before setting value" + firstTextView.getText().toString());
                firstTextView.setText("Celsius Degrees:");
                Log.d(TAG, "conversionSelected: Before setting value" + firstTextView.getText().toString());
                secondTextView.setText("Fahrenheit Degrees:");
                editor.putString("OPTION","TWO");
                break;

                default:
                    break;
        }
        editor.apply();
    }

    public void convert(View v) {
        //EditText temp = findViewById(R.id.txtinput);
        TextView output = findViewById(R.id.txtoutput);
        TextView history = findViewById(R.id.txthistory);

        history.setMovementMethod(new ScrollingMovementMethod());

        String tempString = temp.getText().toString();

        if(tempString.isEmpty())
        {
            Toast.makeText(this, "Please provide input",Toast.LENGTH_LONG).show();

        }
        else
        {
            Double tempValue = Double.parseDouble(tempString);

            if(toc.isChecked()) {
                Double outputValue = (tempValue - 32.0) / 1.8;
                Log.d(TAG, "convert: outputValue");
                String finalresult = String.format("%.2f", outputValue);
                output.setText(finalresult);


                String original = history.getText().toString();

                String newValue = String.format("F %.2f,(C %.2f)%n", tempValue,outputValue);
                history.setText(newValue+original);
            }
            else if(tof.isChecked())
            {
                Double outputValue = (tempValue * 1.8) + 32;
                Log.d(TAG, "convert: outputValue");
                String finalresult = String.format("%.2f", outputValue);
                output.setText(finalresult);
                String original = history.getText().toString();

                String newValue = String.format("C %.2f,(F %.2f)%n", tempValue,outputValue);
                history.setText(newValue+original);
            }

        }


    }

    public void clearHistory(View v)
    {
        TextView history = findViewById(R.id.txthistory);
        TextView output = findViewById(R.id.txtoutput);
        output.setText("");
        temp.setText("");
        history.setText("");

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        TextView history = findViewById(R.id.txthistory);
        TextView output = findViewById(R.id.txtoutput);
        outState.putString("HISTORY", history.getText().toString());
        outState.putString("OUTPUT",output.getText().toString());
        outState.putString("TempInput",temp.getText().toString());

        // Call super last
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);
        TextView history = findViewById(R.id.txthistory);
        TextView output = findViewById(R.id.txtoutput);

        if(savedInstanceState.containsKey("HISTORY"))
        {
            history.setText(savedInstanceState.getString("HISTORY"));
        }
        if(savedInstanceState.containsKey("OUTPUT"))
        {
            output.setText(savedInstanceState.getString("OUTPUT"));
        }
        if(savedInstanceState.containsKey("TempInput"))
        {
            temp.setText(savedInstanceState.getString("TempInput"));
        }



    }
}
