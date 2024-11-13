import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class NicknameGenerator {
    private static final AtomicInteger numOfChar3 = new AtomicInteger(0);
    private static final AtomicInteger numOfChar4 = new AtomicInteger(0);
    private static final AtomicInteger numOfChar5 = new AtomicInteger(0);

    private static final String[] texts = new String[100_000];

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
        Thread thread3 = new Thread(() -> counterBeautyNicks(3, numOfChar3));
        Thread thread4 = new Thread(() -> counterBeautyNicks(4, numOfChar4));
        Thread thread5 = new Thread(() -> counterBeautyNicks(5, numOfChar5));
        thread3.start();
        thread4.start();
        thread5.start();
        thread3.join();
        thread4.join();
        thread5.join();
        // Вывод результатов
        System.out.println("Красивых слов с длиной 3: " + numOfChar3.get() + " шт");
        System.out.println("Красивых слов с длиной 4: " + numOfChar4.get() + " шт");
        System.out.println("Красивых слов с длиной 5: " + numOfChar5.get() + " шт");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    // Метод для проверки полиндрома
    private static boolean isPalindrome(String word) {
        String reversed = new StringBuilder(word).reverse().toString();
        return word.equals(reversed);
    }

    // Метод для проверки на одинаковость букв
    private static boolean isSameLetter(String word) {
        char c = word.charAt(0);
        for (int i = 1; i < word.length(); i++) {
            if (c != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    // Метод для проверки на упорядоченность букв
    private static boolean isAscendingOrder(String word) {
        for (int i = 1; i < word.length(); i++) {
            if (word.charAt(i) < word.charAt(i - 1)) {
                return false;
            }
        }
        return true;
    }

    // Метод подсчета «красивых» слов заданной длины
    private static void counterBeautyNicks(int length, AtomicInteger counter) {
        for (String word : texts) {
            if (length == word.length()) {
                if (isPalindrome(word) || isSameLetter(word) || isAscendingOrder(word)) {
                    counter.incrementAndGet();
                }
            }
        }
    }
}
