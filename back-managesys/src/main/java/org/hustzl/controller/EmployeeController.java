package org.hustzl.controller;


import org.hustzl.entity.Apartment;
import org.hustzl.entity.Employee;
import org.hustzl.form.SearchEmployeeForm;
import org.hustzl.form.SearchForm;
import org.hustzl.service.EmployeeService;
import org.hustzl.service.JobService;
import org.hustzl.vo.EmployeeVO;
import org.hustzl.vo.EmployeeVO2;
import org.hustzl.vo.PageVO;
import org.hustzl.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/save")
    public ResultVO save(@RequestBody Employee employee) {
//        System.out.println(employee);
        Boolean save = employeeService.saveEmployee(employee);
        ResultVO resultVO = new ResultVO<>(0, null);
        return resultVO;
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageVO list = this.employeeService.list(page, size);
        ResultVO resultVO = new ResultVO(0 ,list);
        return resultVO;
    }

    @GetMapping("/search")
    public ResultVO search(SearchEmployeeForm searchForm) {
//         System.out.println(searchForm);
        PageVO pageVO = this.employeeService.search(searchForm);
        ResultVO resultVO = new ResultVO<>(0, pageVO);
        return resultVO;
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        boolean remove = this.employeeService.deleteById(id);
        ResultVO resultVO = null;
        if(remove == false)
            resultVO = new ResultVO<>(-1, null);
        else
            resultVO = new ResultVO<>(0, null);
        return resultVO;
    }

    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        EmployeeVO2 employee = this.employeeService.getById(id);
        ResultVO resultVO = new ResultVO<>(0, employee);
        return resultVO;
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody Employee employee) {
        System.out.println(employee);
        boolean update = this.employeeService.modifyById(employee);
        ResultVO resultVO = null;
        if(update == false)
            resultVO = new ResultVO<>(-1, null);
        else
            resultVO = new ResultVO<>(0, null);
        return resultVO;
    }
}

