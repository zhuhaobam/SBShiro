package com.bamboobam.sbshiro.config.repositoryconfig.ormconfig;

import java.lang.reflect.*;

/**
 * 数据仓库动态代理-代理接口,实际转调用实现类
 */
@SuppressWarnings("unchecked")
public class ProxyRepository implements InvocationHandler {

    private Class<?> repositorydata;

    public ProxyRepository(Class repositorydata) {
        this.repositorydata = repositorydata;
    }

    /**
     * @param proxy  动态代理对象
     * @param method 正在执行方法
     * @param args   目标传入参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {


        Type[] interfacesTypes = proxy.getClass().getGenericInterfaces();
        String interfacename = interfacesTypes[0].getTypeName();//1.自定义的Repository接口
        Object entityclazz = null;//2.操作的bean
        Class<?> clazz = Class.forName(interfacename);
        Type clazzGenericInterface = clazz.getGenericInterfaces()[0];
        if (clazzGenericInterface instanceof ParameterizedType) {
            Type[] types = ((ParameterizedType) clazzGenericInterface).getActualTypeArguments();
            //types[0] class com.bamboobam.sbshiro.entity.Permission
            //types[1]  class java.lang.Long
            // System.out.println(types[0]);
            // System.out.println(types[1]);
            entityclazz = Class.forName(types[0].getTypeName()).newInstance();
        }
        //System.out.println(entityclazz);
        //执行方法
        /*  System.out.println("接口：" + interfacename);//com.bamboobam.sbshiro.persistance.PermissionRepository
        System.out.println("数据源数据：" + repositorydata.getName());//com.bamboobam.sbshiro.repositoryconfig.DataSource
        System.out.println("method name:" + method.getName());//findOne
        System.out.println("method args[0]:" + args[0]);//1*/
        //3.StandardCrudRepository标准类的构造方法，注入对应的bean
        Constructor con = repositorydata.getConstructor(Object.class);//获取到构造函数
        Object dataObject = con.newInstance(entityclazz);//构造注入
        //4.StandardCrudRepository标准类的基准实现
        Method myselfmethod = repositorydata.getMethod("repositoryProxyTransfer", new Class<?>[]{String.class, Object[].class}); //方法名和对应的参数类型  
        return myselfmethod.invoke(dataObject, method.getName(), args);//调用得到的上边的方法
    }

    public static <T> T newMapperProxy(Class<T> mapperInterface, Class<?> repositorydata) {
        ClassLoader classLoader = mapperInterface.getClassLoader();
        Class<?>[] interfaces = new Class[]{mapperInterface};
        ProxyRepository proxy = new ProxyRepository(repositorydata);
        return (T) Proxy.newProxyInstance(classLoader, interfaces, proxy);
    }
}
