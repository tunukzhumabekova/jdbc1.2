package Service.ServiceImpl;

import Config.Config;
import DaoService.DaoImpl.EmployeeDaoImpl;
import DaoService.DaoImpl.JobDaoImpl;
import Model.Job;
import Service.JobServiceInterface;

import java.sql.Connection;
import java.util.List;

public class JobServiceImpl implements JobServiceInterface {
    JobDaoImpl jobDao=new JobDaoImpl();

    @Override
    public void createJobTable() {
        jobDao.createJobTable();
    }

    @Override
    public void addJob(Job job) {
jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {
        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    @Override
    public void deleteDescriptionColumn() {
jobDao.deleteDescriptionColumn();

    }
}
