package com.orange.result;


import lombok.Data;

import java.io.Serializable;

//implements Serializable 后面实现
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    //Object 考虑用 T吗？
    //static <T>
    //为什么需要声明<T>
    //作用域问题：<T>是在方法级别声明的泛型参数，表示这个方法内部会使用到这个类型参数。
    //类型推断：编译器可以根据传入的参数自动推断出T的具体类型。
    //独立于类：即使Result类本身已经是泛型类了，在静态方法中仍需要重新声明泛型参数，因为静态方法不能直接访问类级别的泛型参数。
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = "success";
        return result;
    }
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();//T可以省略，使用钻石操作符<>
        result.code = 1 ;
        result.data = data;
        result.msg = "success";
        return result;
    }
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 0 ;
        result.msg = msg;
        return result;
    }

}
//还要讲究Result类
//        Result类实现Serializable接口的主要原因是为了支持对象的序列化，这在分布式系统和网络传输中非常重要。以下是几个关键点：
//        网络传输：在Web应用中，通常需要通过网络传输数据对象。实现Serializable接口允许对象被转换为字节流进行传输，然后在接收端重新构造对象。
//        会话管理：在Web应用中，有时需要将对象存储在HTTP会话中。当会话需要持久化或在集群环境中共享时，存储在会话中的对象必须是可序列化的。
//        缓存机制：许多缓存框架要求缓存的对象必须实现Serializable接口，以便在需要时可以将对象写入磁盘或在网络中传输。
//        远程方法调用(RMI)：如果应用程序使用Java的RMI机制，那么在远程方法调用中传递的对象必须实现Serializable接口。
