package example.com.lab2.task3;

import java.util.Random;

class Lecturer implements Runnable {
    private final GradeBook gradeBook;
    private final String name;
    private final Random random = new Random();

    public Lecturer(GradeBook gradeBook, String name) {
        this.gradeBook = gradeBook;
        this.name = name;
    }

    @Override
    public void run() {
        int numGroups = gradeBook.getNumGroups();
        int studentsPerGroup = gradeBook.getStudentsPerGroup();

        for (int week = 1; week <= 3; week++) {
            for (int group = 0; group < numGroups; group++) {
                for (int student = 0; student < studentsPerGroup; student++) {
                    int grade = random.nextInt(81) + 20;
                    gradeBook.addGrade(group, student, grade, name);
                }
            }
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}