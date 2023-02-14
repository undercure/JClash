package top.underCure.JClash;

import top.undercure.jClash.JClash;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author underCure
 * @date 2023/02/14 12:26
 */
public class TestJClash {
    public static void main(String[] args) {
        JClash jClash = new JClash();
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入端口号：");
        String Port = scanner.nextLine();
        jClash.setPort(Port);
        System.out.print("输入认证密钥：");
        Port = scanner.nextLine();
        jClash.setSecret(Port);
        jClash.init();
        int a =1 ;
        ArrayList<String> arrayList =jClash.getAllProxy();
        for (int i = 0; i <arrayList.size() ; i++) {
            if (arrayList.size()>(a*a)){
                System.out.print(i+ "---"+ arrayList.get(i)+"\t\t\t");
                a++;
            }else {
                System.out.println();
                a=1;
            }
        }
        System.out.println();
        while (true){
            System.out.println("当前节点为"+jClash.getNow());
            a=scanner.nextInt();
            jClash.switchProxy(a);
            System.out.println("切换到"+jClash.getAllProxy().get(a));
            System.out.println(jClash.getNow());
        }
    }
}
