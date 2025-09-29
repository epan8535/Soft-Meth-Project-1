import java.util.Scanner;
import java.util.Calendar;

public class Frontend {

    private TripList trips = new TripList();
    private Reservation reservations = new Reservation();
    private Fleet fleet = new Fleet();

    private static final int INDEX_GROWTH = 4;
    private Vehicle[] vehicleIndex = new Vehicle[8];
    private int vehicleCount = 0;
    private Booking[] bookingIndex = new Booking[4];
    private int bookingCount = 0;

    private void addVehicle(Scanner scanner) {
        String plate = scanner.next();
        String temp = scanner.next();
        Scanner obtainedScanner = new Scanner(temp).useDelimiter("/");
        int obtainedMonth = obtainedScanner.nextInt();
        int obtainedDay = obtainedScanner.nextInt();
        int obtainedYear = obtainedScanner.nextInt();
        obtainedScanner.close();
        Date obtainedDate = new Date(obtainedYear,obtainedMonth,obtainedDay);
        String obtainedMake = scanner.next();
        int curOdomoter = Integer.parseInt(scanner.next());

        if(!obtainedDate.isValid()){
            System.out.println("invalid calendar date.");
            return;
        }
        Calendar calendarToday = Calendar.getInstance();
        Date today = new Date(calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH) + 1, calendarToday.get(Calendar.DAY_OF_MONTH));
        if(obtainedDate.compareTo(today) >= 0) {
            System.out.println("is today or a future date.");
            return;
        }

        Make make = null;
        for(Make testMake : Make.values()) {
            if (testMake.name().equalsIgnoreCase(obtainedMake)){
                make = testMake;
                break;
            }
        }
        if(make == null) {
            System.out.println("invalid make.");
            return;
        }
        if(curOdomoter <= 0) {
            System.out.println("invalid mileage.");
            return;
        }
        Vehicle vehicle = new Vehicle(plate,obtainedDate,make,curOdomoter);
        if(fleet.contains(vehicle)){
            System.out.println("vehicle already exists.");
            return;
        }
        fleet.add(vehicle);

