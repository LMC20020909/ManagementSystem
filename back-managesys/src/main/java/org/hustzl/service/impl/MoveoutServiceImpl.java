package org.hustzl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.hustzl.Utils.ExportExcel;
import org.hustzl.entity.Movein;
import org.hustzl.entity.Moveout;
import org.hustzl.mapper.MoveoutMapper;
import org.hustzl.service.MoveoutService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@Service
public class MoveoutServiceImpl extends ServiceImpl<MoveoutMapper, Moveout> implements MoveoutService {
    @Autowired(required = false)
    private MoveoutMapper moveoutMapper;

    @Override
    public void exportTest(OutputStream out, String excelTitle) throws IOException {
        // 定义列标 就是一个Excel的标题而已 下面有图介绍
        String[] rowsName = new String[]{"id", "姓名", "岗位", "离职原因", "离职时间"};
        // 创建导出数据集合 后续会将dataList中的数据写到Excel
        List<Object[]> dataList = new ArrayList<>();
        // 从数据库查询用户列表
        QueryWrapper queryWrapper = new QueryWrapper();
        List<Moveout> moveoutList = moveoutMapper.selectList(queryWrapper);
        // 将用户列表信息封装到一个Object数组
        // 我这里封装Object数组 是为了方便后续代码复用,不会将对象类型写死
        // 当然也可以在下一层使用反射来做,太麻烦了 还是这样转一下吧
        for(Moveout moveout: moveoutList){
            //将数据库查到的每条数据 循环封装到Object[]
            Object[] objs = new Object[]{moveout.getId(), moveout.getEmployeeId(), moveout.getJobId(), moveout.getReason(), moveout.getCreateDate()};
            //将转换好的数据 存入dataList
            dataList.add(objs);
        }
        // 创建ExportExcel工具类对象 通过构造方法赋值
        ExportExcel ex = new ExportExcel(excelTitle, rowsName, dataList);
        try {
            // 调用生成Excel的方法,将数据通过输出流写出
            ex.export(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
