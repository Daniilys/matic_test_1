package test.java.com.company;

import com.company.CombinationSearcher;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by mahasamatman on 28/11/16.
 */
public class CombinationSearcherTest {
	private CombinationSearcher combinationSearcher;
	
	@Before
	public void init() {
		combinationSearcher = new CombinationSearcher();
	}

	@Test
	public void shouldFindThreeCombinations() {
		Set<String> dictionary = new HashSet<>();
		dictionary.add("abc");
		dictionary.add("ab");
		dictionary.add("a");
		dictionary.add("b");
		dictionary.add("c");

		String target = "aabc";
		List<String> combinations = combinationSearcher.findCombinations(target, dictionary);
		assertEquals(3, combinations.size());
		assertEquals(target, combinations.get(0).replace(" ", ""));
		assertEquals(target, combinations.get(1).replace(" ", ""));
		assertEquals(target, combinations.get(2).replace(" ", ""));
	}

	@Test
	public void shouldNotFindCombinations() {
		Set<String> dictionary = new HashSet<>();
		dictionary.add("abc");
		dictionary.add("ab");
		dictionary.add("a");
		dictionary.add("b");
		dictionary.add("c");

		String target = "aabcx";
		List<String> combinations = combinationSearcher.findCombinations(target, dictionary);
		assertEquals(0, combinations.size());
	}
}