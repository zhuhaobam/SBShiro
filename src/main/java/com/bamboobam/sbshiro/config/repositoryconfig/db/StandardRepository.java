package com.bamboobam.sbshiro.config.repositoryconfig.db;

import com.bamboobam.sbshiro.config.reposeconfig.CheckException;
import com.bamboobam.sbshiro.config.repositoryconfig.ormconfig.AbstractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Orm标准实现
 *
 * @version 1.0
 */
public class StandardRepository extends AbstractRepository {

    private static final Logger logger = LoggerFactory.getLogger(StandardRepository.class);


    public static Bmdb BMDB = Bmdb.getInstance();


    private Object typeobj;

    public StandardRepository() {
    }

    public StandardRepository(Object baseEntity) {
        this.typeobj = baseEntity;
    }


    @Override
    public Object repositoryProxyTransfer(String method, Object[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        StandardRepository standardCrudRepository = new StandardRepository(typeobj);//这里还是要把注入的构造对象传输进来
        Class<?> paramcls = null;
        if (null != args && args.length == 1) {
            if (args[0].getClass() == Long.class) {
                paramcls = Long.class;
            } else if (args[0].getClass() == String.class) {
                paramcls = String.class;
            } else {
                paramcls = Object.class;
            }
        } else if (null != args && args.length > 1) {
            paramcls = Object[].class;
        }
        Method dataMethod2 = null != paramcls ? standardCrudRepository.getClass().getMethod(method, paramcls) : standardCrudRepository.getClass().getMethod(method);
        Object tobj = null != args ? dataMethod2.invoke(standardCrudRepository, args) : dataMethod2.invoke(standardCrudRepository);
       /* if (null != args) {
            logger.info(String.format("Myself_Bmdb_HQL:%s-%s\nI_ARGS:%s\nR_JSON:%s", method, refBeanName(),
                    null != args ? Arrays.asList(args) : "", null != tobj ? tobj.toString() : ""));
        } else {
            logger.info(String.format("Myself_Bmdb_HQL:%s-%s\nR_JSON:%s", method, refBeanName(),
                    null != tobj ? tobj.toString() : ""));
        }*/
        return tobj;
    }

    private String refBeanName() {
        String entityPackage = typeobj.getClass().getName();
        return entityPackage.substring(entityPackage.lastIndexOf(".") + 1);
    }


    @Override
    public Object saveOrUpdate(Object entity) {
        Long id = 0L;
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true); // 设置些属性是可以访问的
            id = (Long) field.get(entity);
        } catch (NoSuchFieldException e) {
            throw new CheckException("没有id属性");
        } catch (IllegalAccessException e) {
            throw new CheckException("没有访问权限");
        }
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        String entityName = refBeanName();
        BMDB.Insert(entityName, id, entity);
        return BMDB.SelectById(entityName, id);
    }

    @Override
    public List<Object> findAll() {
        String entityName = refBeanName();
        List<Object> objectList = new ArrayList<>();
        Map<Long, Object> objectMap = BMDB.SelectTable(entityName);
        for (Map.Entry<Long, Object> entry : objectMap.entrySet()) {
            objectList.add(entry.getValue());
        }
        return objectList;
    }

    @Override
    public Object findOne(Long id) {
        if (id.compareTo(1L) == -1) {
            throw new CheckException("Long类型id参数最小为1");
        }
        String entityName = refBeanName();
        return BMDB.SelectById(entityName, id);
    }


    @Override
    public Object findByName(String name) {
        if (null == name && name.equals(" ")) {
            throw new CheckException("String类型name参数不为空");
        }
        String entityName = refBeanName();
        return BMDB.SelectByName(entityName, name);
    }

    @Override
    public List<Object> findByNameLike(String name) {
        if (null == name && name.equals(" ")) {
            throw new CheckException("String类型name参数不为空");
        }
        String entityName = refBeanName();
        return BMDB.findByNameLike(entityName, name);
    }


}
