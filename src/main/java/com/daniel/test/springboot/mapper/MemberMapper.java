package com.daniel.test.springboot.mapper;

import com.daniel.test.springboot.entity.Member;
import org.apache.ibatis.annotations.Param;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/7/4 15:18
 */
public interface MemberMapper {

    Member selectMemberById(@Param("id") Long id);
}
