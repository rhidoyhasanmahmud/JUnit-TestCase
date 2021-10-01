package com.rashed.testcaseproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Residence of common fields (attributes) for a standard JPA bean.
 * 
 * @author Imtiaz Rahi
 * @since 2021-01-18
 */
@MappedSuperclass
@Getter
public abstract class AbstractEntity<T extends AbstractEntity<T>> {
	/** Default list of ignore fields to use when copying bean or properties */
	public static final String[] IGNORE_FIELDS = new String[] { "id", "version", "archived", "archiveDate", "createDate", "updateDate", "updatedBy" };

	/** True when object is deleted or archived */
	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
    @Column(columnDefinition = "boolean default false")	
	Boolean archived;

	/** Keep record when (the date) the object was archived */
	@Column(columnDefinition = "timestamp default now()")
	@Setter(value = AccessLevel.PRIVATE)
	LocalDateTime archiveDate;

	/** Object creation date */
	@Column(columnDefinition = "timestamp not null default now()")
	@Setter(value = AccessLevel.PRIVATE)
	LocalDateTime createDate;

	/** Last update date */
	@Column(columnDefinition = "timestamp not null default now()")
	@Setter(value = AccessLevel.PRIVATE)
	LocalDateTime updateDate;

	/** Updated by user. Store GRP employee id here. */
	String updatedBy;

	/** JPA version number */
	@JsonView({ JsonViews.Brief.class, JsonViews.Small.class })
	@Column(columnDefinition = "integer not null default 0")
	@Version
	@Setter(value = AccessLevel.PRIVATE)
	long version;

	@PrePersist
	protected void beforeSave() {
		this.createDate = LocalDateTime.now();
		this.updateDate = LocalDateTime.now();
	}

	@PreUpdate
	public void beforeUpdate() {
		this.updateDate = LocalDateTime.now();
	}

	/**

	 * Returns whether the record is deleted (not live, archived) or not.
	 * 
	 * @return <code>true</code> if record is deleted, <code>false</code> otherwise
	 */
	@JsonIgnore
	public boolean isDeleted() {
		return isArchived();
	}

	/**

	 * Returns whether the record is archived (not live, deleted) or not.
	 * 
	 * @return <code>true</code> if record is archived, <code>false</code> otherwise
	 */
	public boolean isArchived() {
		return archived != null && archived == true;
	}

	/**
	 * Archives the current object instance and returns it.
	 * 
	 * @return Archived object instance
	 * @see #archiveIt()c
	 */
	public T setArchived() {
		return archiveIt();
	}

	/**
	 * Returns whether the record is active (live, not deleted, not archived) or not.
	 * 
	 * @return <code>true</code> if active, <code>false</code> otherwise
	 */
	@JsonIgnore
	public boolean isActive() {
		return archived == null || archived == false;
	}

	/**
	 * Activates (restore) the previously archived object instance and returns it.
	 *  
	 * @return Activated object instance
	 * @see #restoreIt()
	 */
	public T setActive() {
		return restoreIt();
	}

	/**
	 * Archives the current object instance and returns it. <br>
	 * Archiving is done by setting {@link #archived} field to <code>true</code>.
	 * 
	 * @return Archived object instance
	 */
	@SuppressWarnings("unchecked")
	public T archiveIt() {
		this.archived = true;
		this.archiveDate = LocalDateTime.now();
		return (T) this;
	}

	public T archiveOb(Boolean archived) {
		this.archived = archived;
		this.archiveDate = LocalDateTime.now();
		return (T) this;
	}

	/**
	 * Restores (make active) the previously archived object instance and returns it.
	 *  
	 * @return Restored (activated) object instance
	 */
	@SuppressWarnings("unchecked")
	public T restoreIt() {
		this.archived = false;
		this.archiveDate = null;
		return (T) this;
	}

	/**
	 * Returns the unique object reference / identifier. <br>
	 * 
	 * @return Object Id
	 */
	public abstract String getId();
}
