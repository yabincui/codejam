import java.util.*;
import java.io.*;

public class Addition {
  static class Equation {
    String a;
    String b;
    long result;
    Equation(String a, String b, long result) {
      this.a = a;
      this.b = b;
      this.result = result;
    }
  }

  class Node {
    String name;
    ArrayList<Edge> neighbors;

    Node(String name) {
      this.name = name;
      this.neighbors = new ArrayList<Edge>();
    }

    void addEdge(Node toNode, long sumOfTwoNodes) {
      for (int i = 0; i < neighbors.size(); ++i) {
        if (neighbors.get(i).toNode == toNode) {
          return;
        }
      }
      neighbors.add(new Edge(toNode, sumOfTwoNodes));
    }
  }

  class Edge {
    Node toNode;
    long sumOfTwoNodes;

    Edge(Node toNode, long sumOfTwoNodes) {
      this.toNode = toNode;
      this.sumOfTwoNodes = sumOfTwoNodes;
    }
  }

  private HashMap<String, Node> nodeMap = new HashMap<String, Node>();

  private Node createNode(String name) {
    if (!nodeMap.containsKey(name)) {
      nodeMap.put(name, new Node(name));
    }
    return nodeMap.get(name);
  }

  private Node findNode(String name) {
    return nodeMap.get(name);
  }

  public void buildMap(Equation[] equations) {
    for (int i = 0; i < equations.length; ++i) {
      Node nodeA = createNode(equations[i].a);
      Node nodeB = createNode(equations[i].b);
      nodeA.addEdge(nodeB, equations[i].result);
      nodeB.addEdge(nodeA, equations[i].result);
    }
  }

  boolean searchOddLengthPath(Node curNode, Node targetNode, ArrayList<Edge> path,
                              HashMap<Node, Integer> visitedNodes) {
    for (Edge edge : curNode.neighbors) {
      Node node = edge.toNode;
      if (node == targetNode && path.size() % 2 == 0) {
        path.add(edge);
        return true;
      }
      int posMask = (path.size() % 2 == 1) ? 2 : 1;
      if (visitedNodes.containsKey(node)) {
        Integer mask = visitedNodes.get(node);
        if ((posMask & ~mask) == 0) {
          continue;
        }
        posMask |= mask;
      }
      visitedNodes.put(node, posMask);
      path.add(edge);
      boolean ret = searchOddLengthPath(node, targetNode, path, visitedNodes);
      if (ret) {
        return true;
      }
      path.remove(path.size() - 1);
    }
    return false;
  }

  static class AdditionResult {
    boolean have_result;
    long result;

    AdditionResult() {
      have_result = false;
      result = 0;
    }
  }

  public AdditionResult add(String a, String b) {
    AdditionResult result = new AdditionResult();
    Node nodeA = findNode(a);
    Node nodeB = findNode(b);
    if (nodeA == null || nodeB == null) {
      return result;
    }
    ArrayList<Edge> path = new ArrayList<Edge>();
    HashMap<Node, Integer> visitedNodes = new HashMap<Node, Integer>();
    visitedNodes.put(nodeA, 1);
    boolean ret = searchOddLengthPath(nodeA, nodeB, path, visitedNodes);
    if (ret) {
      long addSum = 0;
      long subSum = 0;
      for (int i = 0; i < path.size(); ++i) {
        if (i % 2 == 0) {
          addSum += path.get(i).sumOfTwoNodes;
        } else {
          subSum += path.get(i).sumOfTwoNodes;
        }
      }
      result.have_result = true;
      result.result = addSum - subSum;
    } else {
      if (!a.equals(b)) {
        AdditionResult result1 = add(a, a);
        AdditionResult result2 = add(b, b);
        if (result1.have_result && result2.have_result) {
          result.have_result = true;
          result.result = (result1.result + result2.result) / 2;
        }
      }
    }
    return result;
  }

  public static void main(String[] args) throws IOException {
    String input = "addition_in_large.txt";
    String output = "addition_out_large.txt";
    Scanner scanner = new Scanner(new File(input));
    PrintWriter writer = new PrintWriter(new File(output));
    int testCount = scanner.nextInt();
    for (int test = 1; test <= testCount; ++test) {
      System.out.printf("test = %d\n", test);
      int N = scanner.nextInt();
      Equation[] equations = new Equation[N];
      for (int i = 0; i < N; ++i) {
        String s = scanner.next();
        int plusPos = s.indexOf('+');
        int assignPos = s.indexOf('=');
        String a = s.substring(0, plusPos);
        String b = s.substring(plusPos + 1, assignPos);
        long value = Long.valueOf(s.substring(assignPos + 1));
        equations[i] = new Equation(a, b, value);
      }
      Addition addition = new Addition();
      addition.buildMap(equations);
      int Q = scanner.nextInt();
      writer.printf("Case #%d:\n", test);
      for (int i = 0; i < Q; ++i) {
        String s= scanner.next();
        int plusPos = s.indexOf('+');
        String a = s.substring(0, plusPos);
        String b = s.substring(plusPos + 1);
        System.out.printf("addition %s %s\n", a, b);
        AdditionResult result = addition.add(a, b);
        System.out.printf("addition %s %s, result = %b\n", a, b, result.have_result);
        if (result.have_result) {
          writer.printf("%s+%s=%d\n", a, b, result.result);
        }
      }
    }
    writer.close();
  }
}
