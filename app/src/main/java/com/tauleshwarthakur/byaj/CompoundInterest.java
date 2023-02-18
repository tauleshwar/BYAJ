
package com.tauleshwarthakur.byaj;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.constraintlayout.widget.ConstraintLayout;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.os.Bundle;
        import android.text.InputFilter;
        import android.view.View;
        import android.widget.Button;
        import android.widget.CompoundButton;
        import android.widget.EditText;
        import android.widget.RadioGroup;
        import android.widget.Switch;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.math.BigDecimal;
        import java.text.DecimalFormat;

public class CompoundInterest extends AppCompatActivity {
    private double Principle=0;
    private float interest_rate=0;

    private Button calculateButton;
    private Button resetButton;
    private Button detailButton;

    RadioGroup InterestGroup;
    RadioGroup CompoundingGroup;


    private int InterestType = 12;
    private String InterestType_str = "Month";

    private int  CompoundingTime = 1;
    private String CompoundingTime_str = "Year";

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
        setContentView(R.layout.activity_compound_interest);
        setTitle("Compound Interest");

        //        Switching InterstType from radio group
        InterestGroup = (RadioGroup) findViewById(R.id.interestRadioGroup);
        CompoundingGroup = (RadioGroup) findViewById(R.id.compondRadios);

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
        Switch  advanceModeSwitch = (Switch) findViewById(R.id.advanceSwitchd);
        advanceModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                RadioGroup advanceInterest = (RadioGroup) findViewById(R.id.interestRadioGroup);
                RadioGroup compoundGroup = (RadioGroup) findViewById(R.id.compondRadios);
                TextView interestTitle = findViewById(R.id.interestTitle);
                TextView compounds_title = findViewById(R.id.compoundsTitle);
                ConstraintLayout advanceGroup = findViewById(R.id.advaceRadios);


                if(isChecked == true){
                    advanceGroup.setVisibility(View.VISIBLE);
                }else {
                    InterestType = 12;
                    advanceGroup.setVisibility(View.GONE);
                    compounds_title.setText("Compounds Every Year");
                    interestTitle.setText("Interest (Monthly)");
                    advanceInterest.clearCheck();
                    compoundGroup.clearCheck();

                }
            }
        });




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
                Intent reset = new Intent(CompoundInterest.this, CompoundInterest.class);
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
        int btnId2 = CompoundingGroup.getCheckedRadioButtonId();

        TextView interestTitle = findViewById(R.id.interestTitle);
        TextView CompoundingTitle = findViewById(R.id.compoundsTitle);

        try {
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
                    break;
                default:
                    InterestType = 12;
                    InterestType_str = "Month";
                    interestTitle.setText("Interest (Monthly)");


            }

            switch (btnId2){
                case R.id.quarter:
                    CompoundingTitle.setText("Compounds Every Quarter");
                    CompoundingTime = 4;
                    CompoundingTime_str = "Quarter";
                    break;
                case R.id.everySixMonths:
                    CompoundingTitle.setText("Compounds Every SIx Months");
                    CompoundingTime = 2;
                    CompoundingTime_str = "6 Months";
                    break;
                case R.id.everyYear:
                    CompoundingTitle.setText("Compounds Every Year");
                    CompoundingTime = 1;
                    CompoundingTime_str = "Year";
                    break;
                default:
                    CompoundingTitle.setText("Compounds Every Year");
                    CompoundingTime = 1;
                    CompoundingTime_str = "Year";

            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }

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


//        Calculate Date

        (new CalculateDate(start_year,start_month,start_day,end_year,end_month,end_day)).calculate();


        double CompoundAmount = 0 ;
        double montha=0;
        double dada=0;

        Double.isNaN(interest_rate);
        if(CalculateDate.years>0) {
            CompoundAmount = Principle * Math.pow(1 + ((interest_rate * InterestType) / (100 * CompoundingTime)), CompoundingTime * CalculateDate.years);
        }

        if(CalculateDate.months>0){
            montha = (Math.pow(1 + ((interest_rate*(double)InterestType*((double)CalculateDate.months/12))/(100* (double)CompoundingTime)),CompoundingTime));
            CompoundAmount = CompoundAmount*montha;}
//        Toast.makeText(getApplicationContext(),(CalculateDate.months+" -- "+CalculateDate.days+ fractM),Toast.LENGTH_SHORT).show();

        if(CalculateDate.days>0){
            dada = (Math.pow(1 + ((interest_rate*(double)InterestType*(((double)CalculateDate.days/30)/12))/(100* (double)CompoundingTime)),CompoundingTime));
            CompoundAmount = CompoundAmount*dada;}


        DecimalFormat df = new DecimalFormat("#");
        CompoundAmount = Double.parseDouble(df.format(CompoundAmount));


        InterestOut =  new BigDecimal(CompoundAmount-Principle).toPlainString();
        AmountOut = new BigDecimal(CompoundAmount).toPlainString();

        Principle_str = new BigDecimal(Principle).toPlainString();


        interest_display.setText(new StringBuilder().append("Interest : ").append(InterestOut));

        total_amount_display.setText(new StringBuilder().append("Total Amount : ").append(AmountOut));

        detailButton.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);


    }



    public void detailedResult() {

        Intent intent = new Intent(this, Result.class);
        intent.putExtra("byaj_type","Compound Interest");
        intent.putExtra("principle",Principle_str);



        intent.putExtra("interest_rate_type",(interest_rate+"% per "+InterestType_str+" and compounds every "+CompoundingTime_str));


        intent.putExtra("start_date",(start_year+"/"+start_month+"/"+start_day).toString());
        intent.putExtra("end_date",(end_year+"/"+end_month+"/"+end_day).toString());
        intent.putExtra("run_time",(CalculateDate.years+"Y "+CalculateDate.months+"M "+CalculateDate.days+"D").toString());
        intent.putExtra("interest_amount",(InterestOut).toString());
        intent.putExtra("total_amount",(AmountOut).toString());
        startActivity(intent);

    }
}