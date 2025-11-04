package com.orange.context;

/**
 * 多线程环境中存储和获取当前线程的ID。
 * 通过ThreadLocal实现每个线程独立存储，提供设置、获取和移除当前线程ID的方法。
 * 这通常用于跟踪请求上下文或用户会话等场景。
 * public
 */
public class BaseContext {
 //   public static  这种设计将BaseContext变成了一个工具类，专门用于处理线程本地存储的操作
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }
    public static Long getCurrentId() {
        return threadLocal.get();
    }
    public static void removeCurrentId() {
        threadLocal.remove();
    }

}
