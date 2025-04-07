import java.util.Scanner;

public class IPADDRESS {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter the IP address:");
        String ip=scanner.nextLine();
        String regExp="\\.";
        String[] parts=ip.split(regExp);
        System.out.println("Class  of the IP Address=");
        System.out.print(identifyClass(parts[0]));
        System.out.println();
        String[] nwAddress=CalculatenwAddress(identifyClass(parts[0]),parts);
        int count=0;
        System.out.println("Network A");
        for(String item : nwAddress){
            count+=1;
            System.out.print(item);
            if (count!=4){
                System.out.print(".");
            }
            
        }
    }

    public static String identifyClass(String firstEtBits){
        int firstByte=Integer.parseInt(firstEtBits);
        if(firstByte>=0 && firstByte<=127){
            return "A";
        }
        else if(firstByte>=128 && firstByte<=191){
            return "B";
        }
        else if(firstByte>=192 && firstByte<=223){
            return "C";
        }
        else{
            return "D";
        }
    }

    public static String[] CalculatenwAddress(String classIP,String[] parts){
        String[] nwAddress=new String[4];
        int[] defaultSubmask=new int[4];
        if (classIP=="A"){
            defaultSubmask[0]=255;
            defaultSubmask[1]=0;
            defaultSubmask[2]=0;
            defaultSubmask[3]=0;   
        }
        else if (classIP=="B"){
            defaultSubmask[0]=255;
            defaultSubmask[1]=255;
            defaultSubmask[2]=0;
            defaultSubmask[3]=0;   
        }
        else if (classIP=="C"){
            defaultSubmask[0]=255;
            defaultSubmask[1]=255;
            defaultSubmask[2]=255;
            defaultSubmask[3]=0;   
        }
        else{
            defaultSubmask[0]=255;
            defaultSubmask[1]=255;
            defaultSubmask[2]=255;
            defaultSubmask[3]=255;   
        }
        for(int i=0;i<4;i++){
            int temp=Integer.parseInt(parts[i]);
            nwAddress[i]=String.valueOf(temp & defaultSubmask[i]);
        }
        return nwAddress;
    }
    
}
