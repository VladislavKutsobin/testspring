package net.proselyte.springapp.repository;

import net.proselyte.springapp.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository extends JpaRepository<Department, Integer> {
}
