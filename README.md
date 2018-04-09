# dirscan

This application prints all writable folders of a tree structure which are 
reachable from the root exclusively via readable directories.

## Usage
1. Clone the project
2. Launch ``./gradlew jar`` (on Unix-based OSes) or ``gradlew.bat jar`` (on Windows).
This creates a JAR file in the folder ``build/libs`` which is used to launch the standalone application.
3. The JAR can then be launched using ``java -jar build/libs/dirscan-1.0-SNAPSHOT.jar <readable_dir_names_filename> <writable_dir_names_text_filename>``.

## Arguments
* ``readable_dir_names_filename`` specifies the path to the text file containing the list of the readable directories
* ``writable_dir_names_filename`` specifies the path to the text file containing the list of the writable directories

The files themselves are simple text files. Each line in these files contains an absolute path to a directory.
Examples can be found in the root folder of the project (``readable.txt`` and ``writable.txt``).
