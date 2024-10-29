package src.Model;

import java.util.Arrays;

/**
 * Helper Util Class
 * Note: this class is no longer used by the program as the sorting
 * functionality was
 * transfered to the JTable.
 *
 * Contains: sortData method that uses merge sort to sort the data
 */
public class Util {
    /**
     * Test dirver
     */
    public static void main(String[] args) {
        boolean check = true;
        String string1 = "asdf";
        String string2 = rightPadString(string1, 10);

        if (string1 != "asdf")
            check = false;
        if (string2 != "asdf      ")
            check = false;

        if (!check)
            System.out.println("Util Test Pass");
        else {
            System.out.println("Util Test Fail");
        }
    }

    public static IndexValueData[] sortData(IndexValueData[] data, int length) {
        for (int i = 2; i <= length * 2; i *= 2) {

            int k = 0;
            do {
                int start = k, end = k + i;
                if (end > length) {
                    end = length;
                }
                int mid = (i / 2) + k;
                if (mid > length)
                    break;
                int left_length = mid - start;
                int right_length = end - mid;
                var left_array = Arrays.copyOfRange(data, start, mid);
                var right_array = Arrays.copyOfRange(data, mid, end);
                var temp_array = merge(left_length, left_array, right_length, right_array);
                int temp_index = 0;
                for (int j = start; j < end; j++) {
                    data[j].set(temp_array[temp_index]);
                    temp_index += 1;
                }
                k += i;
            } while (k < length);
        }

        return data;
    }

    public static IndexValueData[] merge(int left_length, IndexValueData[] left,
            int right_length, IndexValueData[] right) {
        int count = 0;
        int length = left_length + right_length;
        var new_array = new IndexValueData[length];
        for (int i = 0; i < length; i++) {
            new_array[i] = new IndexValueData();
        }
        int i = 0, j = 0, k = 0;

        // while there are items in both arrays
        while (i < left_length && j < right_length) {
            if (left[i].compareTo(right[j]) < 0) {
                new_array[k].set(left[i]);
                i += 1;
            } else {
                new_array[k].set(right[j]);
                j += 1;
            }
            k += 1;
        }

        // if the left array has items left
        while (i < left_length) {
            new_array[k].set(left[i]);
            i += 1;
            k += 1;
        }

        // if the right array has items left
        while (j < right_length) {
            new_array[k].set(right[j]);
            j += 1;
            k += 1;
        }

        return new_array;
    }

    public static IndexValueData[] sortData3(IndexValueData[] data, int length) {
        var new_array = new IndexValueData[length];
        if (length > 1) {
            // split the array
            int mid = length / 2;
            var left_array = Arrays.copyOfRange(data, 0, mid);
            var right_array = Arrays.copyOfRange(data, mid, length);
            int left_length = mid;
            int right_length = length - mid;
            if ((length % 2) == 1)
                right_length += 1;
            // calls merge sort on each half
            left_array = sortData(left_array, left_length);
            right_array = sortData(right_array, right_length);
            merge(left_length, left_array, right_length, right_array);
        } else {
            new_array = data;
        }

        return new_array;
    }

    /**
     * Pads the given string to make the string length eqaual to length
     */
    public static String rightPadString(String string, int length) {
        char[] chars = new char[length];
        int index = 0;
        while (index < length) {
            if (index < string.length()) {
                chars[index] = string.charAt(index);
            } else {
                chars[index] = ' ';
            }
            index += 1;
        }
        return new String(chars);
    }

}
