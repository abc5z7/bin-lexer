import java.io.*;

/**
 * @author: caibao
 * @date: 2018-12-13
 * @email: abcwzcb@163.com
 * @version: 2.2.4
 * @description: bin文件转十六进制文本
 */
public class Lexer {

    private static int count = 0;       // 计数器，一共有多少个十六进制数

    private static void lexer(String readFilePath, String writeFilePath) throws IOException {

        int tempChar;
        int n = 0;
        int judge = 0;
        String tempString;
        StringBuffer sb = null;

        // 这里是为了实现去除最后一个逗号不得不先遍历一遍文件
        FileInputStream fileInputStream1 = new FileInputStream(readFilePath);
        try {
            while ((fileInputStream1.read()) != -1) {
                count++;        // 计数器
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileInputStream1.close();
        } // try


        // 以字节流读文件内容
        FileInputStream fileInputStream = new FileInputStream(readFilePath);
        FileWriter writer = new FileWriter(writeFilePath, true);        // 打开写
        try {
            while ((tempChar = fileInputStream.read()) != -1) {
                tempString = Integer.toHexString(tempChar);

                // 补零
                sb = new StringBuffer();
                while (tempString.length() < 2) {
                    sb.append("0").append((tempString));
                    tempString = sb.toString();
                } // innerWhile

                // 字母大写
                tempString = tempString.toUpperCase();

                // 写数据
                judge++;        // 判断是否已经在文件末尾的前一个字符
                if (judge < count) {
                    writer.write("0x" + tempString + ",");
                } else {
                    writer.write("0x" + tempString);
                    break;
                }

                // 换行器
                n++;
                if (n > 15) {
                    writer.write('\r');
                    n = 0;
                }
            } // outerWhile
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileInputStream.close();
            writer.close();
        } // try
    } // lexer


    private static void rename(String oldFilePath) {

        File file = new File(oldFilePath);
        if (!file.exists()) {
            return;
        }

        String rootPath = file.getParent();
        File newFile = new File(rootPath + File.separator + count + ".txt");

        file.renameTo(newFile);
    } // rename


    public static void main(String[] argv) throws IOException {

        // 获取标准的路径
        File readFile = new File("in.bin");
        File writeFile = new File("out.txt");
        String readFilePath = readFile.getCanonicalPath();
        String writeFilePath = writeFile.getCanonicalPath();

        // 判断文件是否存在
        if (!readFile.exists()) {
            return;
        }

        Lexer.lexer(readFilePath, writeFilePath);
        Lexer.rename(writeFilePath);
    }
}