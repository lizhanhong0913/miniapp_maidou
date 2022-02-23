package com.maidou.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author zhanhong
 */
@Data
public class CanalBean {
    /**
     * 数据
     */
    private List<TbCommodityInfo> data;
    /**
     * 数据库名称
     */
    private String database;
    private long es;
    /**
     * 递增，从1开始
     */
    private int id;
    /**
     * 是否是DDL语句
     */
    private boolean isDdl;
    /**
     * 表结构的字段类型
     */
    private MysqlType mysqlType;
    //UPDATE语句，旧数据
    private String old;
    /**
     * 主键名称
     */
    private List<String> pkNames;
    /**
     * sql语句
     */
    private String sql;
    private SqlType sqlType;
    /**
     * 表名
     */
    private String table;
    private long ts;
    /**
     * (新增)INSERT、(更新)UPDATE、(删除)DELETE、(删除表)ERASE等等
     */
    private String type;

    public void setIsDdl(boolean isDdl) {
        this.isDdl = isDdl;
    }

    public boolean getIsDdl() {
        return isDdl;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

