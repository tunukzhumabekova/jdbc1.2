package DaoService;

import Model.Employee;
import Model.Job;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeDaoInterface {

    void createEmployee() ;
    void addEmployee(Employee employee);
    void dropTable();
    void cleanTable();
    void updateEmployee(Long id,Employee employee);
    List<Employee> getAllEmployees();
    Employee findByEmail(String email);
    Map<Employee, Job> getEmployeeById(Long employeeId);
    List<Employee> getEmployeeByPosition(String position);



}
