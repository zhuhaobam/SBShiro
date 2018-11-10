package com.bamboobam.sbshiro.config.repositoryconfig.db;

import com.bamboobam.sbshiro.config.Constant;
import com.bamboobam.sbshiro.config.repositoryconfig.table.PTable;
import com.bamboobam.sbshiro.config.repositoryconfig.table.RTable;
import com.bamboobam.sbshiro.config.repositoryconfig.table.UTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        PTable permissionBaseEntity1 = new PTable(1L, "red", "/red");
        PTable permissionBaseEntity2 = new PTable(2L, "green", "/green");
        PTable permissionBaseEntity3 = new PTable(3L, "blue", "/blue");
        PTable permissionBaseEntity4 = new PTable(4L, "purp", "/purp");
        Permission_Table.put(1L, permissionBaseEntity1);
        Permission_Table.put(2L, permissionBaseEntity2);
        Permission_Table.put(3L, permissionBaseEntity3);
        Permission_Table.put(4L, permissionBaseEntity4);
        //-------
        RTable roleBaseEntity1 = new RTable(1L, "admin", new Long[]{1L, 2L});
        RTable roleBaseEntity2 = new RTable(2L, "vip", new Long[]{3L});
        RTable roleBaseEntity3 = new RTable(3L, "simple", new Long[]{4L});
        Role_Table.put(1L, roleBaseEntity1);
        Role_Table.put(2L, roleBaseEntity2);
        Role_Table.put(3L, roleBaseEntity3);
        //-------
        UTable userBaseEntity1 = new UTable(1L, "admin", Constant.MD5PWD, Constant.SALT, new Long[]{1L});
        UTable userBaseEntity2 = new UTable(2L, "vip", Constant.MD5PWD, Constant.SALT, new Long[]{2L});
        UTable userBaseEntity3 = new UTable(3L, "simple", Constant.MD5PWD, Constant.SALT, new Long[]{3L});
        User_Table.put(1L, userBaseEntity1);
        User_Table.put(2L, userBaseEntity2);
        User_Table.put(3L, userBaseEntity3);

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
            _Result_Table = Permission_Table;
        } else if (tablename.equals("Role")) {
            Role_Table.put(id, entity);
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

    public static Object SelectByName(String tablename, String name) {
        Object object = null;
        if (tablename.equals("Permission")) {
            for (Map.Entry entt : Permission_Table.entrySet()) {
                PTable table = (PTable) entt.getValue();
                if (table.getPermissionname().equals(name)) {
                    object = table;
                }
            }
        } else if (tablename.equals("Role")) {
            for (Map.Entry entt : Role_Table.entrySet()) {
                RTable table = (RTable) entt.getValue();
                if (table.getName().equals(name)) {
                    object = table;
                }
            }
        } else if (tablename.equals("User")) {
            for (Map.Entry entt : User_Table.entrySet()) {
                UTable table = (UTable) entt.getValue();
                if (table.getUsername().equals(name)) {
                    object = table;
                }
            }
        }
        return object;
    }


    public static List<Object> findByNameLike(String tablename, String name) {
        List<Object> objects =  new ArrayList<>();
        if (tablename.equals("Permission")) {
            for (Map.Entry entt : Permission_Table.entrySet()) {
                PTable table = (PTable) entt.getValue();
                if (table.getPermissionname().indexOf(name) >=0) {
                    objects.add(table);
                }
            }
        } else if (tablename.equals("Role")) {
            for (Map.Entry entt : Role_Table.entrySet()) {
                RTable table = (RTable) entt.getValue();
                if (table.getName().indexOf(name) >=0) {
                    objects.add(table);
                }
            }
        } else if (tablename.equals("User")) {
            for (Map.Entry entt : User_Table.entrySet()) {
                UTable table = (UTable) entt.getValue();
                if (table.getUsername().indexOf(name) >=0) {
                    objects.add(table);
                }
            }
        }
        return objects;
    }



}
