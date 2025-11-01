package com.app.todo_app.web;

import com.app.todo_app.model.Todo;
import com.app.todo_app.service.TodoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for web routes.
 */
@Controller
@RequestMapping("/")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * List todos. Optional filter param "filter" : all | pending | done
     */
    @GetMapping
    public String list(@RequestParam(value = "filter", required = false) String filter,
                       Model model) {
        model.addAttribute("todos", todoService.findAll(filter));
        model.addAttribute("filter", filter == null ? "all" : filter);
        return "list"; // renders src/main/resources/templates/list.html
    }

    /**
     * Show form for new todo
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("todo", new Todo());
        model.addAttribute("action", "/save");
        return "form";
    }

    /**
     * Save new todo or update existing
     */
    @PostMapping("/add")
    public String save(@Valid @ModelAttribute("todo") Todo todo,
                       BindingResult bindingResult,
                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", "/save");
            return "form";
        }
        todoService.save(todo);
        return "redirect:/";
    }

    /**
     * Edit page
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        Todo t = todoService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid todo Id:" + id));
        model.addAttribute("todo", t);
        model.addAttribute("action", "/save");
        return "form";
    }

    /**
     * Delete
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        todoService.deleteById(id);
        return "redirect:/";
    }

    /**
     * Inline mark as done
     */
    @GetMapping("/done/{id}")
    public String markDone(@PathVariable Long id, @RequestParam(value = "filter", required = false) String filter) {
        todoService.markAsDone(id);
        // keep current filter if present
        if (filter != null && !filter.isBlank()) {
            return "redirect:/?filter=" + filter;
        }
        return "redirect:/";
    }
}

