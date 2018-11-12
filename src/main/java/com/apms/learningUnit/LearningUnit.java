package com.apms.learningUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.apms.semester.Semester;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class LearningUnit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private Double TEPICCredits;
	@Column(nullable = false)
	private Double SATCACredits;
	@Column(nullable = false)
	private Double theoryHoursPerWeek;
	@Column(nullable = false)
	private Double practiceHoursPerWeek;
	@Column(nullable = false)
	private String formationArea;
	@ManyToOne
	@JoinColumn(nullable = false)
	private Semester semester;

	public LearningUnit() {
	}

	public LearningUnit(Integer id, String name, Double tEPICCredits, Double sATCACredits, Double theoryHoursPerWeek,
			Double practiceHoursPerWeek, String formationArea, Semester semester) {
		super();
		this.id = id;
		this.name = name;
		TEPICCredits = tEPICCredits;
		SATCACredits = sATCACredits;
		this.theoryHoursPerWeek = theoryHoursPerWeek;
		this.practiceHoursPerWeek = practiceHoursPerWeek;
		this.formationArea = formationArea;
		this.semester = semester;
	}

}