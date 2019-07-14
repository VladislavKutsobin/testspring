package net.proselyte.springapp.controller;

import net.proselyte.springapp.model.Department;
import net.proselyte.springapp.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/department")
public class DepartmentController {


    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("departments", departmentService.findAll());
        return "departments";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model model) {
        model.addAttribute("department", new Department());
        return "department_form";
    }

    @PostMapping("/saveDepartment")
    public String saveDepartment(@ModelAttribute("department") Department department, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "department_form";
        }

        departmentService.save(department);
        return "redirect:/department/list";
    }

    @GetMapping("/showFormForUpdate/{departmentId}")
    public String showFormForUpdate(@PathVariable("departmentId") int id, Model model) {
        model.addAttribute("department", departmentService.getById(id));
        return "department_form";
    }

    @GetMapping("/delete/{departmentId}")
    public String deleteForm(@PathVariable("departmentId") int id) {
        departmentService.deleteById(id);
        return "redirect:/department/list";
    }


}
