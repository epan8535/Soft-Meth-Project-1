public enum Employee {
    PATEL("Patel", Department.COMPUTER_SCIENCE),
    LIM("Lim", Department.ELECTRICAL_ENGINEERING),
    ZIMNES("Zimnes", Department.COMPUTER_SCIENCE),
    HARPER("Harper", Department.ELECTRICAL_ENGINEERING),
    KAUR("Kaur", Department.INFORMATION_TECHNOLOGY_AND_INFORMATICS),
    TAYLOR("Taylor", Department.MATHEMATICS),
    RAMESH("Ramesh",Department.MATHEMATICS),
    CERAVOLO("Ceravolo", Department.BUSINESS_ANALYTICS_AND_INFORMATION_TECHNOLOGY);

    private String employeeName;
    private Department dept;

    private Employee(String employeeName, Department dept){
        this.employeeName = employeeName;
        this.dept = dept;
    }

    public String getEmployeeName(){
        return employeeName;
    }

    public Department getDept(){
        return dept;
    }

    @Override
    public String toString(){
        return(employeeName + "(" +dept + ")");
    }

}
