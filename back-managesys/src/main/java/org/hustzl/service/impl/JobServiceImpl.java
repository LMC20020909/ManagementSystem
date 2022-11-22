package org.hustzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hustzl.entity.Apartment;
import org.hustzl.entity.Employee;
import org.hustzl.entity.Job;
import org.hustzl.entity.Moveout;
import org.hustzl.form.SearchForm;
import org.hustzl.mapper.ApartmentMapper;
import org.hustzl.mapper.EmployeeMapper;
import org.hustzl.mapper.JobMapper;
import org.hustzl.mapper.MoveoutMapper;
import org.hustzl.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Autowired(required = false)
    private JobMapper jobMapper;
    @Autowired(required = false)
    private ApartmentMapper apartmentMapper;
    @Autowired(required = false)
    private EmployeeMapper employeeMapper;
    @Autowired(required = false)
    private MoveoutMapper moveoutMapper;

    @Override
    public PageVO list(Integer page, Integer size) {
        Page<Job> jobPage = new Page<>(page, size);
        Page<Job> resultPage = this.jobMapper.selectPage(jobPage, null);
        List<JobVO> jobVOList = new ArrayList<>();
        for (Job job: resultPage.getRecords()) {
            JobVO jobVO = new JobVO();
            BeanUtils.copyProperties(job, jobVO);
            Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
            jobVO.setApartmentName(apartment.getName());
            jobVOList.add(jobVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(jobVOList);
        return pageVO;
    }

    @Override
    public PageVO search(SearchForm searchForm) {
        Page<Job> jobPage = new Page<>(searchForm.getPage(), searchForm.getSize());
        Page<Job> resultPage = null;
        if (searchForm.getValue().equals("")) {
            resultPage = this.jobMapper.selectPage(jobPage, null);
        }
        else {
            System.out.println(searchForm.getKey());
            QueryWrapper<Job> queryWrapper = new QueryWrapper<>();
            if(searchForm.getKey().equals("apartmentName")) {
                System.out.println("lover!");
                QueryWrapper<Apartment> apartmentQuerywrapper = new QueryWrapper<>();
                apartmentQuerywrapper.like("name", searchForm.getValue());
                List<Apartment> apartmentLikelist = this.apartmentMapper.selectList(apartmentQuerywrapper);
                List<Integer> apartmentIdlist = apartmentLikelist.stream().map(e -> e.getId()).collect(Collectors.toList());
//                System.out.println(apartmentIdlist);
                queryWrapper.in("apartment_id", apartmentIdlist);
//                System.out.println(queryWrapper);
//                List<Job> result = this.jobMapper.selectList(queryWrapper);
//                System.out.println(result);
                resultPage = this.jobMapper.selectPage(jobPage, queryWrapper);
            }
            else {
                queryWrapper.like(searchForm.getKey(), searchForm.getValue());
                resultPage = this.jobMapper.selectPage(jobPage, queryWrapper);
            }
        }
        List<JobVO> jobVOList = new ArrayList<>();
        for (Job job: resultPage.getRecords()) {
            JobVO jobVO = new JobVO();
            BeanUtils.copyProperties(job, jobVO);
            Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
            jobVO.setApartmentName(apartment.getName());
            jobVOList.add(jobVO);
        }
        PageVO pageVO = new PageVO();
        pageVO.setTotal(resultPage.getTotal());
        pageVO.setData(jobVOList);
        return pageVO;
    }

    @Override
    public List<Job> getavailableJobByApartmentId(Integer apartmentId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apartment_id", apartmentId);
        queryWrapper.ge("available", 1);
        List<Job> jobList = this.jobMapper.selectList(queryWrapper);
        return jobList;
    }

    @Override
    public List<Job> getJobByApartmentId(Integer apartmentId) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apartment_id", apartmentId);
        List<Job> jobList = this.jobMapper.selectList(queryWrapper);
        return jobList;
    }

    @Override
    public boolean removeById(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("job_id", id);
        List<Employee> employeeList = this.employeeMapper.selectList(queryWrapper);

        if(employeeList.size() != 0) {
            for(Employee employee:employeeList) {
                Job job = this.jobMapper.selectById(employee.getJobId());
                Apartment apartment = this.apartmentMapper.selectById(job.getApartmentId());
                Moveout moveoutRec = new Moveout();
                moveoutRec.setEmployeeId(employee.getName());
                moveoutRec.setJobId(apartment.getName() + "/" + job.getName());
                moveoutRec.setReason("岗位删除");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                moveoutRec.setCreateDate(format.format(new Date()));
                int save = this.moveoutMapper.insert(moveoutRec);
                if(save != 1)
                    return false;
                int delete = this.employeeMapper.deleteById(employee.getId());
                if(delete != 1)
                    return false;
            }
        }

        int delete = this.jobMapper.deleteById(id);
        if(delete != 1)
            return false;

        return true;
    }
}
