package org.hustzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.hustzl.entity.SystemAdmin;
import org.hustzl.form.RuleForm;
import org.hustzl.mapper.SystemAdminMapper;
import org.hustzl.service.SystemAdminService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.hustzl.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@Service
public class SystemAdminServiceImpl extends ServiceImpl<SystemAdminMapper, SystemAdmin> implements SystemAdminService {
    @Autowired(required = false)
    private SystemAdminMapper systemAdminMapper;

    @Override
    public ResultVO login(RuleForm ruleForm) {
        QueryWrapper<SystemAdmin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", ruleForm.getUsername());
        SystemAdmin systemAdmin = this.systemAdminMapper.selectOne(queryWrapper);
        //System.out.println(ruleForm.getUsername());
        ResultVO resultVO = new ResultVO<>();
        if(systemAdmin == null)
            resultVO.setCode(-1);
        else {
            if(!systemAdmin.getPassword().equals(ruleForm.getPassword()))
                resultVO.setCode(-2);
            else {
                resultVO.setCode(0);
                resultVO.setData(systemAdmin);
            }
        }
        return resultVO;
    }
}
