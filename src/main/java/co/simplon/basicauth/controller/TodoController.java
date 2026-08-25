package co.simplon.basicauth.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.basicauth.entity.TodoEntity;
import co.simplon.basicauth.repository.TodoRepository;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepositoryInjected) {
        this.todoRepository = todoRepositoryInjected;
    }

    @PreAuthorize("hasAnyAuthority('SCOPE_ROLE_USER', 'SCOPE_ROLE_ADMIN')")
    @GetMapping("")
    public List<TodoEntity> getAll() {
        return this.todoRepository.findAll();
    }

    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("")
    public TodoEntity create(@RequestBody TodoEntity entity) {
        return this.todoRepository.save(entity);
    }
}
