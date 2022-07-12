package com.b.simple.design.business.student;

public class StudentHelper {

	private static final int EXTRA_FOR_MATH = 10;
	private static final int GRADE_B_UPPER_LIMIT = 80;
	private static final int GRADE_B_LOWER_LIMIT = 51;

	/* PROBLEM 1 */
	/*
	 * You get a grade B if marks are between 51 and 80 (both inclusive). Except for
	 * Maths where the upper limit is increased by 10.
	 */
	public boolean isGradeB(int marks, boolean isMaths) {

		int upperLimit;

		if (isGradeBLessThanLowerLimit(marks)) {
			return false;
		}

		if (isMaths) {

			upperLimit = GRADE_B_UPPER_LIMIT + EXTRA_FOR_MATH;

			return isGradeBMoreThanUpperLimit(marks, upperLimit);
		}

		upperLimit = GRADE_B_UPPER_LIMIT;
		return isGradeBMoreThanUpperLimit(marks, upperLimit);
	}

	public boolean isGradeBLessThanLowerLimit(int marks) {
		return marks < GRADE_B_LOWER_LIMIT;
	}

	public boolean isGradeBMoreThanUpperLimit(int marks, int upperLimit) {
		return isGood(upperLimit, marks);
	}

	/* PROBLEM 2 */
	/*
	 * You are awarded a grade based on your marks. Grade A = 91 to 100, Grade B =
	 * 51 to 90, Otherwise Grade C Except for Maths where marks to get a Grade are 5
	 * higher than required for other subjects.
	 */
	private final int lowerLimitForAGrade = 91;
	private final int lowerLimitForBGrade = 51;
	int extra_limit = 5;

	public String getGrade(int mark, boolean isMaths) {

		int extraLimit = isMaths ? extra_limit : 0;

		if (mark >= lowerLimitForAGrade + extraLimit)
			return "A";

		if (mark >= lowerLimitForBGrade + extraLimit)
			return "B";

		return "C";
	}

	/*
	 * PROBLEM 3 You and your Friend are planning to enter a Subject Quiz. However,
	 * there is a marks requirement that you should attain to qualify.
	 * 
	 * Return value can be YES, NO or MAYBE.
	 * 
	 * YES If either of you are very good at the subject(has 80 or more marks)
	 * However, there is an exception that if either of you is not good in the
	 * subject(20 or less marks), it is NO. In all other conditions, return MAYBE.
	 * 
	 * However, the definition for good and not good are 5 marks higher if the
	 * subject is Mathematics.
	 * 
	 * marks1 - your marks marks2 - your friends marks
	 */
	private final int extraLimit = 5;
	private final int lowerLimit = 20;
	private final int upperLimit = 80;

	public String willQualifyForQuiz(int marks1, int marks2, boolean isMaths) {

		int extraLimit = isMaths ? this.extraLimit : 0;
		int lowerLimit = this.lowerLimit + extraLimit;
		int upperLimit = this.upperLimit + extraLimit;

		if (isGood(marks1, upperLimit) || isGood(marks2, upperLimit)) {
			return "YES";
		}

		if (isNotGood(marks1, lowerLimit) || isNotGood(marks2, lowerLimit)) {
			return "NO";
		}

		return "MAYBE";
	}

	private boolean isGood(int marks, int upperLimit) {
		return upperLimit <= marks;
	}

	private boolean isNotGood(int marks, int lowerLimit) {
		return lowerLimit >= marks;
	}

}