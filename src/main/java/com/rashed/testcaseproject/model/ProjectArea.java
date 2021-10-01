package com.rashed.testcaseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "project_area")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class ProjectArea extends AbstractEntityStringId<ProjectArea> implements Serializable {
	private static final long serialVersionUID = -8527682844195484901L;

	/**
	 * Division (বিভাগ)
	 */
	String division;
	/**
	 * District (জেলা)
	 */
	String district;
	/**
	 * Upazila, City corporation or Municipality. উপজেলা, সিটি কর্পোরেশন, পৌরসভা
	 */
	String upazila;
	/**
	 * Union Parishad
	 */
	@Column(name = "union_parishad")
	String union;
	/**
	 * Project area / location; অঞ্চল
	 */
	String area;

	@JsonIgnore
//	@ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne
	@JoinColumn(name = "project_id",referencedColumnName = "id")
	Project project;

}
