package com.orange.controller.admin;

import com.orange.result.Result;
import com.orange.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Slf4j
public class CommonController {
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        log.info("收到上传文件{}",file);
         file.getName();
         String OriginalFilename = file.getOriginalFilename();
         //获取拓展名
         String extname = OriginalFilename.substring(OriginalFilename.lastIndexOf("."));
         //修改唯一文件名
        String originalName = UUID.randomUUID().toString() + extname;
        //当 Java 编译器遇到 object + string 这样的表达式时，
        // 它会自动将非字符串对象转换为字符串。这个转换过程是通过调用对象的 toString() 方法实现的。
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        //年月日好管理
        String objectName = dir + "/"  + originalName;
        String filePath = aliyunOSSOperator.upload(file.getBytes(),objectName);
        log.info("图片URL:{}",filePath);
        return Result.success(filePath);
    }
}
