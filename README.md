


# 项目为动手的乐趣

##  DB部分

### 第一版DB图
![Orm1](https://raw.githubusercontent.com/zhuhaobam/SBShiro/master/Orm.png)
### 第二版DB图
![Orm2](https://raw.githubusercontent.com/zhuhaobam/SBShiro/master/Orm2.png)
### DB实现和扩展
1. Bmdb【操作部分--1版》entity耦合--2版》（优化）虚拟表】
2. ProxyRepository代理【代理实现类可自定义实现和反射调用方法固定（抽象类扩展，实现了一个标准版StandardRepository）】
3. BeanIOCFactory Bean映射【当前javaconfig】
4. UserRepository 【ProxyRepository代理实现自定义findByName、findByNameLike方法】
5. 详见repositoryconfig目录

## Swagger2部分
1. 2.9.2版本详见pom.xml
2. 基本配置详见apidocconfig、reposeconfig目录

## Shiro部分
1. 详见shiroconfig目录
2. 详见LoginController和Constant类