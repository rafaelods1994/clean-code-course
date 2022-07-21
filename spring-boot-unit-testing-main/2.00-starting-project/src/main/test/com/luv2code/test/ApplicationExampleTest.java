package com.luv2code.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class ApplicationExampleTest {

	private static int count = 0;

	@Value("${info.app.name}")
	private String appInfo;

	@Value("${info.app.description}")
	private String appDescription;

	@Value("${info.app.version}")
	private String appVersion;

	@Value("${info.school.name}")
	private String schoolName;

	@Autowired
	private CollegeStudent student;

	@Autowired
	private ApplicationContext context;

	@Autowired
	private StudentGrades studentGrades;

	@BeforeEach
	public void beforeEach() {

		count = count + 1;
		System.out.println("Testing: " + appInfo + "Which is " + appDescription + " Version: " + appVersion
				+ ". Execution of test method " + count);
		student.setFirstname("Eric");
		student.setLastname("Roby");
		student.setEmailAddress("eric.roby@luv2code_school.com");

		studentGrades.setMathGradeResults(new ArrayList<>(Arrays.asList(100.0, 85.0, 76.50, 91.75)));
		student.setStudentGrades(studentGrades);
	}

	@Test
	void basicTest() {

	}

	@Test
	public void add_grade_results_for_student_grades() {

		assertEquals(353.25,
				studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));

	}

	@Test
	public void add_grade_results_for_student_grades_not_equal() {
		assertNotEquals(0,
				studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults()));

	}

	@Test
	public void is_grade_greater_student_grades() {
		assertTrue(studentGrades.isGradeGreater(90, 75));
	}

	@Test
	public void is_not_grade_greater_student_grades() {
		assertFalse(studentGrades.isGradeGreater(75, 90));
	}

	@Test
	public void check_for_not_null_for_student_grades() {
		assertNotNull(studentGrades.checkNull(student.getStudentGrades().getMathGradeResults()));
	}

	@Test
	public void check_null_for_student_grades() {
		assertNull(studentGrades.checkNull(null));
	}

	@Test
	public void create_student_without_grade_init() {
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
		studentTwo.setFirstname("Chad");
		studentTwo.setLastname("Darby");
		studentTwo.setEmailAddress("chad.derby@luv2code_school.com");
		assertNotNull(studentTwo.getFirstname());
		assertNotNull(studentTwo.getLastname());
		assertNotNull(studentTwo.getEmailAddress());
		assertNull(studentGrades.checkNull(studentTwo.getStudentGrades()));
	}

	@Test
	public void verify_students_are_prototypes() {
		CollegeStudent studentTwo = context.getBean("collegeStudent", CollegeStudent.class);
		assertNotSame(student, studentTwo);
	}

	@Test
	public void find_grade_point_average() {
		assertAll(
				() -> assertEquals(353.25,
						studentGrades.addGradeResultsForSingleClass(student.getStudentGrades().getMathGradeResults())),
				() -> assertEquals(88.31,
						studentGrades.findGradePointAverage(student.getStudentGrades().getMathGradeResults()))

		);
	}
}
