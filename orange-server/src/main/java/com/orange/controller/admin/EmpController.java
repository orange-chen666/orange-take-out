package com.orange.controller.admin;

import com.orange.dto.EmpPageQueryDTO;
import com.orange.result.PageResult;
import com.orange.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController

public class EmpController {
    @GetMapping("/admin/employee/page")
    public Result<PageResult> page(EmpPageQueryDTO empPageQueryDTO) {
        log.info("分页查询,{}",empPageQueryDTO);

        Result.success();
    }
}
