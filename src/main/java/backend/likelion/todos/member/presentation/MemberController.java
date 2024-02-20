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

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
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
