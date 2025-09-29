public class Trip {
    private Booking booking;
    private int beginMileage;
    private int endMileage;

    public Trip (Booking booking, int beginMileage, int endMileage){
        this.booking = booking;
        this.beginMileage = beginMileage;
        this.endMileage = endMileage;
    }

    public Booking getBooking(){
        return booking;
    }

    public int getBeginMileage(){
        return beginMileage;
    }

    public int getEndMileage(){
        return endMileage;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) {
            return true;
        } else if (obj instanceof Trip){
            Trip other = (Trip) obj;
            boolean result = this.booking.equals(other.booking);
            return result;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return booking.getVehicle().getPlate() + " " + booking.getBegin() + " ~ " + booking.getEnd() + " original mileage: " + beginMileage + " current mileage: " + endMileage + " mileage used: " + (endMileage - beginMileage);
    }
}
