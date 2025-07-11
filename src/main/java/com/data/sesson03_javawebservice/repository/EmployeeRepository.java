package com.data.sesson03_javawebservice.repository;
import com.data.sesson03_javawebservice.dto.EmployeeDTO;
import com.data.sesson03_javawebservice.entity.Employee;
import com.data.sesson03_javawebservice.projection.EmployeeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{
    Employee findByPhoneNumber(String phone);

    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeeBySalary(@Param("salary") Double salary);

    @Query("SELECT new com.data.sesson03_javawebservice.dto.EmployeeDTO(e.id, e.name, e.email) FROM Employee e")
    List<EmployeeDTO> findAllEmployeeDTO();

    List<EmployeeInfo> findAllBy();
}
