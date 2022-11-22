package org.hustzl.service;

import org.hustzl.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hustzl.form.SearchForm;
import org.hustzl.vo.PageVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
public interface JobService extends IService<Job> {
    public PageVO list(Integer page, Integer size);
    public PageVO search(SearchForm searchForm);
    public List<Job> getavailableJobByApartmentId(Integer apartmentId);
    public List<Job> getJobByApartmentId(Integer apartmentId);
    public boolean removeById(Integer id);
}
