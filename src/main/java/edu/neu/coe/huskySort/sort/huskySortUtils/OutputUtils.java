package edu.neu.coe.huskySort.sort.huskySortUtils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Caspar
 * @date 2021/12/5 05:47
 */
public class OutputUtils {

    public static void write(String filePath, String data) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath, true))) {
            bufferedWriter.write(data);
            System.out.println("Succeed to output data to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Fail to write file " + filePath);
        }
    }

}
