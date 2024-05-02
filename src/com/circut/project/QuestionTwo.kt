package com.circut.project

/*
* 2. FileSystem:Explain the datastructures and algorithms that you would use to design an in-memory file system. Illustrate with an example in code where possible.
*/

fun main() {
    val fileSystem = FileSystem()

    // Create directories
    fileSystem.create(Constant.HomeDirectory, true)
    fileSystem.create(Constant.HomeUserDirectoryPath, true)

    var fileName1 = "/file1.txt"
    var fileName2 = "/file2.txt"

    val file1 = "${Constant.HomeUserDirectoryPath}$fileName1"
    val file2 = "${Constant.HomeUserDirectoryPath}$fileName2"
    // Create files
    fileSystem.create(file1, false)
    fileSystem.create(file2, false)


    // Write to files
    fileSystem.writeFile(file1, Constant.textFile1)
    fileSystem.writeFile(file2, Constant.textFile2)

    // Read file content
    val content1 = fileSystem.readFile(file1)
    val content2 = fileSystem.readFile(file2)

    println("Content of file1.txt: $content1")
    println("Content of file2.txt: $content2")

    // Delete files
    fileSystem.delete(file1, false)
    fileSystem.delete(file2, false)

    // Delete directories
    fileSystem.delete(Constant.HomeUserDirectoryPath, true)
    fileSystem.delete(Constant.HomeDirectory, true)
}


class FileNode(
    val name: String,
    val isDirectory: Boolean,
    var content: String? = null,
    var size: Int = 0,
    val children: MutableList<FileNode> = mutableListOf(),
    var parent: FileNode? = null
)

class FileSystem {
    private val root: FileNode = FileNode(Constant.slash, true)

    // Method to create a file or directory
    fun create(path: String, isDirectory: Boolean) {
        val parts = path.split(Constant.slash)
        var current = root

        for (i in 1 until parts.size) {
            val name = parts[i]
            val isLast = i == parts.size - 1
            val node = current.children.find { it.name == name && it.isDirectory == isDirectory }

            current = if (node == null) {
                val newNode = FileNode(name, isDirectory)
                current.children.add(newNode)
                newNode
            } else {
                if (isLast && node.isDirectory == isDirectory) {
                    throw IllegalArgumentException("${Constant.FILE_OR_DIRECTORY_EXIST} $path")
                }
                node
            }
        }
    }

    // Method to delete a file or directory
    fun delete(path: String, isDirectory: Boolean) {
        val parts = path.split(Constant.slash)
        var current = root

        for (i in 1 until parts.size) {
            val name = parts[i]
            val node = current.children.find { it.name == name && it.isDirectory == isDirectory }

            if (node == null) {
                throw IllegalArgumentException("${Constant.FILE_OR_DIRECTORY_NOT_FOUND} $path")
            } else {
                current = node
            }
        }

        if (current.children.isNotEmpty()) {
            throw IllegalArgumentException("${Constant.DIRECTORY_NOT_EMPTY}$path")
        }

        current.parent?.children?.remove(current)
    }

    // Method to read file content
    fun readFile(path: String): String? {
        val parts = path.split(Constant.slash)
        var current = root

        for (i in 1 until parts.size) {
            val name = parts[i]
            val node = current.children.find { it.name == name && !it.isDirectory }

            if (node == null) {
                throw IllegalArgumentException("${Constant.FILE_NOT_FOUND} $path")
            } else {
                current = node
            }
        }

        return current.content
    }

    // Method to write to a file
    fun writeFile(path: String, content: String) {
        val parts = path.split(Constant.slash)
        var current = root

        for (i in 1 until parts.size - 1) {
            val name = parts[i]
            val node = current.children.find { it.name == name && it.isDirectory }

            if (node == null) {
                throw IllegalArgumentException("${Constant.DIRECTORY_NOT_FOUND} ${parts.subList(0, i).joinToString(Constant.slash)}")
            } else {
                current = node
            }
        }

        val fileName = parts.last()
        val fileNode = current.children.find { it.name == fileName && !it.isDirectory }
            ?: throw IllegalArgumentException("${Constant.FILE_NOT_FOUND} $path")

        fileNode.content = content
        fileNode.size = content.length
    }
}
