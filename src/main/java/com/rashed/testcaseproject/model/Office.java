package com.rashed.testcaseproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "offices")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class Office  extends AbstractEntityStringId<Office> implements Serializable {
	private static final long serialVersionUID = 31664096368957957L;

	@NonNull
	String name;
	String layer;
	String division;
	String ministry;
	String address;

}
