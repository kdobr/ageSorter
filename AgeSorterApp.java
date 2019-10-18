import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.IntStream;

public class AgeSorterApp {

	public static void main(String[] args) throws IOException {
		//for test
		FileOutputStream outputStream = new FileOutputStream("testFile");
		int[] test = { 15, 24, 3, 45, 15, 45, 15 };
		for (int i = 0; i < test.length; i++) {
			outputStream.write(test[i]);
		}
		outputStream.close();
		ageSorter("testFile", "test2");

	}
	
		public static void ageSorter(String pathNameIn, String pathNameOut) {
		int[] age = new int[127]; // array for age. size = 127. think it's enough
		int[] qty = new int[127]; // array for quantity (qty) of people same age

		try (BufferedInputStream br = new BufferedInputStream(new FileInputStream(new File(pathNameIn)));
				DataOutputStream brOut = new DataOutputStream(new FileOutputStream(new File(pathNameOut)))) {

			// read data from file and sent to stream
			byte[]tempArr = new byte [1000000];
			int test = 0;
			while ((test=br.read(tempArr))!=-1) {
				IntStream.range(0, tempArr.length)
		        .map(i -> tempArr[i] & 0xFF).limit(tempArr.length)

		        // sorting data to arrays of age and qty
					.forEach(n -> {
						for (int i = 0; i < age.length; i++) {
							if (age[i] == n) {
								qty[i] += 1;
								break;
							} else {
								if (age[i] == 0) {
									age[i] = n;
									qty[i] = 1;
									break;
								}
							}
						}
					});
			}
			// uniting data from two arrays and and writing it to  file
			for (int i = 0; i < age.length; i++) {
				if (age[i] != 0) {
					System.out.println("age :" + age[i] + " qty: " + qty[i]);
					int temp = (age[i] << 16) | qty[i];
					System.out.println("temp " + temp);
					try {
						brOut.writeInt(temp);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
