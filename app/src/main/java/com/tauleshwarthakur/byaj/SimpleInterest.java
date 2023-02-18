package com.tauleshwarthakur.byaj;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SimpleInterest extends AppCompatActivity {
    private double Principle=0;
    private float interest_rate=0;

    private Button calculateButton;
    private Button resetButton;
    private Button detailButton;

    RadioGroup InterestGroup;
    RadioButton InterestButton;

    private int InterestType = 12;
    private String InterestType_str = "Month";

    private int start_year;
    private int start_month;
    private int start_day ;

    private int end_year ;
    private int end_month;
    private int end_day;

    String InterestOut;
    String AmountOut;
    String Principle_str;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_interest);
        setTitle("Simple Interest");
        

//      Set filters in input
        EditText startMonth= findViewById(R.id.monthInput);
        startMonth.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        EditText startDay= findViewById(R.id.dayInput);
        startDay.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "32")});

        EditText endMonth= findViewById(R.id.monthInput3);
        endMonth.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "12")});
        EditText endDay= findViewById(R.id.dayInput2);
        endDay.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "32")});

//        Toggling interest type radio group visibility
        Switch interestSwitch = (Switch) findViewById(R.id.advanceSwitch);
        interestSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RadioGroup advanceInterest = (RadioGroup) findViewById(R.id.interestRadioGroup);
                TextView interestTitle = findViewById(R.id.interestTitle);

                if(isChecked == true){
                    advanceInterest.setVisibility(View.VISIBLE);
                }else {
                    advanceInterest.setVisibility(View.GONE);
                    InterestType = 12;
                    interestTitle.setText("Interest (Monthly)");
                    advanceInterest.clearCheck();
                }
            }
        });

//        Switching InterstType from radio group
        InterestGroup = (RadioGroup) findViewById(R.id.interestRadioGroup);


//        Pressing Calculate button
        calculateButton = findViewById(R.id.calculate_btn);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findResult(v);
            }
        });

        //        Pressing Reset button
        resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reset = new Intent(SimpleInterest.this, SimpleInterest.class);
                startActivity(reset);
            }
        });

