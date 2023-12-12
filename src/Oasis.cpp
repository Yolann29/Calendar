#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

int getChain(const string&, int);

int main() {
    string file("Doc/Oasis");
    vector<string> lines;

    ifstream myFile(file.c_str());

    if(myFile) {
        string line;
        while (getline(myFile, line)) {
            lines.push_back(line);
        }
    } else {
        cout << "ERROR: Impossible to open the file." << endl;
    }

    int sum = 0;
    for (string line : lines) {
        sum += getChain(line, 0);
    }

    cout << sum << endl;
}

int getChain(const string& line, int res) {
    vector<string> chain;
    stringstream stringstream(line);  
    string word;
    while (stringstream >> word) {
        chain.push_back(word);
    }
    res += stoi(chain.at(chain.size() - 1));
    string newLine;
    bool zero = true;
    for (int i = 0; i < chain.size() - 1; i++) {
        if (stoi(chain.at(i)) != 0) zero = false;
        newLine += to_string(stoi(chain.at(i + 1)) - stoi(chain.at(i))) + " ";
    }
    if (zero == false) return getChain(newLine, res);
    return res;
}