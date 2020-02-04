package com.lagou.plugin;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.*;

import java.sql.Connection;
import java.util.Properties;
@Intercepts({
        @Signature(type = StatementHandler.class,method ="prepare",args = {Connection.class,Integer.class})
})
public class MyPlugin implements Interceptor {
    /**
     * 拦截方法：只要被拦截的目标对象的目标方法就被会执行，每次都是会执行intercept
     * @param invocation
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("对方法进行了增强...");
        return invocation.proceed();
    }

    /**
     * 主要为了把当前的拦截器生成代理存到拦截器链中
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        Object wrap = Plugin.wrap(target, this);
        return wrap;
    }

    /**
     * 获取配置文件的参数
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        System.out.println("获取配置文件的参数是："+properties);
    }
}
