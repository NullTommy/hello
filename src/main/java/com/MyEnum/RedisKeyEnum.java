package com.MyEnum;

public enum RedisKeyEnum {


    /*
     * 1、枚举类中有三个字段，但可以同时定义有两个和三个字段的枚举值
     * 2、注意 format 方法(该方法是在作用在具体的每个枚举值上的)，以及入参（多个对象）（结合需求定义）
     * 3、注意 getShardIndex 方法（结合需求定义）
     * */

    KEY("key:%s", 60 * 5),
    KEY_TWO_STR("key_two_str:%s-%s", 60 * 5, 1),
    ;

    /**
     * 格式化字符串
     */
    private String formatKey;
    /**
     * 过期时间
     */
    private int expire;
    /**
     * 分片数
     */
    private int shardNum;

    private RedisKeyEnum(String format, int expire) {
        this.formatKey = format;
        this.expire = expire;
    }

    private RedisKeyEnum(String format, int expire, int shardNum) {
        this.formatKey = format;
        this.expire = expire;
        this.shardNum = shardNum;
    }

    public String format(Object... values) {
        return String.format(formatKey, values);
    }

    public Long getShardIndex(Long key) {
        return key % shardNum;
    }

    public String getFormatKey() {
        return formatKey;
    }

    public void setFormatKey(String formatKey) {
        this.formatKey = formatKey;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    public int getShardNum() {
        return shardNum;
    }

    public void setShardNum(int shardNum) {
        this.shardNum = shardNum;
    }

}
