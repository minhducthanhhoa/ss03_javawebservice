package com.data.sesson03_javawebservice.controller;

import com.data.sesson03_javawebservice.dto.EmployeeDTO;
import com.data.sesson03_javawebservice.entity.Employee;
import com.data.sesson03_javawebservice.projection.EmployeeInfo;
import com.data.sesson03_javawebservice.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/add-employee")
    public String showAddForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "formEmployee";
    }

    @PostMapping("/add-employee")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping
    public String listEmployees(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "") String phoneSearch) {

        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Employee> employees;
        if (!phoneSearch.isEmpty()) {
            employees = employeeService.searchByPhone(phoneSearch, pageable);
        } else {
            employees = employeeService.getAll(pageable);
        }

        model.addAttribute("employees", employees);
        model.addAttribute("phoneSearch", phoneSearch);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("direction", direction);
        return "listEmployee";
    }

    @GetMapping("/edit-employee/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Employee employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "formEmployee";
    }

    @PostMapping("/edit-employee/{id}")
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        employee.setId(id);
        employeeService.save(employee);
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/dto")
    @ResponseBody
    public List<EmployeeDTO> getEmployeeDTOs() {
        return employeeService.getAllDTOs();
    }

    @GetMapping("/info")
    @ResponseBody
    public List<EmployeeInfo> getEmployeeInfo() {
        return employeeService.getAllProjection();
    }

    @GetMapping("/salary")
    @ResponseBody
    public List<Employee> getEmployeesWithSalaryMoreThan(@RequestParam double min) {
        return employeeService.getEmployeeWithSalaryAbove(min);
    }
}
