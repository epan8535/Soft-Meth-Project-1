/*
* Department enum class that lists all the Departments of the employees
*
* @author Jake Cordon
 */

public enum Department {
    COMPUTER_SCIENCE("Computer Science"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    INFORMATION_TECHNOLOGY_AND_INFORMATICS("Information Technology and Informatics"),
    MATHEMATICS("Mathematics"),
    BUSINESS_ANALYTICS_AND_INFORMATION_TECHNOLOGY("Business Analytics and Information Technology");

    // ===================== Instance Variables ===================

    private String department;

    // ===================== Constructors ===================

    private Department(String department){
        this.department = department;
    }

    // ===================== Public Methods ===================

    public String getDepartment(){
        return department;
    }

    @Override
    public String toString(){
        return department;
    }

}
