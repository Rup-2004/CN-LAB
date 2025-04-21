import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String[] data, divisor;

        System.out.print("Enter data stream: ");
        data = scanner.nextLine().split(""); // e.g., 1100111

        System.out.print("Enter divisor: ");
        divisor = scanner.nextLine().split(""); // e.g., 1001

        String[] datastreamWithPad = new String[data.length + divisor.length - 1];

        // Copy original data
        for (int i = 0; i < data.length; i++) {
            datastreamWithPad[i] = data[i];
        }

        // Pad with zeros
        for (int i = data.length; i < datastreamWithPad.length; i++) {
            datastreamWithPad[i] = "0";
        }

        System.out.println();

        String[] crc = getCRCFromSender(datastreamWithPad, divisor);

        System.out.print("CRC: ");
        for (String item : crc) {
            System.out.print(item);
        }
        System.out.println();
        System.out.println("Enter transmittedData:");
        String[] transmittedData;

        transmittedData=scanner.nextLine().split("");
        getCRCFromReceiver(transmittedData,divisor);
     
    }

    public static String[] getCRCFromSender(String[] datastream, String[] divisor) {
        int[] activeDiv = new int[divisor.length];
        int[] activeRes = new int[divisor.length];
        int[] defaultZero = new int[divisor.length];
        int[] holdXORes;

        // Convert string to int
        for (int i = 0; i < divisor.length; i++) {
            activeDiv[i] = Integer.parseInt(divisor[i]);
            activeRes[i] = Integer.parseInt(datastream[i]);
        }

        // Division loop
        for (int i = 0; i <= datastream.length - divisor.length; i++) {
            if (activeRes[0] == 1) {
                holdXORes = doXOR(activeRes, activeDiv); // XOR with divisor if first bit is 1
            } else {
                holdXORes = doXOR(activeRes, defaultZero); // else XOR with zero
            }

            // Shift window
            if (i + divisor.length < datastream.length) {
                for (int j = 0; j < divisor.length - 1; j++) {
                    activeRes[j] = holdXORes[j];
                }
                activeRes[divisor.length - 1] = Integer.parseInt(datastream[i + divisor.length]);
            } else {
                // Last XOR result is the CRC
                String[] CRC = new String[divisor.length - 1];
                for (int j = 0; j < CRC.length; j++) {
                    CRC[j] = Integer.toString(holdXORes[j]);
                }
                return CRC;
            }
        }
        return new String[0]; // fallback
    }

    public static int[] doXOR(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length - 1];
        for (int i = 1; i < arr1.length; i++) {
            result[i - 1] = arr1[i] ^ arr2[i];
        }
        return result;
    }

    public static void getCRCFromReceiver(String[] datastream, String[] divisor) {
        int[] activeDiv = new int[divisor.length];
        int[] activeRes = new int[divisor.length];
        int[] defaultZero = new int[divisor.length];
        int[] holdXORes;

        // Convert string to int
        for (int i = 0; i < divisor.length; i++) {
            activeDiv[i] = Integer.parseInt(divisor[i]);
            activeRes[i] = Integer.parseInt(datastream[i]);
        }

        // Division loop
        for (int i = 0; i <= datastream.length - divisor.length; i++) {
            if (activeRes[0] == 1) {
                holdXORes = doXOR(activeRes, activeDiv); // XOR with divisor if first bit is 1
            } else {
                holdXORes = doXOR(activeRes, defaultZero); // else XOR with zero
            }

            // Shift window
            if (i + divisor.length < datastream.length) {
                for (int j = 0; j < divisor.length - 1; j++) {
                    activeRes[j] = holdXORes[j];
                }
                activeRes[divisor.length - 1] = Integer.parseInt(datastream[i + divisor.length]);
            } else {
                // Last XOR result is the CRC
                String[] CRC = new String[divisor.length - 1];
                for (int j = 0; j < CRC.length; j++) {
                    CRC[j] = String.valueOf(holdXORes[j]);
                }
                System.out.print("CRC: ");
                for (String item : CRC) {
                    System.out.print(item);
                    }
                System.out.println();


                for(String item : CRC){
                    if(item.equals("1")){
                        System.out.println("The receiver datastream contains error.");
                        return ;
                    }
                }
                System.out.println("The receiver datastream is accurate.");
                
            }
        }
    }
}



OUTPUT:
--------------------------------------------------------------------------------------------------------------------------------------------------------------------
Enter data stream: 1100111
Enter divisor: 1001

CRC: 010
Enter transmittedData:
1100111011
CRC: 001
The receiver datastream contains error.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------

Enter data stream: 1100111
Enter divisor: 1001

CRC: 010
Enter transmittedData:
1100111010
CRC: 000
The receiver datastream is accurate.
