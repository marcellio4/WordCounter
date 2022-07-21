package com.word.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.word.counter.component.Word;

@SpringBootTest
class WordTest {

	private Word word;
	
	@BeforeEach
	public void setUp() {
		List<String> words = new ArrayList<>();
		words.add("hello");
		words.add("world");
		words.add("java");
		word = new Word();
		word.setWords(words);
		word.setCount(words.size());
	}

	@Test
	void testCalculateAvarageLength() {
		float num = (float) 14 / 3;
		assertEquals(num, word.calculateAvarageLength());
	}

	@Test
	void testFindWordsOfLength() {
		Map<Integer, Long> expected = new HashMap<>();
		expected.put(5, (long) 2);
		expected.put(4, (long) 1);

		assertEquals(2, word.findWordsOfLength().size());
		assertEquals(expected, word.findWordsOfLength());
	}

	@Test
	void testMostOccuringWordLength() {
		assertEquals(2, word.mostOccuringWordLength());
	}

	@Test
	void testMostOccuringLengthforWords() {
		assertEquals("5", word.mostOccuringLengthforWords());
	}

}
