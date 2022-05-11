package Visitor;

import java.util.ArrayList;

/**
 * @Author qinsicheng
 * @Description 内容：
 * @Date 27/01/2022 15:37
 */
interface Department {
    public void visit(FullTimeEmployee department);
    public void visit(PartTimeEmployee department);
}
class FADepartment implements Department{
    @Override
    public void visit(PartTimeEmployee department) {
        System.out.println("人力资源部操作。。。兼职");
    }

    @Override
    public void visit(FullTimeEmployee fullTimeEmployee) {
        System.out.println("人力资源部操作。。。全职");
    }
}
class HRDepartment implements Department{
    @Override
    public void visit(FullTimeEmployee fullTimeEmployee) {
        System.out.println("财务部操作。。。全职");
    }

    @Override
    public void visit(PartTimeEmployee department) {
        System.out.println("财务部操作。。。兼职");
    }
}
class EmployeeList{
    private final ArrayList<Employee> employees = new ArrayList<>();
    public void addEmployee(Employee employee) {
        employees.add(employee);
    }
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    public void accept(Department handle) {
        for (Employee employee : employees) {
            employee.accept(handle);
        }
    }
}
interface Employee{
    public void accept(Department handle);
}
class FullTimeEmployee implements Employee{
    private String name;
    private Double weeklyWage;
    private int workTime;

    public FullTimeEmployee(String name, Double weeklyWage, int workTime) {
        this.name = name;
        this.weeklyWage = weeklyWage;
        this.workTime = workTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeeklyWage() {
        return weeklyWage;
    }

    public void setWeeklyWage(Double weeklyWage) {
        this.weeklyWage = weeklyWage;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    @Override
    public void accept(Department handle) {
        handle.visit(this);
    }
}
class PartTimeEmployee implements Employee{
    private String name;
    private Double hourWage;
    private int workTime;

    public PartTimeEmployee(String name, Double hourWage, int workTime) {
        this.name = name;
        this.hourWage = hourWage;
        this.workTime = workTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHourWage() {
        return hourWage;
    }

    public void setHourWage(Double hourWage) {
        this.hourWage = hourWage;
    }

    public int getWorkTime() {
        return workTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    @Override
    public void accept(Department handle) {
        handle.visit(this);
    }
}
class Client {
    public static void main(String[] args) {
        EmployeeList employeeList = new EmployeeList();
        employeeList.addEmployee(new FullTimeEmployee("jack",20.0,12));
        employeeList.addEmployee(new PartTimeEmployee("alan",20.0,12));
        employeeList.accept((Department)XMLUtil.getBean());
    }
}