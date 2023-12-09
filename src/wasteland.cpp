#include <fstream>
#include <iostream>
#include <string>
#include <list>
#include <vector>
#include <regex>

using namespace std;

//Declared Functions
bool goRight(const string&, int);
vector<int> getRelations(const vector<string>&);
long getResult(const string&, const vector<int>&, const vector<string>&);

int main() {
    string file("Doc/Wasteland.txt");
    vector<string> lines;
    vector<string> letters;

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
    lines.erase(lines.begin());
    lines.erase(lines.begin());

    regex regex("[A-Z]+");
    int i = 0;
    for (string line : lines) {
        smatch match;
        string currentLine = line;
        while (regex_search(currentLine, match, regex)) {
            letters.push_back(match.str());
            currentLine = match.suffix().str();
        }
        cout << "Line " << i << ": " << line << endl;
        i++;
    }

    for (string line : letters) {
        cout << "Letters: " << line << endl;
    }

    vector<int> relations = getRelations(letters);
    cout << "Result: " << getResult(instructions, relations, letters) << endl;

    return 0;
}

vector<int> getRelations(const vector<string>& letters) {
    vector<int> relations;
    for (int i = 0; i < letters.size() / 3; i++) {
        relations.push_back(i);
        relations.push_back(i);
        relations.push_back(i);
        int size = relations.size();
        for (int j = 0; j < letters.size() / 3; j++) {
            if (letters.at(i*3 + 1) == letters.at(j*3)) {
                relations.at(size - 2) = j;
            }
            if (letters.at(i*3 + 2) == letters.at(j*3)) {
                relations.at(size - 1) = j;
            }
        }
    }
    return relations;
}

bool goRight(const string& instructions, long long turn) {
    turn = turn - (turn / instructions.length())* instructions.length();
    // cout << "Index: " << turn << endl;
    if (instructions.at(turn) == 'R') {
        return true;
    }
    return false;
}

long getResult(const string& instructions, const vector<int>& relations, const vector<string>& letters) {
    int index = 0;
    int line = 0;
    long long sum = 0;
    for (int i = 0; i < relations.size(); i++) {
        cout << "Relations: " << relations.at(i) << endl;
    }
    cout << "Instructions: " << instructions.length() << endl;
    while (letters.at(line * 3) != "ZZZ") {
        index = goRight(instructions, sum) + 1;
        line = relations.at(line * 3 + index);
        sum++;
        if (sum % 10000000 == 0) cout << sum << endl;
    }
    cout << "Sum: " << sum << endl;
    cout << "Last change: " << line << " " << letters.at(line*3) << endl;
    return 0;
}