        if(vehicleCount == vehicleIndex.length){
            Vehicle[] newVehicleArray = new Vehicle[vehicleIndex.length + INDEX_GROWTH];
            for (int i = 0; i < vehicleCount; i++){
                newVehicleArray[i] = vehicleIndex[i];
            }
            vehicleIndex = newVehicleArray;
        }
        vehicleIndex[vehicleCount++] = vehicle;
        System.out.println(vehicle + " added to the fleet.");
    }

    private void deleteVehicle(Scanner scanner) {
        String plate = scanner.next();
        Vehicle vehicleSearch = new Vehicle(plate, null, null, 1);
        for(int i = 0; i < bookingCount; i++){
            if(bookingIndex[i].getVehicle().equals(vehicleSearch)){
                System.out.println(plate + " has existing bookings; cannot be removed.");
                return;
            }
        }
        fleet.remove(vehicleSearch);

        for(int i = 0; i < vehicleCount; i++){
            if(vehicleIndex[i].equals(vehicleSearch)){
                vehicleIndex[i] = vehicleIndex[vehicleCount - 1];
                vehicleIndex[vehicleCount - 1] = null;
                vehicleCount--;
                break;
            }
        }
        System.out.println(plate + " removed from the fleet.");
    }

    private void bookVehicle(Scanner scanner) {
        String beginToken = scanner.next();
        Scanner beginScanner = new Scanner(beginToken).useDelimiter("/");
        int beginMonth = beginScanner.nextInt();
        int beginDay = beginScanner.nextInt();
        int beginYear = beginScanner.nextInt();
        beginScanner.close();
        Date beginDate = new Date(beginYear, beginMonth, beginDay);

        String endToken = scanner.next();
        Scanner endScanner = new Scanner(endToken).useDelimiter("/");
        int endMonth = endScanner.nextInt();
        int endDay = endScanner.nextInt();
        int endYear = endScanner.nextInt();
        endScanner.close();
        Date endDate = new Date(endYear, endMonth, endDay);

        String plate = scanner.next();
        String employeeToken = scanner.next();

        if (!beginDate.isValid()) {
            System.out.println("invalid calendar date.");
            return;
        }
        Calendar calendarToday = Calendar.getInstance();
        Date today = new Date(
                calendarToday.get(Calendar.YEAR),
                calendarToday.get(Calendar.MONTH) + 1,
                calendarToday.get(Calendar.DAY_OF_MONTH)
        );
        Calendar calendarLimit = (Calendar) calendarToday.clone();
        calendarLimit.add(Calendar.MONTH, 3);
        Date limitDate = new Date(
                calendarLimit.get(Calendar.YEAR),
                calendarLimit.get(Calendar.MONTH) + 1,
                calendarLimit.get(Calendar.DAY_OF_MONTH)
        );
        if (beginDate.compareTo(today) < 0 || beginDate.compareTo(limitDate) > 0) {
            System.out.println("invalid date range.");
            return;
        }
        if (!endDate.isValid() || endDate.compareTo(beginDate) < 0) {
            System.out.println("invalid date range.");
            return;
        }
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(beginYear, beginMonth - 1, beginDay, 0, 0, 0);
        beginCal.set(Calendar.MILLISECOND, 0);
        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear, endMonth - 1, endDay, 0, 0, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        long millisDiff = endCal.getTimeInMillis() - beginCal.getTimeInMillis();
        int daysBetween = (int) (millisDiff / (24L * 60L * 60L * 1000L));
        if (daysBetween > 7) {
            System.out.println("invalid date range.");
            return;
        }

        Vehicle vehicle = null;
        Vehicle vehicleProbe = new Vehicle(plate, null, null, 1);
        for (int vehicleIndexPos = 0; vehicleIndexPos < vehicleCount; vehicleIndexPos++) {
            if (vehicleIndex[vehicleIndexPos].equals(vehicleProbe)) {
                vehicle = vehicleIndex[vehicleIndexPos];
                break;
            }
        }
        if (vehicle == null) {
            System.out.println("vehicle not in the fleet.");
            return;
        }

        for (int bookingScanIndex = 0; bookingScanIndex < bookingCount; bookingScanIndex++) {
            Booking existing = bookingIndex[bookingScanIndex];
            if (existing.getVehicle().equals(vehicle)) {
                Date existingBegin = existing.getBegin();
                Date existingEnd = existing.getEnd();
                if (beginDate.compareTo(existingEnd) <= 0 && existingBegin.compareTo(endDate) <= 0) {
                    System.out.println("vehicle not available for the given dates.");
                    return;
                }
            }
        }

        Employee employee = null;
        for (Employee candidateEmployee : Employee.values()) {
            if (candidateEmployee.name().equalsIgnoreCase(employeeToken)) {
                employee = candidateEmployee;
                break;
            }
        }
        if (employee == null) {
            System.out.println("employee not eligible.");
            return;
        }

        for (int bookingScanIndex = 0; bookingScanIndex < bookingCount; bookingScanIndex++) {
            Booking existing = bookingIndex[bookingScanIndex];
            if (existing.getEmployee() == employee) {
                Date existingBegin = existing.getBegin();
                Date existingEnd = existing.getEnd();
                if (beginDate.compareTo(existingEnd) <= 0 && existingBegin.compareTo(endDate) <= 0) {
                    System.out.println("employee has a conflicting booking.");
                    return;
                }
            }
        }

        Booking booking = new Booking(beginDate, endDate, employee, vehicle);
        reservations.add(booking);

        if (bookingCount == bookingIndex.length) {
            Booking[] newBookingArray = new Booking[bookingIndex.length + INDEX_GROWTH];
            for (int i = 0; i < bookingCount; i++) {
                newBookingArray[i] = bookingIndex[i];
            }
            bookingIndex = newBookingArray;
        }
        bookingIndex[bookingCount++] = booking;

        Date obtained = vehicle.getDate();
        String obtainedDateText = obtained.getMonth() + "/" + obtained.getDay() + "/" + obtained.getYear();
        int currentMileage = vehicle.getMileage();

        String bookingBeginText = beginDate.getMonth() + "/" + beginDate.getDay() + "/" + beginDate.getYear();
        String bookingEndText   = endDate.getMonth()   + "/" + endDate.getDay()   + "/" + endDate.getYear();

        System.out.println(
                plate + ":" + vehicle.getMake().name() + ":" + obtainedDateText +
                        " [mileage:" + currentMileage + "] [beginning " + bookingBeginText +
                        " ending " + bookingEndText + ":" + employee.name() + "] booked."
        );
    }

    private void cancelBooking(Scanner scanner) {
        String beginToken = scanner.next();
        Scanner beginScanner = new Scanner(beginToken).useDelimiter("/");
        int beginMonth = beginScanner.nextInt();
        int beginDay = beginScanner.nextInt();
        int beginYear = beginScanner.nextInt();
        beginScanner.close();
        Date beginDate = new Date(beginYear, beginMonth, beginDay);

        String endToken = scanner.next();
        Scanner endScanner = new Scanner(endToken).useDelimiter("/");
        int endMonth = endScanner.nextInt();
        int endDay = endScanner.nextInt();
        int endYear = endScanner.nextInt();
        endScanner.close();
        Date endDate = new Date(endYear, endMonth, endDay);

        String plate = scanner.next();

        Booking targetBooking = null;
        int targetIndex = -1;
        for (int scanIndex = 0; scanIndex < bookingCount; scanIndex++) {
            Booking existing = bookingIndex[scanIndex];
            if (existing.getVehicle().getPlate().equals(plate)
                    && existing.getBegin().equals(beginDate)
                    && existing.getEnd().equals(endDate)) {
                targetBooking = existing;
                targetIndex = scanIndex;
                break;
            }
        }
        if (targetBooking == null) {
            System.out.println("booking not found.");
            return;
        }

        reservations.remove(targetBooking);

        bookingIndex[targetIndex] = bookingIndex[bookingCount - 1];
        bookingIndex[bookingCount - 1] = null;
        bookingCount--;

        System.out.println("booking canceled: " + targetBooking);
    }

    private void returnVehicle(Scanner scanner) {
        String endToken = scanner.next();
        Scanner endScanner = new Scanner(endToken).useDelimiter("/");
        int endMonth = endScanner.nextInt();
        int endDay = endScanner.nextInt();
        int endYear = endScanner.nextInt();
        endScanner.close();
        Date endDate = new Date(endYear, endMonth, endDay);

        String plate = scanner.next();
        int newOdometer = Integer.parseInt(scanner.next());

        Booking targetBooking = null;
        int targetIndex = -1;
        for (int scanIndex = 0; scanIndex < bookingCount; scanIndex++) {
            Booking existing = bookingIndex[scanIndex];
            if (existing.getVehicle().getPlate().equals(plate) && existing.getEnd().equals(endDate)) {
                targetBooking = existing;
                targetIndex = scanIndex;
                break;
            }
        }
        if (targetBooking == null) {
            System.out.println("no such booking found for return.");
            return;
        }

        Booking earliestEnding = null;
        for (int scanIndex = 0; scanIndex < bookingCount; scanIndex++) {
            if (earliestEnding == null || bookingIndex[scanIndex].getEnd().compareTo(earliestEnding.getEnd()) < 0) {
                earliestEnding = bookingIndex[scanIndex];
            }
        }
        if (earliestEnding != null && !earliestEnding.getEnd().equals(endDate)) {
            System.out.println("cannot return; not the earliest ending booking.");
            return;
        }

        int originalOdometer = targetBooking.getVehicle().getMileage();
        if (newOdometer <= 0 || newOdometer <= originalOdometer) {
            System.out.println("invalid mileage.");
            return;
        }

        reservations.remove(targetBooking);

        bookingIndex[targetIndex] = bookingIndex[bookingCount - 1];
        bookingIndex[bookingCount - 1] = null;
        bookingCount--;

        Trip completedTrip = new Trip(targetBooking, originalOdometer, newOdometer);
        trips.add(completedTrip);
        targetBooking.getVehicle().setMileage(newOdometer);

        System.out.println("Vehicle returned. " + completedTrip);
    }


    private void printCommand(String subcommand) {
        switch (subcommand) {
            case "PF":
                fleet.printByMake();
                break;
            case "PR":
                reservations.printByVehicle();
                break;
            case "PD":
                reservations.printByDept();
                break;
            case "PT":
                trips.print();
                break;
            default:
                System.out.println("invalid print command.");
        }
    }

    public void run() {
        System.out.println("Vehicle Management System is running.");
        Scanner stdinScanner = new Scanner(System.in);
        while (true) {
            if (!stdinScanner.hasNextLine()) break;

            String line = stdinScanner.nextLine().trim();
            if (line.isEmpty()) continue;

            Scanner lineScanner = new Scanner(line);
            if (!lineScanner.hasNext()) { lineScanner.close(); continue; }

            String command = lineScanner.next();
            if (command.equals("Q")) {
                System.out.println("Vehicle Management System is terminated.");
                lineScanner.close();
                break;
            }

            if (command.equals("A")) {
                addVehicle(lineScanner);
            } else if (command.equals("D")) {
                deleteVehicle(lineScanner);
            } else if (command.equals("B")) {
                bookVehicle(lineScanner);
            } else if (command.equals("C")) {
                cancelBooking(lineScanner);
            } else if (command.equals("R")) {
                returnVehicle(lineScanner);
            } else if (command.startsWith("P")) {
                printCommand(command);
            } else {
                System.out.println("invalid command.");
            }

            lineScanner.close();
        }
    }
}
