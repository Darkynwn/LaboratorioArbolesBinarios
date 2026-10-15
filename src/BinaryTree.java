import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class BinaryTree {

    TreeNode root;
    public BinaryTree() {

        this.root = null;
    }
    public BinaryTree(TreeNode root) {

        this.root = root;
    }

//1. Construccion del arbol

    public void insertRoot(Object data){
     if(this.root == null) {
         this.root = new TreeNode(data);
     }
    }

    public TreeNode search(Object data){
        return searchRec(root, data);
    }

    private TreeNode searchRec(TreeNode node, Object data) {
        if (node == null) return null;
        if (node.data.equals(data)) return node;
        TreeNode foundLeft = searchRec(node.left, data);
        if (foundLeft != null) return foundLeft;
        return searchRec(node.right, data);
    }

    public boolean insertLeft(Object parent, Object data) {
        TreeNode pNode = search(parent);
        if (pNode != null && pNode.left == null) {
            pNode.left = new TreeNode(data);
            return true;
        }
        return false;
    }

    public boolean insertRight(Object parent, Object data) {
        TreeNode pNode = search(parent);
        if (pNode != null && pNode.right == null) {
            pNode.right = new TreeNode(data);
            return true;
        }
        return false;
    }

    public void buildTree(Scanner scanner) {
        System.out.print("Raiz: ");
        String rData = scanner.nextLine().trim();
        if (rData.isEmpty() || rData.equals("-")) {
            return;
        }
        this.root = new TreeNode(rData);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            System.out.print("Hijo izquierdo de " + current.data + ": ");
            String lData = scanner.nextLine().trim();
            if (!lData.isEmpty() && !lData.equals("-")) {
                current.left = new TreeNode(lData);
                queue.add(current.left);
            }

            System.out.print("Hijo derecho de " + current.data + ": ");
            String rData2 = scanner.nextLine().trim();
            if (!rData2.isEmpty() && !rData2.equals("-")) {
                current.right = new TreeNode(rData2);
                queue.add(current.right);
            }
        }
    }

//2. Visualizacion del arbol

    public void printTree() {
        if (root == null) {
            System.out.println("El árbol está vacío.");
            return;
        }
        printTreeRec(root, "", true);
    }

    private void printTreeRec(TreeNode node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data);
            if (node.left != null || node.right != null) {
                printTreeRec(node.left, prefix + (isTail ? "    " : "│   "), node.right == null);
                if (node.right != null) {
                    printTreeRec(node.right, prefix + (isTail ? "    " : "│   "), true);
                }
            }
        }
    }
//3. Recorrido

    public String preorder() {
        StringBuilder sb = new StringBuilder();
        preorderRec(root, sb);
        return sb.toString().trim();
    }

    private void preorderRec(TreeNode node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.data).append(" ");
            preorderRec(node.left, sb);
            preorderRec(node.right, sb);
        }
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorderRec(root, sb);
        return sb.toString().trim();
    }

    private void inorderRec(TreeNode node, StringBuilder sb) {
        if (node != null) {
            inorderRec(node.left, sb);
            sb.append(node.data).append(" ");
            inorderRec(node.right, sb);
        }
    }

    public String postorder() {
        StringBuilder sb = new StringBuilder();
        postorderRec(root, sb);
        return sb.toString().trim();
    }

    private void postorderRec(TreeNode node, StringBuilder sb) {
        if (node != null) {
            postorderRec(node.left, sb);
            postorderRec(node.right, sb);
            sb.append(node.data).append(" ");
        }
    }
//4. Factores de equilibrio

    public int height(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int balanceFactor(TreeNode node) {
        if (node == null) return 0;
        return height(node.right) - height(node.left);
    }

    public boolean isBalanced() {
        return isBalancedRec(root);
    }

    private boolean isBalancedRec(TreeNode node) {
        if (node == null) return true;
        int bf = balanceFactor(node);
        if (Math.abs(bf) > 1) return false;
        return isBalancedRec(node.left) && isBalancedRec(node.right);
    }

    public List<Object> unbalancedNodes() {
        List<Object> unbalanced = new ArrayList<>();
        findUnbalanced(root, unbalanced);
        return unbalanced;
    }

    private void findUnbalanced(TreeNode node, List<Object> list) {
        if (node == null) return;
        if (Math.abs(balanceFactor(node)) > 1) {
            list.add(node.data);
        }
        findUnbalanced(node.left, list);
        findUnbalanced(node.right, list);
    }

    public void printBalanceFactors() {
        if (root == null) {
            System.out.println("El árbol está vacío.");
            return;
        }
        printBalanceFactorsRec(root, "", true);
    }

    private void printBalanceFactorsRec(TreeNode node, String prefix, boolean isTail) {
        if (node != null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + node.data + " (FE: " + balanceFactor(node) + ")");
            if (node.left != null || node.right != null) {
                printBalanceFactorsRec(node.left, prefix + (isTail ? "    " : "│   "), node.right == null);
                if (node.right != null) {
                    printBalanceFactorsRec(node.right, prefix + (isTail ? "    " : "│   "), true);
                }
            }
        }
    }

// 5. Balanceo AVL

    public TreeNode rotateRight(TreeNode node) {
        TreeNode newRoot = node.left;
        TreeNode temp = newRoot.right;
        newRoot.right = node;
        node.left = temp;
        return newRoot;
    }

    public TreeNode rotateLeft(TreeNode node) {
        TreeNode newRoot = node.right;
        TreeNode temp = newRoot.left;
        newRoot.left = node;
        node.right = temp;
        return newRoot;
    }

    public TreeNode rotateLeftRight(TreeNode node) {
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    public TreeNode rotateRightLeft(TreeNode node) {
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    public void balance() {
        System.out.println("\n--- Árbol original antes de balancear ---");
        printBalanceFactors();

        List<Object> unbalanced = unbalancedNodes();
        System.out.println("Nodos que requieren balanceo: " + unbalanced);

        // Se ejecuta hasta que el árbol construido manualmente sea completamente AVL
        while (!isBalanced()) {
            root = applyBalance(root);
        }

        System.out.println("\n--- Árbol después del balanceo AVL ---");
        printBalanceFactors();
    }

    private TreeNode applyBalance(TreeNode node) {
        if (node == null) return null;

        // Recorrido posorden para balancear desde las hojas hacia la raíz
        node.left = applyBalance(node.left);
        node.right = applyBalance(node.right);

        int bf = balanceFactor(node);

        // Desbalanceado hacia la izquierda
        if (bf < -1) {
            if (balanceFactor(node.left) <= 0) {
                return rotateRight(node); // Caso Izquierda-Izquierda
            } else {
                return rotateLeftRight(node); // Caso Izquierda-Derecha
            }
        }

        // Desbalanceado hacia la derecha
        if (bf > 1) {
            if (balanceFactor(node.right) >= 0) {
                return rotateLeft(node); // Caso Derecha-Derecha
            } else {
                return rotateRightLeft(node); // Caso Derecha-Izquierda
            }
        }

        return node;
    }
}