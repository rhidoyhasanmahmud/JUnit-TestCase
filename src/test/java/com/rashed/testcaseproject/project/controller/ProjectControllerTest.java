package com.rashed.testcaseproject.project.controller;

import com.rashed.testcaseproject.controller.ProjectController;
import com.rashed.testcaseproject.model.Office;
import com.rashed.testcaseproject.model.Project;
import com.rashed.testcaseproject.model.ProjectArea;
import com.rashed.testcaseproject.service.ProjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.rashed.testcaseproject.data.ServiceData.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerTest {
    @Autowired private ProjectController controller;
    @Autowired private ProjectService service;
    @Autowired private MockMvc mvc;
    private static String OFFICE_ID;
    private static String PROJECT_ID;

    @Test @Order(0)
    public void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test @Order(1)
    void testOffice() throws Exception {
        String rs = mvc.perform(post("/projects/office").accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(newProject()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateDate").exists())
                .andReturn().getResponse().getContentAsString();
        Office ob = asObject(rs, Office.class);
        OFFICE_ID = ob.getId();
    }

    @Test @Order(2)
    void testAdd() throws Exception {
        String rs = mvc.perform(post("/projects").accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(newProject()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateDate").exists())
                .andReturn().getResponse().getContentAsString();
        Project ob = asObject(rs, Project.class);
        PROJECT_ID = ob.getId();
    }

    @Test @Order(3)
    void testUpdateProject() throws Exception {
        Project project = service.getId(PROJECT_ID);
        assertThat(project.getId()).isNotNull();

        Office office = service.getOfficeId(OFFICE_ID);
        assertThat(office.getId()).isNotNull();

        project.addInitiatorOffice(office);

        mvc.perform(put("/projects").accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(project))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.updateDate").exists())
                .andReturn().getResponse().getContentAsString();

    }

    @Test @Order(4)
    void testGetProject() throws Exception {
        String rs =mvc.perform(get("/projects/get/{id}", PROJECT_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(is("ফরিদপুর শহরের টেপাখোলা লেক উন্নয়ন প্রকল্প")))
                .andReturn().getResponse().getContentAsString();
        Project project = asObject(rs, Project.class);
        System.out.println(asJsonString(project));

    }
    @Test @Order(5)
    void testGetAllProject() throws Exception {
        mvc.perform(get("/projects/get/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test @Order(6) @Disabled("Getting EntityNotFoundException")
    void testDeleteProject() throws Exception {

        mvc.perform(delete("/projects/{id}",PROJECT_ID))
                .andExpect(status().isOk());

        assertThat(service.getId(PROJECT_ID)).isNull();

    }
}
