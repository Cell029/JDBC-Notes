# 一. 什么是 JDBC

JDBC 是 Java 提供的一套标准数据库连接 API（应用程序编程接口），全称是 Java Database Connectivity。
它是 Java 语言访问数据库的官方标准，使 Java 程序可以通过统一的方式访问各种不同的数据库

## 1. 作用和意义

1. 统一访问接口：JDBC 提供一套统一的 API，无论使用的是 MySQL、Oracle、SQL Server 还是 SQLite，都可以使用相同的方式编程

2. 屏蔽底层细节：程序员不必关心数据库的底层实现细节，只需操作 JDBC 提供的接口

3. 跨平台跨数据库：JDBC 是 Java 标准库的一部分，具有跨平台性

4. 广泛应用于企业级开发：几乎所有 Java Web 后端项目（如 Spring、MyBatis、Hibernate 等）底层都用到了 JDBC

****
## 2. JDBC 连接数据库的基本步骤

1. 加载数据库驱动

2. 建立数据库连接

3. 创建 SQL 语句对象

4. 执行 SQL 查询

5. 处理结果集

6. 关闭资源

****
# 二. JDBC 完成新增操作

[InsertJdbc.java](./MyJDBC/src/main/java/com/cell/firstcode/InsertJdbc.java)

第一步: 注册驱动

```java
try {
    Driver driver = new com.mysql.cj.jdbc.Driver(); // 创建MySQL驱动对象
    DriverManager.registerDriver(driver); // 完成驱动注册
} catch(SQLException e){
    e.printStackTrace();
}
```

com.mysql.cj.jdbc.Driver 是 MySQL 驱动的核心类,这个类实现了 java.sql.Driver 接口

第二步:获取连接

获取 java.sql.Connection 对象，该对象的创建标志着 mysql 进程和 jvm 进程之间的通道打开了

```java
try {
    String url = "jdbc:mysql://localhost:3306/jdbc-notes";
    String user = "root";
    String password = "123";
    Connection conn = DriverManager.getConnection(url, user, password);
    System.out.println("连接对象：" + conn);
} catch(SQLException e){
    e.printStackTrace();
}
```

上述程序输出的 conn 对象是: com.mysql.cj.jdbc.ConnectionImpl,它是 java.sql.Connection 接口的实现类,如果换成 Oracle 数据库的话，
这个实现类的类名就会换一个,所以 JDBC 是一种面向接口的编程方式

URL:是统一资源定位符(Uniform Resource Locator)的缩写,是互联网上标识、定位、访问资源的字符串. URL 通常由协议、服务器名、服务器端口、路径和查询字符串组成,其中：

- 协议是规定了访问资源所采用的通信协议,例如 `jdbc:mysql` 表示要使用 MySQL 数据库，`jdbc:postgresql` 表示要使用 PostgreSQL 数据库
- 服务器名是资源所在的服务器主机名或 IP 地址，可以是域名或 IP 地址
- 服务器端口是资源所在的服务器的端口号
- 路径是资源所在的服务器上的路径、文件名等信息
- 查询字符串是向服务器提交的参数信息,用来定位更具体的资

例如,连接 MySQL 数据库的 JDBC URL 的格式一般如下:

```xml
jdbc:mysql://<host>:<port>/<database_name>?<connection_parameters>
```

- `<host>` 是 MySQL 数据库服务器的主机名或 IP 地址
- `<port>` 是 MySQL 服务器的端口号（默认为 3306）
- `<database_name>` 是要连接的数据库名称
- `<connection_parameters>` 包括连接的额外参数，例如用户名、密码、字符集等

MySQL URL中的其它常用配置:

- `serverTimezone`：MySQL 服务器时区(默认为 UTC,全球通用的标准时间),可以通过该参数来指定客户端和服务器的时区
- `useSSL`：是否使用 SSL 安全传输协议来加密 JDBC 和 MySQL 数据库服务器之间的通信,默认为 true
- `useUnicode`：是否使用 Unicode 编码进行数据传输,默认是 true 启用,这意味着无论数据源中使用的是什么编码方案,都会先将数据转换为 Unicode 编码进行传输，确保数据能够跨平台、跨数据库正确传输
- `characterEncoding`：数据被传输到服务器之后,服务器采用哪一种字符集进行编码,默认为 UTF-8


第三步:获取数据库操作对象

数据库操作对象是这个接口: java.sql.Statement,这个对象负责将 SQL 语句发送给数据库服务器,服务器接收到 SQL 后进行编译,然后执行SQL

```java
Statement stmt = conn.createStatement();
System.out.println("数据库操作对象stmt = "+stmt);
```

同样可以看到打印结果: java.sql.Statement 接口在 MySQL 驱动中的实现类是 com.mysql.cj.jdbc.StatementImpl,可以通过一个 Connection 对象是创建多个 Statement 对象

第四,五步: 创建并执行 SQL

当获取到 Statement 对象后,调用这个接口中的 executeUpdate 方法即可执行 SQL 语句.该方法适合执行的 SQL 语句包括 insert,delete,update

```java
String sql = "insert into t_user(name,password,realname,gender,tel) values('tangsanzang','123','唐三藏','男','12566568956')"; // sql语句最后的分号';'可以不写
int count = stmt.executeUpdate(sql);
System.out.println("插入了" + count + "条记录");
```

第六步:释放资源

在 JDBC 编程中建立数据库连接、创建 Statement 对象等操作都需要申请系统资源,例如打开网络端口、申请内存等,
为了避免占用过多的系统资源和避免出现内存泄漏等问题,需要在使用完资源后及时释放它们

释放顺序:

- 从小到大依次释放,先创建 Connection 再创建 Statement,那么就要先关闭 Statement 再关闭 Connection

```java
stmt.close();
conn.close();
```

****
# 三. JDBC 完成修改和删除

修改:[UpdateJdbc.java](./MyJDBC/src/main/java/com/cell/firstcode/UpdateJdbc.java)

删除:[DeleteJdbc.java](./MyJDBC/src/main/java/com/cell/firstcode/DeleteJdbc.java)






