package com.news.security;

public interface MemberDetailsService {

    MemberDetails loadMemberByEmail(String Email) throws EmailNotFoundException;
}
