package com.winston.mapper;

import com.winston.entity.GroupUserProject;
import com.winston.entity.GroupUserProjectExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GroupUserProjectMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    long countByExample(GroupUserProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    int deleteByExample(GroupUserProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    int insert(GroupUserProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    int insertSelective(GroupUserProject record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    List<GroupUserProject> selectByExample(GroupUserProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GroupUserProject record, @Param("example") GroupUserProjectExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table group_user_project
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") GroupUserProject record, @Param("example") GroupUserProjectExample example);
}