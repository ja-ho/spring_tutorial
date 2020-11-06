package Jaho.springtutorial.service;

import Jaho.springtutorial.domain.Member;
import Jaho.springtutorial.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


//@Service
@Transactional  //for JPA 데이터 변경 및 업데이트
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    * 회원가입
    * */
    public Long join(Member member) {
        //중복 회원 검증
//        findByName으로 로직이 쭉 이어질 경우 리팩토링 측면에서 메서드로 빼주는 게 좋다.
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.");
//        });
        long start = System.currentTimeMillis();
        try {
            validateDuplicateMember(member);

            memberRepository.save(member);
            return member.getId();
        } finally {         //exception이 나더라도 finally는 무조건 들어오니까
            long finish = System.currentTimeMillis();
            long timeMS = finish - start;
            System.out.println("join = " + timeMS + "ms");
        }

    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /*
    * 전체 회원 조회
    * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
    * 개인 회원 조회
    * */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
