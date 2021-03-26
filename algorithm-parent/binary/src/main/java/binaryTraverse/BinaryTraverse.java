package binaryTraverse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;


/**
 * 二叉树遍历
 *
 * @author jinyu
 * @date 2021/3/13 10:34
 */
public class BinaryTraverse {

    //递归先序遍历
    public static void preBinaryTraverse(BinaryNode head) {
        if (head == null) {
            return;
        }
        System.out.println(head.getValue());
        preBinaryTraverse(head.getLeft());
        preBinaryTraverse(head.getRight());
    }

    //递归中序遍历
    public static void midBinaryTraverse(BinaryNode head) {
        if (head == null) {
            return;
        }
        midBinaryTraverse(head.getLeft());
        System.out.println(head.getValue());
        midBinaryTraverse(head.getRight());
    }

    //递归后序遍历
    public static void lastBinaryTraverse(BinaryNode head) {
        if (head == null) {
            return;
        }
        lastBinaryTraverse(head.getLeft());
        lastBinaryTraverse(head.getRight());
        System.out.println(head.getValue());
    }

    //栈实现先序遍历
    public static void preBinaryTraverseByStack(BinaryNode head) {
        Deque<BinaryNode> tempStack = new ArrayDeque<>();
        if (head != null) {
            tempStack.push(head);
            while (!tempStack.isEmpty()) {
                BinaryNode currentNode = tempStack.pop();
                System.out.println(currentNode.getValue());
                if (currentNode.getRight() != null) {
                    tempStack.push(currentNode.getRight());
                }

                if (currentNode.getLeft() != null) {
                    tempStack.push(currentNode.getLeft());
                }
            }
        } else {
            System.out.println("二叉树为空");
        }

    }

    //栈实现中序遍历
    public static void midBinaryTraverseByStack(BinaryNode head) {
        Deque<BinaryNode> tempStack = new ArrayDeque<>();
        if (head != null) {
            while (!tempStack.isEmpty() || head != null) {
                if (head != null) {
                    tempStack.push(head);
                    head = head.getLeft();
                } else {
                    head = tempStack.pop();
                    System.out.println(head.getValue());
                    head = head.getRight();
                }

            }
        } else {
            System.out.println("二叉树为空");
        }
    }

    //栈实现后序遍历,需要两个栈
    public static void lastBinaryTraverseByStack01(BinaryNode head) {
        Deque<BinaryNode> resultStack = new ArrayDeque<>();
        Deque<BinaryNode> tempStack = new ArrayDeque<>();
        if (head != null) {
            tempStack.push(head);
            while (!tempStack.isEmpty()) {
                BinaryNode currentNode = tempStack.pop();
                resultStack.push(currentNode);
                if (currentNode.getLeft() != null) {
                    tempStack.push(currentNode.getLeft());
                }

                if (currentNode.getRight() != null) {
                    tempStack.push(currentNode.getRight());
                }
            }

            for (BinaryNode binaryNode : resultStack) {
                System.out.println(binaryNode.getValue());
            }
        } else {
            System.out.println("二叉树为空");
        }
    }

    //栈实现后序遍历，只需要一个栈
    public static void lastBinaryTraverseByStack02(BinaryNode node) {
        BinaryNode lastPrintNode = null;
        BinaryNode nowNode;
        if (node != null) {
            Deque<BinaryNode> tempStack = new ArrayDeque<>();
            tempStack.push(node);
            while (!tempStack.isEmpty()) {
                nowNode = tempStack.peek();
                if (nowNode.getLeft() != null && nowNode.getLeft() != lastPrintNode && nowNode.getRight() != lastPrintNode) {
                    tempStack.push(nowNode.getLeft());
                } else if (nowNode.getRight() != null && nowNode.getRight() != lastPrintNode) {
                    tempStack.push(nowNode.getRight());
                } else {
                    System.out.println(tempStack.pop().getValue());
                    lastPrintNode = nowNode;
                }
            }
        }

    }

    //队列实现按层遍历
    public static void binaryTraverseByQueue(BinaryNode head) {
        if (head != null) {
            Deque<BinaryNode> queue = new ArrayDeque<>();
            queue.add(head);
            BinaryNode currentEndNode = head;
            BinaryNode nextEndNode = null;
            int maxSize = 0;
            int currentSize = 0;
            BinaryNode nowNode;
            while (!queue.isEmpty()) {
                nowNode = queue.poll();
                if (nowNode.getLeft() != null) {
                    queue.add(nowNode.getLeft());
                    nextEndNode = nowNode.getLeft();
                }

                if (nowNode.getRight() != null) {
                    queue.add(nowNode.getRight());
                    nextEndNode = nowNode.getRight();
                }

                if (nowNode != currentEndNode) {
                    currentSize++;
                } else {
                    maxSize = Math.max(currentSize, maxSize);
                    currentSize = 1;
                    currentEndNode = nextEndNode;
                }
            }

            System.out.println(maxSize);
        } else {
            System.out.println("二叉树为空");
        }
    }

