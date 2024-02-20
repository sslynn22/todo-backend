package backend.likelion.todos.member.domain;

import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String profileImageUrl;

    public Member(String username, String password, String nickname, String profileImageUrl) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void signup(MemberValidator validator) {
        // TODO [1단계] validator를 사용하여 닉네임 중복 검사를 진행하세요.
    }
}
