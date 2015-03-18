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
	private String cseStatus;

	@Column(nullable = false)
	private String cseSeverity;
	private String cseCreatedUsrId;
	private String cseTeamId;

	public CaseInstance() {
		super();
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
}
