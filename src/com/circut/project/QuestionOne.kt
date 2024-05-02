package com.circut.project


/*
* 1. Write a data structure for a BinaryTree whose nodes contain Int values in Kotlin.*/


fun main() {
    val tree = BinaryTree()
    tree.add(5)
    tree.add(3)
    tree.add(7)
    tree.add(2)
    tree.add(4)
    tree.add(6)
    tree.add(8)

    val elements = tree.inOrderTraversal(tree.root)
    println("In-order traversal result: $elements")
    tree.reset()

    tree.add(15)
    tree.add(13)
    tree.add(17)
    tree.add(12)
    tree.add(14)
    tree.add(16)
    tree.add(18)

    val valueToFind = 16
    println("Is $valueToFind present in the tree? ${tree.find(tree.root, valueToFind)}")

    val valueToDelete = 13
    println("Deleting $valueToDelete from the tree...")
    tree.delete(tree.root, valueToDelete)
    val elementsAfterDeletion = tree.inOrderTraversal(tree.root)
    println("In-order traversal result after deletion: $elementsAfterDeletion")
}



class TreeNode(var value: Int) {
    var leftNode: TreeNode? = null
    var rightNode: TreeNode? = null
}

class BinaryTree {
    var root: TreeNode? = null // The root node of the binary tree

    // Method to add a value to the binary tree
    // @param value: The value to be added
    fun add(value: Int) {
        root = addRecursive(root, value)
    }

    // Recursive method to add a value to the binary tree
    // @param current: The current node being processed
    // @param value: The value to be added
    // @return: The updated current node
    private fun addRecursive(current: TreeNode?, value: Int): TreeNode? {
        // Base Case
        if (current == null) {
            return TreeNode(value)
        }
        // If the value is less than the current node's value, go left
        if (value < current.value) {
            current.leftNode = addRecursive(current.leftNode, value)
        } else if (value > current.value) { // If the value is greater than the current node's value, go right
            current.rightNode = addRecursive(current.rightNode, value)
        }
        return current
    }

    // Method to find a value in the binary tree
    // @param root: The root node of the binary tree
    // @param value: The value to search for
    // @return: True if the value is found, otherwise false
    fun find(root: TreeNode?, value: Int): Boolean {
        if (root == null) {
            return false
        }
        if (root.value == value) {
            return true
        }
        return find(root.leftNode, value) || find(root.rightNode, value)
    }

    // Method to delete a value from the binary tree
    // @param root: The root node of the binary tree
    // @param value: The value to be deleted
    // @return: The updated root node
    fun delete(root: TreeNode?, value: Int): TreeNode? {
        return when {
            root == null -> null
            value < root.value -> {
                root.leftNode = delete(root.leftNode, value)
                root
            }
            value > root.value -> {
                root.rightNode = delete(root.rightNode, value)
                root
            }
            else -> {
                // Case 1: Node to delete has no children
                if (root.leftNode == null && root.rightNode == null) {
                    null
                }
                // Case 2: Node to delete has only one child
                else if (root.leftNode == null) {
                    root.rightNode
                } else if (root.rightNode == null) {
                    root.leftNode
                }
                // Case 3: Node to delete has two children
                else {
                    // Replace the value of the node with the smallest value in the right subtree
                    root.value = findMin(root.rightNode)
                    // Delete the node with the smallest value in the right subtree
                    root.rightNode = delete(root.rightNode, root.value)
                    root
                }
            }
        }
    }


    // Helper method to find the smallest value in the binary tree
    // @param root: The root node of the binary tree
    // @return: The smallest value in the binary tree
    private fun findMin(root: TreeNode?): Int {
        var current = root
        while (current?.leftNode != null) {
            current = current.leftNode
        }
        return current?.value ?: throw NullPointerException("Expression 'current' must not be null")
    }

    // Example method to traverse in order
    // @param node: The current node being processed
    // @return: A list of node values traversed in order
    fun inOrderTraversal(node: TreeNode?): List<Int> {
        return node?.let {
            inOrderTraversal(it.leftNode) + it.value + inOrderTraversal(it.rightNode)
        } ?: emptyList()
    }

    // Method to reset the binary tree
    fun reset() {
        root = null
    }
}
