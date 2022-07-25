package com.luv2code.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.luv2code.component.MvcTestingExampleApplication;
import com.luv2code.component.dao.ApplicationDao;
import com.luv2code.component.models.CollegeStudent;
import com.luv2code.component.models.StudentGrades;
import com.luv2code.component.service.ApplicationService;

@SpringBootTest(classes = MvcTestingExampleApplication.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class MockAnnotationTest {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private CollegeStudent studentOne;

	@Autowired
	private StudentGrades studentGrades;

	// @Mock
	@MockBean
	private ApplicationDao applicationDao;

	// @InjectMocks
	@Autowired
	private ApplicationService applicationService;

	@BeforeEach
	public void beforeEach() {

		studentOne.setFirstname("Eric");
		studentOne.setLastname("Roby");
		studentOne.setEmailAddress("eric.roby@luv2code_school.com");
		studentOne.setStudentGrades(studentGrades);
	}

	@Test
	public void assert_equals_test_add_grades() {

		when(applicationDao.addGradeResultsForSingleClass(studentGrades.getMathGradeResults())).thenReturn(100.00);

		assertEquals(100,
				applicationService.addGradeResultsForSingleClass(studentOne.getStudentGrades().getMathGradeResults()));

		verify(applicationDao).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
		verify(applicationDao, times(1)).addGradeResultsForSingleClass(studentGrades.getMathGradeResults());
	}

	@Test
	public void assert_equals_test_find_grade_point_average() {

		when(applicationDao.findGradePointAverage(studentGrades.getMathGradeResults())).thenReturn(88.31);

		assertEquals(88.31,
				applicationService.findGradePointAverage(studentOne.getStudentGrades().getMathGradeResults()));

	}

	@Test
	public void test_assert_not_null() {
		when(applicationDao.checkNull(studentGrades.getMathGradeResults())).thenReturn(true);

		assertNotNull(applicationService.checkNull(studentOne.getStudentGrades().getMathGradeResults()));
	}

	@Test
	public void throwRunTimeError() {

		CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

		doThrow(new RuntimeException()).when(applicationDao).checkNull(nullStudent);

		assertThrows(RuntimeException.class, () -> {
			applicationService.checkNull(nullStudent);
		});

		verify(applicationDao, times(1)).checkNull(nullStudent);
	}

	@Test
	public void stubbing_consecutive_calls() {

		CollegeStudent nullStudent = context.getBean("collegeStudent", CollegeStudent.class);

		when(applicationDao.checkNull(nullStudent)).thenThrow(new RuntimeException())
				.thenReturn("Do not throw exception second time");

		assertThrows(RuntimeException.class, () -> {
			applicationService.checkNull(nullStudent);
		});

		assertEquals("Do not throw exception second time", applicationService.checkNull(nullStudent));
		
		verify(applicationDao, times(2)).checkNull(nullStudent);

	}
}
