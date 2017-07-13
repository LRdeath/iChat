package io.weizc.github.italker.push.bean.db;

import org.hibernate.annotations.*;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户model，对应的数据库
 *
 * @author Vzer
 * @version 1.0.0
 *  Date: 2017/6/26.
 */
@Entity
@Table(name = "TB_USER")
public class User implements Principal {
    //这是主键
    @Id
    @PrimaryKeyJoinColumn
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid2")//把生成器改为uuid2，也就是去掉‘-’
    @Column(nullable = false,updatable = false)//不允许更改，不允许为null
    private String id;

    //用户名必须唯一
    @Column(nullable = false,length = 128,unique = true)
    private String name;

    //电话必须唯一
    @Column(nullable = false,length = 64,unique = true)
    private String phone;

    //密码不为空
    @Column(nullable = false)
    private String password;

    //头像允许为null
    @Column
    private String portrait;

    //性别有初始值，所以不为空
    @Column(nullable = false)
    private int sex = 0;

   //用户介绍
    @Column
    private String description;

    //token可以拉取用户信息，所有的token必须唯一
    @Column(unique = true)
    private String token;

    //用于推送设备的id
    @Column
    private String pushId;

    //定义创建时间戳，在创建时就已经写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime creatAt = LocalDateTime.now();

    //定义更新时间戳，在更新时就已经写入
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //最后一次收到消息的时间
    @Column
    private LocalDateTime lastReceivedAt = LocalDateTime.now();


    //定义我关注的列表
    //对应的数据库表字段为TB_USER_FOLLOW.targetId
    @JoinColumn(name = "targetId")
    //懒加载，默认加载User时，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA )
    //一对多，一个用户可以有很多人关注人
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<UserFollow> following = new HashSet<>();

    //定义我关注的列表
    //对应的数据库表字段为TB_USER_FOLLOW.originId
    @JoinColumn(name = "originId")
    //懒加载，默认加载User时，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA )
    //一对多，一个用户可以有很多人关注人
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public LocalDateTime getCreatAt() {
        return creatAt;
    }

    public void setCreatAt(LocalDateTime creatAt) {
        this.creatAt = creatAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public LocalDateTime getLastReceivedAt() {
        return lastReceivedAt;
    }

    public void setLastReceivedAt(LocalDateTime lastReceivedAt) {
        this.lastReceivedAt = lastReceivedAt;
    }
}
