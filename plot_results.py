
import pandas as pd
import matplotlib.pyplot as plt
from pathlib import Path

RESULTS = Path("results")
PLOTS = RESULTS / "plots"
PLOTS.mkdir(parents=True, exist_ok=True)

df = pd.read_csv(RESULTS / "results.csv")

# Median of 5 runs for each experiment
data = (
    df.groupby(["algorithm", "input", "n"], as_index=False)
    .agg(
        time_ms=("time_ms", "median"),
        comparisons=("comparisons", "median"),
        recursive_calls=("recursive_calls", "median"),
        dp_updates=("dp_updates", "median"),
        lps_comparisons=("lps_comparisons", "median")
    )
)

# Plot 1: Edit Distance DP operations
plt.figure(figsize=(9, 5))

for algorithm in ["Table", "TwoRows"]:
    subset = data[
        (data["algorithm"] == algorithm) &
        (data["input"] == "worst")
        ].sort_values("n")

    plt.plot(
        subset["n"],
        subset["dp_updates"],
        marker="o",
        label=algorithm
    )

plt.title("Edit Distance: DP Updates")
plt.xlabel("String length (n)")
plt.ylabel("DP cell updates")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(PLOTS / "edit_distance.png", dpi=200)
plt.close()

# Plot 2: Naive vs KMP worst-case comparisons
plt.figure(figsize=(9, 5))

for algorithm in ["Naive", "KMP"]:
    subset = data[
        (data["algorithm"] == algorithm) &
        (data["input"] == "worst")
        ].sort_values("n")

    comparisons = subset["comparisons"].copy()

    if algorithm == "KMP":
        comparisons += subset["lps_comparisons"]

    plt.plot(
        subset["n"],
        comparisons,
        marker="o",
        label=algorithm
    )

plt.title("String Search: Worst-Case Comparisons")
plt.xlabel("Text length (n)")
plt.ylabel("Character comparisons")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(PLOTS / "string_search.png", dpi=200)
plt.close()

# Plot 3: Recursive Edit Distance growth
subset = data[
    (data["algorithm"] == "Recursive") &
    (data["input"] == "worst")
    ].sort_values("n")

plt.figure(figsize=(9, 5))

plt.plot(
    subset["n"],
    subset["recursive_calls"],
    marker="o",
    label="Recursive calls"
)

plt.yscale("log")

plt.title("Recursive Edit Distance: Call Growth")
plt.xlabel("String length (n)")
plt.ylabel("Recursive calls (log scale)")
plt.legend()
plt.grid(True)
plt.tight_layout()
plt.savefig(PLOTS / "recursive_growth.png", dpi=200)
plt.close()

print("All 3 plots generated successfully!")
