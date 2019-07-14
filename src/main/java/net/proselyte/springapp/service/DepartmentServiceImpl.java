package net.proselyte.springapp.service;

import net.proselyte.springapp.model.Department;
import net.proselyte.springapp.repository.DepartmentsRepository;
import net.proselyte.springapp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentsRepository departmentsRepository;


    @Override
    public void save(Department department) {
        departmentsRepository.save(department);
    }

    @Override
    public List<Department> findAll() {
        return departmentsRepository.findAll();
    }

    @Override
    public void deleteById(Integer integer) {
        departmentsRepository.deleteById(integer);
    }

    @Override
    public Department getById(Integer integer) {
        return departmentsRepository.getOne(integer);
    }
}
