package org.hustzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hustzl.entity.*;
import org.hustzl.form.SearchForm;
import org.hustzl.mapper.ApartmentMapper;
import org.hustzl.mapper.EmployeeMapper;
import org.hustzl.mapper.JobMapper;
import org.hustzl.mapper.MoveoutMapper;
import org.hustzl.service.ApartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hustzl.vo.PageVO;
import org.hustzl.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@Service
public class ApartmentServiceImpl extends ServiceImpl<ApartmentMapper, Apartment> implements ApartmentService {
    @Autowired(required = false)
    private ApartmentMapper apartmentMapper;
    @Autowired(required = false)
    private JobMapper jobMapper;
    @Autowired(required = false)
    private EmployeeMapper employeeMapper;
    @Autowired(required = false)
    private MoveoutMapper moveoutMapper;

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Apartment> apartmentPage = new Page<>(page, size);
        Page<Apartment> resultPage = this.apartmentMapper.selectPage(apartmentPage, null);
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(resultPage.getRecords());
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        // 查询 + 分页
        Page<Apartment> apartmentPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Apartment> resultPage = null;
        if(searchForm.getValue().equals(""))
            resultPage = this.apartmentMapper.selectPage(apartmentPage, null);
        else {
            QueryWrapper<Apartment> queryWrapper = new QueryWrapper<>();
            queryWrapper.like(searchForm.getKey(), searchForm.getValue());
            resultPage = this.apartmentMapper.selectPage(apartmentPage, queryWrapper);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(resultPage.getRecords());
        return pageVO;
    }

    @Override
    public Boolean removeById(Integer id) {
        System.out.println("id ==" + id);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("apartment_id", id);
        List<Job> jobList = this.jobMapper.selectList(queryWrapper);
        System.out.println(jobList);
        System.out.println(jobList.size());
        List<Integer> jobIdlist = null;
        List<Employee> employeeList = null;
        if(jobList.size() != 0) {
            jobIdlist = jobList.stream().map(e -> e.getId()).collect(Collectors.toList());
            QueryWrapper queryWrapper1 = new QueryWrapper();
            queryWrapper1.in("job_id", jobIdlist);
            employeeList = this.employeeMapper.selectList(queryWrapper1);

            for(Employee employee:employeeList) {
                Job job = this.jobMapper.selectById(employee.getJobId());
                Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
                Moveout moveoutRec = new Moveout();
                moveoutRec.setEmployeeId(employee.getName());
                moveoutRec.setJobId(apartment.getName() + "/" + job.getName());
                moveoutRec.setReason("部门删除");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                moveoutRec.setCreateDate(format.format(new Date()));
                int save = this.moveoutMapper.insert(moveoutRec);
                if(save != 1)
                    return false;
                int delete = this.employeeMapper.deleteById(employee.getId());
                if(delete != 1)
                    return false;
            }

            int delete = this.jobMapper.delete(queryWrapper);
            if(delete != 1)
                return false;
        }
        System.out.println("WHat fuck1!!!");
        int delete = this.apartmentMapper.deleteById(id);
        if(delete != 1) {
            System.out.println("fuck!!!!like a shit!!!");
            return false;
        }
        return true;
    }
}
