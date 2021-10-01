package com.rashed.testcaseproject.service;

import com.rashed.testcaseproject.model.Office;
import com.rashed.testcaseproject.model.Project;
import com.rashed.testcaseproject.model.ProjectArea;
import com.rashed.testcaseproject.model.ProjectFinance;
import com.rashed.testcaseproject.repo.OfficeRpo;
import com.rashed.testcaseproject.repo.ProjectAreaRepo;
import com.rashed.testcaseproject.repo.ProjectFinanceRpo;
import com.rashed.testcaseproject.repo.ProjectRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j

public class ProjectService {

    @Autowired private OfficeRpo officeRpo;
    @Autowired private ProjectRepo repo;
    @Autowired private ProjectFinanceRpo projectFinanceRpo;
    @Autowired private ProjectAreaRepo areaRepo;

    public Office add(Office office){
        return officeRpo.save(office);
    }
    @Transactional
    public Project add(Project project) {
        for (ProjectArea it : project.getAreas()) areaRepo.save(it.setProject(project));
        for (ProjectFinance it: project.getFundBySource().values()) projectFinanceRpo.save(it);
        for (ProjectFinance it: project.getFundByYear().values()) projectFinanceRpo.save(it);
        return repo.save(project);
    }

    @Transactional
    public Project update(Project project) {
        for (ProjectArea it : project.getAreas()) areaRepo.save(it.setProject(project));

//        for (ProjectFinance it: project.getFundBySource().values()) projectFinanceRpo.save(it);
//        for (ProjectFinance it: project.getFundByYear().values()) projectFinanceRpo.save(it);
        return repo.save(project);
    }

    public Project getId(String id){
        return repo.getById(id);
    }

    public Office getOfficeId(String id){
        return officeRpo.getById(id);
    }

    public Project projectArchive(Project project){
        return repo.save(project.archiveIt());
    }

    public void projectRemove(String id){
        repo.deleteById(id);
    }

    public Project projectAreaArchive(Project project){
        project.getAreas().get(0).archiveIt();
        return repo.save(project);
    }
    public Project projectAreaRemove(Project project){
        project.removeArea(project.getAreas().get(0));
        return repo.save(project);
    }

    public List<Project> getAll(){
        return repo.findAll();
    }

   }
