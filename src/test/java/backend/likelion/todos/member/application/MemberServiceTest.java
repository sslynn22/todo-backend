package backend.likelion.todos.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import backend.likelion.todos.common.ConflictException;
import backend.likelion.todos.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@DisplayName("회원 서비스 (MemberService) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.clear();
    }

    @Test
    void 회원가입을_진행하고_생성된_회원의_id를_반환한다() {
        // when
        Long id = memberService.signup(
                "username",
                "password",
                "회원",
                "image"
        );

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 회원가입_시_아이디가_중복되면_예외() {
        // given
        memberService.signup(
                "username",
                "password",
                "회원",
                "image"
        );

        // when & then
        assertThatThrownBy(() -> {
            memberService.signup(
                    "username",
                    "password",
                    "회원",
                    "image"
            );
        }).isInstanceOf(ConflictException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }
}
