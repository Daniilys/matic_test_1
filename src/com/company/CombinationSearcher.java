package com.company;

import javafx.util.Pair;

import java.util.*;

public class CombinationSearcher {

	public static void main(String[] args) {
		Set<String> dictionary = new HashSet<>();
		dictionary.add("abc");
		dictionary.add("ab");
		dictionary.add("a");
		dictionary.add("b");
		dictionary.add("c");

		String target = "abcabc";
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


		TreeNode treeRoot = createChildren(target, dictionary);
		return getCombinations(target.length(), treeRoot);
	}

	private TreeNode createChildren(final String target, final Set<String> dictionary) {
		TreeNode treeRoot = new TreeNode(-1, "");
		Stack<ChildCreationData> dataStack = new Stack<>();
		ChildCreationData data = new ChildCreationData(treeRoot, 0);

		while (data != null) {
			String substring = target.substring(data.start, data.start + data.length);
			TreeNode treeNode = new TreeNode(data.start, substring);
			if (dictionary.contains(substring)) {
				data.parent.addChild(treeNode);
				//search for tree children that starts with current node end
				List<TreeNode> children = data.parent.findChildrenAt(treeNode.getEnd());
				if (children != null) {
					//get already created children
					treeNode.getChildren().addAll(children);
					if (data.start + data.length < target.length()) {
						data.length++;
					}
				} else {
					dataStack.push(data);
					data = new ChildCreationData(treeNode, treeNode.getEnd());
				}
			} else {
				data.length++;
			}

			while (data.start + data.length > target.length()) {
				if (dataStack.isEmpty()) {
					data = null;
					break;
				} else {
					data = dataStack.pop();
					data.length++;
				}
			}
		}

		return treeRoot;
	}

	private List<String> getCombinations(final int targetLength, final TreeNode treeRoot) {
		final List<String> combinations = new ArrayList<>();
		final List<String> combination = new LinkedList<>();
		Stack<Pair<TreeNode, Integer>> dataStack = new Stack<>();
		TreeNode parent = treeRoot;
		int position = 0;

		while (parent != null) {
			if (parent.getChildren().size() > position) {
				dataStack.push(new Pair<>(parent, position));
				parent = parent.getChildren().get(position);
				position = 0;
				combination.add(parent.getValue());
				continue;
			}

			if (parent.isLeaf()) {
				if (isValidLeaf(targetLength, parent)) {
					StringBuilder combinationStringBuilder = new StringBuilder();
					for (String item : combination) {
						if (combinationStringBuilder.length() > 0) {
							combinationStringBuilder.append(" ");
						}
						combinationStringBuilder.append(item);
					}
					combinations.add(combinationStringBuilder.toString());
				}
			}

			if (parent.getStart() == -1) {
				break;
			}
			combination.remove(combination.size() - 1);
			Pair<TreeNode, Integer> pair = dataStack.pop();
			parent = pair.getKey();
			position = pair.getValue() + 1;
		}

		return combinations;
	}

	private boolean isValidLeaf(final int targetLength, final TreeNode leaf) {
		if (!leaf.isLeaf()) {
			throw new IllegalArgumentException("Not a leaf");
		}
		return leaf.getEnd() == targetLength;
	}

	private static class ChildCreationData {
		public final TreeNode parent;
		public final int start;
		public int length = 1;

		public ChildCreationData(TreeNode parent, int start) {
			this.parent = parent;
			this.start = start;
		}
	}

}
