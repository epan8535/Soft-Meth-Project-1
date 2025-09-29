public class Fleet {
    private static final int CAPACITY = 4; //initial capacity
    private static final int NOT_FOUND = -1;
    private Vehicle[] fleet; // fleet is stored as an array of vehicles
    private int size; //current number of vehicles in the fleet

    //constructor with initial size
    public Fleet(){
        fleet = new Vehicle[CAPACITY];
        size = 0;
    }



    private int find(Vehicle vehicle){      //returns the index at which the vehicle is found
        for(int i = 0;i<size;i++){
            if(vehicle.equals(fleet[i])){
                return i;
            }
        }

        return NOT_FOUND;
    }

    private void grow(){ // resizes the array

            int newArrayLength = fleet.length + 4; // fleet capacity grows by 4, add 4 to the current capacity

            Vehicle[] newFleet = new Vehicle[newArrayLength];   // create new array

            for(int i = 0; i<fleet.length; i++){   // point the old fleet to the new larger fleet
                newFleet[i] = fleet[i];
            }

            fleet = newFleet;

    }

    public void add(Vehicle vehicle){         // adds vehicle to the end of the array;

        if(size == fleet.length){         // check if max capacity has been reached
            grow();                       // call grow if true
        }

        fleet[size] = vehicle;              // add vehicle to the last free slot

        size++;                          // increment size

    }

    public void remove(Vehicle vehicle){ // overwrite the last element

        int index = find(vehicle); // find the index of the vehicle

        if(index== NOT_FOUND){     // test if the vehicle cannot be found
            return;                // return if vehicle is not in fleet
        }

        fleet[index] = fleet[size-1]; // replace the found vehicle with the last vehicle in the fleet

        fleet[size-1] = null;   //remove the last vehicle from fleet

        size--;       //decrease the size to account for the change

    }

    public boolean contains(Vehicle vehicle){
        int index = find(vehicle);

        if(index == NOT_FOUND){
            return false;
        }
        return true;
    }

    public void printByMake(){ //ordered by make, then date obtained

        if(size == 0){
            System.out.println("There is no vehicle in the fleet.");
            return;
        }

        for(int i = 0; i<size-1;i++){
            int minimumIndex = i;

            for(int j = i+1; j<size; j++){
                if(compareByMakeThenDate(fleet[j],fleet[minimumIndex]) <0){
                    minimumIndex = j;
                }
            }

            if(minimumIndex != i){
                Vehicle temp  = fleet[i];
                fleet[i] = fleet[minimumIndex];
                fleet[minimumIndex] = temp;
            }



        }

        System.out.println("*List of vehicles in the fleet, ordered by make and date obtained.");
        for(int i = 0; i<size;i++){
            System.out.println(fleet[i].toString());
        }
        System.out.println("*end of list.");

    }

    private int compareByMakeThenDate(Vehicle first, Vehicle second){
        int makeComparison = first.getMake().compareTo(second.getMake());

        if(makeComparison!=0){
            return makeComparison;
        }
        return first.getDate().compareTo(second.getDate());
    }








}
