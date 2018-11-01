package com.zac.batch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="stress_testing")
public class StressTesting implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "test_id")
	private Long testId;
	
	@Column(name = "test_point")
	private String testPoint;

	@Column(name = "test_date")
	private String testDate;

	public StressTesting() {
	}

	public StressTesting(String testPoint, String testDate) {
		this.testPoint = testPoint;
		this.testDate = testDate;
	}

  public Long getTestId() {
    return testId;
  }

  public void setTestId(Long testId) {
    this.testId = testId;
  }

  public String getTestPoint() {
    return testPoint;
  }

  public void setTestPoint(String testPoint) {
    this.testPoint = testPoint;
  }

  public String getTestDate() {
    return testDate;
  }

  public void setTestDate(String testDate) {
    this.testDate = testDate;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
