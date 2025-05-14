package com.hpmath.common.web;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;


/**
 * 테스트 환경에서 보안 인증 및 인가 처리를 간소화하기 위한 설정 어노테이션입니다.
 * <p>
 * 이 어노테이션을 테스트 클래스에 선언하면,
 * {@link WebAuthorizationTestConfiguration}이 자동으로 등록되어,
 * 실제 서비스에 적용되는 보안 필터에 추가로  테스트 전용 인증 필터를 추가합니다.
 * <p>
 * 구체적으로는 {@code request.setAttribute("role", ...)} 를 통해
 * 테스트용 {@link com.hpmath.common.web.authentication.MemberPrincipal}을 주입하며, 컨트롤러 테스트를 가능하도록 합니다.
 *
 * <p><strong>사용 예시:</strong></p>
 * <pre>{@code
 * @SpringBootTest
 * @TestAuthorization
 * class UserControllerTest {
 *
 *     @Autowired
 *     private MockMvc mockMvc;
 *
 *     @Test
 *     void testAdminAccess() throws Exception {
 *         mockMvc.perform(get("/api/admin")
 *                 .requestAttr("role", Role.ADMIN))
 *                 .andExpect(status().isOk());
 *     }
 * }
 * }</pre>
 *
 * <p>이 어노테이션은 테스트 클래스에만 적용되며, 서비스 코드에는 절대 사용하지 않아야 합니다.</p>
 *
 * @see WebAuthorizationTestConfiguration
 * @see com.hpmath.common.web.authentication.MemberPrincipal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(WebAuthorizationTestConfiguration.class)
public @interface TestAuthorization {
}
