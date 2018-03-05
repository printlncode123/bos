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
	private Class<T> entityClass;//��������ʱ�����ͣ���Ϊ��ȷ��ʹ���ĸ���
	//ͨ�����췽��ȷ������ʱ�������
	public  BaseDAOImpl() {
		Type genericSuperclass = this.getClass().getGenericSuperclass();//��ȡ��������,//parameterizedType��Type�����࣬ǿתΪ��������
		ParameterizedType pType=(ParameterizedType) genericSuperclass;//��ȡʵ�ָø����������������
		//��ȡ����ķ�������
		Type[] actualTypeArguments = pType.getActualTypeArguments();
		//�����������еĵ�һ����Ϊ����ʱ����
		Class<T> parameterizedType=(Class<T>)actualTypeArguments[0];
		entityClass=parameterizedType;
	}
	
	//���ûỰ��������hibernateTemplateģ���еĻỰ������final�ģ����಻��ֱ�ӵ��ø����final�Ự����
	@Resource//��spring�����ļ���bean�� id/class���������/����һ��ʱ�Ϳ��Խ�sessionFactoryע�����
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);//super��ʾ���࣬����ؼ��ֿ��Ե����Լ�����
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
		return this.getHibernateTemplate().get(entityClass, id);//entityClass��ʾ����ʱ�������
	}

	@Override
	public List<T> findAll() {
		String hql="FROM "+entityClass.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public void executeUpdate(String queryName, Object... objects) {
		// queryName��*.hbm.xml��<query name="">��ֵ��objects��ʾ�ɱ�����Ǹ�����
		Session currentSession = getSessionFactory().getCurrentSession();//��ȡ��ǰ�̵߳�session
		Query query = currentSession.getNamedQuery(queryName);//����queryName��ȡquery����
		//���ò���
		//����ѭ�����
		int i=0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		//ִ�и���
		query.executeUpdate();
		
	}

	@Override
	public void pageQuery(PageBean pageBean) {//��ǰҳ(ҳ�洫��)��ÿҳ��ʾ�ļ�¼��(ҳ�洫��)����ѯ�������Ѿ���װ��pageBean�У������ܼ�¼����ÿҳ��ʾ�ļ�¼
		//�̳���hibernateģ�壬ͨ��ģ��ĸ���������ѯ������ȡ��ѯ�ܼ�¼��
		DetachedCriteria detachedCriteria = pageBean.getDetachedCriteria();
		//List findByCriteria = this.getHibernateTemplate().findByCriteria(detachedCriteria);//select * from staff;������Ҫselect count(*) from staff;������Ҫ��������ֵ
		
		detachedCriteria.setProjection(Projections.rowCount());//Projections.rowCount()===>count(*),��������������projection����
		List<Long> totalList = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);//���ݲ�ѯ������ѯ�ܼ�¼��
		Long total=totalList.get(0);
		pageBean.setTotal(total.intValue());//����ѯ�����ܼ�¼����װ��pageBean��
		
		//��ѯÿҳ��ʾ�ļ�¼(select * from staff limit ?,?;)
		detachedCriteria.setProjection(null);//��ԭ��ѯ����
		//ָ��hibernate��ܷ�װ����ķ�ʽ
		detachedCriteria.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		//findByCriteria(��ѯ����, ÿҳ�ĵ�һ����¼������, ÿҳ��ʾ������¼��);
		int maxResults=pageBean.getPageSize();
		int firstResult=(pageBean.getCurrentPage()-1)*maxResults;
		List rows = this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, maxResults);
		pageBean.setRows(rows);
	}

	@Override
	public void saveorupdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);//�Ȳ�ѯ���ݿ��ڸ���
		
	}

	//ͨ�õĲ�ѯ����
	public List<T> findByDetachedCriteria(DetachedCriteria detachedCriteria) {
		List<T> sList = (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		return sList;
	}

}
