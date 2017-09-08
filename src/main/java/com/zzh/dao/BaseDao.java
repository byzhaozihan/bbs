package com.zzh.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by zhao on 2017/6/25.
 *  DAO基类，其它DAO可以直接继承这个DAO，不但可以复用共用的方法，还可以获得泛型的好处。
 */
public class BaseDao<T> {

    private Class<T> entityClass;

    private HibernateTemplate hibernateTemplate;

    /**
     * Autowired注解放在setter上而不是属性上的原因：
     * 如果放在属性上那么他的子类就不能使用hibernatetemplate
     * 而通过方法则子类就可以使用hibernateTemplate
     * @param hibernateTemplate
     */
    @Autowired
    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }



    public Session getSession() {
        return hibernateTemplate.getSessionFactory().getCurrentSession();
    }
    public BaseDao() {

        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    public T load(Serializable id) {
        return (T) getHibernateTemplate().load(entityClass, id);
    }

    public T get(Serializable id) {
        return (T) getHibernateTemplate().get(entityClass, id);
    }

    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(entityClass);
    }

    public void save(T entity) {
        getHibernateTemplate().save(entity);
    }

    public void remove(T entity) {
        getHibernateTemplate().delete(entity);
    }

    /**
     * 删除表中数据
     * truncate table快速删除数据表中的所有记录，保留数据表结构。
     * 这种快速删除与delete from 数据表的删除全部数据表记录不一样，
     * delete命令删除的数据将存储在系统回滚段中，需要的时候，数据可以回滚恢复，
     * 而truncate命令删除的数据是不可以恢复的
     * TRUNCATE之后的自增字段从头开始计数了，而DELETE的仍保留原来的最大数值
     */
    public void removeAll(String tableName) {
        getSession().createSQLQuery("truncate TABLE  " + tableName + "").executeUpdate();
    }

    public void update(T entity) {
        getHibernateTemplate().update(entity);
    }

    /**
     * 执行HQL查询
     * Object... 即这个方法可以传递多个参数，这个参数的个数是不确定的。
     */
    public List find(String hql, Object... params) {

        return getHibernateTemplate().find(hql, params);
    }

    /**
     * 对延迟加载的实体PO执行初始化
     * @param entity
     */
    public void initialize(Object entity) {

        getHibernateTemplate().initialize(entity);
    }


    //分页查询函数
    //返回包含业务数据和分页信息的Page对象，而非仅包含业务数据的List对象
    public Page pagedQuery(String hql, int pageNo, int pageSize, Object... values) {

        Assert.hasText(hql);//hql不为null且至少包含一个非空格字符串
        Assert.isTrue(pageNo >= 1, "pageNo should start from 1");
        //Count查询
        String countQueryString = "select count(*) " + removeSelect(removeOrders(hql));
        //
        List countlist = getHibernateTemplate().find(countQueryString, values);
        long totalCount = (long) countlist.get(0);//符合的条数

        if (totalCount < 1) {
            return new Page();
        }

        //实际查询返回分页对象
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        Query query = createQuery(hql, values);
        //Query接口的list（）方法执行HQL查询
        //集合中存放符合查询条件的持久化对象
        //Query.setMaxResults方法简单地设置需要查询的最大结果集。
        List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        //这里的list是当前页的数据，而不是所有数据
        return new Page(startIndex, totalCount, pageSize, list);
    }

    //创建Query对象
    public Query createQuery(String hql, Object... values) {
        Assert.hasText(hql);
        //Query接口作用：把HQL查询语句解析成相应的SQL语句来执行数据库的查询操作
        Query query = getSession().createQuery(hql);
        for (int i=0;i<values.length;i++) {
            query.setParameter(i, values[i]);
        }
        return query;
    }

    //去除hql的select子句
    private static String removeSelect(String hql) {
        Assert.hasText(hql);
        //返回from的索引位置，没有找到则返回-1
        int beginPos = hql.toLowerCase().indexOf("from");
        Assert.isTrue(beginPos != -1, "hql :" + hql + "must has a keyword 'from'");
        //取得的字符串为从beginPos开始到结束
        return hql.substring(beginPos);
    }

    //去除hql的orderby字句，用于pagedQuery
    private static String removeOrders(String hql) {
        Assert.hasText(hql);
        /**
         * 反斜杠在java里是转义字符，需要将转义去除
         * \s* :匹配任意数量的空格，换行和制表符
         * \S :匹配非空白
         * \W :匹配非单词字符
         * \w :匹配字母数字或下划线
         * * ：匹配前面的子表达式零次或多次
         * Pattern.CASE_INSENSITIVE：忽略大小写
         */
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        //find:部分匹配，从当前位置开始找到匹配的字串
        while (m.find()) {
            //用空替换匹配的串并添加到StringBuffer里，达到去除orderby字句的目的
            m.appendReplacement(sb, "");
        }
        //不匹配的按原样添加到StringBuffer中
        m.appendTail(sb);
        return sb.toString();
    }
}
