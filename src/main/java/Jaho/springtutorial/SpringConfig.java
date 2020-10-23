package Jaho.springtutorial;

import Jaho.springtutorial.repository.MemberRepository;
import Jaho.springtutorial.repository.MemoryMemberRepository;
import Jaho.springtutorial.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}
