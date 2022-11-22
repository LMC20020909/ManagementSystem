package org.hustzl.controller;


import org.hustzl.entity.Apartment;
import org.hustzl.form.SearchForm;
import org.hustzl.service.ApartmentService;
import org.hustzl.vo.PageVO;
import org.hustzl.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/apartment")
public class ApartmentController {
    @Autowired
    private ApartmentService apartmentService;

    @PostMapping("/save")
    public ResultVO save(@RequestBody Apartment apartment) {
        boolean save = this.apartmentService.save(apartment);
        if(save == false) {
            ResultVO resultVO = new ResultVO<>();
            resultVO.setCode(-1);
            return resultVO;
        }
        else { // 部门负责人与院长分离，已经处理完
            ResultVO resultVO = new ResultVO<>();
            resultVO.setCode(0);
            return resultVO;
        }
    }

    @GetMapping("/list/{page}/{size}")
    public ResultVO list(@PathVariable("page") Integer page, @PathVariable("size") Integer size) {
        PageVO list = this.apartmentService.list(page, size);
        ResultVO resultVO = new ResultVO(0 ,list);
        return resultVO;
    }

    @GetMapping("/list")
    public ResultVO list() {
        List<Apartment> list = this.apartmentService.list();
        ResultVO resultVO = new ResultVO(0 ,list);
        return resultVO;
    }

    @GetMapping("/search")
    public ResultVO search(SearchForm searchForm) {
        System.out.println(searchForm);
        PageVO pageVO = this.apartmentService.search(searchForm);
        ResultVO resultVO = new ResultVO<>(0, pageVO);
        return resultVO;
    }

    @GetMapping("/findById/{id}")
    public ResultVO findById(@PathVariable("id") Integer id) {
        Apartment apartment = this.apartmentService.getById(id);
        ResultVO resultVO = new ResultVO<>(0, apartment);
        return resultVO;
    }

    @PutMapping("/update")
    public ResultVO update(@RequestBody Apartment apartment) {
        boolean update = this.apartmentService.updateById(apartment);
        ResultVO resultVO = null;
        if(update == false)
            resultVO = new ResultVO<>(-1, null);
        else
            resultVO = new ResultVO<>(0, null);
        return resultVO;
    }

    @DeleteMapping("/deleteById/{id}")
    public ResultVO deleteById(@PathVariable("id") Integer id) {
        boolean remove = this.apartmentService.removeById(id);
        ResultVO resultVO = null;
        if(remove == false)
            resultVO = new ResultVO<>(-1, null);
        else
            resultVO = new ResultVO<>(0, null);
        return resultVO;
    }
}

