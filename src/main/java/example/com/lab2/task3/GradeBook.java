package example.com.lab2.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

//
//class GradeBook {
//    private final List<List<List<Integer>>> journal;
//    private final ReentrantLock lock = new ReentrantLock();
//    private final int numGroups;
//    private final int studentsPerGroup;
//
//    public GradeBook(int numGroups, int studentsPerGroup) {
//        this.numGroups = numGroups;
//        this.studentsPerGroup = studentsPerGroup;
//        journal = new ArrayList<>();
//        for (int i = 0; i < numGroups; i++) {
//            List<List<Integer>> group = new ArrayList<>();
//            for (int j = 0; j < studentsPerGroup; j++) {
//                group.add(new ArrayList<>());
//            }
//            journal.add(group);
//        }
//    }
//
//    public void addGrade(int groupIndex, int studentIndex, int grade, String teacher) {
//        lock.lock();
//        try {
//            journal.get(groupIndex).get(studentIndex).add(grade);
//            System.out.printf("%s assigned %d points to Student %d in Group %d%n", teacher, grade, studentIndex + 1, groupIndex + 1);
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    public void printJournal() {
//        System.out.println("\nElectronic Journal:");
//        for (int i = 0; i < numGroups; i++) {
//            System.out.println("Group " + (char) ('A' + i) + ":");
//            for (int j = 0; j < studentsPerGroup; j++) {
//                System.out.printf("  Student %d: %s%n", j + 1, journal.get(i).get(j));
//            }
//        }
//    }
//}

class GradeBook {
    private final List<List<List<Integer>>> journal;
    private final ReentrantLock lock = new ReentrantLock();

    public GradeBook(int numGroups, int studentsPerGroup) {
        journal = new ArrayList<>();
        for (int i = 0; i < numGroups; i++) {
            List<List<Integer>> group = new ArrayList<>();
            for (int j = 0; j < studentsPerGroup; j++) {
                group.add(new ArrayList<>());
            }
            journal.add(group);
        }
    }

    public void addGrade(int groupIndex, int studentIndex, int grade, String teacher) {
        lock.lock();
        try {
            journal.get(groupIndex).get(studentIndex).add(grade);
            System.out.printf("%s assigned %d points to Student %d in Group %d%n", teacher, grade, studentIndex + 1, groupIndex + 1);
        } finally {
            lock.unlock();
        }
    }

    public void printJournal() {
        System.out.println("\nElectronic Journal:");
        for (int i = 0; i < journal.size(); i++) {
            System.out.println("Group " + (char) ('A' + i) + ":");
            for (int j = 0; j < journal.get(i).size(); j++) {
                System.out.printf("  Student %d: %s%n", j + 1, journal.get(i).get(j));
            }
        }
    }

    public int getNumGroups() {
        return journal.size();
    }

    public int getStudentsPerGroup() {
        return journal.isEmpty() ? 0 : journal.get(0).size();
    }
}