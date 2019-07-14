package net.proselyte.springapp.controller;

import net.proselyte.springapp.model.Employee;
import net.proselyte.springapp.service.DepartmentService;
import net.proselyte.springapp.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public String listEmployee(Model model){
        List<Employee> employees = employeeService.findAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model){
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", departmentService.findAll());
        System.out.println("===========>> HUIIIIIIIIIIIIIILO FOREVER");

        return "employee_form";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee")
                                       Employee employee,
                               BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.findAll());
            return "employee_form";
        }

        employeeService.save(employee);
        return "redirect:/employee/list";
    }

    @GetMapping("/showFormForUpdate/{employeeId}")
    public String showFormForUpdate(@PathVariable("employeeId") int id, Model model){
        model.addAttribute("employee",employeeService.getById(id));
        model.addAttribute("departments", departmentService.findAll());
        return "employee_form";
    }

    @GetMapping("/delete/{employeeId}")
    public String deleteCustomer(@PathVariable("employeeId") int id){

        employeeService.deleteById(id);

        return "redirect:/employee/list";
    }
}
