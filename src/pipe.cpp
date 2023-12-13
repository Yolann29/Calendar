#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <regex>
#include <list>

using namespace std;

char getPipeBehindS();
char getDirection(char , char);

vector<string> lines;
vector<string> zeros;
int indexRow = 0;
int indexCol = 0;

int main() {
    string path("Doc/Pipe");
    ifstream file(path.c_str());

    string line;
    while (getline(file, line)) {
        lines.push_back(line);
        line.assign(line.length(), '0');
        zeros.push_back(line);
    }

    regex regex("S");
    int i = 0;
    for (string line : lines) {
        smatch match;
        string currentLine = line;
        // cout << line << endl;
        if (regex_search(currentLine, match, regex)) {
            indexRow = i;
            for (int j = 0; j < currentLine.length(); j++) {
                if (currentLine.at(j) == 'S') indexCol = j;
            }
        }
        i++;
    }

    cout << lines.at(indexRow).at(indexCol) << endl;
    char symbol = getPipeBehindS();
    cout << "Indexes: " << indexRow << " " << indexCol << endl;
    cout << getPipeBehindS() << endl;
    char direction = 'd';
    cout << "Dire: " << direction << endl << endl;
    int count = 0;
    while (symbol != 'S') {
        switch (direction) {
            case 'u':
                indexRow = indexRow + 1;
                break;
            case 'd':
                indexRow = indexRow - 1;
                break;
            case 'l':
                indexCol += 1;
                break;
            case 'r':
                indexCol = indexCol - 1;
        }
        symbol = lines.at(indexRow).at(indexCol);
        direction = getDirection(symbol, direction);
        count++;
        // cout << "Indexes: " << indexRow << " " << indexCol << endl;
        // cout << "Count: " << count << endl;
        // cout << "Dire: " << direction << endl;
        // cout << "Symbol: " << symbol << endl << endl;
    }

    cout << count << endl;
    cout << count / 2 << endl;

}

char getPipeBehindS() {
    list<char> symbols = {'|', '-', 'J', 'L', 'F', '7'};
    if (indexCol != 0) {
        if (getDirection(lines.at(indexRow).at(indexCol - 1), 'r') == 'i') {
            symbols.remove('-');
            symbols.remove('J');
            symbols.remove('7');
        }
    } else {
        symbols.remove('-');
        symbols.remove('J');
        symbols.remove('7');
    }
    if (indexCol != lines.at(0).length()) {
        if (getDirection(lines.at(indexRow).at(indexCol + 1), 'l') == 'i') {
            symbols.remove('-');
            symbols.remove('L');
            symbols.remove('F');
        }
    } else {
        symbols.remove('-');
        symbols.remove('L');
        symbols.remove('F');
    }
    
    if (indexRow != 0) {
        if (getDirection(lines.at(indexRow - 1).at(indexCol), 'd') == 'i') {
            symbols.remove('|');
            symbols.remove('J');
            symbols.remove('L');
        }
    } else {
        symbols.remove('|');
        symbols.remove('F');
        symbols.remove('7');
    }

    if (indexRow != lines.at(0).length()) {
        if (getDirection(lines.at(indexRow + 1).at(indexCol), 'u') == 'i') {
            symbols.remove('|');
            symbols.remove('F');
            symbols.remove('7');
        }
    } else {
        symbols.remove('|');
        symbols.remove('F');
        symbols.remove('7');
    }
    

    return symbols.front();
}

char getDirection(char symbol, char direction) {
    switch(symbol) {
        case '|':
            switch (direction) {
                case 'u':
                    return 'u';
                case 'd':
                    return 'd';
                case 'r':
                case 'l' :
                    return 'i';
            }
        case '-':
            switch (direction) {
                case 'r':
                    return 'r';
                case 'l' :
                    return 'l';
                case 'u':
                case 'd':
                    return 'i';
            }
        case 'J':
            switch (direction) {
                case 'u':
                    return 'r';
                case 'l' :
                    return 'd';
                case 'd':
                case 'r':
                    return 'i';
            }
        case 'L':
            switch (direction) {
                case 'u':
                    return 'l';
                case 'r' :
                    return 'd';
                case 'd':
                case 'l':
                    return 'i';
            }
        case 'F':
            switch (direction) {
                case 'd':
                    return 'l';
                case 'r' :
                    return 'u';
                case 'u':
                case 'l':
                    return 'i';
            }
        case '7':
            switch (direction) {
                case 'd':
                    return 'r';
                case 'l' :
                    return 'u';
                case 'u':
                case 'r':
                    return 'i';
            }
        case '.':
            return 'i';
    }
}