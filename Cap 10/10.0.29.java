// albero binario di ricerca su Integer
// ongi nodo dell'albero binario è strettamente maggiore  dei valodi dei nodi del sottoalbeo sinistro e minore o uguale dei valori dei nodi del sottoalbero destro


class Node{
	Integer data;
	Node left;
	Node right;
	public Node() {
		data = null;
		left = null;
		right = null;
	}

}

class AlbBinRic {
	Node head = null;

	public AlbBinRic() {
		head = null;
	}

	public AlbBinRic(Integer i) {
		head = new Node();
		head.data = i;
	}

	public void insert(Integer i) {
		if(i>head.data) {
			if (head.left == null)
				head.left = new AlbBinRic(i);
			else
				(head.left).insert(i);
		}
		else {
			if (head.right == null)
				head.left = new AlbBinRic(i);
			else
				(head.left).insert(i);
		}
	}

	public boolean presente(Integer i) {
		if(head.data == i)
			return true;
		else{
			if((head.left == null) && (head.right == null))
				return false;
			else {
				if(head.left == null)
					return (head.right).presente(i);
				else {
					if(head.right == null)
						return (head.left).presente(i);
					else
						return ((head.left).presente(i) || (head.right).presente(i));
				}
			}
		}
		return false;
	}

	public void stampa() {
		if (head != null){
			if (head.right != null)
				head.right.stampa();
			System.out.print(head.data);
			if (head.left != null)
				head.left.stampa();
		}
	}
}