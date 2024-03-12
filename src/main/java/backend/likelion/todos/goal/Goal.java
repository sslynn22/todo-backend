package backend.likelion.todos.goal;

import backend.likelion.todos.common.ForbiddenException;
import backend.likelion.todos.member.Member;
import lombok.Getter;

@Getter
public class Goal {

    private Long id;
    private String name;
    private String color;
    private Member member;

    public Goal(String name, String color, Member member) {
        this.name = name;
        this.color = color;
        this.member = member;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 주어진 멤버가 현재 멤버와 동일한지 검증하고, 아니라면 ForbiddenException을 발생시킵니다.
    public void validateMember(Member member) {
        if (!this.member.equals(member)) {
            throw new ForbiddenException("해당 목표에 대한 권한이 없습니다.");
        }
    }

    // 이 객체의 이름과 색상을 업데이트합니다.
    public void update(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
