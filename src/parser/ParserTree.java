package parser;

import java.util.ArrayList;
import java.util.List;

class ParserTree {
	private final SimpleNode parentNode;
	private SimpleNode currentNode;

	public ParserTree() {
		parentNode = new SimpleNode(null, "$");
		parentNode.addChild("<tiger-program>");
		currentNode = parentNode;
		currentNode = currentNode.getLastChild();
	}

	public void moveDown()
	{
		currentNode = currentNode.getLastChild();
	}

	public void moveUp() 
	{
		currentNode = currentNode.getParent();
	}

	public void add(String label) 
	{
		currentNode.addChild(label);
	}

	public String print() 
	{
		return parentNode.print("");
	}

	private class SimpleNode {
		private final String label;
		private final SimpleNode parent;
		private final List<SimpleNode> children;

		SimpleNode(SimpleNode parent, String label) {
			this.parent = parent;
			this.label = label;
			children = new ArrayList<SimpleNode>();
		}

		SimpleNode getLastChild() {
			return children.get(children.size() - 1);
		}

		SimpleNode getParent() 
		{
			return parent;
		}

		public void addChild(String label)
		{
			children.add(new SimpleNode(this, label));
		}

		public String print(String prepend)	{
			String res = "";
			res += prepend + label + "\n";
			for (SimpleNode n : children)
				res += n.print(prepend + "\t");
			return res;
		}
	}
}
