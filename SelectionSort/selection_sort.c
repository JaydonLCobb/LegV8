/* To compile this program:                                     *
 * gcc 321_s2023_selection_sort.c -o 321_s2023_selection_sort   */

#include <stdio.h>

/* This one-function version of selection sort is provided for reference. *
 * You are not required to implement this function.                       */
void selection_sort_orig(int *a, int n)
{
  int i, j, min, jmin, t;

  for (i = 0; i < n; i++) {
    min = a[i];
    for (jmin = i, j = i + 1; j < n; j++) {
      if (a[j] < min) {
        min = a[j];
        jmin = j;
      }
    }
    t = a[i];
    a[i] = a[jmin];
    a[jmin] = t;
  }
}

/* fill fills an array of length n with decreasing values from n - 1 to *
 * zero (reverse sorted order).                                         */
void fill(int *a, int n) {
  int i;
  
  for (i = 0; i < n; i++) {
    a[i] = n - i - 1;
  }
}

/* swap swaps the value at address p with the value at address q. */
void swap(int *p, int *q)
{
  int tmp;

  tmp = *p;
  *p = *q;
  *q = tmp;
}

/* find_min_idx takes an array, a, and the number of elements in that *
 * array, n, and returns the index of the smallest element in the     *
 * input array.                                                       *
 *                                                                    *
 * With respect to its use in selection_sort, a should be advanced to *
 * point to the next element for each subsequent call to find_min_idx *
 * (see the parameter to this function in selection_sort, below).     */
int find_min_idx(int *a, int n)
{
  int i;
  int min_idx = 0;

  for (i = 1; i < n; i++) {
    if (a[i] < a[min_idx]) {
      min_idx = i;
    }
  }

  return min_idx;
}

/* bubble_sort takes an array, a, and the number of elements in that *
 * array, n, and sorts them into non-decreasing order.  This version *
 * function implements only the outer loop of the standard selection *
 * sort algorithm (see selection_sort_orig, above), calling          *
 * find_min_idx repeatedly and swapping the current element with the *
 * element at the returned index over the length of the input array. */
void selection_sort(int *a, int n)
{
  int i;

  for (i = 0; i < n; i++) {
    swap(a + i, a + i + find_min_idx(a + i, n - i));
  }
}

/* binary_search is an implementation of the standard recursive *
 * binary search algorithm.  It searches the array a between    *
 * the values of start (inclusive) and end (exclusive) for      *
 * value.  If value is found, its index is returned; otherwise  *
 * the function returns -1 to indicate failure.                 */
int binary_search(int *a, int start, int end, int value)
{
  int index;

  if (end < start) {
    return -1;
  }

  index = (start + end) / 2;

  if (a[index] == value) {
    return index;
  }
  if (a[index] > value) {
    return binary_search(a, start, index - 1, value);
  }
  return binary_search(a, index + 1, end, value);
}

/* Your main function should allocate space for an array, call fill to   *
 * fill it with decreasing numbers, and then call selection_sort to sort *
 * it.  Use the HALT emulator instruction to see the memory contents and *
 * confirm that your functions work.  You may choose any array size you  *
 * like (up to the default limit of memory, 4096 bytes or 512 8-byte     *
 * integers); the choice of 10000 here is arbitrary.                     *
 *                                                                       * 
 * After sorting, call binary search with 4 values: the smallest,        *
 * largest, and middle items in your array, followed by a value not in   *
 * the array.  After each call PRNT X0 to display the return value.      *
 *                                                                       *
 * After completing all of the above, HALT the emulator to force a core  *
 * dump so that you (and the TAs) can examing the contents of memory.    */
int main(int argc, char *argv[])
{
  int a[10000];

  /* sizeof (array) / sizeof (array[0]) is a standard C idiom that *
   * statically calculates the number of elements in the array.    *
   * It allows you to change the size of an array without having   *
   * to change parameters where you use it.  You don't have a      *
   * sizeof operator in assembly, so you can simply do these       *
   * calculations by hard and hard-code the results.               */
  fill(a, sizeof (a) / sizeof (a[0]));
  selection_sort(a, sizeof (a) / sizeof (a[0]));
  print_array(a, 100);
  /* Returns 0 */
  binary_search(a, 0, (sizeof (a) / sizeof (a[0])) - 1, 0);
  /* Returns 9999 */
  binary_search(a, 0, (sizeof (a) / sizeof (a[0])) - 1,
                sizeof (a) / sizeof (a[0]) - 1);
  /* Returns 5000 */
  binary_search(a, 0, (sizeof (a) / sizeof (a[0])) - 1,
                (sizeof (a) / sizeof (a[0])) / 2);
  /* Returns -1 */
  binary_search(a, 0, (sizeof (a) / sizeof (a[0])) - 1,
                sizeof (a) / sizeof (a[0]));
  
  return 0;
}