package com.luv2code.springmvc.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.MethodOrderer.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luv2code.springmvc.models.CollegeStudent;
import com.luv2code.springmvc.repository.HistoryGradesDao;
import com.luv2code.springmvc.repository.MathGradesDao;
import com.luv2code.springmvc.repository.ScienceGradesDao;
import com.luv2code.springmvc.repository.StudentDao;
import com.luv2code.springmvc.service.StudentAndGradeService;

@TestMethodOrder(DisplayName.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class GradebookControllerTest {

	private static MockHttpServletRequest request;

	@PersistenceContext
	private EntityManager entityManager;

	@Mock
	private StudentAndGradeService studentCreateServiceMock;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CollegeStudent student;

	@Autowired
	private JdbcTemplate jdbc;

	@Autowired
	private StudentDao studentDao;

	@Autowired
	private MathGradesDao mathGradeDao;

	@Autowired
	private ScienceGradesDao scienceGradeDao;

	@Autowired
	private HistoryGradesDao historyGradeDao;

	@Autowired
	private StudentAndGradeService studentService;

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

	public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;

	@BeforeAll
	public static void setup() {
		request = new MockHttpServletRequest();
		request.setParameter("firstname", "Chad");
		request.setParameter("lastname", "Darby");
		request.setParameter("emailAddress", "chad.darby@luv2code_school.com");

	}

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
	public void test_get_all_students_http_request() throws Exception {

		student.setFirstname("Chad");
		student.setLastname("Darby");
		student.setEmailAddress("chad.derby@luv2code_school.com");
		entityManager.persist(student);
		entityManager.flush();

		mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andExpect(jsonPath("$", hasSize(2)));

	}

	@Test
	public void test_create_a_new_student_http_request() throws JsonProcessingException, Exception {

		assertFalse(studentDao.findById(2).isPresent());

		student.setFirstname("Chad");
		student.setLastname("Darby");
		student.setEmailAddress("chad_darby@luv2code_school.com");

		mockMvc.perform(MockMvcRequestBuilders.post("/").contentType(APPLICATION_JSON_UTF8)
				.content(objectMapper.writeValueAsString(student))).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)));
		
		assertTrue(studentDao.findById(2).isPresent());
	}
	
	@Test
	public void test_delete_student_exist_http_request() throws Exception {
		
		assertTrue(studentDao.findById(1).isPresent());
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}",1))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
		
		assertFalse(studentDao.findById(1).isPresent());
	}
	
	@Test
	public void test_get_student_information_valid_http_request() throws Exception {
		
		Optional<CollegeStudent> student = studentDao.findById(1);
		
		assertTrue(student.isPresent());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}",1))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.id",is(1)))
			.andExpect(jsonPath("$.firstname",is("Eric")))
			.andExpect(jsonPath("$.lastname",is("Roby")))
			.andExpect(jsonPath("$.emailAddress",is("eric.roby@luv2code_school.com")));
	}
	
	
	@Test
	public void test_get_student_information_not_found_http_request() throws Exception {
		
		Optional<CollegeStudent> student = studentDao.findById(0);
		
		assertFalse(student.isPresent());
		
		mockMvc.perform(MockMvcRequestBuilders.get("/studentInformation/{id}",0))
			.andExpect(status().is4xxClientError())
			.andExpect(content().contentType(APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.message",is("Student or Grade was not found")))
			.andExpect(jsonPath("$.status",is(404)));
	}
	
	@Test
	public void test_delete_student_does_not_exist() throws Exception {
		
		assertFalse(studentDao.findById(0).isPresent());
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}",0))
			.andExpect(status().is4xxClientError())
			.andExpect(jsonPath("$.status", is(404)))
			.andExpect(jsonPath("$.message",is("Student or Grade was not found")));
		
	}
	
	@Test
	public void test_create_valid_grade() throws Exception {
		
		assertTrue(studentDao.findById(1).isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/grades")
				.contentType(APPLICATION_JSON_UTF8)
				.param("grade", "85.00")
				.param("gradeType", "math")
				.param("studentId","1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.firstname",is("Eric")))
		.andExpect(jsonPath("$.lastname",is("Roby")))
		.andExpect(jsonPath("$.emailAddress",is("eric.roby@luv2code_school.com")))
		.andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(2)));
	}
	
	@Test
	public void test_create_invalid_grade() throws Exception {
		
		assertTrue(studentDao.findById(1).isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/grades")
				.contentType(APPLICATION_JSON_UTF8)
				.param("grade", "85.00")
				.param("gradeType", "literature")
				.param("studentId","1"))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.status", is(404)))
		.andExpect(jsonPath("$.message",is("Student or Grade was not found")));
	}
	
	@Test
	public void test_create_valid_grade_student_does_not_exist() throws Exception {
		
		assertFalse(studentDao.findById(0).isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.post("/grades")
				.contentType(APPLICATION_JSON_UTF8)
				.param("grade", "85.00")
				.param("gradeType", "math")
				.param("studentId","0"))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.status", is(404)))
		.andExpect(jsonPath("$.message",is("Student or Grade was not found")));
	}
	
	@Test
	public void test_delete_valid_grade() throws Exception {
		
		assertTrue(mathGradeDao.findById(1).isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1, "math"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(APPLICATION_JSON_UTF8))
		.andExpect(jsonPath("$.id",is(1)))
		.andExpect(jsonPath("$.firstname",is("Eric")))
		.andExpect(jsonPath("$.lastname",is("Roby")))
		.andExpect(jsonPath("$.emailAddress",is("eric.roby@luv2code_school.com")))
		.andExpect(jsonPath("$.studentGrades.mathGradeResults", hasSize(0)));;
		
		assertFalse(mathGradeDao.findById(1).isPresent());
	}
	
	@Test
	public void test_delete_invalid_grade_id() throws Exception {
		
		assertFalse(mathGradeDao.findById(0).isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 0, "math"))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.status", is(404)))
		.andExpect(jsonPath("$.message",is("Student or Grade was not found")));
				
	}
	
	@Test
	public void test_delete_invalid_gradetype() throws Exception {
		
		assertTrue(mathGradeDao.findById(1).isPresent());
		
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/grades/{id}/{gradeType}", 1, "literature"))
		.andExpect(status().is4xxClientError())
		.andExpect(jsonPath("$.status", is(404)))
		.andExpect(jsonPath("$.message",is("Student or Grade was not found")));
				
	}


}
