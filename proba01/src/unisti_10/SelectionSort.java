package unisti_10;


public class SelectionSort {
	
	public static int[] arr = {65, 5, 102, 2}; 
	
	public static int[] selectionSort(int[] arr){
		for(int i = 0; i < arr.length-1; i++){
			int minIndex = i;
			for(int j = i+1; j < arr.length; j++){
				if(arr[j] < arr[minIndex]){
					swap(arr, j, minIndex);
				}
			}
		}
		return arr;
	}
	public static int[] insertionSort(int[] arr){
		for(int i = 1; i < arr.length; i++){
			int index = i;
			while(index > 0 && arr[index] < arr[index-1]){
				swap(arr, index, index-1);
				index = index-1;
			}
		}
		return arr;
	}
	public static void swap(int[] arr, int a, int b){
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}

	public static void main(String[] args) {
		
		int[] arr02 = selectionSort(arr);
		for(int a : arr02){
			System.out.print(a + "  ");
		}
		System.out.println();
		int[] arr03 = insertionSort(arr);
		for(int a : arr03){
			System.out.print(a + "  ");
		}
	}
}
