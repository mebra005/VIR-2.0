package com.vir.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vir.model.Text;
import com.vir.model.Word;
import com.vir.service.TextProcessorService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTextProcessorServiceTest {
	
	@Autowired
	@Qualifier("simpleTextProcessorService")
	TextProcessorService textProcessorService;

	@Test
	public void process_WordDogWithCapitalLettersAndADot_ReturnsOneValue() {
		final String word = "Dog.";
		final Text result = textProcessorService.process(word);
		assertFalse(result.getWords().isEmpty());
	}
	
	@Test
	public void process_WordDogWithCapitalLettersAndADot_ReturnsADogValue() {
		final String word = "Dog.";
		final Text result = textProcessorService.process(word);
		assertEquals("dog", result.getWords().get(0).getValue());
	}
	
	@Test
	public void process_DogWithDotTestGoodTest_ReturnsValidList() {
		final String word = "Dog. test good test";
		final Text result = textProcessorService.process(word);
		assertEquals("dog", result.getWords().get(0).getValue());
		assertEquals("test", result.getWords().get(1).getValue());
		assertEquals("good", result.getWords().get(2).getValue());
		assertEquals("test", result.getWords().get(3).getValue());
	}

	@Test
	public void process_UnknownWord_ReturnsWordWithoutData() {
		final String word = "zzzz";
		final Word expected = new Word(StringUtils.EMPTY, StringUtils.EMPTY, word);
		final Text result = textProcessorService.process(word);
		assertEquals(expected, result.getWords().get(0));
	}

}