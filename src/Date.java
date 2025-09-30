/**
 * Date Class stores calendar date objects with
 * an int year, an int month, and an int day
 *
 *  isValid() tests whether the date inputted is a valid Calendar date
 *
 * @author Jake Cordon and Everett Pan
 */

public class Date implements Comparable<Date>{

    // ===================== Instance Variables ===================

    private int year;
    private int month;
    private int day;

    // ===================== Constants ===================

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;


    // ===================== Constructors ===================

    /*
    * Date Constructor that creates the date object
    *
    * @param year   integer that is the year of the object
    * @param month  integer that is the month of the object
    * @param day    integer that is the day of the object
     */

    public Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }


    // ===================== Getter Methods ===================

    public int getYear(){
        return this.year;
    }

    public int getMonth(){
        return this.month;
    }

    public int getDay(){
        return this.day;
    }


    /*
    *   isLeap() tests whether the year parameter is a leap year by ensuring it
    *   is divisible by 4 and 100 but not 400
    *
    * @param year   integer that is the year being tested for being a leap year
     */

    public boolean isLeap(int year){

        if(year%QUADRENNIAL==0){

            if(year%CENTENNIAL==0){

               if(year%QUARTERCENTENNIAL==0){

                   return true;

               } else {

                   return false;
               }

            } else {

                return true;
            }

        } else {

            return false;
        }
    }

    /*
    *   has30Days() is a helper method that tests if a month is one of the months with 30 days
    *
    * @param month  the month being tested for having 30 days
     */

    public boolean has30Days(int month){

        if(month == 4 || month == 6 || month == 9 || month == 11){

            return true;

        }
        return false;
    }

    /*
     *   has31Days() is a helper method that tests if a month is one of the months with 31 days
     *
     * @param month  the month being tested for having 31 days
     */

    public boolean has31Days(int month){

        if(month == 1 || month ==  3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){

            return true;

        }
        return false;
    }

    /*
     *   isFebruary() is a helper method that tests if the month is February.
     *
     * @param month  the month being tested for being February.
     */

    public boolean isFebruary(int month){

        if(month == 2){

            return true;

        }
        return false;
    }

    /*
    * isValid() tests whether a calendar date that does not violate any rules
    * - 2/29 only exists on a Leap Year
    * - month cannot be <1 or >12
    * - year cannot be <1900
    * - day cannot be <1 or > 30 or 31 depending on the month
    *
     */

    public boolean isValid(){

        //Check if Month is inValid 0<x<=12

        if(month<=0 || month>12){
            return false;
        }

        if(has30Days(month)){
            if(day<=0 || day>30){
                return false;
            }
        }

        else if(has31Days(month)){
            if(day<=0 ||day>31){
                return false;
            }
        }
        else if(isFebruary(month)){
            if(isLeap(year)){
                if(day<=0 || day>29){
                    return false;
                }
            }
            else{
                if(day<=0 || day>28){
                    return false;
                }
            }
        }

        if(year<1900){
            return false;
        }

        return true;
    }

    /*
    * Tests if Two date objects are equal
    *
    * @param Object obj     Object that gets cast to a Date that is going to be tested for equality.
     */

    @Override
    public boolean equals(Object obj) {

        if(this == obj){
            return true;
        }

        if(!(obj instanceof Date)){
            return false;
        }

        Date dateBeingCompared = (Date) obj;
        return this.year==dateBeingCompared.year && this.month == dateBeingCompared.month && this.day==dateBeingCompared.day;

    }

    /*
    * toString() method is Overridden to print out the date in the month day year format
    *
     */

    @Override
    public String toString(){
       return (this.month + "/" + this.day + "/" + this.year);
    }

    /*
    * compareTo returns -1 0 and 1
    *
    * @param date   the date being compared to the first Date object
     */

    @Override
    public int compareTo(Date date){
        if(this.year != date.getYear()){
            return this.year - date.getYear();
        }

        if(this.month != date.getMonth()){
            return this.month - date.getMonth();
        }

        return this.day - date.getDay();

    }

    // ===================== TestBed Main ===================

    /*
     * Unit Test for the isValid() method
     *
     */

    public static void main(String[] args){

        Date failCaseOne = new Date(2017,2,29);
        System.out.println("testCaseOne: " + failCaseOne.isValid() + " (expected false)" );

        Date failCaseTwo = new Date(2020,2,31);
        System.out.println("failCaseTwo: " + failCaseTwo.isValid() + " (expected false)");

        Date failCaseThree = new Date(0,13,32);
        System.out.println("failCaseThree: " + failCaseThree.isValid() + " (expected false)");

        Date failCaseFour = new Date(1,1,1);
        System.out.println("failCaseFour: " + failCaseFour.isValid() + " (expected false)");

        Date succeedCaseOne = new Date(2020,2,29);
        System.out.println("succeedCaseOne: " + succeedCaseOne.isValid() + " (expected true)");

        Date succeedCaseTwo = new Date(2025,1,31);
        System.out.println("succeedCaseTwo: " + succeedCaseTwo.isValid() + " (expected true)");
    }

}
