package com.apms.experienciaprofesional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alumnos.models.Alumno;

@Repository
public interface ExperienciaProfesionalRepository extends CrudRepository<ExperienciaProfesional, Integer>{

}
