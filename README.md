
# DAA Assignment 4 — Dynamic Programming and String Algorithms

**Student:** Mansur Akhmetov  
**Group:** SE-2517  
**University:** Astana IT University  
**Course:** Design and Analysis of Algorithms

## Project Description

In this assignment, I implemented and compared different algorithms for Edit Distance and String Search using Java.

The main goal was to understand how these algorithms work, compare their time and space complexity, and analyze their performance using experiments.

## Implemented Algorithms

### 1. Edit Distance

I implemented three versions of the Levenshtein Edit Distance algorithm:

- **Recursive Edit Distance** — uses direct recursion without memoization.
- **Table Edit Distance** — uses dynamic programming with a full 2D table.
- **Two Rows Edit Distance** — uses dynamic programming with only two rows to reduce memory usage.

All three implementations calculate the minimum number of insertions, deletions, and replacements needed to transform one string into another.

### 2. String Search

I implemented two string searching algorithms:

- **Naive Search** — checks the pattern at every possible position in the text.
- **KMP Search** — uses the LPS (Longest Prefix Suffix) array to avoid unnecessary comparisons.

Both algorithms return all matching positions, including overlapping matches.

## Technologies Used

- Java 25
- Maven
- JUnit 5
- Python
- pandas
- matplotlib
- IntelliJ IDEA

## Project Structure

```text
DAA_Assignment4_Mansur_Akhmetov_SE2517/
├── src/
│   ├── main/
│   │   └── java/
│   │       └── org/daa/
│   │           ├── editdistance/
│   │           │   ├── EditDistance.java
│   │           │   ├── RecursiveEditDistance.java
│   │           │   ├── TableEditDistance.java
│   │           │   └── TwoRowsEditDistance.java
│   │           ├── search/
│   │           │   ├── StringSearch.java
│   │           │   ├── NaiveSearch.java
│   │           │   └── KMPSearch.java
│   │           ├── metrics/
│   │           │   └── Metrics.java
│   │           ├── experiment/
│   │           │   ├── InputGenerator.java
│   │           │   └── ExperimentRunner.java
│   │           └── Main.java
│   └── test/
│       └── java/
│           └── org/daa/
│               ├── EditDistanceTest.java
│               └── StringSearchTest.java
├── results/
│   ├── results.csv
│   └── plots/
│       ├── edit_distance.png
│       ├── string_search.png
│       └── recursive_growth.png
├── plot_results.py
├── REPORT.md
├── README.md
└── pom.xml
```

## How to Build the Project

Open the project in IntelliJ IDEA or use a terminal in the project directory.

Compile the project:

```bash
mvn clean compile
```

Run the tests:

```bash
mvn test
```

Run the main demonstration class:

```bash
mvn exec:java -Dexec.mainClass="org.daa.Main"
```

The last command requires the Maven Exec Plugin to be available. Alternatively, run Main.java directly from IntelliJ IDEA.

## Running the Experiments

The experiments are implemented in:

`src/main/java/org/daa/experiment/ExperimentRunner.java`

To run them:

1. Open ExperimentRunner.java in IntelliJ IDEA.
2. Run its main() method.
3. Wait until the experiments finish.
4. Check the generated CSV file.

The results are saved in:

`results/results.csv`

Each experiment is executed five times.

The program measures execution time, character comparisons, recursive calls, DP updates, and LPS comparisons.

A fixed random seed of 42 is used to make the generated inputs reproducible.

## Generating Plots

Install the required Python libraries:

```bash
py -m pip install pandas matplotlib
```

Generate the plots:

```bash
py plot_results.py
```

Three PNG plots will be saved in `results/plots/`:

1. `edit_distance.png` — DP cell updates.
2. `string_search.png` — Naive vs KMP comparisons.
3. `recursive_growth.png` — recursive call growth.

## Testing

JUnit 5 is used for testing.

The tests include:

- Reference Edit Distance examples.
- Empty string cases.
- 100 random Edit Distance pairs.
- Reference String Search examples.
- Overlapping pattern matches.
- Empty pattern validation.
- Patterns longer than the text.
- LPS array verification.
- 100 random String Search cases.

The tests compare the results of different implementations to check their correctness.

## Experiment Results

The experiments showed that Recursive Edit Distance becomes very slow as the input size increases.

Table DP and Two Rows DP perform the same number of DP cell updates, but Two Rows DP uses less memory.

For String Search, KMP performs significantly fewer comparisons than Naive Search on repetitive worst-case inputs.

For example, with a text length of 8000:

- Naive Search: 5,760,800 comparisons.
- KMP Search: 15,201 comparisons.
- KMP LPS construction: 1,597 comparisons.

More detailed results, tables, plots, and explanations are available in [REPORT.md](REPORT.md).


## Conclusion

This assignment helped me understand the differences between recursive algorithms, dynamic programming, and efficient string searching.

I also learned how to measure algorithm performance, compare operation counts, and visualize experimental results.
