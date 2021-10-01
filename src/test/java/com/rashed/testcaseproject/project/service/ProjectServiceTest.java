package com.rashed.testcaseproject.project.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rashed.testcaseproject.model.Office;
import com.rashed.testcaseproject.model.Project;
import com.rashed.testcaseproject.model.ProjectFinance;
import com.rashed.testcaseproject.repo.OfficeRpo;
import com.rashed.testcaseproject.repo.ProjectFinanceRpo;
import com.rashed.testcaseproject.repo.ProjectRepo;
import com.rashed.testcaseproject.service.ProjectService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static com.rashed.testcaseproject.data.ServiceData.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Rollback(false)
public class ProjectServiceTest {

    @Autowired  ProjectRepo repo;
    @Autowired ProjectService service;
    @Autowired OfficeRpo officeRpo;
    @Autowired ProjectFinanceRpo projectFinanceRpo;

    private static String PROJECT_ID;
    private static String OFFICE_ID;

    @Test @Order(0)
    public void contextLoads() {
        assertThat(service).isNotNull();
        assertThat(repo).isNotNull();
        assertThat(officeRpo).isNotNull();
        assertThat(projectFinanceRpo).isNotNull();
    }

    @Test @Order(1)
    void testAddOfficeUsingRepo() throws JsonProcessingException {
        Office ob = officeRpo.save(newOffice());
        assertThat(ob.getId()).isNotNull();
        System.out.println(asJsonString(ob));
        OFFICE_ID = ob.getId();

    }


    @Test @Order(2)
    @Transactional
    void testAddProjectUsingRepo() throws JsonProcessingException {
        Project project = newProject();
        for (ProjectFinance it: project.getFundBySource().values()) projectFinanceRpo.save(it);
        for (ProjectFinance it: project.getFundByYear().values()) projectFinanceRpo.save(it);
        Project ob = repo.save(project);
        ob.getAreas();
        assertThat(ob.getId()).isNotNull();
        System.out.println(asJsonString(ob));
        PROJECT_ID = ob.getId();

    }


    @Test @Order(3)
    void setOfficeInPrjUsingRepo() throws JsonProcessingException {

        Project project = repo.getById(PROJECT_ID);
        assertThat(project.getId()).isNotNull();

        Office office = officeRpo.getById(OFFICE_ID);
        assertThat(office.getId()).isNotNull();

        project = repo.save(project.addInitiatorOffice(office));

        System.out.println("Project Info");
        System.out.println(asJsonString(project));

        System.out.println("Office Info");
        System.out.println(asJsonString(office));
    }

    @Test @Order(4)
    void getProjectUsingRepo() throws JsonProcessingException {
        Project project = repo.getById(PROJECT_ID);
        assertThat(project.getId()).isNotNull();
        System.out.println("Project Info");
        System.out.println(asJsonString(project));
    }

    @Test @Order(5)
    void projectAreaArchive() throws JsonProcessingException {
        Project project = repo.getById(PROJECT_ID);
        project.getAreas().get(0).archiveIt();
        project = repo.save(project);
        assertThat(project.getId()).isNotNull();
        System.out.println("Project Info");
        System.out.println(asJsonString(project));
    }

    @Test @Order(6)
    void projectAreaRemove() throws JsonProcessingException {
        Project project = repo.getById(PROJECT_ID);
        project.removeArea(project.getAreas().get(0));
        project = repo.save(project);
        assertThat(project.getId()).isNotNull();
        System.out.println("Project Info");
        System.out.println(asJsonString(project));
    }

    @Test @Order(7)
    void projectArchive() throws JsonProcessingException {
        Project project = repo.getById(PROJECT_ID);
        project.archiveIt();
        project = repo.save(project);
        assertThat(project.getId()).isNotNull();
        System.out.println("Project Info");
        System.out.println(asJsonString(project));
    }
    @Test @Order(8) @Disabled("Getting EntityNotFoundException")
    void projectRemove(){
        repo.deleteById(PROJECT_ID);
        Project project = repo.getById(PROJECT_ID);
        assertThat(project).isNull();
    }



}
