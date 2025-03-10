package example.com.lab4;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordLengthAnalysis {
    public static void main(String[] args) {
        String text = "Це приклад тексту для аналізу. ForkJoinFramework використовується для розподілу обчислень.";
        List<String> words = Stream.of(text.split("\\W+")).filter(w -> !w.isEmpty()).collect(Collectors.toList());

        ForkJoinPool pool = new ForkJoinPool();
        WordLengthTask task = new WordLengthTask(words, 0, words.size());

        WordStats stats = pool.invoke(task);
        System.out.println("Середня довжина слова: " + stats.getMean());
        System.out.println("Дисперсія: " + stats.getVariance());
        System.out.println("Середньоквадратичне відхилення: " + stats.getStdDev());
    }
}

class WordLengthTask extends RecursiveTask<WordStats> {
    private static final int THRESHOLD = 10;
    private final List<String> words;
    private final int start, end;

    public WordLengthTask(List<String> words, int start, int end) {
        this.words = words;
        this.start = start;
        this.end = end;
    }

    @Override
    protected WordStats compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeDirectly();
        } else {
            int mid = start + length / 2;
            WordLengthTask leftTask = new WordLengthTask(words, start, mid);
            WordLengthTask rightTask = new WordLengthTask(words, mid, end);
            invokeAll(leftTask, rightTask);
            return WordStats.combine(leftTask.join(), rightTask.join());
        }
    }

    private WordStats computeDirectly() {
        double sum = 0, sumSq = 0;
        for (int i = start; i < end; i++) {
            int len = words.get(i).length();
            sum += len;
            sumSq += len * len;
        }
        int count = end - start;
        return new WordStats(sum, sumSq, count);
    }
}

class WordStats {
    private final double sum, sumSq;
    private final int count;

    public WordStats(double sum, double sumSq, int count) {
        this.sum = sum;
        this.sumSq = sumSq;
        this.count = count;
    }

    public double getMean() {
        return count == 0 ? 0 : sum / count;
    }

    public double getVariance() {
        if (count == 0) return 0;
        double mean = getMean();
        return (sumSq / count) - (mean * mean);
    }

    public double getStdDev() {
        return Math.sqrt(getVariance());
    }

    public static WordStats combine(WordStats a, WordStats b) {
        return new WordStats(a.sum + b.sum, a.sumSq + b.sumSq, a.count + b.count);
    }
}