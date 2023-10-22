package DaoService.DaoImpl;

import Config.Config;
import DaoService.EmployeeDaoInterface;
import Model.Employee;
import Model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDaoInterface {

    @Override
    public void createEmployee() {
        String sql = "CREATE TABLE IF NOT EXISTS employee ("
                + "id SERIAL PRIMARY KEY, "
                + "firstName VARCHAR(50), "
                + "lastName VARCHAR(50), "
                + "age INT, "
                + "email VARCHAR(255), "
                + "job_Id INT REFERENCES job(id)"
                + ")";
        try (
                Connection connection = Config.getConnection();
                Statement statement = connection.createStatement();
        ) {
            statement.executeUpdate(sql);
            System.out.println("Table successfully created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    Connection connection= Config.getConnection();



    @Override
    public void addEmployee(Employee employee) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("""
                                 
                             insert into employee( id, firstName, lastName, age,  email, job_id)
                                 values (?, ?, ?, ?, ?, ?)
                                                      """)){
            preparedStatement.setLong(1, employee.getId());
            preparedStatement.setString(2, employee.getFirstName());
            preparedStatement.setString(3, employee.getLastName());
            preparedStatement.setInt(4, employee.getAge());
            preparedStatement.setString(5, employee.getEmail());
            preparedStatement.setLong(6, employee.getJobId());

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }


    }

    @Override
    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS \"employee\""; // This SQL statement will drop the "employee" table if it exists.

        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void cleanTable() {
        String sql = "DELETE FROM \"employee\"";

        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void updateEmployee(Long id, Employee employee) {
        String sql = "UPDATE employee SET firstname = ?, email = ? WHERE id = ?";

        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setLong(3, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new RuntimeException("Employee not found with ID: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();

        String sql = "SELECT * FROM employee";

        try (Connection connection = Config.getConnection();
             Statement statement=connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstname"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return employees;
    }



    @Override
    public Employee findByEmail(String email) {
        Employee employee=new Employee();
        PreparedStatement preparedStatement=null;
        String sql="select * from employee where email=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (!resultSet.next()){
                throw new RuntimeException("Not founded");
            }else {
                employee.setEmail(resultSet.getString("email"));
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return employee;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> map=new HashMap<>();
        PreparedStatement preparedStatement=null;
        String sql="select e.*, j.* from employee e inner join job j on e.job_id=j.id where e.id=?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (!resultSet.next()){
                throw new RuntimeException("Not found");
            }else {
                Employee employee=new Employee();
                Job job=new Job();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstname"));
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                map.put(employee, job);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return map;
    }

    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee> employees=new ArrayList<>();
        PreparedStatement preparedStatement=null;
        String sql="SELECT e.* FROM employee e INNER JOIN job j ON e.job_id = j.id WHERE j.position = ?";
        try {
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1, position);
            ResultSet resultSet=preparedStatement.executeQuery();
            if (!resultSet.next()){
                throw new RuntimeException("Not founded");
            }else {
                Employee employee=new Employee();
                employee.setId(resultSet.getLong("id"));
                employee.setFirstName(resultSet.getString("firstname"));
                employees.add(employee);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
        return employees;
    }


    public void creatEmployee(String table, List<String> column) {
        StringBuilder stringBuilder =
                new StringBuilder(String.format("create table %s (",table));
        try {
            Statement statement = connection.createStatement();
            for (int i = 0; i <column.size(); i++) {
                stringBuilder.append(column.get(i));
                if (i < column.size() - 1) {
                    stringBuilder.append(",");
                }
            }
            stringBuilder.append(")");
            statement.executeUpdate(stringBuilder.toString());
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}




