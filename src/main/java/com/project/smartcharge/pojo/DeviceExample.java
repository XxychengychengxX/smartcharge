package com.project.smartcharge.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeviceExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public DeviceExample() {
        oredCriteria = new ArrayList<>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDeviceIDIsNull() {
            addCriterion("deviceID is null");
            return (Criteria) this;
        }

        public Criteria andDeviceIDIsNotNull() {
            addCriterion("deviceID is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceIDEqualTo(Integer value) {
            addCriterion("deviceID =", value, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDNotEqualTo(Integer value) {
            addCriterion("deviceID <>", value, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDGreaterThan(Integer value) {
            addCriterion("deviceID >", value, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDGreaterThanOrEqualTo(Integer value) {
            addCriterion("deviceID >=", value, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDLessThan(Integer value) {
            addCriterion("deviceID <", value, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDLessThanOrEqualTo(Integer value) {
            addCriterion("deviceID <=", value, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDIn(List<Integer> values) {
            addCriterion("deviceID in", values, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDNotIn(List<Integer> values) {
            addCriterion("deviceID not in", values, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDBetween(Integer value1, Integer value2) {
            addCriterion("deviceID between", value1, value2, "deviceID");
            return (Criteria) this;
        }

        public Criteria andDeviceIDNotBetween(Integer value1, Integer value2) {
            addCriterion("deviceID not between", value1, value2, "deviceID");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuIsNull() {
            addCriterion("workingStatu is null");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuIsNotNull() {
            addCriterion("workingStatu is not null");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuEqualTo(Boolean value) {
            addCriterion("workingStatu =", value, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuNotEqualTo(Boolean value) {
            addCriterion("workingStatu <>", value, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuGreaterThan(Boolean value) {
            addCriterion("workingStatu >", value, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuGreaterThanOrEqualTo(Boolean value) {
            addCriterion("workingStatu >=", value, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuLessThan(Boolean value) {
            addCriterion("workingStatu <", value, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuLessThanOrEqualTo(Boolean value) {
            addCriterion("workingStatu <=", value, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuIn(List<Boolean> values) {
            addCriterion("workingStatu in", values, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuNotIn(List<Boolean> values) {
            addCriterion("workingStatu not in", values, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuBetween(Boolean value1, Boolean value2) {
            addCriterion("workingStatu between", value1, value2, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andWorkingStatuNotBetween(Boolean value1, Boolean value2) {
            addCriterion("workingStatu not between", value1, value2, "workingStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuIsNull() {
            addCriterion("chargeStatu is null");
            return (Criteria) this;
        }

        public Criteria andChargeStatuIsNotNull() {
            addCriterion("chargeStatu is not null");
            return (Criteria) this;
        }

        public Criteria andChargeStatuEqualTo(Boolean value) {
            addCriterion("chargeStatu =", value, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuNotEqualTo(Boolean value) {
            addCriterion("chargeStatu <>", value, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuGreaterThan(Boolean value) {
            addCriterion("chargeStatu >", value, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuGreaterThanOrEqualTo(Boolean value) {
            addCriterion("chargeStatu >=", value, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuLessThan(Boolean value) {
            addCriterion("chargeStatu <", value, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuLessThanOrEqualTo(Boolean value) {
            addCriterion("chargeStatu <=", value, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuIn(List<Boolean> values) {
            addCriterion("chargeStatu in", values, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuNotIn(List<Boolean> values) {
            addCriterion("chargeStatu not in", values, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuBetween(Boolean value1, Boolean value2) {
            addCriterion("chargeStatu between", value1, value2, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeStatuNotBetween(Boolean value1, Boolean value2) {
            addCriterion("chargeStatu not between", value1, value2, "chargeStatu");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountIsNull() {
            addCriterion("chargeTimeCount is null");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountIsNotNull() {
            addCriterion("chargeTimeCount is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountEqualTo(String value) {
            addCriterion("chargeTimeCount =", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountNotEqualTo(String value) {
            addCriterion("chargeTimeCount <>", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountGreaterThan(String value) {
            addCriterion("chargeTimeCount >", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountGreaterThanOrEqualTo(String value) {
            addCriterion("chargeTimeCount >=", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountLessThan(String value) {
            addCriterion("chargeTimeCount <", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountLessThanOrEqualTo(String value) {
            addCriterion("chargeTimeCount <=", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountLike(String value) {
            addCriterion("chargeTimeCount like", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountNotLike(String value) {
            addCriterion("chargeTimeCount not like", value, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountIn(List<String> values) {
            addCriterion("chargeTimeCount in", values, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountNotIn(List<String> values) {
            addCriterion("chargeTimeCount not in", values, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountBetween(String value1, String value2) {
            addCriterion("chargeTimeCount between", value1, value2, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimeCountNotBetween(String value1, String value2) {
            addCriterion("chargeTimeCount not between", value1, value2, "chargeTimeCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountIsNull() {
            addCriterion("chargeTimesCount is null");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountIsNotNull() {
            addCriterion("chargeTimesCount is not null");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountEqualTo(Integer value) {
            addCriterion("chargeTimesCount =", value, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountNotEqualTo(Integer value) {
            addCriterion("chargeTimesCount <>", value, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountGreaterThan(Integer value) {
            addCriterion("chargeTimesCount >", value, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("chargeTimesCount >=", value, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountLessThan(Integer value) {
            addCriterion("chargeTimesCount <", value, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountLessThanOrEqualTo(Integer value) {
            addCriterion("chargeTimesCount <=", value, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountIn(List<Integer> values) {
            addCriterion("chargeTimesCount in", values, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountNotIn(List<Integer> values) {
            addCriterion("chargeTimesCount not in", values, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountBetween(Integer value1, Integer value2) {
            addCriterion("chargeTimesCount between", value1, value2, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeTimesCountNotBetween(Integer value1, Integer value2) {
            addCriterion("chargeTimesCount not between", value1, value2, "chargeTimesCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountIsNull() {
            addCriterion("chargeFeeCount is null");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountIsNotNull() {
            addCriterion("chargeFeeCount is not null");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountEqualTo(Double value) {
            addCriterion("chargeFeeCount =", value, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountNotEqualTo(Double value) {
            addCriterion("chargeFeeCount <>", value, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountGreaterThan(Double value) {
            addCriterion("chargeFeeCount >", value, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountGreaterThanOrEqualTo(Double value) {
            addCriterion("chargeFeeCount >=", value, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountLessThan(Double value) {
            addCriterion("chargeFeeCount <", value, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountLessThanOrEqualTo(Double value) {
            addCriterion("chargeFeeCount <=", value, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountIn(List<Double> values) {
            addCriterion("chargeFeeCount in", values, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountNotIn(List<Double> values) {
            addCriterion("chargeFeeCount not in", values, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountBetween(Double value1, Double value2) {
            addCriterion("chargeFeeCount between", value1, value2, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andChargeFeeCountNotBetween(Double value1, Double value2) {
            addCriterion("chargeFeeCount not between", value1, value2, "chargeFeeCount");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNull() {
            addCriterion("deviceType is null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIsNotNull() {
            addCriterion("deviceType is not null");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeEqualTo(Boolean value) {
            addCriterion("deviceType =", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotEqualTo(Boolean value) {
            addCriterion("deviceType <>", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThan(Boolean value) {
            addCriterion("deviceType >", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deviceType >=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThan(Boolean value) {
            addCriterion("deviceType <", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeLessThanOrEqualTo(Boolean value) {
            addCriterion("deviceType <=", value, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeIn(List<Boolean> values) {
            addCriterion("deviceType in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotIn(List<Boolean> values) {
            addCriterion("deviceType not in", values, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeBetween(Boolean value1, Boolean value2) {
            addCriterion("deviceType between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andDeviceTypeNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deviceType not between", value1, value2, "deviceType");
            return (Criteria) this;
        }

        public Criteria andChargeRateIsNull() {
            addCriterion("chargeRate is null");
            return (Criteria) this;
        }

        public Criteria andChargeRateIsNotNull() {
            addCriterion("chargeRate is not null");
            return (Criteria) this;
        }

        public Criteria andChargeRateEqualTo(Integer value) {
            addCriterion("chargeRate =", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotEqualTo(Integer value) {
            addCriterion("chargeRate <>", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateGreaterThan(Integer value) {
            addCriterion("chargeRate >", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateGreaterThanOrEqualTo(Integer value) {
            addCriterion("chargeRate >=", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLessThan(Integer value) {
            addCriterion("chargeRate <", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateLessThanOrEqualTo(Integer value) {
            addCriterion("chargeRate <=", value, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateIn(List<Integer> values) {
            addCriterion("chargeRate in", values, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotIn(List<Integer> values) {
            addCriterion("chargeRate not in", values, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateBetween(Integer value1, Integer value2) {
            addCriterion("chargeRate between", value1, value2, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andChargeRateNotBetween(Integer value1, Integer value2) {
            addCriterion("chargeRate not between", value1, value2, "chargeRate");
            return (Criteria) this;
        }

        public Criteria andCreated_atIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreated_atIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreated_atEqualTo(Date value) {
            addCriterion("created_at =", value, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atGreaterThan(Date value) {
            addCriterion("created_at >", value, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atLessThan(Date value) {
            addCriterion("created_at <", value, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atIn(List<Date> values) {
            addCriterion("created_at in", values, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "created_at");
            return (Criteria) this;
        }

        public Criteria andCreated_atNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "created_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atIsNull() {
            addCriterion("updated_at is null");
            return (Criteria) this;
        }

        public Criteria andUpdated_atIsNotNull() {
            addCriterion("updated_at is not null");
            return (Criteria) this;
        }

        public Criteria andUpdated_atEqualTo(Date value) {
            addCriterion("updated_at =", value, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atNotEqualTo(Date value) {
            addCriterion("updated_at <>", value, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atGreaterThan(Date value) {
            addCriterion("updated_at >", value, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atGreaterThanOrEqualTo(Date value) {
            addCriterion("updated_at >=", value, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atLessThan(Date value) {
            addCriterion("updated_at <", value, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atLessThanOrEqualTo(Date value) {
            addCriterion("updated_at <=", value, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atIn(List<Date> values) {
            addCriterion("updated_at in", values, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atNotIn(List<Date> values) {
            addCriterion("updated_at not in", values, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atBetween(Date value1, Date value2) {
            addCriterion("updated_at between", value1, value2, "updated_at");
            return (Criteria) this;
        }

        public Criteria andUpdated_atNotBetween(Date value1, Date value2) {
            addCriterion("updated_at not between", value1, value2, "updated_at");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table device
     *
     * @mbg.generated do_not_delete_during_merge Wed Jun 07 14:47:42 CST 2023
     */
    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table device
     *
     * @mbg.generated Wed Jun 07 14:47:42 CST 2023
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}