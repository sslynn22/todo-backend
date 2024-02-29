package backend.likelion.todos.member.domain;

import backend.likelion.todos.common.ConflictException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


// TODO [1단계] 아래 클래스에서 생성자는 빠져있어요 하지만 오류가 없게 할 수 있습니다. 어떤 어노테이션을 쓸까요?
// TODO [1단계] Validator는 컴포넌트입니다. 등록해야겠죠?
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateDuplicatedUsername(String username) {
        if (memberRepository.findByUsername(username).isPresent()) {
            throw new ConflictException("이미 존재하는 아이디입니다.");
        }
    }
}
