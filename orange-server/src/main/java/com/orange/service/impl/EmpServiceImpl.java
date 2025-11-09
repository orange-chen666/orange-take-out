package com.orange.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.orange.constant.JwtClaimsConstant;
import com.orange.dto.EmpDTO;
import com.orange.dto.EmpLoginDTO;
import com.orange.dto.EmpPageQueryDTO;
import com.orange.dto.EmpPwdDTO;
import com.orange.entity.Emp;
import com.orange.mapper.EmpMapper;
import com.orange.properties.JwtProperties;
import com.orange.result.PageResult;
import com.orange.service.EmpService;
import com.orange.utils.JwtUtil;
import com.orange.vo.EmpLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public PageResult page(EmpPageQueryDTO empPageQueryDTO) {
//        Page a = PageHelper.startPage(empPageQueryDTO);//不是得传页码，还有每页展示数量
//        System.out.println(a);
        //Pge这东西得看看；
        PageHelper.startPage(empPageQueryDTO.getPage(),empPageQueryDTO.getPageSize());
        Page<Emp> page = empMapper.pageQuery(empPageQueryDTO);
        Long total = page.getTotal();
        List<Emp> result = page.getResult();
        return new PageResult(total,result);
    }

    @Override
    public void add(EmpDTO empDTO) {
        Emp emp = new Emp();
        BeanUtils.copyProperties(empDTO,emp);// 正确：从 empDTO 复制到 emp
//        emp.setCreateTime(LocalDateTime.now());有了公共字段填充不需要了
//        emp.setUpdateTime(LocalDateTime.now());
        emp.setPassword("123456");
        emp.setStatus(1);
        empMapper.add(emp);
    }

    @Override
    public void editPwd(EmpPwdDTO empPwdDTO) {

    }

    @Override
    public EmpLoginVO login(EmpLoginDTO empLoginDTO) {
        String password = empLoginDTO.getPassword();
        String username = empLoginDTO.getUsername();
        Emp emp = empMapper.login(username);
        if (emp == null) {
            log.info("无该用户名");//以后可以抛异常
            throw new RuntimeException("员工账号错误");
        }
        if (!password.equals(emp.getPassword())) {
            log.info("密码错误");
            throw new RuntimeException("密码错误");
        }
        if (emp.getStatus() == 0) {
            log.info("员工被锁定");
            throw new RuntimeException("员工被锁定");
        }
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,emp.getId());//实在是emp_id太常用了改成常量吧
        claims.put(JwtClaimsConstant.USER_ID,emp.getUsername());


        String token = JwtUtil.creatJWT(jwtProperties.getAdminSecretKey(), claims, jwtProperties.getAdminTtl());
        log.info("生成token{}:",token);
//        EmpLoginVO empLoginVO = new EmpLoginVO();
//        empLoginVO.setId(emp.getId());
//        empLoginVO.setName(emp.getName());
//        empLoginVO.setUserName(emp.getUsername());
//        empLoginVO.setToken(token);
//用@Builder 使用链式调用来创建对象实例,好像更简洁了
        EmpLoginVO empLoginVO = EmpLoginVO.builder().
                id(emp.getId()).
                name(emp.getName()).
                userName(emp.getUsername()).
                token(token).
                build();
        return empLoginVO;
    }

    @Override
    public void startOrStop(Integer status,Long id) {
        Emp emp = new Emp();
        emp.setStatus(status);
        emp.setId(id);
        empMapper.update(emp);
    }

    @Override
    public void update(EmpDTO empDTO) {
        Emp emp = new Emp();
        BeanUtils.copyProperties(empDTO,emp);
        log.info("要修改的员工信息：{}",emp);
        empMapper.update(emp);
    }

    @Override
    public void deleteById(Long id) {
        empMapper.deleteById(id);
    }

    @Override
    public Emp queryById(Long id) {

        return empMapper.queryById(id);
    }
}
