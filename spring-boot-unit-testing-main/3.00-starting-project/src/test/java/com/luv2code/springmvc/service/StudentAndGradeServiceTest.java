package com.luv2code.springmvc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.StudentDao;

@TestPropertySource("/application.properties")
@SpringBootTest
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(DisplayName.class)
public class StudentAndGradeServiceTest {

	@Autowired
	private StudentAndGradeService studentService;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private JdbcTemplate jdbc;

	@BeforeEach
	public void setupDatabase() {

		jdbc.execute("insert into student(id, firstname, lastname, email_address) "
				+ "values (1, 'Eric', 'Roby', 'eric.roby@luv2code_school.com')");

	}

	@AfterEach
	public void setupAfterTransaction() {
		jdbc.execute("DELETE FROM student");
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

		assertTrue(deletedCollegeStudent.isPresent());

		studentService.deleteStudent(1);

		deletedCollegeStudent = studentDao.findById(1);

		assertFalse(deletedCollegeStudent.isPresent());

	}

	@Test
	public void is_student_null_check() {

		assertTrue(studentService.checkIfStudentIsNull(1));

		assertFalse(studentService.checkIfStudentIsNull(0));
	}

}
