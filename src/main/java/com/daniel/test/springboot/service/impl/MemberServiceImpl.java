package com.daniel.test.springboot.service.impl;

import com.daniel.test.springboot.entity.Member;
import com.daniel.test.springboot.mapper.MemberMapper;
import com.daniel.test.springboot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>描述内容</p>
 *
 * @author Daniel_Du
 * @since 2017/9/18 17:23
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Member getMember(long memberId) {
        return memberMapper.selectMemberById(memberId);
    }
}
