package com.itheima.bos.domain;

import java.sql.Timestamp;

/**
 * ����
 */

public class Workbill implements java.io.Serializable {

	// Fields

	private String id;
	private Noticebill noticebill;
	private Staff staff;
	private String type;//��������:�¡�׷���ġ���
	private String pickstate;//ȡ��״̬:δȡ����ȡ���С���ȡ��
	private Timestamp buildtime;
	private Integer attachbilltimes;
	private String remark;

	public static final String  TYPE_1 = "�µ�";
	public static final String  TYPE_2 = "׷��";
	public static final String  TYPE_3 = "�ĵ�";
	public static final String  TYPE_4 = "����";
	
	public static final String  PICKSTATE_NO = "δȡ��";
	public static final String  PICKSTATE_RUNNING = "ȡ����";
	public static final String  PICKSTATE_YES = "��ȡ��";
	// Constructors

	/** default constructor */
	public Workbill() {
	}

	/** minimal constructor */
	public Workbill(String id, Timestamp buildtime) {
		this.id = id;
		this.buildtime = buildtime;
	}

	/** full constructor */
	public Workbill(String id, Noticebill noticebill, Staff staff,
			String type, String pickstate, Timestamp buildtime,
			Integer attachbilltimes, String remark) {
		this.id = id;
		this.noticebill = noticebill;
		this.staff = staff;
		this.type = type;
		this.pickstate = pickstate;
		this.buildtime = buildtime;
		this.attachbilltimes = attachbilltimes;
		this.remark = remark;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Noticebill getNoticebill() {
		return this.noticebill;
	}

	public void setNoticebill(Noticebill noticebill) {
		this.noticebill = noticebill;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPickstate() {
		return this.pickstate;
	}

	public void setPickstate(String pickstate) {
		this.pickstate = pickstate;
	}

	public Timestamp getBuildtime() {
		return this.buildtime;
	}

	public void setBuildtime(Timestamp buildtime) {
		this.buildtime = buildtime;
	}

	public Integer getAttachbilltimes() {
		return this.attachbilltimes;
	}

	public void setAttachbilltimes(Integer attachbilltimes) {
		this.attachbilltimes = attachbilltimes;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}