package org.hustzl.controller;


import org.apache.tools.ant.util.DateUtils;
import org.hustzl.service.MovejobService;
import org.hustzl.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuechuhaoxi020609
 * @since 2022-06-29
 */
@Controller
@RequestMapping("/movejob")
public class MovejobController {
    @Autowired
    private MovejobService movejobService;

    @GetMapping("/report")
    public ResultVO prodTest(HttpServletResponse response) throws IOException {
//        System.out.println("fuck!!!!!!!!!!!!!!!");
        response.reset();
        // 创建导出文件名称 当前日期
        String fileName = DateUtils.format(new Date(),"yyyyMMdd-HHmmss") + "-" + "员工调动报表" + ".xls";
        System.out.println(fileName);
        // 设置返回的消息头和返回值类型 并设置编码 不设置编码文件名为中文的话 不会显示
        // 当设置成如下返回值时，浏览器才会执行下载文件动作
        // 避免中文乱码
        response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        response.setHeader("Connection", "close");
        // 设置传输的类型
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Transfer-Encoding", "chunked");
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 创建输出流，调用service中exportTest方法，参数：输出流 标题名
        movejobService.exportTest(response.getOutputStream(), "员工调动报表");
        return null;
    }
}

