package com.bamboobam.sbshiro.config;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Constant {

    public final static String T_PERMISSION = "Permission";
    public final static String T_ROLE = "Role";
    public final static String T_USER = "User";
    public final static String ALLSALT = "9986";

    public static String getPwd(String username, String password) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = password;//密码原值
        ByteSource salt = ByteSource.Util.bytes(username + ALLSALT);//盐值
        int hashIterations = 2;//加密2次
        Object result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations);
        return result.toString();
    }

    public static String getPwd(String username, String password, String asalt) {
        String hashAlgorithmName = "MD5";//加密方式
        Object crdentials = password;//密码原值
        ByteSource salt = ByteSource.Util.bytes(username + asalt);//盐值
        int hashIterations = 2;//加密2次
        Object result = new SimpleHash(hashAlgorithmName, crdentials, salt, hashIterations);
        return result.toString();
    }

    public static void main(String[] args) {
        System.out.println(getPwd("admin","123456"));//a574d0ac174a2bc730d442c0823ad5f2
        //
    }


}
