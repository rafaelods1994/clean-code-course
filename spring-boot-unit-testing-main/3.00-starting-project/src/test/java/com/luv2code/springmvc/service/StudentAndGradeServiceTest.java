package com.luv2code.springmvc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.models.GradebookCollegeStudent;
import com.luv2code.springmvc.models.HistoryGrade;
import com.luv2code.springmvc.models.MathGrade;
import com.luv2code.springmvc.models.ScienceGrade;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;

@TestPropertySource("/application-test.properties")
@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(DisplayName.class)
public class StudentAndGradeServiceTest {

	@Autowired
	private StudentAndGradeService studentService;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private MathGradesDao mathGradesDao;

	@Autowired
	private ScienceGradesDao scienceGradesDao;

	@Autowired
	private HistoryGradesDao historyGradesDao;

	@Autowired
	private JdbcTemplate jdbc;
	
	@Value("${sql.script.create.student}")
	private String sqlAddStudent;
	
	@Value("${sql.script.create.math.grade}")
	private String sqlAddMathGrade;
	
	@Value("${sql.script.create.science.grade}")
	private String sqlAddScienceGrade;
	
	@Value("${sql.script.create.history.grade}")
	private String sqlAddHistoryGrade;
	
	
	@Value("${sql.script.delete.student}")
	private String sqlDeleteStudent;
	
	@Value("${sql.script.delete.math.grade}")
	private String sqlDeleteMathGrade;
	
	@Value("${sql.script.delete.science.grade}")
	private String sqlDeleteScienceGrade;
	
	@Value("${sql.script.delete.history.grade}")
	private String sqlDeleteHistoryGrade;
	
	@BeforeEach
	public void setupDatabase() {

		jdbc.execute(sqlAddStudent);

		jdbc.execute(sqlAddMathGrade);
		jdbc.execute(sqlAddScienceGrade);
		jdbc.execute(sqlAddHistoryGrade);

	}

	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute(sqlDeleteStudent);

		jdbc.execute(sqlDeleteMathGrade);
		jdbc.execute(sqlDeleteScienceGrade);
		jdbc.execute(sqlDeleteHistoryGrade);
	}

	@Test
	@Sql("/insertData.sql")
	public void get_gradebook_service() {
		Iterable<CollegeStudent> iterableCollegeStudents = studentService.getGradebook();
		List<CollegeStudent> collegeStudents = new ArrayList<>();

		for (CollegeStudent collegeStudent : iterableCollegeStudents) {

			collegeStudents.add(collegeStudent);

		}
		assertEquals(5, collegeStudents.size());

	}

	@Test
	public void create_student_service() {

		studentService.createStudent("Chad", "Darby", "chad.darby@luv2code_school.com");

		CollegeStudent student = studentDao.findByEmailAddress("chad.darby@luv2code_school.com");

		assertEquals("chad.darby@luv2code_school.com", student.getEmailAddress());

	}

	@Test
	public void delete_student_service() {

		Optional<CollegeStudent> deletedCollegeStudent = studentDao.findById(1);
		Optional<MathGrade> deletedMathGrade = mathGradesDao.findById(1);
		Optional<ScienceGrade> deletedScienceGrade = scienceGradesDao.findById(1);
		Optional<HistoryGrade> deletedHistoryGrade = historyGradesDao.findById(1);

		assertTrue(deletedCollegeStudent.isPresent());
		assertTrue(deletedMathGrade.isPresent());
		assertTrue(deletedScienceGrade.isPresent());
		assertTrue(deletedHistoryGrade.isPresent());

		studentService.deleteStudent(1);

		deletedCollegeStudent = studentDao.findById(1);
		deletedMathGrade = mathGradesDao.findById(1);
		deletedScienceGrade = scienceGradesDao.findById(1);
		deletedHistoryGrade = historyGradesDao.findById(1);

		assertFalse(deletedCollegeStudent.isPresent());
		assertFalse(deletedMathGrade.isPresent());
		assertFalse(deletedScienceGrade.isPresent());
		assertFalse(deletedHistoryGrade.isPresent());

	}

	@Test
	public void is_student_null_check() {

		assertTrue(studentService.checkIfStudentIsNotNull(1));

		assertFalse(studentService.checkIfStudentIsNotNull(0));
	}

	@Test
	public void create_grade_service() {

		// Create the grade
		assertTrue(studentService.createGrade(80.5, 1, "math"));
		assertTrue(studentService.createGrade(80.5, 1, "science"));
		assertTrue(studentService.createGrade(80.5, 1, "history"));

		// Get all grades with studentId
		Iterable<MathGrade> mathGrades = mathGradesDao.findGradeByStudentId(1);
		Iterable<ScienceGrade> scienceGrades = scienceGradesDao.findGradeByStudentId(1);
		Iterable<HistoryGrade> historyGrades = historyGradesDao.findGradeByStudentId(1);

		// Verify there is grades
		assertTrue(((Collection<MathGrade>) mathGrades).size() == 2);
		assertTrue(((Collection<ScienceGrade>) scienceGrades).size() == 2);
		assertTrue(((Collection<HistoryGrade>) historyGrades).size() == 2);

	}

	@Test
	public void create_grade_service_return_false() {
		assertFalse(studentService.createGrade(101, 1, "math"));
		assertFalse(studentService.createGrade(-1, 1, "math"));
		assertFalse(studentService.createGrade(80.50, 2, "math"));
		assertFalse(studentService.createGrade(80.50, 1, "literature"));
	}

	@Test
	public void delete_grade_service() {
		assertEquals(1, studentService.deleteGrade(1, "math"));
		assertEquals(1, studentService.deleteGrade(1, "science"));
		assertEquals(1, studentService.deleteGrade(1, "history"));
	}

	@Test
	public void delete_grade_service_return_student_id_of_zero() {
		assertEquals(0, studentService.deleteGrade(0, "science"));
		assertEquals(0, studentService.deleteGrade(1, "literature"));
	}

	@Test
	public void student_information() {

		GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(1);

		assertNotNull(gradebookCollegeStudent);
		assertEquals(1, gradebookCollegeStudent.getId());
		assertEquals("Eric", gradebookCollegeStudent.getFirstname());
		assertEquals("Roby", gradebookCollegeStudent.getLastname());
		assertEquals("eric.roby@luv2code_school.com", gradebookCollegeStudent.getEmailAddress());
		assertTrue(gradebookCollegeStudent.getStudentGrades().getMathGradeResults().size() == 1);
		assertTrue(gradebookCollegeStudent.getStudentGrades().getHistoryGradeResults().size() == 1);
		assertTrue(gradebookCollegeStudent.getStudentGrades().getScienceGradeResults().size() == 1);

	}

	@Test
	public void student_information_service_return_null() {
		GradebookCollegeStudent gradebookCollegeStudent = studentService.studentInformation(0);
		assertNull(gradebookCollegeStudent);
	}
}
