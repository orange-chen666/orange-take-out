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

    @Autowired
    private EmpServiceImpl empService;
    /**
     * 分页查询
     * 根据名字模糊查询
     */
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
    /**
     * 启用、禁用员工账号
     */
    @PostMapping("/status/{status}")//暂时不明白id的意思但是@PathVariable是根据url获取状态
    public Result startOrStop (@PathVariable Integer status,Long id) {
        log.info("根据id启用、禁用员工账号：{}",id);
        empService.startOrStop(status,id);
        return Result.success();
    }
    /**
     *编辑员工信息
     */
    @PutMapping
    public Result update(@RequestBody EmpDTO empDTO) {
        log.info("编辑员工信息:{}",empDTO);
        empService.update(empDTO);
        return Result.success();
    }
    /**
     * 根据id删除员工
     */
    @DeleteMapping("/delete")
    public Result deleteById(Long id) {
        log.info("被删除id：{}",id);
        empService.deleteById(id);
        return Result.success();
    }
    /**
     * 根据Id查询
     */
    @GetMapping("/{id}")
    public Result<Emp> queryById(@PathVariable Long id) {
        log.info("id:",id);
        Emp emp = empService.queryById(id);
        return Result.success(emp);

    }
}
