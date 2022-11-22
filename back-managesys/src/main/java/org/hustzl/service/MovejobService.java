package org.hustzl.service;

import org.hustzl.entity.Movejob;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;
import java.io.OutputStream;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
public interface MovejobService extends IService<Movejob> {
    void exportTest(OutputStream out, String excelTitle) throws IOException;
}
