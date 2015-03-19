package com.appbootup.hibernate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CaseInstance {
	@Id
	@GeneratedValue
	private Long cseId;

	@Column(nullable = false)
	private Date cseCreatedDttm;

	@Column(nullable = false)
	private Integer cseCreatedYear;

	@Column(nullable = false)
	private String cseStatus;

	@Column(nullable = false)
	private String cseSeverity;

	@Column(nullable = false)
	private Long cseCost;

	private String cseCreatedUsrId;
	private String cseTeamId;

	public CaseInstance() {
		super();
	}

	public CaseInstance(String cseStatus, String cseSeverity,
			Date cseCreatedDttm, Integer cseCreatedYear, long cseCost) {
		this.cseStatus = cseStatus;
		this.cseSeverity = cseSeverity;
		this.cseCreatedDttm = cseCreatedDttm;
		this.cseCost = cseCost;
		this.setCseCreatedYear(cseCreatedYear);
	}

	public Long getCseId() {
		return cseId;
	}

	public void setCseId(Long cseId) {
		this.cseId = cseId;
	}

	public Date getCseCreatedDttm() {
		return cseCreatedDttm;
	}

	public void setCseCreatedDttm(Date cseCreatedDttm) {
		this.cseCreatedDttm = cseCreatedDttm;
	}

	public String getCseStatus() {
		return cseStatus;
	}

	public void setCseStatus(String cseStatus) {
		this.cseStatus = cseStatus;
	}

	public String getCseSeverity() {
		return cseSeverity;
	}

	public void setCseSeverity(String cseSeverity) {
		this.cseSeverity = cseSeverity;
	}

	public Long getCseCost() {
		return cseCost;
	}

	public void setCseCost(Long cseCost) {
		this.cseCost = cseCost;
	}

	public String getCseCreatedUsrId() {
		return cseCreatedUsrId;
	}

	public void setCseCreatedUsrId(String cseCreatedUsrId) {
		this.cseCreatedUsrId = cseCreatedUsrId;
	}

	public String getCseTeamId() {
		return cseTeamId;
	}

	public void setCseTeamId(String cseTeamId) {
		this.cseTeamId = cseTeamId;
	}

	public Integer getCseCreatedYear() {
		return cseCreatedYear;
	}

	public void setCseCreatedYear(Integer cseCreatedYear) {
		this.cseCreatedYear = cseCreatedYear;
	}
}