package DaoService.DaoImpl;

import Config.Config;
import DaoService.JobDaoInterface;
import Model.Job;

import java.sql.*;
import java.util.List;

public class JobDaoImpl implements JobDaoInterface {
    @Override
    public void createJobTable() {
        String sql = "create table  job(" +
                "                     id serial primary key, " +
                "                    position varchar(50)," +
                "                    profession varchar," +
                "                    description varchar," +
                "                    experience integer" +
                ")";
        try (
                Connection connection = Config.getConnection();

                Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            System.out.println("Table successfully created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void addJob(Job job) {

        String sql = "insert  into job(position,  profession, description, experience)" +
                "values (?, ?, ?, ?)";

        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        Connection connection = Config.getConnection();

        Job job = new Job();
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement =
                    connection.prepareStatement("""
                              select * from job where id = ?                        
                            """);
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("Not found");
            } else {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
                job.setProfession(resultSet.getString("profession"));
                job.setDescription(resultSet.getString("description"));
                job.setExperience(resultSet.getInt("experience"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return job;
    }


    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return null;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        Connection connection = Config.getConnection();

        Job job = new Job();
        PreparedStatement preparedStatement = null;
        try {
            String sql = "select * from job where id=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("Not founded");
            } else {
                job.setId(resultSet.getLong("id"));
                job.setPosition(resultSet.getString("position"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return job;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql = "ALTER TABLE job DROP COLUMN description"; // Replace your_table_name with the actual table name.

        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}