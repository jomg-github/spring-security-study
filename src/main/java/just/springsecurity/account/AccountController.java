package just.springsecurity.account;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

//    @PostConstruct
//    public void postConstruct() {
//        accountService.save(new Account("account1", "123", "USER"));
//        accountService.save(new Account("account2", "123", "USER"));
//        accountService.save(new Account("account3", "123", "USER"));
//        accountService.save(new Account("account4", "123", "USER"));
//
//        accountService.save(new Account("admin", "123", "ADMIN"));
//    }
}
