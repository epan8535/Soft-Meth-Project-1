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
        for(int i = 0; i < 4; i++){ //make sure scanner has 4 tokens
            if(!scanner.hasNext()){
                System.out.println("invalid command!");
                return;
            }
        }

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
            System.out.println(temp + " - invalid calendar date.");
            return;
        }
        Calendar calendarToday = Calendar.getInstance();
        Date today = new Date(calendarToday.get(Calendar.YEAR), calendarToday.get(Calendar.MONTH) + 1, calendarToday.get(Calendar.DAY_OF_MONTH));
        if(obtainedDate.compareTo(today) >= 0) {
            System.out.println(temp + " - is today or a future date.");
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
            System.out.println(obtainedMake + " - invalid make.");
            return;
        }
        if(curOdomoter <= 0) {
            System.out.println(curOdomoter + " - invalid mileage.");
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
        System.out.println(vehicle + " has been added to the fleet.");
    }

    private void deleteVehicle(Scanner scanner) {

        if(!scanner.hasNext()){
            System.out.println("invalid command!");
            return;
        }

        String plate = scanner.next();
        int vehiclePosition = -1;
        for (int vehicleIndexPos = 0; vehicleIndexPos < vehicleCount; vehicleIndexPos++) {
            if (vehicleIndex[vehicleIndexPos].getPlate().equals(plate)) {
                vehiclePosition = vehicleIndexPos;
                break;
            }
        }
        if (vehiclePosition == -1) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }
        Vehicle vehicleToRemove = vehicleIndex[vehiclePosition];

        // 2) block removal if there are active bookings for this vehicle
        for (int bookingIndexPos = 0; bookingIndexPos < bookingCount; bookingIndexPos++) {
            if (bookingIndex[bookingIndexPos].getVehicle().equals(vehicleToRemove)) {
                System.out.println(plate + " - has existing bookings; cannot be removed.");
                return;
            }
        }

        // 3) remove from authoritative fleet
        fleet.remove(vehicleToRemove);

        // 4) remove from mirror index (swap-with-last)
        vehicleIndex[vehiclePosition] = vehicleIndex[vehicleCount - 1];
        vehicleIndex[vehicleCount - 1] = null;
        vehicleCount--;

        // 5) success message must print the full vehicle string
        System.out.println(vehicleToRemove + " has been removed from the fleet.");
    }

    private void bookVehicle(Scanner scanner) {

        // needs 4 tokens: begin, end, plate, employee
        for (int need = 0; need < 4; need++) {
            if (!scanner.hasNext()) { System.out.println("invalid command!"); return; }
        }

        // raw tokens (keep for messages)
        String beginToken = scanner.next();
        String endToken = scanner.next();
        String plate = scanner.next();
        String employeeToken = scanner.next();

        // parse dates
        Scanner beginScanner = new Scanner(beginToken).useDelimiter("/");
        int beginMonth = beginScanner.nextInt();
        int beginDay   = beginScanner.nextInt();
        int beginYear  = beginScanner.nextInt();
        beginScanner.close();
        Date beginDate = new Date(beginYear, beginMonth, beginDay);

        Scanner endScanner = new Scanner(endToken).useDelimiter("/");
        int endMonth = endScanner.nextInt();
        int endDay   = endScanner.nextInt();
        int endYear  = endScanner.nextInt();
        endScanner.close();
        Date endDate = new Date(endYear, endMonth, endDay);

        // ---- validations (exact phrasing) ----
        if (!beginDate.isValid()) {
            System.out.println(beginToken + " - beginning date is not a valid calendar date.");
            return;
        }
        if (!endDate.isValid()) {
            System.out.println(endToken + " - ending date is not a valid calendar date.");
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

        if (beginDate.compareTo(today) < 0) {
            System.out.println(beginToken + " - beginning date is not today or a future date.");
            return;
        }
        if (beginDate.compareTo(limitDate) > 0) {
            System.out.println(beginToken + " - beginning date beyond 3 months.");
            return;
        }
        if (endDate.compareTo(beginDate) < 0) {
            System.out.println(endToken + " - ending date must be equal or after the beginning date " + beginToken);
            return;
        }

        // duration: reject >= 7 days (because 10/26 ~ 11/2 must fail per spec)
        Calendar beginCal = Calendar.getInstance();
        beginCal.set(beginYear, beginMonth - 1, beginDay, 0, 0, 0);
        beginCal.set(Calendar.MILLISECOND, 0);
        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear, endMonth - 1, endDay, 0, 0, 0);
        endCal.set(Calendar.MILLISECOND, 0);
        long millisDiff = endCal.getTimeInMillis() - beginCal.getTimeInMillis();
        int daysBetween = (int) (millisDiff / (24L * 60L * 60L * 1000L));
        if (daysBetween >= 7) {
            System.out.println(beginToken + " ~ " + endToken + " - duration more than a week.");
            return;
        }

        // find vehicle by plate (mirror index)
        Vehicle vehicle = null;
        for (int pos = 0; pos < vehicleCount; pos++) {
            if (vehicleIndex[pos].getPlate().equals(plate)) { vehicle = vehicleIndex[pos]; break; }
        }
        if (vehicle == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }

        // vehicle availability overlap
        for (int i = 0; i < bookingCount; i++) {
            Booking existing = bookingIndex[i];
            if (existing.getVehicle().equals(vehicle)) {
                Date existingBegin = existing.getBegin();
                Date existingEnd   = existing.getEnd();
                if (beginDate.compareTo(existingEnd) <= 0 && existingBegin.compareTo(endDate) <= 0) {
                    System.out.println(plate + " - booking with " + beginToken + " ~ " + endToken + " not available.");
                    return;
                }
            }
        }

        // employee parse (no try/catch)
        Employee employee = null;
        for (Employee candidate : Employee.values()) {
            if (candidate.name().equalsIgnoreCase(employeeToken)) { employee = candidate; break; }
        }
        if (employee == null) {
            System.out.println(employeeToken + " - not an eligible employee to book.");
            return;
        }

        // employee conflict overlap
        for (int i = 0; i < bookingCount; i++) {
            Booking existing = bookingIndex[i];
            if (existing.getEmployee() == employee) {
                Date existingBegin = existing.getBegin();
                Date existingEnd   = existing.getEnd();
                if (beginDate.compareTo(existingEnd) <= 0 && existingBegin.compareTo(endDate) <= 0) {
                    System.out.println(employee.name() + " - has an existing booking conflicting with the beginning date " + beginToken);
                    return;
                }
            }
        }

        // create booking
        Booking booking = new Booking(beginDate, endDate, employee, vehicle);
        reservations.add(booking);

        // grow mirror if needed
        if (bookingCount == bookingIndex.length) {
            Booking[] newBookingArray = new Booking[bookingIndex.length + INDEX_GROWTH];
            for (int i = 0; i < bookingCount; i++) newBookingArray[i] = bookingIndex[i];
            bookingIndex = newBookingArray;
        }
        bookingIndex[bookingCount++] = booking;

        // success print (exact format)
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
        for (int need = 0; need < 3; need++) {
            if (!scanner.hasNext()) { System.out.println("invalid command!"); return; }
        }

        String beginToken = scanner.next();
        Scanner beginScanner = new Scanner(beginToken).useDelimiter("/");
        int beginMonth = beginScanner.nextInt();
        int beginDay   = beginScanner.nextInt();
        int beginYear  = beginScanner.nextInt();
        beginScanner.close();
        Date beginDate = new Date(beginYear, beginMonth, beginDay);

        String endToken = scanner.next();
        Scanner endScanner = new Scanner(endToken).useDelimiter("/");
        int endMonth = endScanner.nextInt();
        int endDay   = endScanner.nextInt();
        int endYear  = endScanner.nextInt();
        endScanner.close();
        Date endDate = new Date(endYear, endMonth, endDay);

        String plate = scanner.next();

        Booking targetBooking = null;
        int targetIndex = -1;
        for (int i = 0; i < bookingCount; i++) {
            Booking b = bookingIndex[i];
            if (b.getVehicle().getPlate().equals(plate)
                    && b.getBegin().compareTo(beginDate) == 0
                    && b.getEnd().compareTo(endDate) == 0) {
                targetBooking = b;
                targetIndex = i;
                break;
            }
        }
        if (targetBooking == null) {
            System.out.println(plate + ":" + beginToken + " ~ " + endToken + " - cannot find the booking.");
            return;
        }

        reservations.remove(targetBooking);

        bookingIndex[targetIndex] = bookingIndex[bookingCount - 1];
        bookingIndex[bookingCount - 1] = null;
        bookingCount--;

        System.out.println(plate + ":" + beginToken + " ~ " + endToken + " has been canceled.");
    }


    private void returnVehicle(Scanner scanner) {
        for (int need = 0; need < 3; need++) {
            if (!scanner.hasNext()) { System.out.println("invalid command!"); return; }
        }

        String endToken = scanner.next();
        Scanner endScanner = new Scanner(endToken).useDelimiter("/");
        int endMonth = endScanner.nextInt();
        int endDay   = endScanner.nextInt();
        int endYear  = endScanner.nextInt();
        endScanner.close();
        Date endDate = new Date(endYear, endMonth, endDay);

        String plate = scanner.next();
        String odometerToken = scanner.next();
        int newOdometer = Integer.parseInt(odometerToken);

        // find booking by (plate, end date)
        Booking targetBooking = null;
        int targetIndex = -1;
        for (int i = 0; i < bookingCount; i++) {
            Booking b = bookingIndex[i];
            if (b.getVehicle().getPlate().equals(plate) && b.getEnd().compareTo(endDate) == 0) {
                targetBooking = b;
                targetIndex = i;
                break;
            }
        }
        if (targetBooking == null) {
            System.out.println(plate + " booked with ending date " + endToken + " - cannot find the booking.");
            return;
        }

        // must be the earliest ending booking among all current bookings
        Booking earliestEnding = null;
        for (int i = 0; i < bookingCount; i++) {
            if (earliestEnding == null || bookingIndex[i].getEnd().compareTo(earliestEnding.getEnd()) < 0) {
                earliestEnding = bookingIndex[i];
            }
        }
        if (earliestEnding != null && earliestEnding.getEnd().compareTo(endDate) != 0) {
            System.out.println(plate + " booked with ending date " + endToken + " - returning not in order of ending date.");
            return;
        }

        int originalOdometer = targetBooking.getVehicle().getMileage();
        if (newOdometer <= 0) {
            System.out.println(newOdometer + " - invalid mileage.");
            return;
        }
        if (newOdometer <= originalOdometer) {
            System.out.println("Invalid mileage - current mileage: " + originalOdometer + " entered mileage: " + newOdometer);
            return;
        }

        // finalize trip
        Trip completedTrip = new Trip(targetBooking, originalOdometer, newOdometer);
        trips.add(completedTrip);
        targetBooking.getVehicle().setMileage(newOdometer); // ensure Vehicle has setMileage(int)

        // remove booking from active sets
        reservations.remove(targetBooking);
        bookingIndex[targetIndex] = bookingIndex[bookingCount - 1];
        bookingIndex[bookingCount - 1] = null;
        bookingCount--;

        String beginStr = targetBooking.getBegin().getMonth() + "/" + targetBooking.getBegin().getDay() + "/" + targetBooking.getBegin().getYear();
        String endStr   = targetBooking.getEnd().getMonth()   + "/" + targetBooking.getEnd().getDay()   + "/" + targetBooking.getEnd().getYear();
        int used = newOdometer - originalOdometer;

        System.out.println("Trip completed: " + plate + " " + beginStr + " ~ " + endStr
                + " original mileage: " + originalOdometer
                + " current mileage: " + newOdometer
                + " mileage used: " + used);
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
        System.out.println();
        Scanner stdinScanner = new Scanner(System.in);
        while (true) {
            if (!stdinScanner.hasNextLine()) break;

            String line = stdinScanner.nextLine().trim();
            if (line.isEmpty()) continue;

            Scanner lineScanner = new Scanner(line);
            if (!lineScanner.hasNext()) { lineScanner.close(); continue; }

            String command = lineScanner.next();

            if (command.equals("Q")) {
                System.out.println();
                System.out.println("Vehicle Management System is terminated. ");
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
            } else if (command.equals("PF") || command.equals("PR") || command.equals("PD") || command.equals("PT")) {
                printCommand(command);
            } else {
                System.out.println(command + " - invalid command!");
            }

            lineScanner.close();
        }
    }
}
