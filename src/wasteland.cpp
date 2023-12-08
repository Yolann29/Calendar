#include <fstream>
#include <iostream>
#include <string>
#include <list>

using namespace std;

//Declared Functions
bool goRight(const string&, int);

int main() {
    string file("Doc/Test2");
    list<string> lines;

    ifstream myFile(file.c_str());

    if(myFile) {
        string line;
        while (getline(myFile, line)) {
            lines.push_back(line);
        }
    } else {
        cout << "ERROR: Impossible to open the file." << endl;
    }
    string instructions = lines.front();
    lines.pop_front();
    lines.pop_front();


    for (string line : lines) {
        cout << line << endl;
    }
    cout << "Instructions: " + instructions << endl;
    bool instru = goRight(instructions, 42);
    bool instru2 = goRight(instructions, 1);

    

    return 0;
}

int getNextLine(int indexLine, bool instruction) {

}

bool goRight(const string& instructions, int turn) {
    turn = turn - (turn / instructions.length())* instructions.length();
    if (instructions.at(turn) == 'R') {
        return true;
    }
    return false;
}

