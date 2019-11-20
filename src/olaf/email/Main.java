package olaf.email;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main
{
    public static void main(String[] args)
    {
        Path filePath = Paths.get("sample.txt");

        try
        {
            String input = Files.readString(filePath);
            HashMap<String, Integer> domainMap = buildMap(input);
//            displayAll(domainMap);
            displayTop(domainMap,10);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter minimum number of occurrences:");
            Integer threshold = scanner.nextInt();
            displayAbove(domainMap, threshold);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    static HashMap<String, Integer> buildMap(String input) {
        Pattern pattern = Pattern.compile("(\\s|^)[\\w.'%+-]+@([\\w'%+-]+\\.[\\w.'%+-]+)(\\s|$)");
        Matcher matcher = pattern.matcher(input);
        HashMap<String, Integer> domainMap = new HashMap<String, Integer>();
        if (matcher.find()) {
            do {
                String domain = matcher.group(2);
                if (domainMap.keySet().contains(domain)) {
                    domainMap.put(domain,domainMap.get(domain)+1);
                } else {
                    domainMap.put(domain,1);
                }
            } while (matcher.find(matcher.start(3)));
        }
        return domainMap;
    }

    static void displayAll(HashMap<String, Integer> domainMap) {
        System.out.println("All domains:");
        for (String domain : domainMap.keySet()) {
            System.out.println("domain " + domain + " occurs " + domainMap.get(domain) + " times.");
        }
    }

    static void displayTop(HashMap<String, Integer> domainMap, Integer number) {
        System.out.println("Top " + number + " domains:");
        domainMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(number)
                .forEach(entry -> System.out.println("domain "+ entry.getKey() + " occurs " + entry.getValue() + " times."));
    }

    private static void displayAbove(HashMap<String, Integer> domainMap, Integer threshold) {
        System.out.println("All domains with " + threshold + " or more:");
        for (String domain : domainMap.keySet()) {
            if (domainMap.get(domain) >= threshold) System.out.println("domain " + domain + " occurs " + domainMap.get(domain) + " times.");
        }
    }
}