package backend.likelion.todos.member;

import backend.likelion.todos.common.ConflictException;
import backend.likelion.todos.common.NotFoundException;
import backend.likelion.todos.common.UnAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원 가입을 처리하고, 저장된 회원의 ID를 반환합니다.
    public Long signup(String username, String password, String nickname, String profileImageUrl) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("해당 아이디로 이미 가입한 회원이 있습니다");
        }
        Member member = new Member(username, password, nickname, profileImageUrl);
        return memberRepository.save(member).getId();
    }

    // 로그인을 처리하고, 로그인한 회원의 ID를 반환합니다.
    public Long login(String username, String password) {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UnAuthorizedException("존재하지 않는 아이디입니다"));
        member.login(password);
        return member.getId();
    }

    // 회원 ID로 회원 정보를 조회하고, 그 결과를 MemberResponse로 반환합니다.
    public MemberResponse findById(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원 정보가 없습니다"));
        return new MemberResponse(member.getId(), member.getUsername(), member.getNickname(), member.getProfileImageUrl());
    }
}
