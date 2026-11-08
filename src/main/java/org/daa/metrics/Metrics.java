
package org.daa.metrics;

public class Metrics {

    public long comparisons = 0;
    public long recursiveCalls = 0;
    public long dpUpdates = 0;
    public long lpsComparisons = 0;

    public void reset() {
        comparisons = 0;
        recursiveCalls = 0;
        dpUpdates = 0;
        lpsComparisons = 0;
    }
}
