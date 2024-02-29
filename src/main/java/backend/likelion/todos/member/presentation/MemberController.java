package backend.likelion.todos.member.presentation;

import backend.likelion.todos.member.application.MemberService;
import backend.likelion.todos.member.presentation.request.MemberSignupRequest;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/members")
// TODO [1단계] 이 부분은 멤버의 컨트롤러 부분입니다. 어떤 컴포넌트를 써야할까요?
// TODO [1단계] 생성자를 직접 구현하지 않고 쓰는 방법을 알아보세요. Constructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Void> signup(
            @Valid @RequestBody MemberSignupRequest request
    ) {
        Long memberId = memberService.signup(
                request.username(),
                request.password(),
                request.nickname(),
                request.profileImageUrl()
        );
        return ResponseEntity.created(URI.create("/members/" + memberId))
                .build();
    }
}
