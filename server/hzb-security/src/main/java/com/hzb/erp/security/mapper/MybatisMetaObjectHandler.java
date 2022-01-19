package com.hzb.erp.security.mapper;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.hzb.erp.security.Util.UserAuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Ryan 541720500@qq.com
 * description
 */
@Slf4j
@Component
public class MybatisMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject, "addTime", LocalDateTime::now, LocalDateTime.class);
        this.fillStrategy(metaObject, "creator", UserAuthUtil.getCurrentUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "editTime", LocalDateTime::now, LocalDateTime.class);
        this.fillStrategy(metaObject, "editor", UserAuthUtil.getCurrentUserId());

    }
}
