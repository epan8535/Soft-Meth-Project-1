public class Vehicle implements Comparable<Vehicle> {
    private String plate;
    private Date obtained;
    private Make make;
    private int mileage;

    //constructor for Vehicle
    public Vehicle(String plate, Date obtained, Make make, int mileage){

        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;

    }

    // getter methods
    public String getPlate(){

        return this.plate;

    }

    public Date getDate(){
        return this.obtained;
    }

    public Make getMake(){
        return this.make;
    }

    public int getMileage(){
        return this.mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    //Override .equals() method to test if plates Strings match one another
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;

        Vehicle objectBeingCompared = (Vehicle) obj;

        return plate.equals(objectBeingCompared.getPlate());

    }


    //Override toString to print out in a specific format
    @Override
    public String toString(){
        return (plate +":"+ make +":"+ obtained + " [mileage:" + mileage+ "]" );
    }

    @Override
    public int compareTo(Vehicle vehicleBeingCompared) {
        int makeComparison = this.make.compareTo(vehicleBeingCompared.make);

        if(makeComparison!=0){
            return makeComparison;
        }

        return this.getDate().compareTo(vehicleBeingCompared.getDate());
    }

}
