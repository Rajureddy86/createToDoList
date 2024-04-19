package org.example.todolist.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.todolist.model.ToDo;
import org.example.todolist.repository.ToDoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class ToDoControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private ToDoRepository todoRepository;

    @InjectMocks
    private ToDoController todoController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testDeleteTodo_Success() throws Exception {
        Long id = 1L;
        when(todoRepository.deleteById(id)).thenReturn(1);

        mockMvc.perform(delete("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testDeleteTodo_NotFound() throws Exception {
        Long id = 1L;
        when(todoRepository.deleteById(id)).thenReturn(0); // No entities deleted

        mockMvc.perform(delete("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(todoRepository, times(1)).deleteById(id);
    }

    @Test
    public void testCreateTodo() throws Exception {
        ToDo todo = new ToDo();
        todo.setId(1L);
        todo.setDescription("Sample Todo");
        todo.setCompletionStatus(false);

        when(todoRepository.save(any(ToDo.class))).thenReturn(todo);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.description").value(todo.getDescription()))
                .andExpect(jsonPath("$.completionStatus").value(todo.isCompletionStatus()));

        verify(todoRepository, times(1)).save(any(ToDo.class));
    }

    @Test
    public void testUpdateTodo_Success() throws Exception {
        Long id = 1L;
        ToDo todo = new ToDo();
        todo.setDescription("Updated Description");
        todo.setCompletionStatus(true);

        when(todoRepository.update(anyLong(), any(ToDo.class))).thenReturn(1);

        mockMvc.perform(patch("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todo)))
                .andExpect(status().isOk());

        verify(todoRepository, times(1)).update(anyLong(), any(ToDo.class));
    }

    @Test
    public void testUpdateTodo_NotFound() throws Exception {
        Long id = 1L;
        ToDo todo = new ToDo();
        todo.setDescription("Nonexistent Description");
        todo.setCompletionStatus(false);

        when(todoRepository.update(anyLong(), any(ToDo.class))).thenReturn(0);

        mockMvc.perform(patch("/todos/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(todo)))
                .andExpect(status().isNotFound());

        verify(todoRepository, times(1)).update(anyLong(), any(ToDo.class));
    }

    @Test
    public void testGetAllTodos() throws Exception {

        ToDo todo1 = new ToDo();
        ToDo todo2 = new ToDo();
        List<ToDo> todos = Arrays.asList(todo1, todo2);

        when(todoRepository.findAll()).thenReturn(todos);


        mockMvc.perform(get("/todos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect((ResultMatcher) jsonPath("$[0].description", is(todo1.getDescription())))
                .andExpect((ResultMatcher) jsonPath("$[1].description", is(todo2.getDescription())));
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    public void testGetTodoById_Found() throws Exception {
        Long id = 1L;
        ToDo todo = new ToDo();
        todo.setId(id);
        todo.setDescription("Test ToDo");


        when(todoRepository.findById(eq(id))).thenReturn(todo);

        mockMvc.perform(get("/todos/{id}", id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(todo.getId().intValue())))
                .andExpect(jsonPath("$.description", is(todo.getDescription())));


        verify(todoRepository, times(1)).findById(eq(id));
    }



}
