package org.hustzl.controller;


import org.hustzl.entity.Job;
import org.hustzl.form.SearchForm;
import org.hustzl.service.JobService;
import org.hustzl.vo.PageVO;
import org.hustzl.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;

    @PostMapping("/save")
    public ResultVO save(@RequestBody Job job) {
        job.setAvailable(job.getMaxnum());
        boolean save = this.jobService.save(job);
        if(save == false) {
            ResultVO resultVO = new ResultVO<>();
            resultVO.setCode(-1);
            return resultVO;
        }
        else {
            ResultVO resultVO = new ResultVO<>();
            resultVO.setCode(0);
            return resultVO;
        }
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageVO list = this.jobService.list(page, size);
        ResultVO resultVO = new ResultVO(0 ,list);
        return resultVO;
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
//         System.out.println(searchForm);
        PageVO pageVO = this.jobService.search(searchForm);
        ResultVO resultVO = new ResultVO<>(0, pageVO);
        return resultVO;
    }

    @GetMapping("/findById/{id}")
    public ResultVO search(@PathVariable("id") Integer id) {
//        System.out.println(searchForm);
        Job job = this.jobService.getById(id);
        ResultVO resultVO = new ResultVO<>(0, job);
        return resultVO;
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody Job job) {
        System.out.println(job);
        boolean update = this.jobService.updateById(job);
        ResultVO resultVO = null;
        if(update == false)
            resultVO = new ResultVO<>(-1, null);
        else
            resultVO = new ResultVO<>(0, null);
        System.out.println(resultVO);
        return resultVO;
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        boolean remove = this.jobService.removeById(id);
        ResultVO resultVO = null;
        if(remove == false)
            resultVO = new ResultVO<>(-1, null);
        else
            resultVO = new ResultVO<>(0, null);
        return resultVO;
    }

    @GetMapping("/getavailableJpbByApartmentId/{apartmentId}")
    public ResultVO getJpbByApartmentId(@PathVariable("apartmentId") Integer apartmentId) {
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        List<Job> jobList = this.jobService.getavailableJobByApartmentId(apartmentId);
        ResultVO resultVO = new ResultVO<>(0, jobList);
        return resultVO;
    }

    @GetMapping("/getJpbByApartmentId/{apartmentId}")
    public ResultVO getJpbByApartmentId2(@PathVariable("apartmentId") Integer apartmentId) {
//        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
        List<Job> jobList = this.jobService.getJobByApartmentId(apartmentId);
        ResultVO resultVO = new ResultVO<>(0, jobList);
        return resultVO;
    }
}

