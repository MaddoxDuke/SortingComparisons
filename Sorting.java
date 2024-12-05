import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.Duration;

public class Sorting {

    public static long comparisons = 0;
    public static long swaps = 0;
    public static void main(String args[]) {

        String fileName = "sortme1000000.txt";
        String outputFile = "sortedData.txt";
        int[] largeArray = new int[1000000];       

        try {
            importFile(fileName, largeArray);
        }
        catch (IOException e) {
            System.err.println(e);
        }

        
        //All of the info for the spreadsheet/bubbleSort.
        long[] timeArray = new long[6];
        int c = 0;
        for (int count = 10; count <= 1000000; count*=10) {

            swaps = 0;
            comparisons = 0;

            Instant start = Instant.now();
            mergeSort(largeArray, 0, count-1);
            Instant finish = Instant.now();
            long elapsed = (long)Duration.between(start, finish).toMillis();
            timeArray[c] = elapsed;

            System.out.println("\n\nComparisons for range 0 -> " + count + " = " + comparisons);
            System.out.println("Swaps for range 0 -> " + count + " = " + swaps);
            System.out.println("Time elapsed for range 0 -> " + count + " = " + timeArray[c]);

            c++; // <-- Peak language
        }
        System.out.print("The times were "); 
        for (int i = 0; i < timeArray.length; i++) System.out.print(timeArray[i] + ", ");
        System.out.print(" respectively.\n\n");

        // try {
        //     outputFile(outputFile, largeArray);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

    }
    static void quickSort(int[] array, int start, int end) {
        if (start < end) {
            int p = partition(array, start, end);
    
            quickSort(array, start, p - 1);
            quickSort(array, p + 1, end);
        }
    }
    static int partition(int array[], int start, int end) {
        int pointer = array[end];
        int i = (start - 1);
    
        for (int j = start; j < end; j++) {
            comparisons++;
            if (array[j] <= pointer) {
                i++;
    
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
                swaps++;
            }
        }
    
        int temp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = temp;
        swaps++;
    
        return i + 1;
    }
    public static void selectionSort(int array[], int count) {

        long comparisons = 0;
        long swaps = 0;

        for (int i = 0; i < count -1; i++) {
            int min = i;
            for (int j = i+1; j < count; j++) {
                if (array[min] < array[j])  min = j; 
                comparisons++;
            }

            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
            swaps++;
        }
        System.out.println("For count 0->" + count + ":\nSwaps were " + swaps + "\nComparisons were " + comparisons);
    }
    public static void bubbleSort(int array[], int count) {
        long[] compSwap = new long[2];

        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                compSwap[1]++;
                if (array[j] > array[j+1]) { // descending
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    compSwap[0]++;
                }
            }
        }
        
        System.out.println("For the range 0 -> " + count + ":" + 
                        "\nThere were " + compSwap[0] + " swaps." + 
                        "\nThere were " + compSwap[1] + " comparisons.\n");
    }
    static void merge(int arr[], int left, int middle, int right)
    {
        // Find sizes of two subarrays to be merged
        int n1 = middle - left + 1;
        int n2 = right - middle;

        // Create two temp arrays
        int L[] = new int[n1];
        int R[] = new int[n2];

        // Copy data to temp arrays
        for (int i = 0; i < n1; ++i)
            L[i] = arr[left + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[middle + 1 + j];

        // Merge the temp arrays

        // Initial indices of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = left;
        while (i < n1 && j < n2) {

            comparisons++;

            if (L[i] >= R[j]) { // Determines (Acsending <=) or (Descending >=)
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
                swaps++;
            }
            k++;
        }
        // Copy remaining elements of L[] if any
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        // Copy remaining elements of R[] if any
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    static void mergeSort(int arr[], int left, int right)
    // Main function that sorts arr[left..right] using merge()
    //Note, very similar to binary search. Gets faster the larger the dataset is. 
    //left and right can be used to limit the amount of data being sorted.
    {
        if (left < right) {

            // Find the middle point
            int middle = left + (right - left) / 2;

            // Sort first and second halves
            mergeSort(arr, left, middle);
            mergeSort(arr, middle+1, right);
            // Merge the sorted halves
            merge(arr, left, middle, right);
        }
    }
    public static void importFile(String fileName, int array[]) throws IOException {

        String line = "";
        FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
        int i = 0;

        while ((line = br.readLine()) != null) {
            array[i] = Integer.valueOf(line);
            i++;
        }
        fr.close();
    }
    public static void outputFile(String outputFile, int arr[]) throws IOException {
        
        System.out.println("Outputing sorted data...");
        int i = 0;
        FileWriter writer = new FileWriter(outputFile, false);
        while (i < arr.length) {
            try {
                writer.write(arr[i] + "\n");
            } catch (IOException e) {
                System.out.println("File Error");
            }
            i++;
        }
        writer.close();
    }
}
