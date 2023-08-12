package just.springsecurity.form;

import just.springsecurity.account.Account;
import just.springsecurity.account.AccountContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SampleService {

    public void dashboard() {
        // 현재 로그인한 계정 정보를 참조하고 싶다!
        // principal 꺼내와야 됨 (SecurityContextHolder만 기억하면 됨)
        // principal은 임의의 객체타입 Object 이긴하지만 사실상 User(UserDetails) 타입

        // principal : 애플리케이션 인증한 사용자 정보
        // credentials : 지워져있음 - 인증 후에 지우는 듯?
        // authorities : 해당 사용자의 권한을 의미, 권한은 여러개 일 수 있으니 collection
        // isAuthenticated() : 해당 사용자의 인증 여부

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Object credentials = authentication.getCredentials();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean authenticated = authentication.isAuthenticated();

        Account account = AccountContext.getAccount();
        System.out.println("=========================");
        System.out.println("account.getUsername() = " + account.getUsername());
        System.out.println("=========================");
    }
}
