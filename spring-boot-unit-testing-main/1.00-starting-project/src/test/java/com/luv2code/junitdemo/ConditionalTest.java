package com.luv2code.junitdemo;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ConditionalTest {

	@Test
	@Disabled("Don't run until Jira #123 is resolved")
	void test_basics() {
		// execute method and perform asserts
	}

	@Test
	@EnabledOnOs(OS.WINDOWS)
	void test_for_windows_only() {
		// execute method and perform asserts
	}

	@Test
	@EnabledOnOs(OS.LINUX)
	void test_for_linux_only() {
		// execute method and perform asserts
	}

	@Test
	@EnabledOnOs(OS.MAC)
	void test_for_mac_only() {
		// execute method and perform asserts
	}

	@Test
	@EnabledOnOs({ OS.MAC, OS.WINDOWS })
	void test_for_mac_and_windows() {
		// execute method and perform asserts
	}

	@Test
	@EnabledOnJre(JRE.JAVA_17)
	void test_for_java_17_only() {
		// execute method and perform asserts
	}

	@Test
	@EnabledOnJre(JRE.JAVA_13)
	void test_for_java_13_only() {
		// execute method and perform asserts
	}

	@Test
	@EnabledForJreRange(min = JRE.JAVA_13, max = JRE.JAVA_18)
	void test_for_java_range() {
		// execute method and perform asserts
	}

	@Test
	@EnabledForJreRange(min = JRE.JAVA_13)
	void test_for_java_range_min_13() {
		// execute method and perform asserts
	}

	@Test
	@EnabledIfEnvironmentVariable(named = "LUV2CODE_DEV", matches = "DEV")
	void test_only_for_dev_environment() {
		// execute method and perform asserts
	}

	@Test
	@EnabledIfSystemProperty(named = "LUV2CODE_SYS_PROP", matches = "CI_CD_DEPLOY")
	void test_only_for_system_property() {
		// execute method and perform asserts
	}
}
