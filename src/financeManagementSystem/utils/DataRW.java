package financeManagementSystem.utils;

import financeManagementSystem.model.FinanceManagementSystem;

import java.io.*;

public class DataRW {

    public static FinanceManagementSystem loadFMSFromFile(FinanceManagementSystem fms, String fileNme) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileNme));
            fms = (FinanceManagementSystem) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Failed with class recognition.");
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Failed to open file.");
        }
        return fms;
    }

    public static void writeFMSToFile(FinanceManagementSystem fms) {
        /*try {
            System.out.println("Enter file name:\n");
            String fileName = scanner.nextLine();
            scanner.nextLine(); //
            if (fileName.isEmpty()) fileName = "bibDB";
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName + ".lib"));
            out.writeObject(fms);
            out.close();
        } catch (IOException e) {
            System.out.println("Fail.\n");
        }*/

        try (FileOutputStream fos = new FileOutputStream("temp.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            // write object to file
            oos.writeObject(fms);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
