package backend.likelion.todos.member.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;


// TODO [1단계] MemberRepository는 저장소입니다. 어떤 컴포넌트로 해야할까요?
public class MemberRepository {

    private final Map<Long, Member> members = new HashMap<>();
    private Long sequence = 1L;

    public Member save(Member member) {
        // TODO [1단계] 회원의 id를 sequence로 세팅한 후, sequence 값을 1 증가시킵니다.
        // TODO [1단계] members 에 member의 id를 Key로, member를 value로 집어넣으세요.
        return member;
    }

    public Optional<Member> findById(Long id) {
        // TODO [1단계] members에서 id에 해당하는 member를 찾으세요.
        // TODO [1단계] 찾은 member는 null일 수도 있습니다. NullPointException 방지를 위해 Optional로 감싸 반환하세요.
        return null;
    }

    public Optional<Member> findByUsername(String username) {
        // TODO [1단계] stream을 이용합니다.
        // TODO [1단계] members에서 인자로 들어온 username을 갖는 member를 찾으세요.
        // TODO [1단계] 힌트: stream, filter, findAny를 사용합니다.
        return null;
    }

    // for test
    public void clear() {
        this.members.clear();
    }
}
