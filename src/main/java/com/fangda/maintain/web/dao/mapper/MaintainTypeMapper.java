package com.fangda.maintain.web.dao.mapper;


import com.fangda.maintain.web.model.MaintainType;

public interface MaintainTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_type
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_type
     *
     * @mbggenerated
     */
    int insert(MaintainType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_type
     *
     * @mbggenerated
     */
    int insertSelective(MaintainType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_type
     *
     * @mbggenerated
     */
    MaintainType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(MaintainType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fd_maintain_type
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(MaintainType record);
}