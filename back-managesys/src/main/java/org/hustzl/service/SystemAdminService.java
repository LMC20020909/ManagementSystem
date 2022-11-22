package org.hustzl.service;

import org.hustzl.entity.SystemAdmin;
import com.baomidou.mybatisplus.extension.service.IService;
import org.hustzl.form.RuleForm;
import org.hustzl.vo.ResultVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
public interface SystemAdminService extends IService<SystemAdmin> {
    public ResultVO login(RuleForm ruleForm);
}
