package com.winston.mapper;

import com.winston.entity.Parameter;
import com.winston.entity.ParameterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ParameterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    long countByExample(ParameterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int deleteByExample(ParameterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int insert(Parameter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int insertSelective(Parameter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    List<Parameter> selectByExample(ParameterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    Parameter selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Parameter record, @Param("example") ParameterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Parameter record, @Param("example") ParameterExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Parameter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table parameter
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Parameter record);
}