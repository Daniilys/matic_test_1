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

		String target = "abcabcabcabcabcabcabcabc";
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
		LinkedList<ChildrenCreationStep> dataStack = new LinkedList<>();
		ChildrenCreationStep data = new ChildrenCreationStep(treeRoot, 0);

		while (data != null) {
			String substring = target.substring(data.start, data.start + data.length);
			if (dictionary.contains(substring)) {
				TreeNode treeNode = new TreeNode(data.start, substring);
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
					data = new ChildrenCreationStep(treeNode, treeNode.getEnd());
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
		StringBuilder combinationStringBuilder = new StringBuilder();
		LinkedList<IterationData> dataStack = new LinkedList<>();
		IterationData data = new IterationData(treeRoot, combinationStringBuilder.length());

		while (data.node != null) {
			if (data.node.getChildren().size() > data.position) {
				dataStack.push(data);
				data = new IterationData(data.node.getChildren().get(data.position), combinationStringBuilder.length());
				combinationStringBuilder.append(data.node.getValue()).append(" ");
				continue;
			}

			if (data.node.isLeaf()) {
				if (isValidLeaf(targetLength, data.node)) {
					combinations.add(combinationStringBuilder.toString());
				}
			}

			if (data.node.getStart() == -1) {
				break;
			}
			combinationStringBuilder.delete(data.start, combinationStringBuilder.length());
			data = dataStack.pop();
			data.position++;
		}

		return combinations;
	}

	private boolean isValidLeaf(final int targetLength, final TreeNode leaf) {
		if (!leaf.isLeaf()) {
			throw new IllegalArgumentException("Not a leaf");
		}
		return leaf.getEnd() == targetLength;
	}

	private static class ChildrenCreationStep {
		public final TreeNode parent;
		public final int start;
		public int length = 1;

		public ChildrenCreationStep(TreeNode parent, int start) {
			this.parent = parent;
			this.start = start;
		}
	}

	private static class IterationData {
		public final TreeNode node;
		public int position;
		public final int start;

		public IterationData(TreeNode node, int start) {
			this.node = node;
			this.start = start;
		}
	}

}
