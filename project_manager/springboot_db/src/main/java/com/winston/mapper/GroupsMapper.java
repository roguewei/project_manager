package com.winston.mapper;

import com.winston.entity.Groups;
import com.winston.entity.GroupsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    long countByExample(GroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int deleteByExample(GroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int insert(Groups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int insertSelective(Groups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    List<Groups> selectByExample(GroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    Groups selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Groups record, @Param("example") GroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Groups record, @Param("example") GroupsExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Groups record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table groups
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Groups record);
}