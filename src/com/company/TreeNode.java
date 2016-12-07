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
		Stack<IterationData> stack = new Stack<>();
		IterationData data = new IterationData(this);

		while (data.node != null) {
			if (data.node.isLeaf()) {
				return null;
			}
			if (data.node.getEnd() == start) {
				return data.node.getChildren();
			}

			if (data.node.getChildren().size() > data.position) {
				stack.push(data);
				data = new IterationData(data.node.getChildren().get(data.position));
				continue;
			}

			data = stack.pop();
			data.position++;
		}

		return null;
	}

	public static class IterationData {
		public final TreeNode node;
		public int position;

		public IterationData(TreeNode node) {
			this.node = node;
		}
	}
}
