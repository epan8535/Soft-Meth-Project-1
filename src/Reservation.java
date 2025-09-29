public class Reservation {
    private Booking[] bookings;
    private int size;

    public Reservation() {
        this.bookings = new Booking[4];
        this.size = 0;
    }

    private int find(Booking booking) {
        for(int i = 0; i < size; i++){
            if(bookings[i] == booking){
                return i;
            }
        }
        return -1;
    } //search the given booking

    private void grow() {
        Booking[] temp = new Booking[bookings.length + 4];
        for(int i = 0; i < size; i++){
            temp[i] = bookings[i];
            bookings = temp;
        }
    } //resize the array

    public void add(Booking booking) {
        grow();
        bookings[bookings.length-1] = booking;
    } //add to end of array

    public void remove(Booking booking) {
        int search = find(booking);
        if(search == -1) {
            return;
        } else {
            bookings[search] = bookings[size-1];
            bookings[size-1] = null;
            size--;
        }
    } //overwrite with last element

    public boolean contains(Booking booking) {
        int result = find(booking);
        if(result != -1){
            return true;
        } else {
            return false;
        }
    }

    public void printByVehicle(){
        for (int i = 1; i < size; i++){
            Booking key = bookings[i];
            int j = i - 1;
            while (j >= 0) {
                String plateKey = key.getVehicle().getPlate();
                String plateJ = bookings[j].getVehicle().getPlate();
                Date keyBegin = key.getBegin();
                Date jBegin = bookings[j].getBegin();
                boolean moveLeft = (plateKey.compareTo(plateJ) < 0) || (plateKey.compareTo(plateJ) == 0 && keyBegin.compareTo(jBegin) < 0);
                if(!moveLeft) {
                    break;
                }
                bookings[j + 1] = bookings[j];
                j--;
            }
            bookings[j + 1] = key;
        }
        for(int k = 0; k < size; k++){
            System.out.println(bookings[k]);
        }
    } //ordered by plate then beginning date


    public void printByDept() {
        for (int i = 1; i < size; i++){
            Booking key = bookings[i];
            int j = i - 1;
            while (j >= 0) {
                Department deptKey = key.getEmployee().getDept();
                Department deptJ = bookings[j].getEmployee().getDept();
                String empKey = key.getEmployee().name();
                String empJ = bookings[j].getEmployee().name();
                int cmpDept = deptKey.name().compareTo(deptJ.name());
                boolean moveLeft = (cmpDept < 0) || (cmpDept == 0 && empKey.compareTo(empJ) < 0);
                if(!moveLeft) {
                    break;
                }
                bookings[j + 1] = bookings[j];
                j--;
            }
            bookings[j + 1] = key;
        }
        for(int k = 0; k < size; k++){
            System.out.println(bookings[k]);
        }
    } //ordered by department then by employee
}