//        Pressing detail button
        detailButton = findViewById(R.id.detailedResult);
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailedResult();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    public void checkButton(View V){

        int btnId = InterestGroup.getCheckedRadioButtonId();
        InterestButton= findViewById(btnId);

        TextView interestTitle = findViewById(R.id.interestTitle);

        switch (btnId){
            case R.id.yearlyInterest:
                InterestType = 1;
                InterestType_str = "Year";
                interestTitle.setText("Interest (Yearly)");
                break;
            case R.id.halfYearlyInterest:
                InterestType = 2;
                InterestType_str = "6 Months";
                interestTitle.setText("Interest (Half Yearly)");
                break;
            case R.id.monthlyInterest:
                InterestType = 12;
                InterestType_str = "Month";
                interestTitle.setText("Interest (Monthly)");
            default:
                InterestType = 12;
                InterestType_str = "Month";
                interestTitle.setText("Interest (Monthly)");


        }

        Toast.makeText(getApplicationContext(),InterestButton.getText(), Toast.LENGTH_SHORT).show();
    }

    public void findResult(View v) {
        EditText startYear= findViewById(R.id.yearInput);
        EditText startMonth= findViewById(R.id.monthInput);

        EditText startDay= findViewById(R.id.dayInput);

        EditText endYear= findViewById(R.id.yearInput3);
        EditText endMonth= findViewById(R.id.monthInput3);
        EditText endDay= findViewById(R.id.dayInput2);


        EditText principle_= findViewById(R.id.principleInput);
        EditText interestRate= findViewById(R.id.interestInput);

        TextView interest_display = findViewById(R.id.interestRateDisplay);
        TextView total_amount_display = findViewById(R.id.totalAmountDisplay);




//        start date below
        if (startYear.getText().toString().equals("")) {
            startYear.setError("Write the starting year");
            Toast.makeText(getApplicationContext(),"Write the starting year",Toast.LENGTH_SHORT).show();
            return;

        }else {
            start_year = Integer.parseInt(startYear.getText().toString());

        }


        if (startMonth.getText().toString().equals("")) {
            startMonth.setError("Write the starting month");
            Toast.makeText(getApplicationContext(),"Write the starting month",Toast.LENGTH_SHORT).show();
            return;
        }else {
            start_month = Integer.parseInt( startMonth.getText().toString());
        }


        if (startDay.getText().toString().equals("")) {
            startDay.setError("Write the starting day");
            Toast.makeText(getApplicationContext(),"Write the starting day",Toast.LENGTH_SHORT).show();
            return;
        }else{
            start_day = Integer.parseInt(startDay.getText().toString());
        }


        //        End date below //

        if (endYear.getText().toString().equals("")) {
            endYear.setError("Write the ending year");
            Toast.makeText(getApplicationContext(),"Write the ending year",Toast.LENGTH_SHORT).show();
            return;

        }else {
            end_year = Integer.parseInt(endYear.getText().toString());

        }


        if (endMonth.getText().toString().equals("")) {
            endMonth.setError("Write the ending month");
            Toast.makeText(getApplicationContext(),"Write the ending month",Toast.LENGTH_SHORT).show();
            return;
        }else {
            end_month = Integer.parseInt( endMonth.getText().toString());
        }

        if (endDay.getText().toString().equals("")) {
            endDay.setError("Write the ending day");
            Toast.makeText(getApplicationContext(),"Write the ending day",Toast.LENGTH_SHORT).show();
            return;
        }else {
            end_day = (int) Double.parseDouble( endDay.getText().toString());
        }


        if(principle_.getText().toString().equals("")){
            principle_.setError("Write the actual Principle");
            Toast.makeText(getApplicationContext(),"Write the Principle",Toast.LENGTH_SHORT).show();
            return;
        }else {
            Principle = Double.parseDouble( principle_.getText().toString());
        }

        if(interestRate.getText().toString().equals("")){
            interestRate.setError("Write interest rate in %");
            Toast.makeText(getApplicationContext(),"Write interest rate in %",Toast.LENGTH_SHORT).show();
            return;
        }else {
            interest_rate = Float.parseFloat( interestRate.getText().toString());
        }

        if (end_year<start_year){
            endYear.setError("Payment date is earlier than loan taken");
            return;
        }
        if (end_year==start_year && end_month<start_month){
            endMonth.setError("Payment date is earlier than loan taken");
            return;
        }if (end_year==start_year && end_month==start_month && end_day<start_day){
            endDay.setError("Payment date is earlier than loan taken");
            return;
        }



        (new CalculateDate(start_year,start_month,start_day,end_year,end_month,end_day)).calculate();


        double SimpleInterest = 0 ;

        Double.isNaN(interest_rate);
        if(CalculateDate.years>0)
            SimpleInterest = (double) (((Principle * (double) (CalculateDate.years) * interest_rate) * InterestType) / 100);

        if(CalculateDate.months>0)
            SimpleInterest = (double) ((SimpleInterest + (Principle * ((double) (CalculateDate.months) / 12) * interest_rate)* InterestType) / 100);

        if(CalculateDate.days>0)
            SimpleInterest = (double) ((SimpleInterest + (Principle * ((double) (CalculateDate.days) / 30) * interest_rate)* InterestType) / 100);

//        Toast.makeText(getApplicationContext(),new StringBuilder().append((SimpleInterest)).toString(),Toast.LENGTH_SHORT).show();


        DecimalFormat df = new DecimalFormat("#");
        SimpleInterest = Double.parseDouble(df.format(SimpleInterest));

        InterestOut =  new BigDecimal(SimpleInterest).toPlainString();
        AmountOut = new BigDecimal(SimpleInterest + Principle).toPlainString();
        Principle_str = new BigDecimal(Principle).toPlainString();


        interest_display.setText(new StringBuilder().append("Interest : ").append(InterestOut));

        total_amount_display.setText(new StringBuilder().append("Total Amount : ").append(AmountOut));

        detailButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);


    }



    public void detailedResult() {

        Intent intent = new Intent(this, Result.class);
        intent.putExtra("principle",Principle_str);


        intent.putExtra("interest_rate_type",(interest_rate+"% per "+InterestType_str));
        intent.putExtra("byaj_type","Simple Interest");
        intent.putExtra("start_date",(start_year+"/"+start_month+"/"+start_day).toString());
        intent.putExtra("end_date",(end_year+"/"+end_month+"/"+end_day).toString());
        intent.putExtra("run_time",(CalculateDate.years+"Y "+CalculateDate.months+"M "+CalculateDate.days+"D").toString());
        intent.putExtra("interest_amount",(InterestOut).toString());
        intent.putExtra("total_amount",(AmountOut).toString());
        startActivity(intent);

    }
}