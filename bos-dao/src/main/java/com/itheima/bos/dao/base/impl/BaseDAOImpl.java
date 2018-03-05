package com.itheima.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.bos.dao.base.IBaseDAO;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public class BaseDAOImpl<T> extends HibernateDaoSupport implements IBaseDAO<T>{
	private Class<T> entityClass;//定义运行时类类型，因为不确定使用哪个类
	//通过构造方法确定运行时类的类型
	public  BaseDAOImpl() {
		Type genericSuperclass = this.getClass().getGenericSuperclass();//获取父类类型,//parameterizedType是Type的子类，强转为子类类型
		ParameterizedType pType=(ParameterizedType) genericSuperclass;//获取实现该父类的所有子类类型
		//获取父类的泛型数组
		Type[] actualTypeArguments = pType.getActualTypeArguments();
		//将泛型数组中的第一个作为运行时类型
		Class<T> parameterizedType=(Class<T>)actualTypeArguments[0];
		entityClass=parameterizedType;
	}
	
	//设置会话工厂由于hibernateTemplate模版中的会话工厂是final的，子类不能直接调用父类的final会话工厂
	@Resource//当spring配置文件中bean的 id/class与参数名称/类型一致时就可以将sessionFactory注入进来
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);//super表示父类，父类关键字可以调用自己方法
	}
	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
		
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);//entityClass表示运行时类的类型
	}

	@Override
	public List<T> findAll() {
		String hql="FROM "+entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public void executeUpdate(String queryName, Object... objects) {
		// queryName是*.hbm.xml中<query name="">的值，objects表示可变参数是个数组
		Session currentSession = getSessionFactory().getCurrentSession();//获取当前线程的session
		Query query = currentSession.getNamedQuery(queryName);//根据queryName获取query对象
		//设置参数
		//计数循环设参
		int i=0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		//执行更新
		query.executeUpdate();
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {//当前页(页面传参)和每页显示的记录数(页面传参)，查询条件都已经封装在pageBean中；还需总记录数和每页显示的记录
		//继承了hibernate模板，通过模板的根据条件查询方法获取查询总记录数
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//List findByCriteria = this.getHibernateTemplate().findByCriteria(detachedCriteria);//select * from staff;我们需要select count(*) from staff;所以需要设置属性值
		
		detachedCriteria.setProjection(Projections.rowCount());//Projections.rowCount()===>count(*),给条件对象设置projection属性
		List<Long> totalList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);//根据查询条件查询总记录数
		Long total=totalList.get(0);
		pageBean.setTotal(total.intValue());//将查询到的总记录数封装到pageBean中
		
		//查询每页显示的记录(select * from staff limit ?,?;)
		detachedCriteria.setProjection(null);//还原查询条件
		//指定hibernate框架封装对象的方式
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		//findByCriteria(查询条件, 每页的第一条记录的索引, 每页显示的最大记录数);
		int maxResults=pageBean.getPageSize();
		int firstResult=(pageBean.getCurrentPage()-1)*maxResults;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	@Override
	public void saveorupdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);//先查询数据库在更新
		
	}

	//通用的查询方法
	public List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria) {
		List<T> sList = (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return sList;
	}

}
