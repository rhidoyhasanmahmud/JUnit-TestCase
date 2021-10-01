package com.rashed.testcaseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Year;

@Entity
@Table(name = "prj_funding")
@Getter
@Setter
@Accessors(chain = true)
public class ProjectFinance extends AbstractEntityStringId<ProjectFinance> implements Serializable {
	private static final long serialVersionUID = 3268719280479512720L;

	@JsonIgnore
	@Embedded
	Fund fund;

	/**
	 * Source of fund. e.g.
	 */
	@Enumerated(EnumType.STRING)
	FundSource source;

	/**
	 * Start of fiscal year
	 */
	Year startYear;
	/**
	 * end of fiscal year
	 */
	Year endYear;

	public ProjectFinance() {
		super();
		this.fund = new Fund();
	}

	public ProjectFinance(double total) {
		super();
		this.fund = new Fund(BigDecimal.valueOf(total));
	}

	public ProjectFinance setTotal(BigDecimal amount) {
		this.fund.setTotal(amount);
		return this;
	}

	public ProjectFinance setGob(BigDecimal amount) {
		this.fund.setGob(amount);
		return this;
	}

	public ProjectFinance setOwn(BigDecimal amount) {
		this.fund.setOwn(amount);
		return this;
	}

	public ProjectFinance setOther(BigDecimal amount) {
		this.fund.setOther(amount);
		return this;
	}
	public BigDecimal getTotal() {
		return this.fund.getTotal();
	}

	public BigDecimal getGob() {
		return this.fund.getGob();
	}

	public BigDecimal getOwn() {
		return this.fund.getOwn();
	}

	public BigDecimal getOther() {
		return this.fund.getOther();
	}

	public ProjectFinance total(double amount) {
		this.fund.setTotal(BigDecimal.valueOf(amount));
		return this;
	}

	public ProjectFinance gob(double amount) {
		this.fund.setGob(BigDecimal.valueOf(amount));
		return this;
	}

	public ProjectFinance own(double amount) {
		this.fund.setOwn(BigDecimal.valueOf(amount));
		return this;
	}

	public ProjectFinance other(double amount) {
		this.fund.setOther(BigDecimal.valueOf(amount));
		return this;
	}

	public ProjectFinance startYear(int year) {
		this.startYear = Year.of(year);
		return this;
	}

	public ProjectFinance endYear(int year) {
		this.endYear = Year.of(year);
		return this;
	}

	public ProjectFinance setFinancialYear(int year) {
		this.startYear(year).setEndYear(this.getStartYear().plusYears(1L));
		return this;
	}

	public ProjectFinance setFinancialYear(int start, int end) {
		return this.startYear(start).endYear(end);
	}

}
