package com.daniel.test.springboot.service;


import com.daniel.test.springboot.entity.Member;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/7/6 16:05
 */
public interface MemberService {

    public Member getMember(long memberId);
}
