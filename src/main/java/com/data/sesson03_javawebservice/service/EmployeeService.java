package com.data.sesson03_javawebservice.service;

import com.data.sesson03_javawebservice.dto.EmployeeDTO;
import com.data.sesson03_javawebservice.entity.Employee;
import com.data.sesson03_javawebservice.projection.EmployeeInfo;
import com.data.sesson03_javawebservice.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Page<Employee> getPaginatedEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public void save(Employee employee) {
        employeeRepository.save(employee);
    }

    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Page<Employee> getAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Page<Employee> searchByPhone(String phone, Pageable pageable) {
        return new PageImpl<>(
                List.of(employeeRepository.findByPhoneNumber(phone)), pageable, 1
        );
    }

    public List<EmployeeDTO> getAllDTOs() {
        return employeeRepository.findAllEmployeeDTO();
    }

    public List<EmployeeInfo> getAllProjection() {
        return employeeRepository.findAllBy();
    }

    public List<Employee> getEmployeeWithSalaryAbove(double salary) {
        return employeeRepository.findEmployeeBySalary(salary);
    }
}
