package com.hpmath.client.member;

import com.hpmath.hpmathcore.Role;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@Slf4j
public class MemberClient {
    private final RestClient restClient;

    public MemberClient(
            @Value("${client.member.url:http://localhost:80}") String baseUrl
    ) {
        log.info("baseUrl initialized with  = {}", baseUrl);
        restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    public boolean isMatch(final Long memberId, final Role... roles) {
        MemberRole body = restClient.get()
                .uri("/api/inner/v1/member/role?memberId=" + memberId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, rep) -> log.warn("there is no member with id {}", memberId))
                .body(MemberRole.class);

        return Arrays.asList(roles).contains(body.role);
    }

    public MemberInfo getMemberDetail(final Long memberId) {
        return restClient.get()
                .uri("/api/inner/v1/member?memberId=" + memberId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, rep) -> log.warn("there is no member with id {}", memberId))
                .body(MemberInfo.class);
    }

    public record MemberInfo(
            Long memberId,
            String memberName,
            Integer memberGrade,
            String role
    ) {
    }

    private record MemberRole(
            Role role
    ) {
    }
}
