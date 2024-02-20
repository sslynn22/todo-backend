package backend.likelion.todos.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

import backend.likelion.todos.common.ConflictException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;

@DisplayName("회원 (Member) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberTest {

    private final MemberRepository memberRepository = mock(MemberRepository.class);
    private final MemberValidator validator = new MemberValidator(memberRepository);

    @Test
    void 회원가입_시_아이디가_중복되지_않으면_성공() {
        // given
        Member member = new Member(
                "username",
                "password",
                "spicy",
                "image"
        );
        BDDMockito.given(memberRepository.findByUsername("username"))
                .willReturn(Optional.empty());

        // when & then
        assertDoesNotThrow(() -> {
            member.signup(validator);
        });
    }

    @Test
    void 회원가입_시_아이디가_중복되면_오류가_발생한다() {
        // given
        Member member1 = new Member(
                "username",
                "password",
                "회원 1",
                "image"
        );
        Member member2 = new Member(
                "username",
                "password",
                "회원 2",
                "image"
        );
        BDDMockito.given(memberRepository.findByUsername("username"))
                .willReturn(Optional.of(member1));

        // when & then
        assertThatThrownBy(() -> {
            member2.signup(validator);
        }).isInstanceOf(ConflictException.class)
                .hasMessage("이미 존재하는 아이디입니다.");
    }
}
