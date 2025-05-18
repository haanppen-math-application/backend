package com.hpmath.client.member;

import com.hpmath.common.Role;
import java.time.Duration;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
        restClient = RestClient.builder()
                .requestFactory(timeoutConfigurationFactory())
                .baseUrl(baseUrl)
                .build();
    }

    private static ClientHttpRequestFactory timeoutConfigurationFactory() {
        final SimpleClientHttpRequestFactory clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(Duration.ofSeconds(1));
        clientHttpRequestFactory.setReadTimeout(Duration.ofSeconds(1));
        return clientHttpRequestFactory;
    }

    public boolean isMatch(final Long memberId, final Role... roles) {
        MemberRole body = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/member/role")
                        .queryParam("memberId", memberId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, rep) -> log.warn("there is no member with id {}", memberId))
                .body(MemberRole.class);
        log.debug("role Sets: {}, target Role: {}", Arrays.toString(roles), body.role);
        return Arrays.asList(roles).contains(body.role);
    }

    public MemberInfo getMemberDetail(final Long memberId) {
        final MemberInfo memberInfo = restClient.get()
                .uri(uriBuilder -> uriBuilder.path("/api/inner/v1/member")
                        .queryParam("memberId", memberId)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, rep) -> log.warn("there is no member with id {}", memberId))
                .body(MemberInfo.class);
        log.debug("memberInfo: {}", memberInfo);
        return memberInfo;
    }

    public record MemberInfo(
            Long memberId,
            String memberName,
            Integer memberGrade,
            Role role
    ) {
    }

    private record MemberRole(
            Role role
    ) {
    }
}
