package com.rashed.testcaseproject.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Fund {

	/**
	 * Total fund amount (মোট খরচ) of allocation, release etc. in BDT.
	 */
	@Column(scale = 2)
	@NonNull
	BigDecimal total;

	/**
	 * Fund from GOB [p: জিওবি ] in BDT.
	 */
	@Column(scale = 2)
	BigDecimal gob;
	/**
	 * Own fund of agency [p: নিজস্ব অর্থায়ন / সংস্থার নিজস্ব অর্থ ] in BDT.
	 */
	@Column(scale = 2)
	BigDecimal own;
	/**
	 * Other source of fund in BDT. অন্যান্য
	 */
	@Column(scale = 2)
	BigDecimal other;
}
