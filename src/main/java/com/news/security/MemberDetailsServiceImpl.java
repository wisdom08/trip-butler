package com.news.security;

import com.news.entity.Member;
import com.news.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + email));

        if (findMember != null) {
            return MemberDetailsImpl.builder()
                    .email(findMember.getEmail())
                    .password(findMember.getPassword())
                    .nickname(findMember.getNickname())
                    .imageUrl(findMember.getImageUrl())
                    .build();
        }
        return null;
    }
}
