package example.com.lab2.task3;

import java.util.Random;

//class Assistant implements Runnable {
//    private final GradeBook gradeBook;
//    private final String name;
//    private final int assignedGroup;
//    private final Random random = new Random();
//
//    public Assistant(GradeBook gradeBook, String name, int assignedGroup) {
//        this.gradeBook = gradeBook;
//        this.name = name;
//        this.assignedGroup = assignedGroup;
//    }
//
//    @Override
//    public void run() {
//        for (int week = 1; week <= 3; week++) {
//            for (int student = 0; student < 5; student++) {
//                int grade = random.nextInt(81) + 20;
//                gradeBook.addGrade(assignedGroup, student, grade, name);
//            }
//            try {
//                Thread.sleep(random.nextInt(100));
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//}

class Assistant implements Runnable {
    private final GradeBook gradeBook;
    private final String name;
    private final int assignedGroup;
    private final Random random = new Random();

    public Assistant(GradeBook gradeBook, String name, int assignedGroup) {
        this.gradeBook = gradeBook;
        this.name = name;
        this.assignedGroup = assignedGroup;
    }

    @Override
    public void run() {
        int numGroups = gradeBook.getNumGroups();
        int studentsPerGroup = gradeBook.getStudentsPerGroup();

        for (int week = 1; week <= 3; week++) {
            for (int student = 0; student < studentsPerGroup; student++) {
                int grade = random.nextInt(81) + 20;
                gradeBook.addGrade(assignedGroup, student, grade, name);
            }
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
