package entities;

import util.DateUtils;
import util.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee {

    private Integer id;
    private String name;
    private String role;
    private LocalDate hireDate;
    private BigDecimal salary;

    public Employee(String name, String role, LocalDate hireDate, BigDecimal salary) {
        this(null, name, role, hireDate, salary);
    }

    public Employee(Integer id, String name, String role, LocalDate hireDate, BigDecimal salary) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal salary) { this.salary = salary; }

    public String getFormattedHireDate() { return DateUtils.format(hireDate); }

    public String getFormattedSalary() { return MoneyUtils.formatCurrency(salary); }

    @Override
    public String toString() {
        return "#" + id + " - " + name + " (" + role + ")";
    }
}
