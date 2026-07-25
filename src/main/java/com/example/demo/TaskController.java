package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "*") // Allows our future frontend to talk to this backend
public class TaskController {
	
	@Autowired
	private TaskRepository taskRepository;
	
	// 1. GET ALL TASKS
	@GetMapping
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}
	
	// 2. CREATE A TASK
	@PostMapping
	public Task createTask(@RequestBody Task task) {
		return taskRepository.save(task);
	}
	
	// 3. TOGGLE COMPLETED STATUS
    @PutMapping("/{id}")
    public Task toggleTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        
        // If it's true, make it false. If it's false, make it true.
        task.setCompleted(!task.isCompleted()); 
        
        return taskRepository.save(task);
    }
	
	// 4. DELETE A TASK
	@DeleteMapping("/{id}")
	public String deleteTask(@PathVariable Long id) {
		taskRepository.deleteById(id);
		return "Task with ID " + id + " was deleted succesfully.";
	}
}
