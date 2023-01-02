package com.mycompany.app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mycompany.app.Day7.Type.DIR;
import static com.mycompany.app.Day7.Type.FILE;
import static com.mycompany.app.FileReader.readInput;
import static java.lang.Math.min;

class Day7 implements Day {

    private Node root;

    private final String filename;
    private List<String> input;

    public Day7(String filename) throws IOException {
        this.filename = filename;
        loadData();
    }

    private void loadData() throws IOException {
        input = readInput(filename);
        prepareData();
    }

    private void prepareData() {
        // Create tree.
        root = new Node(new FileNode(DIR, "/", -1), null, new ArrayList<>());
        Node currentNode = root;
        for (String command : input) {
            if (command.equals("$ cd ..")) { // Go up in tree.
                currentNode = currentNode.parent;
            } else if (command.startsWith("$ cd")) { // Go down in tree.
                String[] args = command.split(" ");
                if (!args[2].equals("/")) {
                    currentNode = currentNode.children.stream()
                                                      .filter(n -> n.data.name.equals(args[2]))
                                                      .findAny()
                                                      .get();
                }
            } else if (command.equals("$ ls")) { // List all the file nodes.
                // Do nothing.
            } else { // ls output.
                String[] args = command.split(" ");
                if (args[0].startsWith("dir")) {
                    currentNode.children.add(new Node(new FileNode(DIR, args[1], -1), currentNode, new ArrayList<>()));
                } else {
                    currentNode.children.add(new Node(new FileNode(FILE, args[1], Long.parseLong(args[0])), currentNode, new ArrayList<>()));
                }
            }
        }
        // Calculate folders size.
        calculateNodeSize(root);
    }

    private long calculateNodeSize(Node currentNode) {
        if (DIR.equals(currentNode.data.type)) {
            currentNode.data.size = currentNode.children.stream().mapToLong(this::calculateNodeSize).sum();
        }
        return currentNode.data.size;
    }

    @Override
    public String calculateFirstStar() {
        return "" + calculateDirectorySize(root);
    }

    private long calculateDirectorySize(Node currentNode) {
        long acc = 0;
        if (currentNode.children != null) {
            for (Node node : currentNode.children) {
                if (node.data.type.equals(DIR) && node.data.size <= 100000) {
                    acc += node.data.size;
                }
                acc += calculateDirectorySize(node);
            }
        }
        return acc;
    }

    @Override
    public String calculateSecondStar() {
        long totalDiscSpace = 70_000_000;
        long updateSize = 30_000_000;
        long occupiedSpace = root.data.size;
        long neededSpace = occupiedSpace - (totalDiscSpace - updateSize);
        return "" + findSmallestDirectoryToDelete(root, neededSpace);
    }

    private long findSmallestDirectoryToDelete(Node currentNode, long size) {
        long smallestDirectorySize = Long.MAX_VALUE;
        if (DIR.equals(currentNode.data.type)) {
            for (Node node : currentNode.children) {
                if (DIR.equals(node.data.type) && node.data.size > size) {
                    smallestDirectorySize = min(smallestDirectorySize, node.data.size);
                }
                smallestDirectorySize = min(smallestDirectorySize, findSmallestDirectoryToDelete(node, size));
            }
        }
        return smallestDirectorySize;
    }

    private record Node(FileNode data, Node parent, List<Node> children) {
    }

    private static class FileNode {
        private final Type type;
        private final String name;
        private long size;

        FileNode(Type type, String name, long size) {
            this.type = type;
            this.name = name;
            this.size = size;
        }
    }

    enum Type {
        FILE, DIR
    }
}
