package com.example.spendingmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText txtBalance;
    EditText txtDate, txtAmount, txtActivity, txtHistory;
    Button btnCredit, btnDebit;

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String Balance = "balance";
    SharedPreferences sharedpreferences;
    double saveBalance = 100.00;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtBalance = findViewById(R.id.txtBalance);
        txtDate = findViewById(R.id.txtDate);
        txtAmount = findViewById(R.id.txtAmount);
        txtActivity = findViewById(R.id.txtActivity);
        txtHistory = findViewById(R.id.txtHistory);

        btnCredit = findViewById(R.id.btnCredit);
        btnDebit = findViewById(R.id.btnDebit);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        btnCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(txtAmount.getText().toString());
                double newBalance = Double.parseDouble(txtBalance.getText().toString());
                newBalance = newBalance + amount;
                String value = Double.valueOf(newBalance).toString();
                String strDate = txtDate.getText().toString();
                String strPrice = txtAmount.getText().toString();
                String strActivity = txtActivity.getText().toString();
                String strHistory = txtHistory.getText().toString();
                String newLine = "Added $" + strPrice+ " on " + strDate + " from " + strActivity;
                String strUpdate = strHistory + "\n" + newLine;
                saveBalance = newBalance;
                MainActivity.this.txtBalance.setText(value);
                MainActivity.this.txtHistory.setText(strUpdate);

                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(Balance, value);
                editor.commit();
            }
        });

        btnDebit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double amount = Double.parseDouble(txtAmount.getText().toString());
                double newBalance = Double.parseDouble(txtBalance.getText().toString());
                newBalance = newBalance - amount;
                String value = Double.valueOf(newBalance).toString();
                String strDate = txtDate.getText().toString();
                String strPrice = txtAmount.getText().toString();
                String strActivity = txtActivity.getText().toString();
                String strHistory = txtHistory.getText().toString();
                String newLine = "Spent $" + strPrice + " on " + strDate + " for " + strActivity;
                String strUpdate = strHistory + "\n" + newLine;
                MainActivity.this.txtBalance.setText(value);
                MainActivity.this.txtHistory.setText(strUpdate);
                saveBalance = newBalance;
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Balance, value);
                editor.commit();
            }
        });


    }

    @Override
    protected void onResume()
    {

        super.onResume();
        String value = Double.valueOf(saveBalance).toString();
        MainActivity.this.txtBalance.setText(value);

    }

    @Override
    protected void onPause()
    {
        super.onPause();

        // Creating a shared pref object
        // with a file name "MySharedPref" in private mode
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        double value = Double.parseDouble(txtBalance.getText().toString());
        saveBalance = value;
        editor.putString("balance", Balance);

        editor.commit();
    }

}