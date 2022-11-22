package org.hustzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hustzl.entity.*;
import org.hustzl.form.SearchEmployeeForm;
import org.hustzl.mapper.*;
import org.hustzl.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hustzl.vo.EmployeeVO;
import org.hustzl.vo.EmployeeVO2;
import org.hustzl.vo.JobVO;
import org.hustzl.vo.PageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
    @Autowired(required = false)
    private EmployeeMapper employeeMapper;
    @Autowired(required = false)
    private JobMapper jobMapper;
    @Autowired(required = false)
    private ApartmentMapper apartmentMapper;
    @Autowired(required = false)
    private MoveoutMapper moveoutMapper;
    @Autowired(required = false)
    private MoveinMapper moveinMapper;
    @Autowired(required = false)
    private MovejobMapper movejobMapper;

    @Override
    public Boolean saveEmployee(Employee employee) {
        Movein movein = new Movein();
        Job job = this.jobMapper.selectById(employee.getJobId());
        Apartment apartment = this.apartmentMapper.selectById((job.getApartmentId()));
        movein.setEmployeeId(employee.getName());
        movein.setJobId(apartment.getName() + "/" + job.getName());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        movein.setCreateDate(format.format(new Date()));
        int save = this.moveinMapper.insert(movein);
        if(save != 1)
            return false;

        employee.setState("正式员工");
        employee.setCreateDate(format.format(new Date()));
        int insert = this.employeeMapper.insert(employee);
        if(insert != 1)
            return false;
        if(job.getAvailable() == 0)
            return false;
        job.setAvailable(job.getAvailable() - 1);
        int update = this.jobMapper.updateById(job);
        if(update != 1)
            return false;
        return true;
    }

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Employee> employeePage = new Page<>(page, size);
        Page<Employee> resultPage = this.employeeMapper.selectPage(employeePage, null);
        List<EmployeeVO> employeeVOList = new ArrayList<>();
        for (Employee employee: resultPage.getRecords()) {
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVO);
            Job job = this.jobMapper.selectById(employee.getJobId());
            Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
            employeeVO.setApartmentName(apartment.getName());
            employeeVO.setJobName(job.getName());
            employeeVOList.add(employeeVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(employeeVOList);
        return pageVO;
    }

    @Override
    public PageVO search(SearchEmployeeForm searchEmployeeForm) {
        System.out.println("456465454646546545555555555fuck test");
        System.out.println(searchEmployeeForm);
        Page<Employee> employeePage = new Page<>(searchEmployeeForm.getPage(), searchEmployeeForm.getSize());
        Page<Employee> resultPage = null;
        if(searchEmployeeForm.getApartmentId().equals("")) {
            resultPage = this.employeeMapper.selectPage(employeePage, null);
        }
        else {
            if(searchEmployeeForm.getJobId().equals("")) {
                QueryWrapper queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("apartment_id", searchEmployeeForm.getApartmentId());
                List<Job> jobList = this.jobMapper.selectList(queryWrapper);
                List<Integer> jobIdlist = jobList.stream().map(e -> e.getId()).collect(Collectors.toList());
                QueryWrapper queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.in("job_id", jobIdlist);
                resultPage = this.employeeMapper.selectPage(employeePage, queryWrapper1);
            }
            else {
                QueryWrapper queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("job_id", searchEmployeeForm.getJobId());
                resultPage = this.employeeMapper.selectPage(employeePage, queryWrapper);
            }
        }
        List<EmployeeVO> employeeVOList = new ArrayList<>();
        for (Employee employee: resultPage.getRecords()) {
            EmployeeVO employeeVO = new EmployeeVO();
            BeanUtils.copyProperties(employee, employeeVO);
            Job job = this.jobMapper.selectById(employee.getJobId());
            Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
            employeeVO.setApartmentName(apartment.getName());
            employeeVO.setJobName(job.getName());
            employeeVOList.add(employeeVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(employeeVOList);
        return pageVO;
    }

    @Override
    public boolean deleteById(Integer id) {
        Employee employee = this.employeeMapper.selectById(id);

        Job job = this.jobMapper.selectById(employee.getJobId());
        Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
        Moveout moveoutRec = new Moveout();
        moveoutRec.setEmployeeId(employee.getName());
        moveoutRec.setJobId(apartment.getName() + "/" + job.getName());
        moveoutRec.setReason("其他原因");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        moveoutRec.setCreateDate(format.format(new Date()));
        int save = this.moveoutMapper.insert(moveoutRec);
        if(save != 1)
            return false;

        job.setAvailable(job.getAvailable() + 1);
        int update = this.jobMapper.updateById(job);
        if(update != 1)
            return false;
        int delete = this.employeeMapper.deleteById(id);
        if(delete != 1)
            return false;
        return true;
    }

    @Override
    public EmployeeVO2 getById(Integer id) {
        Employee employee = this.employeeMapper.selectById(id);
        EmployeeVO2 employeeVO = new EmployeeVO2();
        BeanUtils.copyProperties(employee, employeeVO);
        Job job = this.jobMapper.selectById(employee.getJobId());
        employeeVO.setApartmentId(job.getApartmentId());
        return employeeVO;
    }

    @Override
    public Boolean modifyById(Employee employee) {
        Employee oldemployee = this.employeeMapper.selectById(employee.getId());
        Job oldJob = this.jobMapper.selectById(oldemployee.getJobId());
        Job newJob = this.jobMapper.selectById(employee.getJobId());

        if(oldJob.getId() != newJob.getId()) {
            Apartment oldApart = this.apartmentMapper.selectById(oldJob.getApartmentId());
            Apartment newApart = this.apartmentMapper.selectById(newJob.getApartmentId());
            Movejob movejob = new Movejob();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            movejob.setCreateDate(format.format(new Date()));
            movejob.setEmployeeId(employee.getName());
            movejob.setPreJobId(oldApart.getName() + "/" + oldJob.getName());
            movejob.setNextJobId(newApart.getName() + "/" + newJob.getName());
            movejob.setReason("工作需要");
            int save = this.movejobMapper.insert(movejob);
            if(save != 1)
                return false;
            oldJob.setAvailable(oldJob.getAvailable() + 1);
            newJob.setAvailable(newJob.getAvailable() - 1);
        }

        int update1 = this.jobMapper.updateById(oldJob);
        if(update1 != 1)
            return false;

        int update2 = this.jobMapper.updateById(newJob);
        if(update2 != 1)
            return false;

        int update = this.employeeMapper.updateById(employee);
        if(update != 1)
            return false;
        return true;
    }
}