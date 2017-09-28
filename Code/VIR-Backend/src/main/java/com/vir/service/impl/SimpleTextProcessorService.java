package com.vir.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.vir.model.Text;
import com.vir.model.Word;
import com.vir.repository.WordRepository;
import com.vir.service.TextProcessorService;
import com.vir.service.WordService;

/**
 * A simple text processor to match a word to the category.
 * 
 * @author Alfredo Lopez
 *
 */
@Service("simpleTextProcessorService")
@Transactional
public class SimpleTextProcessorService implements TextProcessorService {

	@Autowired
	private WordRepository wordRepository;

	@Autowired
	@Qualifier("simpleWordService")
	private WordService wordService;

	@Override
	public Text process(String textString) {

		List<String> initialStrings = getStrings(textString);
		Map<String, Word> map = new HashMap<>();
		List<Word> finalList = new ArrayList<>();

		Word result = null;
		for (String initialString : initialStrings) {
			if (map.containsKey(initialString)) {
				result = map.get(initialString);
			} else {
				String cleanValue = wordService.removePunctuation(initialString);
				
				if (StringUtils.isEmpty(cleanValue)) {
					result = new Word(initialString);
				} else {
					result = getWord(cleanValue, initialString);
				}
				map.put(initialString, result);
			}
			finalList.add(result);
		}

		Text text = new Text();
		text.setWords(finalList);

		return text;
	}

	/**
	 * Gets the list of strings from a text.
	 * 
	 * @param textString
	 *            the string to split
	 * @return A list of strings
	 * 
	 *         Note: We make sure there is no more than two white spaces between the
	 *         words.
	 */
	private List<String> getStrings(String textString) {

		String regex = "[\\n\\r\\s]";
		String regexPlus = "[\\n\\r\\s]+";

		textString = textString.trim();
		textString = textString.replaceAll(regexPlus, StringUtils.SPACE);

		return Arrays.asList(textString.split(regex));
	}

	/**
	 * Gets a word from a string.
	 * 
	 * @param cleanValue
	 *            The clean value of the string
	 * @param initialValue
	 *            The initial value of the word.
	 * @return A word if found, else and empty word with the initial value
	 * 
	 */
	private Word getWord(String cleanValue, String initialValue) {
		Word result;
		result = wordRepository.findFirstByValue(cleanValue.toLowerCase());

		if (result == null) {
			result = new Word(initialValue);
		} else {
			result.setInitialValue(initialValue);
		}
		return result;
	}
}