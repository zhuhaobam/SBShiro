package com.bamboobam.sbshiro.config.repositoryconfig.db;

import com.bamboobam.sbshiro.entity.Permission;
import com.bamboobam.sbshiro.entity.Role;
import com.bamboobam.sbshiro.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Bmdb【Bamboo Map Database】
 * 自定义私有数据库
 *
 * @version 1.0
 */
public class Bmdb {

    private static final Logger logger = LoggerFactory.getLogger(Bmdb.class);

    /*---------------------------数据库单例模式调用-----------------------------*/

    private static final class SingHolder {
        private static final Bmdb BMDB = new Bmdb();
    }

    private Bmdb() {
    }


    public static Bmdb getInstance() {
        return SingHolder.BMDB;
    }

    /*---------------------------数据存储结构和数据初始化-----------------------------*/

    private static Map<Long, Object> Permission_Table = new HashMap<>();
    private static Map<Long, Object> Role_Table = new HashMap<>();
    private static Map<Long, Object> User_Table = new HashMap<>();

    static {
        Permission p1 = new Permission(1L, "pname1", "/purl1");
        Permission p2 = new Permission(2L, "pname2", "/purl2");
        Permission p3 = new Permission(3L, "pname3", "/purl3");
        Permission_Table.put(1L, p1);
        Permission_Table.put(2L, p2);
        Permission_Table.put(3L, p3);
        //-------
        Role r1 = new Role(1L, "role1", Arrays.asList(p1, p2));
        Role r2 = new Role(2L, "role2", Arrays.asList(p3));
        Role_Table.put(1L, r1);
        Role_Table.put(2L, r2);
        //-------
        User u1 = new User(1L, "admin", "123456", Arrays.asList(r1));
        User u2 = new User(2L, "lala", "123456", Arrays.asList(r2));
        User_Table.put(1L, u1);
        User_Table.put(2L, u2);

    }

    /*---------------------------Myself_Bmdb_HQL（屏蔽内部数据结构操作）-----------------------------*/

    public static Map<Long, Object> SelectTable(String tablename) {
        Map<Long, Object> _Result_Table = new HashMap<>();
        if (tablename.equals("Permission")) {
            _Result_Table = Permission_Table;
        } else if (tablename.equals("Role")) {
            _Result_Table = Role_Table;
        } else if (tablename.equals("User")) {
            _Result_Table = User_Table;
        }
        return _Result_Table;
    }


    public static Map<Long, Object> Insert(String tablename, Long id, Object entity) {
        Map<Long, Object> _Result_Table = null;
        if (tablename.equals("Permission")) {
            Permission_Table.put(id, entity);
            Map<Long, Object> _Temp_User_Table = new HashMap<>();
            Map<Long, Object> _Temp_Role_Table = new HashMap<>();
            _Temp_User_Table.putAll(User_Table);
            _Temp_Role_Table.putAll(Role_Table);
            //先更新直属上级
            for (Map.Entry<Long, Object> entry : _Temp_Role_Table.entrySet()) {
                Role role = (Role) entry.getValue();
                List<Permission> manys = new ArrayList<>();
                for (Permission ps : role.getPermissions()) {
                    manys.add((Permission) Permission_Table.get(ps.getId()));
                }
                role.setPermissions(manys);
                Role_Table.put(role.getId(), role);
            }
            //间接上级
            for (Map.Entry<Long, Object> entry : _Temp_User_Table.entrySet()) {
                User user = (User) entry.getValue();
                List<Role> manys = new ArrayList<>();
                for (Role rl : user.getRoles()) {
                    manys.add((Role) Role_Table.get(rl.getId()));
                }
                user.setRoles(manys);
                User_Table.put(user.getId(), user);
            }
            _Temp_User_Table = null;//建议GC回收
            _Temp_Role_Table = null;//建议GC回收
            _Result_Table = Permission_Table;
        } else if (tablename.equals("Role")) {
            Map<Long, Object> _Temp_User_Table = new HashMap<>();
            Role_Table.put(id, entity);
            _Temp_User_Table.putAll(User_Table);
            for (Map.Entry<Long, Object> entry : _Temp_User_Table.entrySet()) {
                User user = (User) entry.getValue();
                List<Role> manys = new ArrayList<>();
                for (Role rl : user.getRoles()) {
                    manys.add((Role) Role_Table.get(rl.getId()));
                }
                user.setRoles(manys);
                User_Table.put(user.getId(), user);
            }
            _Temp_User_Table = null;//建议GC回收
            _Result_Table = Role_Table;
        } else if (tablename.equals("User")) {
            User_Table.put(id, entity);
            _Result_Table = User_Table;
        }
        return _Result_Table;
    }

    public static Object SelectById(String tablename, Long id) {
        Object object = null;
        if (tablename.equals("Permission")) {
            object = Permission_Table.get(id);
        } else if (tablename.equals("Role")) {
            object = Role_Table.get(id);
        } else if (tablename.equals("User")) {
            object = User_Table.get(id);
        }
        return object;
    }

}
