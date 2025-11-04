package com.orange.controller.admin;

import com.orange.dto.EmpDTO;
import com.orange.dto.EmpLoginDTO;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.dto.EmpPwdDTO;
import com.orange.entity.Emp;
import com.orange.result.PageResult;
import com.orange.result.Result;
import com.orange.service.impl.EmpServiceImpl;
import com.orange.vo.EmpLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin/emp")

public class EmpController {
    /**
     * 分页查询,根据名字模糊查询
     */
    @Autowired
    private EmpServiceImpl empService;
    @GetMapping("/page")
    public Result<PageResult> page(EmpPageQueryDTO empPageQueryDTO) {
        log.info("分页查询,{}",empPageQueryDTO);
        PageResult pageResult = empService.page(empPageQueryDTO);

        return Result.success(pageResult);
    }
    /**
     * 添加员工功能
     */
    @PostMapping()
    public Result add(@RequestBody EmpDTO empDTO) {
        log.info("添加员工{}",empDTO);
        empService.add(empDTO);
        return  Result.success();
    }
    /**
     * 员工登录
     */
    @PostMapping("/login")
    public Result<EmpLoginVO> login(@RequestBody EmpLoginDTO empLoginDTO) {
        log.info("员工登录{}",empLoginDTO);
         EmpLoginVO empLoginVO = empService.login(empLoginDTO);
        return Result.success(empLoginVO);

    }
}
