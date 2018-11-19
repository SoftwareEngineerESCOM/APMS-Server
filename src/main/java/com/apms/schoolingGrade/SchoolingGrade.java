package com.apms.schoolingGrade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.apms.academicLevel.AcademicLevel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class SchoolingGrade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String specialty;
	@OneToOne
	@JoinColumn(nullable = false)
	private AcademicLevel academicLevel;

	public SchoolingGrade() {

	}
}