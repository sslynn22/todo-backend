package backend.likelion.todos.member.application;

import backend.likelion.todos.member.domain.Member;
import backend.likelion.todos.member.domain.MemberRepository;
import backend.likelion.todos.member.domain.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


// TODO [1단계] 이 클래스는 서비스 클래스입니다. 어떤 컴포넌트로 해야할까요?
// TODO [1단계] 생성자를 알아서 만들어주는 어노테이션이 있습니다.
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    public Long signup(
            String username,
            String password,
            String nickname,
            String profileImageUrl
    ) {
        Member member = new Member(username, password, nickname, profileImageUrl);
        member.signup(memberValidator);
        Member saved = memberRepository.save(member);
        return saved.getId();
    }
}
