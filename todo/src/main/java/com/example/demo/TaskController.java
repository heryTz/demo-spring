package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
public class TaskController {
  private final TaskRepository repository;

  public TaskController(TaskRepository repository) {
    this.repository = repository;
  }
  
  @GetMapping("/tasks")
  List<Task> all() {
    return repository.findAll();
  }

  @GetMapping("/tasks/{id}")
  public Task one(@PathVariable Long id) {
    return repository.findById(id).orElseThrow(() -> new TaskNotFoundException(id));
  }

  @PostMapping("/tasks")
  public Task create(@RequestBody Task newTask) {
    return repository.save(newTask);
  }
  
  @PutMapping("/tasks/{id}")
  public Task replace(@PathVariable Long id, @RequestBody Task newTask) {
    return repository.findById(id)
      .map(task -> {
        task.setTitle(newTask.getTitle());
        return repository.save(task);
      })
      .orElseThrow(() -> new TaskNotFoundException(id));
  }
  
  @DeleteMapping("/tasks/{id}")
  void delete(@PathVariable Long id) {
    repository.deleteById(id);
  }
}
