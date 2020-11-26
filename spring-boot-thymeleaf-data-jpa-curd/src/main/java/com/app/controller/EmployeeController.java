package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.entity.Employee;
import com.app.reponsitory.EmployeeRepository;
import com.app.validators.EmployeeValidator;

@Controller
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping(value = { "/", "/{id}" })
	public String landlingPage(Model model, @PathVariable(value = "id", required = false) Integer id) {
		Employee employee = new Employee();
		if (id != null)
			employee = employeeRepository.getOne(id);
		model.addAttribute("employee", employee);
		return "index";
	}

	@ModelAttribute("list")
	public List<Employee> listOfEmployee() {
		return employeeRepository.findAll();
	}

	@PostMapping(value = "employee")
	public String saveOrUpdate(Model model, @ModelAttribute("employee") @Validated Employee employee,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("employee", employee);
			return "index";
		}

		employee = employeeRepository.save(employee);
		if (employee != null)
			redirectAttributes.addFlashAttribute("success", "Employee Save or Update with id : " + employee.getId());
		else
			redirectAttributes.addFlashAttribute("error", "Something went wrong try again!");

		return "redirect:/";
	}

	@GetMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
		employeeRepository.deleteById(id);
		attributes.addFlashAttribute("success", "Employee Deleted with id : " + id);
		return "redirect:/";
	}

	@InitBinder("employee")
	public void formValidator(WebDataBinder binder) {
		binder.setValidator(new EmployeeValidator());
	}

}
