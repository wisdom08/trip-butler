package com.news.security;

import com.news.entity.Member;
import com.news.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class MemberDetailsServiceImpl implements MemberDetailsService {

    @Autowired
    private MemberRepository memberRepository;

    public MemberDetailsServiceImpl() {

    }

    @Override
    public MemberDetails loadMemberByEmail(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> {
                    throw new EntityNotFoundException();
                }
        );
        return new MemberDetailsImpl(member);
    }
}
