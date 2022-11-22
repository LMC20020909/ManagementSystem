package org.hustzl.service;

import org.hustzl.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hustzl.form.SearchEmployeeForm;
import org.hustzl.vo.EmployeeVO;
import org.hustzl.vo.EmployeeVO2;
import org.hustzl.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
public interface EmployeeService extends IService<Employee> {
    public Boolean saveEmployee(Employee employee);
    public PageVO list(Integer page, Integer size);
    public PageVO search(SearchEmployeeForm searchEmployeeForm);
    public boolean deleteById(Integer id);
    public EmployeeVO2 getById(Integer id);
    public Boolean modifyById(Employee employee);
}
