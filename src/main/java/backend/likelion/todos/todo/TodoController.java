package backend.likelion.todos.todo;

import backend.likelion.todos.auth.Auth;
import java.net.URI;
import java.time.YearMonth;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/todos")
@RestController
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Auth Long memberId,
            @RequestBody TodoCreateRequest request
    ) {
        Long todoId = todoService.save(memberId, request.goalId(), request.content(), request.date());
        URI location = URI.create("/todos/" + todoId);
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{id}/check")
    public void check(
            @Auth Long memberId,
            @PathVariable("id") Long todoId
    ) {
        todoService.check(memberId, todoId);
    }

    @PostMapping("/{id}/uncheck")
    public void uncheck(
            @Auth Long memberId,
            @PathVariable("id") Long todoId
    ) {
        todoService.uncheck(memberId, todoId);
    }

    @PutMapping("/{id}")
    public void update(
            @Auth Long memberId,
            @PathVariable("id") Long todoId,
            @RequestBody TodoUpdateRequest request
    ) {
        todoService.update(memberId, todoId, request.content(), request.date());
    }

    @DeleteMapping("/{id}")
    public void delete(
            @Auth Long memberId,
            @PathVariable("id") Long todoId
    ) {
        todoService.delete(memberId, todoId);
    }

    @GetMapping("/my")
    public ResponseEntity<List<TodoWithDayResponse>> findMine(
            @Auth Long memberId,
            @RequestParam(value = "year", required = true) int year,
            @RequestParam(value = "month", required = true) int month
    ) {
        YearMonth yearMonth = YearMonth.of(year, month);
        List<TodoWithDayResponse> todos = todoService.findAllByMemberIdAndDate(memberId, yearMonth);
        return ResponseEntity.ok(todos);
    }
}
