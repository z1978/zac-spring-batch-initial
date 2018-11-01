package com.zac.batch.dto;

public class StressTestingDto {

    private Integer testId;
    private String testPoint;
    private String testDate;

    public StressTestingDto() {
    }

    public StressTestingDto(Integer testId, String testPoint, String testDate) {
        this.testId = testId;
        this.testPoint = testPoint;
        this.testDate = testDate;
    }

    public Integer getTestId() {
      return testId;
    }

    public void setTestId(Integer testId) {
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

    @Override
    public String toString() {
        return "testId: " + testId + ", testPoint: " + testPoint + ", testDate: " + testDate;
    }

}