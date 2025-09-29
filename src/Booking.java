public class Booking {
    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle){
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    //getter methods
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

    @Override
    public String toString(){
        return vehicle.getPlate() + ":" + vehicle.getMake() + vehicle.getDate() + " [mileage:" + vehicle.getMileage() + "]" + " [beginning " + begin + " ending " + end + ":" + employee + "]";
    }




}
