package com.tauleshwarthakur.byaj;


public class CalculateDate {
    static int years;
    static int months;
    static int days;

    private int startYear;
    private int startMonth;
    private int startDay;

    private int endYear;
    private int endMonth;
    private int endDay;

//    static string start_date;

    public  CalculateDate(int sYear, int sMonth, int sDay, int eYear, int eMonth, int eDay){
        this.startYear = sYear;
        this.startMonth = sMonth;
        this.startDay = sDay;

        this.endYear = eYear;
        this.endMonth = eMonth;
        this.endDay = eDay;


    }

    public CalculateDate() {

    }

    public void calculate(){

            if (this.endDay < this.startDay) {
                this.endDay += 30;
                days = this.endDay - this.startDay;
                this.endMonth--;
            } else {
                days = this.endDay - this.startDay;
            }
            if (this.endMonth < this.startMonth) {
                this.endMonth += 12;
                months = this.endMonth - this.startMonth;
                this.endYear--;
            } else {
                months = this.endMonth - this.startMonth;
            }
            years = this.endYear - this.startYear;

    }


    public int get_startYear(){
        return startYear;
    }
    public int get_startMonth(){
        return startMonth;
    }
    public int get_startDay(){
        return startDay;
    }

    public String get_startDate(){
        return (new StringBuilder().append(startYear).append("/").append(startMonth).append("/").append(startDay)).toString();
    }

    public int get_endYear(){
        return endYear;
    }
    public int get_endMonth(){
        return endMonth;
    }
    public int get_endDay(){
        return endDay;
    }
    public String get_endDate(){
        return (new StringBuilder().append(endYear).append("/").append(endMonth).append("/").append(endMonth)).toString();
    }

}





