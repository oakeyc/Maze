import java.util.ArrayList;
import java.util.Comparator;

// Utilities
class Utils {
    // Sorts the given list using the given comparator using merge sort.
    static <T> void mergeSort(ArrayList<T> list, Comparator<T> comp) {
        Utils.mergeHelp(list, comp, 0, list.size() - 1);
    }
    
    // Sorts the given list using the given comparator
    //   from index start to index end using merge sort.
    static <T> void mergeHelp(ArrayList<T> list, Comparator<T> comp, int start, int end) {
        // End condition.
        if (end > start) {
            int mid = (start + end) / 2;
            // Split and sort halves.
            Utils.mergeHelp(list, comp, start, mid);
            Utils.mergeHelp(list, comp, mid + 1, end);
            
            // Merge the halves.
            int ind1 = start;
            int ind2 = mid + 1;
            ArrayList<T> result = new ArrayList<T>();

            while (ind1 <= mid && ind2 <= end) {
                if (comp.compare(list.get(ind1), list.get(ind2)) >= 0) {
                    result.add(list.get(ind1));
                    ind1++;
                }
                else {
                    result.add(list.get(ind2));
                    ind2++;
                }
            }
            
            // Handle extra values in one half.
            if (ind1 <= mid) {
                for (int i = ind1; i <= mid; i++) {
                    result.add(list.get(i));
                }
            }
            else if (ind2 <= end) {
                for (int i = ind2; i <= end; i++) {
                    result.add(list.get(i));
                }
            }

            // Fix original list.
            for (int i = 0; i < result.size(); i++) {
                list.set(start + i, result.get(i));
            }
        } 
    }
}

// Compares two edges by weight.
class EdgeWeightComparator implements Comparator<Edge> {

    @Override
    public int compare(Edge e1, Edge e2) {
        return e2.weight - e1.weight;
    }
    
}
