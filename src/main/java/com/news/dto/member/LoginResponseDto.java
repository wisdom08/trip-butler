package com.news.dto.member;

import co.elastic.clients.elasticsearch.security.GetTokenRequest;
import com.news.entity.Member;
import com.news.entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

    private String grantType;
    private String accessToken;

    private String email;
    private String nickname;
    private String imageUrl;

}
