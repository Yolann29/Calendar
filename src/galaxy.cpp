#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <regex>
#include <list>

using namespace std;

int getPairs(int);

vector<string> lines;
list<int> numbersAbscisse;
list<int> numbersOrdonnee;
vector<int> abscisse;
vector<int> ordonnee;
int pairs = 0;

int main() {
    string path("Doc/galaxy");
    ifstream file(path.c_str());

    string line;
    while (getline(file, line)) {
        lines.push_back(line);
    }

    regex regex("#");
    int i = 0;
    for (string line : lines) {
        smatch match;
        string currentLine = line;
        int j = 0;
        while (regex_search(currentLine, match, regex)) {
            ordonnee.push_back(i);
            while (line.at(j) != '#') {
                j++;
            }
            abscisse.push_back(j);
            j = abscisse.back() + 1;
            currentLine = match.suffix().str();
        }
        i++;
    }

    for (int i = 0; i < lines.at(0).length(); i++) {
        numbersAbscisse.push_back(i);
        numbersOrdonnee.push_back(i);
    }

    for (int num : abscisse) {
        numbersAbscisse.remove(num);
    }

    for (int num : ordonnee) {
        numbersOrdonnee.remove(num);
    }

    for (int num : numbersAbscisse) {
        cout << "NumAb: " << num << endl;
    }

    int j = 0;
    for (int num : numbersAbscisse) {
        for (int i = 0; i < abscisse.size(); i++) {
            if (abscisse.at(i) > num + j) {
                abscisse.at(i) += 1;
            }
        }
        j++;
    }

    j = 0;
    for (int num : numbersOrdonnee) {
        for (int i = 0; i < ordonnee.size(); i++) {
            if (ordonnee.at(i) > num + j) {
                ordonnee.at(i) += 1;
            }
        }
        j++;
    }

    for (int num : abscisse) {
        cout << "Ab: " << num << endl;
    }

    for (int num : ordonnee) {
        cout << "Or: " << num << endl;
    }

    int sum = 0;
    for (int i = 0; i < abscisse.size(); i++) {
        sum += getPairs(i);
        // cout << "Abscisse: " << abscisse.at(i) << endl;
        // cout << "Ordonnée: " << ordonnee.at(i) << endl;
        // cout << "sum: " << sum << endl;
    }

    cout << pairs << endl;
    cout << sum << endl;
}

int getPairs(int index) {
    int sum = 0;
    for (int i = index; i < abscisse.size(); i++) {
        pairs += 1;
        sum += abs(abscisse.at(i) - abscisse.at(index)) + abs(ordonnee.at(i) - ordonnee.at(index));
    }
    pairs -= 1;
    return sum;
}