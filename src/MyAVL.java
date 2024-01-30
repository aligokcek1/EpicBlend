public class MyAVL {
    class AVLNode { // AVL CLASS TO STORE EPIC BLEND SONGS, USING FOR ASK OPERATION
        Song song;
        AVLNode left, right;
        int height;

        public AVLNode(Song song) {
            this.song = song;
            this.height = 1;
        }
    }

    private AVLNode root;

    private int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(AVLNode node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }

    private AVLNode insert(AVLNode node, Song song) {
        if (node == null)
            return new AVLNode(song);

        if (song.compareCountNum(node.song) < 0)
            node.left = insert(node.left, song);
        else if (song.compareCountNum(node.song) > 0)
            node.right = insert(node.right, song);
        else
            return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if (balance > 1) {
            if (song.compareCountNum(node.left.song) < 0)
                return rightRotate(node);
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        if (balance < -1) {
            if (song.compareCountNum(node.right.song) > 0)
                return leftRotate(node);
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    public void insert(Song song) {
        root = insert(root, song);
    }

    private void printTree(AVLNode node) {
        if (node != null) {
            printTree(node.right);
            Project3.out.print(node.song.id + " ");
            printTree(node.left);
        }
    }

    public void printTree() {
        printTree(root);
        Project3.out.print("\n");
    }

    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    private AVLNode deleteNode(AVLNode root, Song song) {
        if (root == null) {
            return root;
        }

        if (song.compareCountNum(root.song) < 0) {
            root.left = deleteNode(root.left, song);
        } else if (song.compareCountNum(root.song) > 0) {
            root.right = deleteNode(root.right, song);
        } else {
            if (root.left == null || root.right == null) {
                AVLNode temp = (root.left != null) ? root.left : root.right;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else {
                    root = temp;
                }
            } else {
                AVLNode temp = minValueNode(root.right);
                root.song = temp.song;
                root.right = deleteNode(root.right, temp.song);
            }
        }

        if (root != null) {
            root.height = 1 + Math.max(height(root.left), height(root.right));

            int balance = getBalance(root);

            if (balance > 1) {
                if (getBalance(root.left) >= 0) {
                    return rightRotate(root);
                } else {
                    root.left = leftRotate(root.left);
                    return rightRotate(root);
                }
            }

            if (balance < -1) {
                if (getBalance(root.right) <= 0) {
                    return leftRotate(root);
                } else {
                    root.right = rightRotate(root.right);
                    return leftRotate(root);
                }
            }
        }

        return root;
    }

    public void remove(Song song) {
        root = deleteNode(root, song);
    }
}

