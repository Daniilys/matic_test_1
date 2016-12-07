package com.company;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by mahasamatman on 05/12/16.
 */
public class TreeNode {
	private final int start;
	private final String value;
	private final List<TreeNode> children = new LinkedList<>();

	public TreeNode(final int start, final String value) {
		this.start = start;
		this.value = value;
	}

	public int getStart() {
		return start;
	}

	public int getEnd() {
		return start + value.length();
	}

	public String getValue() {
		return value;
	}

	public void addChild(TreeNode child) {
		children.add(child);
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public List<TreeNode> findChildrenAt(final int start) {
		Stack<Pair<TreeNode, Integer>> stack = new Stack<>();
		TreeNode node = this;
		int position = 0;

		while (node != null) {
			if (node.isLeaf()) {
				return null;
			}
			if (node.getEnd() == start) {
				return node.getChildren();
			}

			if (node.getChildren().size() > position) {
				stack.push(new Pair<>(node, position));
				node = node.getChildren().get(position);
				position = 0;
				continue;
			}

			Pair<TreeNode, Integer> pair = stack.pop();
			node = pair.getKey();
			position = pair.getValue() + 1;
		}

		return null;
	}
}
