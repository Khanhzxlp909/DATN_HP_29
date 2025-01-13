package com.example.hp_29_MiniatureCrafts.controller.admin.employee;


import com.example.hp_29_MiniatureCrafts.dto.EmployeeDTO;
import com.example.hp_29_MiniatureCrafts.service.account.customer.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("admin/employee"))
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("findall")
    public List<EmployeeDTO> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("result/{id}")
    public EmployeeDTO findByID(Long id){
        return employeeService.findByID(id);
    }

    @PostMapping("save")
    public EmployeeDTO save(@RequestBody EmployeeDTO dto){
        return employeeService.saveEmployee(dto);
    }

    @PostMapping("update")
    public EmployeeDTO update(@RequestBody EmployeeDTO dto){
        return employeeService.updateEmployee(dto);
    }

}
