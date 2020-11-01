package Jaho.springtutorial.service;

import Jaho.springtutorial.domain.Member;
import Jaho.springtutorial.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

     //실제로 스프링 올려서 테스트하고 싶을때. 보통 통합 테스트. 스프링 컨테이너와 테스트를 함께 실행한다.
                    //단위 테스트로 쪼개서 순수 자바 코드로 테스트하는 것이 더 좋은 테스트일 가능성은 높다. 훨빠르기도 함
                    //통합 테스트는 어쩔 수 없는 경우에 하자. db, spring 올려서 해야 하는 경우
                    //단위 테스트에서는 보통 테스트 전용 가짜 repository를 만들어서 mockito 같은 가짜 객체 만들어주는 라이브러리 사용
@SpringBootTest
@Transactional
             //db에서 commit까지는 안하고 테스트 끝나면 자동으로 rollback 시켜줌. test는 반복할 수 있어야 한다.
             //db는 transaction이라는 개념이 있다. db에 데이터 insert query 후 commit을 해줘야 db에 반영이 있다. auto commit 모드도 있다.
             //test method 하나하나 마다 적용됨.
class MemberServiceIntegrationTest {
    //test니까 constructor로 DI 안하고 걍 Autowird. 어디서 가져다쓸거 아니니까
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;


    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("hello1");

        //when
        Long memberId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(memberId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void duplicate_member_exception() {
        //given
        Member member1 = new Member();
        member1.setName("hello1");

        Member member2 = new Member();
        member2.setName("hello1");

        //when
        memberService.join(member1);


        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//parameter (class, lambda)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }


}