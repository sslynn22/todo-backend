package backend.likelion.todos.todo;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class TodoRepository {

    private final Map<Long, Todo> todos = new HashMap<>();
    private Long id = 1L;

    // Todo를 저장하고 저장된 Todo를 반환합니다.
    public Todo save(Todo todo) {
        todo.setId(id);
        todos.put(id, todo);
        id++;
        return todo;
    }

    // 주어진 id로 Todo를 찾아 Optional로 반환합니다.
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todos.get(id));
    }

    // 모든 Todo를 삭제합니다.
    public void clear() {
        todos.clear();
    }

    // 주어진 Todo를 삭제합니다.
    public void delete(Todo todo) {
        todos.remove(todo.getId());
    }

    // 특정 회원 ID와 날짜에 해당하는 모든 Todo를 찾아 리스트로 반환합니다.
    public List<Todo> findAllByMemberIdAndDate(Long memberId, YearMonth yearMonth) {
        List<Todo> memberTodos = new ArrayList<>();
        for (Todo todo : todos.values()) {
            if (todo.getGoal().getMember().getId().equals(memberId)
                && todo.getDate().getYear() == yearMonth.getYear()
                && todo.getDate().getMonth() == yearMonth.getMonth())
            {
                memberTodos.add(todo);
            }
        }
        Collections.sort(memberTodos, Comparator.comparing(Todo::getDate));
        return memberTodos;
    }
}
