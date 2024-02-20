package backend.likelion.todos.member.application;

import backend.likelion.todos.member.domain.Member;
import backend.likelion.todos.member.domain.MemberRepository;
import backend.likelion.todos.member.domain.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
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
