package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(DisplayName.class)
public class ReflectionTestUtilsTest {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private CollegeStudent studentOne;

	@Autowired
	private StudentGrades studentGrades;

	@BeforeEach
	public void studentBeforeEach() {

		studentOne.setFirstname("Eric");
		studentOne.setLastname("Roby");
		studentOne.setEmailAddress("eric.roby@luv2code_school.com");
		studentOne.setStudentGrades(studentGrades);

		ReflectionTestUtils.setField(studentOne, "id", 1);
		ReflectionTestUtils.setField(studentOne, "studentGrades",
				new StudentGrades(new ArrayList<>(Arrays.asList(100.0, 95.0, 76.50, 91.75))));
	}

	@Test
	public void get_private_field() {

		assertEquals(1, ReflectionTestUtils.getField(studentOne, "id"));
	}

	@Test
	public void invoke_private_method() {

		assertEquals("Eric 1", ReflectionTestUtils.invokeMethod(studentOne, "getFirstNameAndId"));
	}

}
