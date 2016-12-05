package com.company;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mahasamatman on 05/12/16.
 */
public class Tree {
	private final int start;
	private final String value;
	private final List<Tree> children = new LinkedList<>();

	public Tree(final int start, final String value) {
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

	public void addChild(Tree child) {
		children.add(child);
	}

	public List<Tree> getChildren() {
		return children;
	}

	public boolean isLeaf() {
		return children.isEmpty();
	}

	public List<Tree> findChildrenAt(final int start) {
		if (isLeaf()) {
			return null;
		}

		if (getEnd() == start) {
			return getChildren();
		} else {
			for (Tree child : getChildren()) {
				List<Tree> children = child.findChildrenAt(start);
				if (children != null) {
					return children;
				}
			}
			return null;
		}
	}
}
