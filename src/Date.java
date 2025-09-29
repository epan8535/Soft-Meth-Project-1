public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;
    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;


    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }


    //getter methods
    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDay(){
        return this.day;
    }


    public boolean isLeap(int year){
        if(year%QUADRENNIAL==0){
            if(year%CENTENNIAL==0){
               if(year%QUARTERCENTENNIAL==0){
                   return true;
               }
            }

        }

        return false;

    }

    public boolean has30Days(int month){
        if(month == 4 || month ==6 || month ==9 || month ==11){
            return true;
        }
        return false;
    }

    public boolean has31Days(int month){
        if(month == 1 || month ==  3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            return true;
        }
        return false;
    }

    public boolean isFebruary(int month){
        if(month == 2){
            return true;
        }
        return false;
    }



    public boolean isValid(){

        //Check if Month is inValid 0<x<=12

        if(month<=0 || month>12){
            return false;
        }

        //Check if Day is Valid 0<x< 30 or 31 and 28 or 29 for February

        if(has30Days(month)){
            if(day<0 || day>30){
                return false;
            }
        }

        else if(has31Days(month)){
            if(day<0 ||day>31){
                return false;
            }
        }
        else if(isFebruary(month)){
            if(isLeap(year)){
                if(day<0 || day>29){
                    return false;
                }
            }
            else{
                if(day<0 || day>28){
                    return false;
                }
            }
        }





        //Check if Year is Valid
        if(year<1900 || year>2025){
            return false;
        }

        return true;
    }

    public boolean equals() {
        return false;
    }

    @Override
    public String toString(){
       return (this.month + "/" + this.day + "/" + this.year);
    }

    @Override
    public int compareTo(Date d){
        if(this.year != d.getYear()){
            return this.year - d.getYear();
        }

        if(this.month != d.getMonth()){
            return this.month - d.getMonth();
        }

        return this.day - d.getDay();

    }

}
