import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    protected static AtomicInteger c1 = new AtomicInteger(0);
    protected static AtomicInteger c2 = new AtomicInteger(0);
    protected static AtomicInteger c3 = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<Thread>();

        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        for (String text: texts) {
            Thread thread1 = new Thread(() -> {
               if (text.equals(new StringBuilder(text).reverse().toString())) {
                   if (text.length() == 3) {
                       c1.incrementAndGet();
                   }
                   if (text.length() == 4) {
                       c2.incrementAndGet();
                   }
                   if (text.length() == 5) {
                       c3.incrementAndGet();
                   }
               }
            });
            threads.add(thread1);
            thread1.start();
            Thread thread2 = new Thread(() -> {
                if (text.chars().filter((ch) -> ch == 'a').count() == text.length() || text.chars().filter((ch) -> ch == 'b').count() == text.length() || text.chars().filter((ch) -> ch == 'c').count() == text.length()) {
                    if (text.length() == 3) {
                        c1.incrementAndGet();
                    }
                    if (text.length() == 4) {
                        c2.incrementAndGet();
                    }
                    if (text.length() == 5) {
                        c3.incrementAndGet();
                    }
                }
            });
            threads.add(thread2);
            thread2.start();
            Thread thread3 = new Thread(() -> {
                if (text.chars().sorted().toString() == text) {
                    if (text.length() == 3) {
                        c1.incrementAndGet();
                    }
                    if (text.length() == 4) {
                        c2.incrementAndGet();
                    }
                    if (text.length() == 5) {
                        c3.incrementAndGet();
                    }
                }
            });
            threads.add(thread3);
            thread3.start();
        }
        for (Thread thread: threads) {
            thread.join();
        }
        System.out.println("Красивых слов с длиной 3: " + c1.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + c2.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + c3.get() + " шт");
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}