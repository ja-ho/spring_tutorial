package Jaho.springtutorial.service;

import Jaho.springtutorial.domain.Member;
import Jaho.springtutorial.repository.MemberRepository;
import Jaho.springtutorial.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

//    MemberService memberService = new MemberService();
//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();     //store가 static으로 선언되어 있기 때문.
    // class에서 static으로 선언한 멤버는 인스턴스가 다르더라도 인스턴스끼리 같은 주소(초기에 할당된 static 변수)를 공유
    // 근데 현재는 static으로 class 관점에서는 같은 공간이라 clear가 되지만 테스트 관점에서는 다른 instance의 repository를 사용하고 있기 때문에 좀 애매
    // 직접 new 하지 않고 constructor에서 외부에서 repository를 넣어주면 이 문제가 해결.
    // memberService 입장에서 볼 때 직접 new 하지 않고 외부에서 넣어주는 걸 dependency injection: DI라고 한다.

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {      //@BeforeEach는 @AfterEach와 반대로 모든 테스트 시작 전에 실행된다.
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

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

//        try {
//            memberService.join(member2);
//            fail(); //여기까지 내려오면 안 돼
//        } catch(IllegalStateException e) {
//            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
//        } try exception 을 사용하기는 좀 애매. 좋은 문법을 제공하고 있다. 아래 예제

        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));//parameter (class, lambda)
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then

    }


}