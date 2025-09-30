/**
 * Booking class constructs and deals with all the Booking objects and contains
 * Date object begin, Date object end, Employee object Employee, and Vehicle object vehicle
 *
 *  toString() prints out the Booking Beginning, End date, Vehicle being used, and Employee that inputted
 *
 * @author Jake Cordon
 */

public class Booking {

    // ===================== Instance Variables ===================

    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    // ===================== Constructors ===================

    /*
    * Booking constructor that creates the booking objects
    *
    * @param begin  -beginning date for the booking
    * @param end    -end date for the booking
    * @param employee -employee that completed the booking
    * @param vehicle  - vehicle used for the booking
     */

    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle){

        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;

    }



    // ===================== Getter Methods ===================
    public Date getBegin(){
        return begin;
    }

    public Date getEnd(){
        return end;
    }

    public Employee getEmployee(){
        return employee;
    }

    public Vehicle getVehicle(){
        return vehicle;
    }

    // ===================== Public Methods ===================


    /*
    * Overriden equals method
    * Tests whether two bookings have the same vehicle and beginning and end date
    *
    * @param obj Object that is cast to the booking that is being compared
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        Booking bookBeingCompared = (Booking)obj;

        if(vehicle.equals(bookBeingCompared.vehicle) && begin.equals(bookBeingCompared.begin) && end.equals(bookBeingCompared.end)){
            return true;
        }
        return false;
    }

    /*
    * toString() that is Overridden to print in a specific format
    *
     */

    @Override
    public String toString(){
        Date obtained = vehicle.getDate();
        String obtainedStr = obtained.getMonth() + "/" + obtained.getDay() + "/" + obtained.getYear();

        String beginStr = begin.getMonth() + "/" + begin.getDay() + "/" + begin.getYear();
        String endStr   = end.getMonth()   + "/" + end.getDay()   + "/" + end.getYear();

        return vehicle.getPlate() + ":" + vehicle.getMake().name() + ":" + obtainedStr
                + " [mileage:" + vehicle.getMileage() + "]"
                + " [beginning " + beginStr + " ending " + endStr + ":" + employee.name() + "]";

    }




}
