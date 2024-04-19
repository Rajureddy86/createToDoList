package org.example.todolist.integration;

import com.jayway.jsonpath.JsonPath;
import org.example.todolist.model.ToDo;
import org.example.todolist.repository.ToDoRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ToDoApiIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository todoRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testGetAllTodos() throws Exception {
        mockMvc.perform(get("/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testCreateTodo() throws Exception {
        ToDo newTodo = new ToDo();
        newTodo.setDescription("Test Todo");
        newTodo.setCompletionStatus(false);

        mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newTodo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("Test Todo")));
    }

    @Test
    public void testGetAndDeleteTodo() throws Exception {
        String todoJson = "{\"description\":\"Test Todo for GET and DELETE\",\"completionStatus\":false}";
        MvcResult result = mockMvc.perform(post("/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(todoJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseString = result.getResponse().getContentAsString();
        Integer createdTodoId = JsonPath.parse(responseString).read("$.id");

        mockMvc.perform(get("/todos/{id}", createdTodoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdTodoId)));

        mockMvc.perform(delete("/todos/{id}", createdTodoId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/todos/{id}", createdTodoId))
                .andExpect(status().isNotFound());
    }

}
