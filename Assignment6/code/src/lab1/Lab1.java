package lab1;
// Remove all duplicate elements from a list of string using streams.
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lab1 {

    public static void main(String[] args) {
        String story = "Automation testing is a competitive market with many " +
                        "global and regional players. To maintain our competitive " +
                        "edge, FPT Software places great emphasis on continuous " +
                        "research, development, and practice to provide our customers " +
                        "with excellent quality assurance and quality engineering services";
        List<String> words = Arrays.asList(story.split(" "));
        List<String> removeDuplicate = words.stream().distinct().collect(Collectors.toList());
        for (String word : removeDuplicate){
            System.out.print(word + " ");
        }
    }
    
}