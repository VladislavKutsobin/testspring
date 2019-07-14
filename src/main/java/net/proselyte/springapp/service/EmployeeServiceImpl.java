package net.proselyte.springapp.service;

import net.proselyte.springapp.model.Employee;
import net.proselyte.springapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        employeeRepository.deleteById(integer);
    }

    @Override
    public Employee getById(Integer integer) {
        return employeeRepository.getOne(integer);
    }
}
