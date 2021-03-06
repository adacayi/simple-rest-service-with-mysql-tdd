package com.sanver.trials.simplerestservicewithmysqltdd.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanver.trials.simplerestservicewithmysqltdd.models.Student;
import com.sanver.trials.simplerestservicewithmysqltdd.models.StudentDTO;
import com.sanver.trials.simplerestservicewithmysqltdd.models.StudentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentController studentController;

    @Autowired
    private StudentRepository repository;

    @Test
    public void should_load_controller() {
        assertNotNull(studentController);
    }

    @Test
    public void should_load_repository() {
        assertNotNull(repository);
    }

    @Test
    public void should_get_student_with_id() throws Exception {
        int id = 1;
        ResultActions perform = mockMvc.perform(get(String.format("/students/%d", id)));
        perform.andDo(print()).andExpect(status().isOk());
        String actualJson = perform.andReturn().getResponse().getContentAsString();
        StudentDTO actual = objectMapper.readValue(actualJson, StudentDTO.class);
        assertNotNull(actual);
        assertThat(actual.getAge(),greaterThan(0));
    }

    @Test
    public void should_return_null_with_invalid_id() throws Exception {
        long id = Long.MAX_VALUE;
        ResultActions perform = mockMvc.perform(get(String.format("/students/%d", id)));
        perform.andDo(print()).andExpect(status().isOk()).andExpect(content().string(""));
    }

    @Test
    public void should_return_record_7to9_when_page_2_size_3() throws Exception {
        ResultActions perform = mockMvc.perform(get("/students?page=2&size=3"));
        perform.andDo(print()).andExpect(status().isOk());
        String actualJson = perform.andReturn().getResponse().getContentAsString();
        StudentDTO[] actual = objectMapper.readValue(actualJson, StudentDTO[].class);
        assertEquals(3, actual.length);
    }

    @Test
    public void should_save_new_student() throws Exception {
        StudentDTO student = new StudentDTO("Hatice", LocalDate.of(2002, 8, 1));
        String studentJson = objectMapper.writeValueAsString(student);
        ResultActions perform = mockMvc.perform(post("/students").
                contentType(MediaType.APPLICATION_JSON).content(studentJson));
        perform.andDo(print()).andExpect(status().isOk());
        perform.andExpect(content().json(studentJson));
    }

    @Test
    public void should_update_student() throws Exception {
        Student student = repository.findById(1L).orElse(null);
        assertNotNull(student);
        student.setBirthDate(student.getBirthDate().minusYears(3).minusMonths(2));
        student.setName(student.getName() + " updated");
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        String studentJson = objectMapper.writeValueAsString(studentDTO);
        ResultActions perform = mockMvc.perform(put(String.format("/students/%d", student.getId())).
                contentType(MediaType.APPLICATION_JSON).content(studentJson));
        perform.andDo(print()).andExpect(status().isOk());
        perform.andExpect(content().json(studentJson));
    }

    @Test
    public void should_not_update_with_invalid_id() throws Exception {
        Student student = repository.findById(1L).orElse(null);
        assertNotNull(student);
        student.setBirthDate(student.getBirthDate().minusYears(3).minusMonths(2));
        student.setName(student.getName() + " updated");
        StudentDTO studentDTO = new StudentDTO();
        BeanUtils.copyProperties(student, studentDTO);
        String studentJson = objectMapper.writeValueAsString(studentDTO);
        ResultActions perform = mockMvc.perform(put(String.format("/students/%d", Long.MAX_VALUE)).
                contentType(MediaType.APPLICATION_JSON).content(studentJson));
        perform.andDo(print()).andExpect(status().isOk());
        perform.andExpect(content().string(""));
    }

    @Test
    public void should_delete_student() throws Exception {
        long id = 2L;
        // All tests are run on the same database with a specific order.
        // Hence this might be executed before an update test and a record might not be found to update
        // because it is deleted first. So delete a record that will not be used in other tests.
        Student student = repository.findById(id).orElse(null);
        assertNotNull(student);
        ResultActions perform = mockMvc.perform(delete(String.format("/students/%d", student.getId())));
        perform.andDo(print()).andExpect(status().isOk());
        assertFalse(repository.findById(id).isPresent());
    }
}
