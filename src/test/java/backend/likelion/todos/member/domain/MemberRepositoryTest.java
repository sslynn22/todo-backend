package backend.likelion.todos.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@DisplayName("회원 레포지토리 (MemberRepository) 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 주어진_회원에_Id를_세팅하고_저장한다() {
        // given
        Member member = new Member(
                "username",
                "1234",
                "Spicy",
                "이미지"
        );

        // when
        Member saved = memberRepository.save(member);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void id로_회원_정보를_조회한다() {
        // given
        Member member = new Member(
                "username",
                "1234",
                "Spicy",
                "이미지"
        );
        Member saved = memberRepository.save(member);

        // when
        Optional<Member> found = memberRepository.findById(saved.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getNickname()).isEqualTo("Spicy");
    }

    @Test
    void id로_회원_조회_시_id_정보가_없으면_Optional_empty_를_반환한다() {
        // given
        Member member = new Member(
                "username",
                "1234",
                "Spicy",
                "image"
        );
        Member saved = memberRepository.save(member);

        // when
        Optional<Member> found = memberRepository.findById(999L);

        // then
        assertThat(found).isEmpty();
    }

    @Test
    void 닉네임으로_회원_정보를_조회한다() {
        // given
        Member member = new Member(
                "username",
                "1234",
                "Spicy",
                "image"
        );
        Member saved = memberRepository.save(member);

        // when
        Optional<Member> found = memberRepository.findByUsername("empty");

        // then
        assertThat(found).isEmpty();
    }
}
