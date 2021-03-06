package com.winston.entity;

import java.io.Serializable;

public class Parameter implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column parameter.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column parameter.param_name
     *
     * @mbg.generated
     */
    private String paramName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column parameter.param_type
     *
     * @mbg.generated
     */
    private String paramType;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column parameter.param_value
     *
     * @mbg.generated
     */
    private String paramValue;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column parameter.param_desc
     *
     * @mbg.generated
     */
    private String paramDesc;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table parameter
     *
     * @mbg.generated
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column parameter.id
     *
     * @return the value of parameter.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column parameter.id
     *
     * @param id the value for parameter.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column parameter.param_name
     *
     * @return the value of parameter.param_name
     *
     * @mbg.generated
     */
    public String getParamName() {
        return paramName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column parameter.param_name
     *
     * @param paramName the value for parameter.param_name
     *
     * @mbg.generated
     */
    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column parameter.param_type
     *
     * @return the value of parameter.param_type
     *
     * @mbg.generated
     */
    public String getParamType() {
        return paramType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column parameter.param_type
     *
     * @param paramType the value for parameter.param_type
     *
     * @mbg.generated
     */
    public void setParamType(String paramType) {
        this.paramType = paramType == null ? null : paramType.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column parameter.param_value
     *
     * @return the value of parameter.param_value
     *
     * @mbg.generated
     */
    public String getParamValue() {
        return paramValue;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column parameter.param_value
     *
     * @param paramValue the value for parameter.param_value
     *
     * @mbg.generated
     */
    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column parameter.param_desc
     *
     * @return the value of parameter.param_desc
     *
     * @mbg.generated
     */
    public String getParamDesc() {
        return paramDesc;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column parameter.param_desc
     *
     * @param paramDesc the value for parameter.param_desc
     *
     * @mbg.generated
     */
    public void setParamDesc(String paramDesc) {
        this.paramDesc = paramDesc == null ? null : paramDesc.trim();
    }
}