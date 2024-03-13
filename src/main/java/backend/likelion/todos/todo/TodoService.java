package backend.likelion.todos.todo;


import static java.util.stream.Collectors.*;

import backend.likelion.todos.common.ForbiddenException;
import backend.likelion.todos.common.NotFoundException;
import backend.likelion.todos.goal.Goal;
import backend.likelion.todos.goal.GoalRepository;
import backend.likelion.todos.member.Member;
import backend.likelion.todos.member.MemberRepository;
import backend.likelion.todos.todo.TodoWithDayResponse.TodoResponse;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final MemberRepository memberRepository;
    private final GoalRepository goalRepository;
    private final TodoRepository todoRepository;

    // Todo를 저장하고 저장된 Todo의 ID를 반환합니다.
    public Long save(Long goalId, Long memberId, String content, LocalDate date) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new NotFoundException("목표 정보가 없습니다."));
        if (!goal.getMember().equals(member)) {
            throw new ForbiddenException("조회한 목표의 멤버가 다릅니다.");
        }
        Todo todo = new Todo(content, date, goal);
        return todoRepository.save(todo).getId();
    }

    // 주어진 Todo의 내용과 날짜를 업데이트합니다.
    public void update(Long todoId, Long memberId, String content, LocalDate date) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        Todo todo = todoRepository
            .findById(todoId).orElseThrow(() -> new NotFoundException("투두 정보가 없습니다."));
        if (!todo.getGoal().getMember().equals(member)) {
            throw new ForbiddenException("해당 투두에 대한 권한이 없습니다.");
        }
        todo.update(content, date);
    }

    // 주어진 Todo를 완료 상태로 표시합니다.
    public void check(Long todoId, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new NotFoundException("투두 정보가 없습니다."));
        if (!todo.getGoal().getMember().equals(member)) {
            throw new ForbiddenException("조회한 목표의 멤버가 다릅니다.");
        }
        todo.check();
    }

    // 주어진 Todo를 미완료 상태로 표시합니다.
    public void uncheck(Long todoId, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new NotFoundException("투두 정보가 없습니다."));
        if (!todo.getGoal().getMember().equals(member)) {
            throw new ForbiddenException("조회한 목표의 멤버가 다릅니다.");
        }
        todo.uncheck();
    }

    // 주어진 Todo를 삭제합니다.
    public void delete(Long todoId, Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new NotFoundException("회원 정보가 없습니다."));
        Todo todo = todoRepository.findById(todoId)
            .orElseThrow(() -> new NotFoundException("투두 정보가 없습니다."));
        if (!todo.getGoal().getMember().equals(member)) {
            throw new ForbiddenException("조회한 목표의 멤버가 다릅니다.");
        }
        todoRepository.delete(todo);
    }

    // 특정 회원 ID와 날짜에 해당하는 모든 Todo를 찾아 TodoWithDayResponse 리스트로 반환합니다.
    public List<TodoWithDayResponse> findAllByMemberIdAndDate(Long memberId, YearMonth date) {
        List<Todo> todos = todoRepository.findAllByMemberIdAndDate(memberId, date);
        Map<Integer, List<Todo>> todoWithDaysMap = todos.stream()
            .collect(Collectors.groupingBy(it -> it.getDate().getDayOfMonth()));
        List<TodoWithDayResponse> responses = new ArrayList<>();
        for (Entry<Integer, List<Todo>> todo : todoWithDaysMap.entrySet()) {
            List<TodoResponse> todoResponses = todo.getValue().stream()
                .map(it -> new TodoResponse(
                    it.getId(),
                    it.getContent(),
                    it.getGoal().getId(),
                    it.isCompleted()
                ))
                .toList();
            responses.add(new TodoWithDayResponse(todo.getKey(), todoResponses));
        }
        return responses;
    }
}
