package com.hzb.erp.dataScope;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.hzb.erp.annotation.DataScoped;
import com.hzb.erp.common.entity.DataPermission;
import com.hzb.erp.common.entity.StaffOrginfo;
import com.hzb.erp.common.enums.DataScopeTypeEnum;
import com.hzb.erp.security.Util.SecurityUtils;
import com.hzb.erp.service.UserAuthService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * <p> </p>
 *
 * @author Ryan 541720500@qq.com
 */
@Slf4j
public class DataScopeInterceptor implements InnerInterceptor {
    @SneakyThrows
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds,
                            ResultHandler resultHandler, BoundSql boundSql) {

        if (!SqlCommandType.SELECT.equals(ms.getSqlCommandType())) {
            return;
        }

        /*
        * 获取登录者
        * */
        if(UserAuthService.getCurrentUserId() == null) {
            System.out.println("=======uid: null");
            return;
        }
        // 获取登录信息 未登录跳过
        try {
            Boolean isStudent = SecurityUtils.isStudent();
            if (isStudent == null || isStudent) {
                return;
            }
        } catch (Exception e) {
            log.info("数据权限验证时，因未登录引起的计划内异常: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        /*
        * 获取注解
        * */
        String namespace = ms.getId();
        //获取mapper名称
        String className = namespace.substring(0, namespace.lastIndexOf("."));
        //获取方法名
        String methedName = namespace.substring(namespace.lastIndexOf(".") + 1, namespace.length());
        //获取当前mapper 的方法
        Method[] methods = Class.forName(className).getMethods();

        boolean dataScoped = false;

        for (Method m : methods) {
            if (m.getName().equals(methedName)) {
                DataScoped annotation = m.getAnnotation(DataScoped.class);
                if (annotation != null) {
                    dataScoped = annotation.scoped();
                }
            }
        }
        if (!dataScoped) {
            return;
        }



        // 创建新查询条件
        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        String originalSql = boundSql.getSql();
        mpBs.sql(buildSql(originalSql ));
        // mpBs.sql(originalSql);

    }

    /**
     * 解析SQL语句，并返回新的SQL语句
     * 注意，该方法使用了JSqlParser来操作SQL，该依赖包Mybatis-plus已经集成了。如果要单独使用，请先自行导入依赖
     *
     * @param sql 原SQL
     * @return 新SQL
     */
    private String buildSql(String sql) {

        try {
            Select statement = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = statement.getSelectBody();
            if (!(selectBody instanceof PlainSelect)) {
                return sql;
            }
            PlainSelect plainSelect = (PlainSelect) selectBody;
            FromItem fromTable = plainSelect.getFromItem();
            Table table = (Table) fromTable;
            log.info("====>table: " + table);

            String entityName = table.getName();
            log.info("====>entityName: " + table);

            if(org.apache.commons.lang3.StringUtils.isBlank(entityName)) {
                return sql;
            }

            Alias alias = fromTable.getAlias();
            String aliasName = alias == null ? "" : alias.getName();

            String sqlStr = makeCondition(entityName, aliasName);
            if(StringUtils.isBlank(sqlStr)) {
                System.out.println("sqlStr为空");
                return sql;
            }

            Expression parseRes = CCJSqlParserUtil.parseCondExpression(sqlStr);
            Expression sqlSegment = new AndExpression(plainSelect.getWhere(), parseRes);
            plainSelect.setWhere(sqlSegment);

            System.out.println("==============buildSql===================");
            System.out.println(plainSelect);
            System.out.println("==============buildSql===================");

            return plainSelect.toString();

        } catch (JSQLParserException e) {
            log.error("解析原SQL并构建新SQL错误：" + e);
            return sql;
        }

    }

    private String makeCondition(String entityName, String tableAliasName) {

        DataScopeEntityEnum scopeEntityEnum = null;
        for (DataScopeEntityEnum entityEnum: DataScopeEntityEnum.values()) {
            if(entityName.equals(entityEnum.getCode())){
                scopeEntityEnum = entityEnum;
                break;
            }
        }
        if(scopeEntityEnum == null) {
            return "";
        }

        System.out.println("===========makeCondition");
        Long uid = UserAuthService.getCurrentUserId();
        if(uid == null) {
            return "";
        }
        List<DataPermission> permissions = DataPermissionService.getPermissionList(UserAuthService.getCurrentUserId(), entityName);
        if (permissions == null || permissions.size() == 0) {
            System.out.println("===========null1");
            return "";
        }
        StaffOrginfo orgInfo = DataPermissionService.getOrgInfo(uid);
        if (orgInfo == null) {
            System.out.println("===========null2");
            return "";
        }

        List<String> sqlList = new ArrayList<>();

        for (DataPermission dp : permissions) {

            DataScopeTypeEnum scopeType = dp.getScopeType();
            if (scopeType == null
                    || DataScopeTypeEnum.ALL.equals(scopeType)
                    || org.apache.commons.lang3.StringUtils.isBlank(dp.getEntityName())
                    || org.apache.commons.lang3.StringUtils.isBlank(dp.getOwnerField())) {
                continue;
            }

            String scopeSql;
            if (DataScopeTypeEnum.GROUP.equals(scopeType)) {
                if (orgInfo.getGroupId() == null) {
                    continue;
                }
                scopeSql = " in( select staff_id from staff_orginfo where org_id in ( " +
                        "    select id from org where id = '" + orgInfo.getGroupId() + "' or find_in_set('" + orgInfo.getGroupId() + "', id_path) " +
                        "  )) ";
            } else if (DataScopeTypeEnum.COM.equals(scopeType)) {
                scopeSql = " in( select staff_id from staff_orginfo where org_id in ( " +
                        "    select id from org where id = '" + orgInfo.getComId() + "' or find_in_set('" + orgInfo.getComId() + "', id_path) " +
                        "  )) ";
            } else if (DataScopeTypeEnum.DPT.equals(scopeType)) {
                scopeSql = " in( select staff_id from staff_orginfo where org_id in ( " +
                        "    select id from org where id = '" + orgInfo.getDptId() + "' or find_in_set('" + orgInfo.getDptId() + "', id_path) " +
                        "  )) ";
            } else if (DataScopeTypeEnum.SELF.equals(scopeType)) {
                scopeSql = "";

            } else if (DataScopeTypeEnum.CUSTOM.equals(scopeType)) {
                scopeSql = " in( select staff_id from staff_orginfo where org_id in ( " +
                        "    select id from org where id in (select org_id from data_permission_custom where position_id = '" + dp.getPositionId() + "')" +
                        "  )) ";
            } else {
                return "";
            }

            if (org.apache.commons.lang3.StringUtils.isNotBlank(scopeSql)) {
                sqlList.add(buildTableName(tableAliasName, dp.getOwnerField()) + scopeSql);
            } else {
                sqlList.add(buildTableName(tableAliasName, dp.getOwnerField()) + " = " + orgInfo.getStaffId());
            }
        }
        if (sqlList.size() == 0) {
            return "";
        }
        return "(" + org.apache.commons.lang3.StringUtils.join(sqlList, " OR ") + ")";
    }

    private static String buildTableName(String tableAlias, String columnName) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(tableAlias)) {
            columnName = tableAlias + "." + columnName;
        }
        return columnName;
    }
}
