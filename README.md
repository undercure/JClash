# JClash

基于Java调用本地Clash服务，方便的将代理整合进入Java工程

示例

```java
public static void main(String[] args) {
        JClash jClash = new JClash();
        Scanner scanner = new Scanner(System.in);
        System.out.print("输入端口号：");
        String systemIn = scanner.nextLine();
        jClash.setPort(systemIn);
        System.out.print("输入认证密钥：");
        systemIn = scanner.nextLine();
        jClash.setSecret(systemIn);
        //从本地服务中获取当前Clash信息
        jClash.init();
        int a =1 ;
        ArrayList<String> arrayList =jClash.getAllProxy();
        //列出当前代理节点清单
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
        //调用方法切换节点
        while (true){
            System.out.println("当前节点为"+jClash.getNow());
            a=scanner.nextInt();
            jClash.switchProxy(a);
            System.out.println("切换到"+jClash.getAllProxy().get(a));
            System.out.println(jClash.getNow());
        }
    }
```

