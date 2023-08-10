package just.springsecurity.account;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    AccountService accountService;
    @Autowired
    MockMvc mockMvc;

    @Test @WithAnonymousUser
    @DisplayName("index 페이지에 익명유저접근 성공 - 익명 user 어노테이션 사용")
    void index_anonymous() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @WithMockUser(username = "account", roles = "USER")
    @DisplayName("index 페이지에 일반유저접근 성공 - mock user 어노테이션 사용")
    void index_user_1() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @WithUserAccount
    @DisplayName("index 페이지에 일반유저접근 성공 - 커스텀 어노테이션 사용")
    void index_user_2() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("admin 페이지에 일반유저접근 실패 - 테스트 코드에 user 명시")
    void admin_user() throws Exception {
        mockMvc.perform(get("/admin").with(user("account").roles("USER")))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test @WithMockUser(username = "account", roles = "ADMIN")
    @DisplayName("admin 페이지에 어드민유저접근 성공")
    void admin_admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void form_login_success() throws Exception {
        String username = "account";
        String password = "123";
        String role = "USER";

        Account account = accountService.save(new Account(username, password, role));

        mockMvc.perform(formLogin().user(account.getUsername()).password(password))
                .andExpect(authenticated());
    }

    @Test
    @Transactional
    void form_login_fail() throws Exception {
        String username = "account";
        String password = "123";
        String role = "USER";

        Account account = accountService.save(new Account(username, password, role));

        mockMvc.perform(formLogin().user(account.getUsername()).password(password + "XXX"))
                .andExpect(unauthenticated());
    }

}