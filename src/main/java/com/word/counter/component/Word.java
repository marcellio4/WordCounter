package com.word.counter.component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Component
@Getter
@Setter
public class Word {

    private int count;
    private List<String> words;

    /**
     * Calculate avarage of all words
     * 
     * @return
     */
    public float calculateAvarageLength() {
        int total = 0;
        for (String word : words) {
            int wordLength = word.length();
            total += wordLength;
        }

        return (float) total / count;
    }

    /**
     * Map words length as key and value as how many they appeared in the text
     * 
     * @return Map<Integer, Long>
     */
    public Map<Integer, Long> findWordsOfLength() {
        return words.stream()
                .map(word -> word.length())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    /**
     * finds the most occuring word length in the text
     * 
     * @return long
     */
    public long mostOccuringWordLength() {
        return Collections.max(findWordsOfLength().values());
    }

    /**
     * Finds all most occuring words length
     * 
     * @return string
     */
    public String mostOccuringLengthforWords() {
        Long maxValue = mostOccuringWordLength();
        List<Integer> maxResultList = new ArrayList<>();
        for (Map.Entry<Integer, Long> entry : findWordsOfLength().entrySet()) {
            if (entry.getValue() >= maxValue) {
                maxValue = entry.getValue();
                maxResultList.add(entry.getKey());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        int maxSize = maxResultList.size();
        for (int i = 0; i < maxSize; i++) {
            stringBuilder.append(maxResultList.get(i));
            if (i != maxSize - 1) {
                stringBuilder.append(" & ");
            }
        }
        return stringBuilder.toString();
    }



}
