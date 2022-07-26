package com.luv2code.springmvc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.luv2code.springmvc.models.MathGrade;

@Repository
public interface MathGradesDao extends CrudRepository<MathGrade, Integer> {

	Iterable<MathGrade> findGradeByStudentId(int id);

}
