package backend.likelion.todos.goal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

@Repository
public class GoalRepository {

    private final Map<Long, Goal> goals = new HashMap<>();
    private Long id = 1L;

    // 목표(Goal)를 저장하고 저장된 목표를 반환합니다.
    public Goal save(Goal goal) {
        goal.setId(id);
        goals.put(id, goal);
        //id++;
        return goal;
    }

    // 주어진 id로 목표를 찾아 Optional로 반환합니다.
    public Optional<Goal> findById(Long id) {
        return Optional.ofNullable(goals.get(id));
    }

    // 모든 목표를 삭제합니다.
    public void clear() {
        goals.clear();
    }

    // 주어진 목표를 삭제합니다.
    public void delete(Goal goal) {
        goals.remove(goal.getId());
    }

    // 특정 회원 ID에 속하는 모든 목표를 찾아 리스트로 반환합니다.
    public List<Goal> findAllByMemberId(Long memberId) {
        return goals.values().stream()
            .filter(goal -> goal.getMember().getId().equals(memberId))
            .collect(Collectors.toList());
    }
}