    //使用一个map记录每个节点的层数
    public static void binaryTraverseByQueueAndMap(BinaryNode head) {
        if (head != null) {
            HashMap<BinaryNode, Integer> nodeLevel = new HashMap<>();
            Deque<BinaryNode> queue = new ArrayDeque<>();
            queue.add(head);
            nodeLevel.put(head, 1);
            int currentSize = 0;
            int currentLevel = 1;
            int maxSize = 0;
            while (!queue.isEmpty()) {
                BinaryNode nowNode = queue.poll();
                Integer nowLevel = nodeLevel.get(nowNode);
                if (nowNode.getLeft() != null) {
                    queue.add(nowNode.getLeft());
                    nodeLevel.put(nowNode.getLeft(), nowLevel + 1);
                }

                if (nowNode.getRight() != null) {
                    queue.add(nowNode.getRight());
                    nodeLevel.put(nowNode.getRight(), nowLevel + 1);
                }

                if (nowLevel == currentLevel) {
                    currentSize++;
                } else {
                    currentLevel++;
                    maxSize = Math.max(maxSize, currentSize);
                    currentSize = 1;
                }
            }

            maxSize = Math.max(maxSize, currentSize);
            System.out.println(maxSize);
        } else {
            System.out.println("二叉树为空");
        }
    }

    //先序序列化,没有的子节点使用null代替
    public static Deque<String> preSerial(BinaryNode head) {
        Deque<String> queue = new ArrayDeque<>();
        pres(head, queue);
        return queue;
    }

    private static void pres(BinaryNode head, Deque<String> queue) {
        if (head == null) {
            queue.add("null");
        } else {
            queue.add(String.valueOf(head.getValue()));
            pres(head.getLeft(), queue);
            pres(head.getRight(), queue);
        }

    }

    //先序反序列化
    public static BinaryNode preBuild(Deque<String> queue) {
        if (queue == null || queue.size() == 0) {
            return null;
        }

        return preb(queue);
    }

    private static BinaryNode preb(Deque<String> queue) {
        String node = queue.poll();
        if ("null".equals(node)) {
            return null;
        }
        BinaryNode head = new BinaryNode();
        head.setValue(Integer.parseInt(node));
        head.setLeft(preb(queue));
        head.setRight(preb(queue));

        return head;


    }

    //按层序列化
    public static Deque<String> serialByC(BinaryNode head) {
        if (head != null) {
            //Deque<BinaryNode> queue = new ArrayDeque<>();
            Queue<BinaryNode> queue = new LinkedList<>();
            Deque<String> serialResult = new ArrayDeque<>();
            queue.add(head);
            while (!queue.isEmpty()) {
                BinaryNode node = queue.poll();
                if (node != null) {
                    serialResult.add(String.valueOf(node.getValue()));
                    if (node.getLeft() != null) {
                        queue.add(node.getLeft());
                    } else {
                        queue.add(null);
                    }

                    if (node.getRight() != null) {
                        queue.add(node.getRight());
                    } else {
                        queue.add(null);
                    }

                } else {
                    serialResult.add("null");
                }
            }

            return serialResult;
        }

        return null;

    }

    public static void main(String[] args) {
        BinaryNode head = new BinaryNode(1,
                new BinaryNode(2,
                        new BinaryNode(4, null, null),
                        new BinaryNode(5, null, null)),
                new BinaryNode(3,
                        new BinaryNode(6, null, null),
                        new BinaryNode(7, null, null)));

        BinaryNode head1 = new BinaryNode(1,
                new BinaryNode(2,
                        new BinaryNode(4, null, null),
                        null),
                new BinaryNode(3,
                        new BinaryNode(6, null, null),
                        new BinaryNode(7, null, null)));

      /*  System.out.println("递归先序遍历");
        preBinaryTraverse(head);
        System.out.println();
        System.out.println("递归中序遍历");
        midBinaryTraverse(head);
        System.out.println();
        System.out.println("递归后序遍历");
        lastBinaryTraverse(head);
        System.out.println();
        System.out.println("栈实现先序遍历");
        preBinaryTraverseByStack(head);
        System.out.println();
        System.out.println("栈实现中序遍历");
        midBinaryTraverseByStack(head);
        System.out.println("栈实现后序遍历");
        lastBinaryTraverseByStack01(head);
        System.out.println();
        System.out.println("只使用一个栈进行后序遍历");
        lastBinaryTraverseByStack02(head);*/


        Deque<String> strings = serialByC(head1);
        strings.forEach(System.out::println);

    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class BinaryNode {
    private int value;
    private BinaryNode left;
    private BinaryNode right;

    public BinaryNode(int value) {
        this.value = value;
    }
}