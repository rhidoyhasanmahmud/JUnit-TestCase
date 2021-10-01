package com.rashed.testcaseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static javax.persistence.CascadeType.*;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@Table(name = "projects")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class Project extends AbstractEntityStringId<Project> implements Serializable {
    private static final long serialVersionUID = -178066393241147551L;

    /** Project name or title (in Bangla). প্রকল্প শিরোনাম */
    @JsonView({ JsonViews.Brief.class, JsonViews.Small.class })
    String name;
    /** Project name (in English) */
    String nameEn;
    /** Project revision remarks; e.g. ১ম সংশোধিত,২য় সংশোধিত, আন্তঃ অঙ্গব্যয় সমন্বয়  etc. */
    String revision;
    /** Project office name or title. All project employees belongs to this office. @see #officeId */
    @JsonView(JsonViews.Brief.class)
    String office;
    /** Project director and leader. প্রকল্প পরিচালক */
    @JsonView(JsonViews.Brief.class)
    String projectDirector;
    /** Total estimated project cost (approved). প্রকল্পের প্রাক্কলিত ব্যয়। */
    @Column(scale = 2)
    BigDecimal cost;
    /** Project identification code (প্রকল্প সনাক্তকরণ কোড) assigned by Finance Department (অর্থ বিভাগ) */
    String identityCode;

    /** Sponsoring ministry or division. উদ্যোগী মন্ত্রনালয়/বিভাগ */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "prj_office_sponsor",
            joinColumns = { @JoinColumn(name = "project_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "office_id", referencedColumnName = "id") })
    Set<Office> initiatorOffice = new HashSet<>();

    /** Project implementation areas or locations */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "project", fetch=FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval=true)
    List<ProjectArea> areas = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    ProjectFinance fundOverview;

//    @JsonIgnore
//    @Setter(AccessLevel.PRIVATE)
//    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = { PERSIST, MERGE, DETACH, REFRESH })
//    List<UserEntity> members;

    /** List of files (documents, images etc.) attached to the project */
    @Convert(converter = CollectionToStringConverter.class)
    @Column(columnDefinition = "varchar(1600)")
    List<String> attachments;

    /** Fund breakdown by {@link FundSource} */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="prj_funding_source")
    @MapKeyColumn(name="source")
    @MapKeyEnumerated(EnumType.STRING)
    Map<FundSource, ProjectFinance> fundBySource = new HashMap<>();

    /** Fund breakdown by financial year */
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name="prj_funding_year")
    @MapKeyColumn(name="year")
    Map<String, ProjectFinance> fundByYear = new HashMap<>();

    @Column(columnDefinition="TEXT")
    String activities;
    String officeId;
    String projectDirectorEmpId;
    LocalDate startDate;
    LocalDate finishDate;
    LocalDate approveDate;
    LocalDate closeDate;

    public Project addInitiatorOffice(Office office) {
        initiatorOffice.add(office);
        return this;
    }

    public Project addInitiatorOfficeList(List<Office> officeList) {
        for (Office it : officeList) initiatorOffice.add(it);
        return this;
    }

    public Project addProjectArea(ProjectArea area) {
        area.setProject(this);
        this.areas.add(area);
        return this;
    }
    public Project addProjectAreaList(List<ProjectArea> areas) {
        this.areas = areas;
        for (ProjectArea it: this.areas) it.setProject(this);
        return this;
    }

    public Project removeArea(ProjectArea area){
        this.getAreas().remove(area);
        return this;
    }

    public Project areaArchive(ProjectArea area){
        this.getAreas().stream()
                .filter(ar -> ar == area)   // filtering area
                .map(ob -> ob.archived = true) // archived updated
                .forEach(System.out::println); // archived print
        return this;
    }

    public Project setProjectArea(List<ProjectArea> list) {
        areas = list;
        for (ProjectArea it : list) it.setProject(this);
        return this;
    }

    public Project setFundOverview(ProjectFinance fund) {
        this.fundOverview = fund;
        return this;
    }

    public Project setFundBySource(Map<FundSource, ProjectFinance> funds) {
        this.fundBySource = funds;
        return this;
    }

    public Project setFundByYear(Map<String, ProjectFinance> funds) {
        this.fundByYear = funds;
        return this;
    }


}
