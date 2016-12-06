package com.company;

import java.util.*;

public class CombinationSearcher {

	public static void main(String[] args) {
		Set<String> dictionary = new HashSet<>();
		dictionary.add("abc");
		dictionary.add("ab");
		dictionary.add("a");
		dictionary.add("b");
		dictionary.add("c");

		String target = "abcabacca";
		List<String> combinations = new CombinationSearcher().findCombinations(target, dictionary);
		for (String combination : combinations) {
			System.out.println(combination);
		}
		System.out.println(combinations.size());
	}

	/**
	 * Search and return list of strings which represents all possible
	 * ways to form target from elements of dictionary
	 *
	 * @param target     - text that will be searched for combination of elements in dictionary
	 * @param dictionary not empty set of dictionary elements that will be searched in target
	 * @return list of founded combination that form target from dictionary elements
	 */
	public List<String> findCombinations(final String target, final Set<String> dictionary) {
		if (target == null) {
			throw new NullPointerException("Target text is null");
		} else if (target.isEmpty()) {
			throw new IllegalArgumentException("Target text is empty");
		}

		if (dictionary == null) {
			throw new NullPointerException("Dictionary list is null");
		} else if (dictionary.isEmpty()) {
			throw new IllegalArgumentException("Dictionary list is empty");
		}

		TreeNode treeRoot = new TreeNode(-1, "");
		createChildren(target, dictionary, treeRoot, 0);
		ArrayList<String> lCombinations = new ArrayList<>();
		getCombinations(target.length(), treeRoot, new StringBuilder(), lCombinations);
		return lCombinations;
	}

	private void createChildren(final String target, final Set<String> dictionary, final TreeNode parent, final int start) {
		int maxLength = target.length() - start;
		for (int length = 1; length <= maxLength; length++) {
			String substring = target.substring(start, start + length);
			if (dictionary.contains(substring)) {
				TreeNode treeNode = new TreeNode(start, substring);
				//search for tree children that starts with current node end
				List<TreeNode> children = parent.findChildrenAt(treeNode.getEnd());
				if (children != null) {
					//get already created children
					treeNode.getChildren().addAll(children);
				} else {
					//create children for that node
					createChildren(target, dictionary, treeNode, treeNode.getEnd());
				}
				parent.addChild(treeNode);
			}
		}
	}

	private void getCombinations(final int targetLength, final TreeNode treeRoot, final StringBuilder parentCombination,
	                             final ArrayList<String> combinations) {
		for (TreeNode child : treeRoot.getChildren()) {
			StringBuilder childCombination = new StringBuilder(parentCombination);
			if (childCombination.length() > 0) {
				childCombination.append(" ");
			}
			childCombination.append(child.getValue());
			if (!child.isLeaf()) {
				getCombinations(targetLength, child, childCombination, combinations);
			} else if (isValidLeaf(targetLength, child)) {
				combinations.add(childCombination.toString());
			}
		}
	}

	private boolean isValidLeaf(final int targetLength, final TreeNode leaf) {
		if (!leaf.isLeaf()) {
			throw new IllegalArgumentException("Not a leaf");
		}
		return leaf.getEnd() == targetLength;
	}

}
