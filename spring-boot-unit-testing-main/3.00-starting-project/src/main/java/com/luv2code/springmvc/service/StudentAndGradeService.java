package com.luv2code.springmvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.Grade;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.models.StudentGrades;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;

@Service
@Transactional
public class StudentAndGradeService {

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private MathGradesDao mathGradesDao;

	@Autowired
	@Qualifier("mathGrades")
	private MathGrade mathGrade;

	@Autowired
	private ScienceGradesDao scienceGradesDao;

	@Autowired
	@Qualifier("scienceGrades")
	private ScienceGrade scienceGrade;

	@Autowired
	private HistoryGradesDao historyGradesDao;

	@Autowired
	@Qualifier("historyGrades")
	private HistoryGrade historyGrade;

	@Autowired
	private StudentGrades studentGrades;

	public void createStudent(String firstName, String lastName, String emailAddress) {
		CollegeStudent student = new CollegeStudent(firstName, lastName, emailAddress);
		student.setId(0);
		studentDao.save(student);
	}

	public boolean checkIfStudentIsNotNull(int id) {
		Optional<CollegeStudent> student = studentDao.findById(id);
		return student.isPresent();
	}

	public void deleteStudent(int id) {

		if (checkIfStudentIsNotNull(id)) {
			studentDao.deleteById(id);
			mathGradesDao.deleteByStudentId(id);
			scienceGradesDao.deleteByStudentId(id);
			historyGradesDao.deleteByStudentId(id);
		}

	}

	public Iterable<CollegeStudent> getGradebook() {

		return studentDao.findAll();
	}

	public boolean createGrade(double grade, int id, String gradeType) {

		if (!checkIfStudentIsNotNull(id)) {
			return false;
		}

		if (grade >= 0 && grade <= 100) {

			switch (gradeType) {
			case "math":
				mathGrade.setId(0);
				mathGrade.setGrade(grade);
				mathGrade.setStudentId(id);
				mathGradesDao.save(mathGrade);
				return true;
			case "science":
				scienceGrade.setId(0);
				scienceGrade.setGrade(grade);
				scienceGrade.setStudentId(id);
				scienceGradesDao.save(scienceGrade);
				return true;
			case "history":
				historyGrade.setId(0);
				historyGrade.setGrade(grade);
				historyGrade.setStudentId(id);
				historyGradesDao.save(historyGrade);
				return true;
			}
		}
		return false;

	}

	public int deleteGrade(int id, String gradeType) {
		int studentId = 0;

		if ("math".equals(gradeType)) {
			Optional<MathGrade> grade = mathGradesDao.findById(id);
			if (!grade.isPresent()) {
				return studentId;
			}
			mathGradesDao.deleteById(id);
			studentId = grade.get().getStudentId();
		}

		if ("science".equals(gradeType)) {
			Optional<ScienceGrade> grade = scienceGradesDao.findById(id);
			if (!grade.isPresent()) {
				return studentId;
			}
			scienceGradesDao.deleteById(id);
			studentId = grade.get().getStudentId();

		}

		if ("history".equals(gradeType)) {
			Optional<HistoryGrade> grade = historyGradesDao.findById(id);
			if (!grade.isPresent()) {
				return studentId;
			}
			historyGradesDao.deleteById(id);
			studentId = grade.get().getStudentId();

		}
		return studentId;
	}

	public GradebookCollegeStudent studentInformation(int id) {

		if(!checkIfStudentIsNotNull(id)) {
			return null;
		}
		
		Optional<CollegeStudent> student = studentDao.findById(id);
		Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(id);
		Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(id);
		Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(id);

		List<Grade> mathGradesList = new ArrayList<>();
		mathGrades.forEach(mathGradesList::add);

		List<Grade> scienceGradesList = new ArrayList<>();
		scienceGrades.forEach(scienceGradesList::add);

		List<Grade> historyGradesList = new ArrayList<>();
		historyGrades.forEach(historyGradesList::add);

		studentGrades.setMathGradeResults(mathGradesList);
		studentGrades.setScienceGradeResults(scienceGradesList);
		studentGrades.setHistoryGradeResults(historyGradesList);

		GradebookCollegeStudent gradebookCollegeStudent = new GradebookCollegeStudent(id, student.get().getFirstname(),
				student.get().getLastname(), student.get().getEmailAddress(), studentGrades);

		return gradebookCollegeStudent;
	}
	
	public void configureStudentInformationModel(int id, Model m) {
		GradebookCollegeStudent studentEntity = studentInformation(id);
		m.addAttribute("student", studentEntity);
		if (studentEntity.getStudentGrades().getMathGradeResults().size() > 0) {
			m.addAttribute("mathAverage", studentEntity.getStudentGrades()
					.findGradePointAverage(studentEntity.getStudentGrades().getMathGradeResults()));
		} else {
			m.addAttribute("mathAverage", "N/A");
		}

		if (studentEntity.getStudentGrades().getScienceGradeResults().size() > 0) {
			m.addAttribute("scienceAverage", studentEntity.getStudentGrades()
					.findGradePointAverage(studentEntity.getStudentGrades().getScienceGradeResults()));
		} else {
			m.addAttribute("scienceAverage", "N/A");
		}

		if (studentEntity.getStudentGrades().getHistoryGradeResults().size() > 0) {
			m.addAttribute("historyAverage", studentEntity.getStudentGrades()
					.findGradePointAverage(studentEntity.getStudentGrades().getHistoryGradeResults()));
		} else {
			m.addAttribute("historyAverage", "N/A");
		}
		
	}

}
