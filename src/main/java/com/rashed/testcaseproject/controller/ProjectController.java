package com.rashed.testcaseproject.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rashed.testcaseproject.model.JsonViews;
import com.rashed.testcaseproject.model.Office;
import com.rashed.testcaseproject.model.Project;
import com.rashed.testcaseproject.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @PostMapping
    public Project add(@RequestBody Project project) {
        return service.add(project);
    }

    @PutMapping
    public Project update(@RequestBody Project prj) {
        return service.update(prj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        service.projectRemove(id);
    }

//    @JsonView(JsonViews.Brief.class)
    @GetMapping("/get/{id}")
    public Project get(@PathVariable String id){
        return service.getId(id);
    }

    @JsonView(JsonViews.Brief.class)
    @GetMapping("/get/all")
    public List<Project> getAll(){
        return service.getAll();
    }

    @PostMapping("/office")
    public Office addOffice(@RequestBody Office office) {
        return service.add(office);
    }
}
