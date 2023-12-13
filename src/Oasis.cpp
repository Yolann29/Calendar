#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <bits/stdc++.h>

using namespace std;

int getChain(const string&, int);
int getChain2(const string&, vector<int>);

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
    vector<int> res;
    for (string line : lines) {
        // sum += getChain(line, 0);
        sum += getChain2(line, res);
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

int getChain2(const string& line, vector<int> add) {
    vector<string> chain;
    stringstream stringstream(line);  
    string word;
    while (stringstream >> word) {
        chain.push_back(word);
    }
    add.push_back(stoi(chain.at(0)));
    string newLine;
    bool zero = true;
    for (int i = 0; i < chain.size() - 1; i++) {
        if (stoi(chain.at(i)) != 0) zero = false;
        newLine += to_string(stoi(chain.at(i + 1)) - stoi(chain.at(i))) + " ";
    }
    if (zero == false) return getChain2(newLine, add);
    int res = 0;
    for (int i = add.size() - 1; i >= 0; i--) {
        res = add.at(i) - res;
    }
    return res;
}