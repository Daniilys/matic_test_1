package com.company;

import java.util.LinkedList;
import java.util.List;

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
		if (isLeaf()) {
			return null;
		}

		if (getEnd() == start) {
			return getChildren();
		}

		for (TreeNode child : getChildren()) {
			List<TreeNode> children = child.findChildrenAt(start);
			if (children != null) {
				return children;
			}
		}

		return null;
	}
}
