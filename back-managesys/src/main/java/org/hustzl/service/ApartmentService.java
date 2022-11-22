package org.hustzl.service;

import org.hustzl.entity.Apartment;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hustzl.form.SearchForm;
import org.hustzl.vo.PageVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
public interface ApartmentService extends IService<Apartment> {
    public PageVO list(Integer page, Integer size);
    public PageVO search(SearchForm searchForm);
    Boolean removeById(Integer id);
}
