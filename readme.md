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

****
# 四. 注册驱动与连接数据库

## 1. 注册驱动

JDBC 是 Java 提供的一套访问数据库的标准 API，但不同数据库（如 MySQL、Oracle）有不同的实现。
JDBC 本身不直接实现数据库连接，而是依赖于对应数据库厂商提供的驱动类（Driver）,所以在使用 JDBC 前必须告诉 JVM 用的是哪种数据库,
然后加载并注册相应的数据库驱动类,一旦驱动注册成功,`DriverManager.getConnection()` 就可以根据传入的数据库 URL 找到合适的驱动来建立连接,如果没有注册驱动,
那么 `DriverManager` 就不知道如何处理 URL ,也就无法与数据库建立连接

注册驱动的方式:

第一种:

这种方式是自己 new 驱动对象，然后调用 DriverManager 的 registerDriver() 方法来完成驱动注册

```java
java.sql.Driver driver = new com.mysql.cj.jdbc.Driver();
java.sql.DriverManager.registerDriver(driver);
```

第二种:

这种是最常用的,在 `com.mysql.cj.jdbc.Driver` 类中有一个静态代码块,在这个静态代码块中调用了 `java.sql.DriverManager.registerDriver(new Driver());` 完成了驱动的注册,
而 `Class.forName("com.mysql.cj.jdbc.Driver");` 代码的作用就是让 `com.mysql.cj.jdbc.Driver` 类完成加载,执行它的静态代码块

```java
Class.forName("com.mysql.cj.jdbc.Driver");
```

JDBC 4.0 后不用手动注册驱动:

从JDBC 4.0(也就是Java6)版本开始,驱动的注册不需要再手动完成,由系统自动完成,但在实际的开发中有些数据库驱动程序不支持自动发现功能,仍然需要手动注册

****
## 2. 连接数据库

动态连接数据库:

为了程序的通用性,以及切换数据库的时候不需要修改 Java 程序(符合 OCP 开闭原则),建议将连接数据库的信息配置到属性文件中，例如：

```xml
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/jdbc-notes?useUnicode=true&serverTimezone=Asia/Shanghai&useSSL=true&characterEncoding=utf-8
user=root
password=123
```

然后使用IO流读取属性文件，动态获取连接数据库的信息：

```java
ResourceBundle bundle = ResourceBundle.getBundle("jdbc");
String driver = bundle.getString("driver");
String url = bundle.getString("url");
String user = bundle.getString("user");
String password = bundle.getString("password");
```

其他连接方式:

这种方式参数只有一个 url,用户名和密码放在 url 当中:

```java
String url = "jdbc:mysql://localhost:3306/jdbc-note?user=root&password=123456";
Connection conn = DriverManager.getConnection(url);
```

也可以传递 url 和一个 Properties 对象,把 url 后面的信息封装到 Properties 对象(本质上是一个 Map 集合)中:

```java
String url = "jdbc:mysql://localhost:3306/jdbc";
Properties info = new Properties();
info.setProperty("user","root");
info.setProperty("password","123456");
info.setProperty("useUnicode","true");
info.setProperty("serverTimezone","Asia/Shanghai");
info.setProperty("useSSL","true");
info.setProperty("characterEncoding","utf-8");
Connection conn = DriverManager.getConnection(url, info);
```

****
# 五. JDBC 实现查询

[SelectJdbc.java](./MyJDBC/src/main/java/com/cell/firstcode/SelectJdbc.java)

执行 insert delete update 语句的时候，调用 Statement 接口的 executeUpdate() 方法。
执行 select 语句的时候，调用 Statement 接口的 executeQuery() 方法。执行 select 语句后返会回一个结果集对象：ResultSet,它是一个带有光标的表格,每次调用 rs.next(),光标向下一行移动,
初始位置在第一行之前,一旦光标到达最后一行之后,rs.next() 返回 false,表示遍历结束,每次移动光标后,可以使用 getXXX() 方法获取当前行的数据(可以全部使用 getString(),也可也根据字段的具体类型使用对应的方法)

可以使用字段下标获取数据(每一列对应一个下标,从 1 开始)

```java
String id = rs.getString(1);
String name = rs.getString(2);
String pwd = rs.getString(3);
String realname = rs.getString(4);
String gender = rs.getString(5);
String tel = rs.getString(6);
```

也可也直接用列名获取,这种方式的可读性更高,但其底层使用的仍然是下标的方式:

```java
String id = rs.getString("id");
String name = rs.getString("name");
```

****

## 获取结果集的元数据信息

ResultSetMetaData 是一个接口，可以通过 ResultSet 接口的 getMetaData() 方法获取，用于描述 ResultSet 中的元数据信息，即查询结果集的结构信息，例如查询结果集中包含了哪些列，每个列的数据类型、长度、标识符等

```java
ResultSetMetaData rsmd = rs.getMetaData();
int columnCount = rsmd.getColumnCount();
for (int i = 1; i <= columnCount; i++) {
    System.out.println("列名：" + rsmd.getColumnName(i) + "，数据类型：" + rsmd.getColumnTypeName(i) + "，列的长度：" + rsmd.getColumnDisplaySize(i));
}
```

****
## 获取新增行的主键值

如果要获取插入数据后的主键值，可以使用 Statement 接口的 executeUpdate() 方法的重载版本，该方法接受一个额外的参数，用于指定是否需要获取自动生成的主键值

```java
String sql = "insert into t_user(name,password,realname,gender,tel) values('zhangsan','111','张三','男','19856525352')";
// 第一步
int count = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
// 第二步
rs = stmt.getGeneratedKeys();
if(rs.next()){
    int id = rs.getInt(1);
    System.out.println("新增数据行的主键值：" + id);
}
```

****
# 六. DbUtils 的封装

[DbUtils.java](./MyJDBC/src/main/java/com/cell/firstcode/DbUtils.java)

****
# 七. SQL 注入

SQL 注入本质上是将数据当成了代码来执行,也就是 SQL 的拼接比 SQL 的编译更早执行,这就导致输入的数据全部被识别为字段内容,如果此时:

```sql
String sql = "SELECT * FROM users WHERE username = '" + userInput + "'";
-- 输入以下内容:
admin' OR '1'='1
-- SQL 拼接后:
SELECT * FROM users WHERE username = 'admin' OR '1'='1' -- 这条语句恒为真，意味着数据库将返回所有用户，直接绕过了身份验证
```

****
解决办法:使用 PreparedStatement 类

PreparedStatement 是 Statement 接口的子接口,它是一种预编译 SQL 模板 + 参数绑定的方式,可以自动将参数值与 SQL 语句逻辑分开,避免注入风险

```java
String sql = "select * from users where username = ? and password = ?";
PreparedStatement pstmt = conn.prepareStatement(sql);
pstmt.setString(1, username);
pstmt.setString(2, password);
ResultSet rs = pstmt.executeQuery();
```

- ? 是占位符,SQL 模板结构提前传给数据库,不能给占位符添加 `""` 或 `''`,否则会失效

- SQL 编译只发生一次,可重复使用,Statement 每次使用都要重新编译 SQL,无缓存优化机制

- 用户输入的数据通过 setXXX() 设置参数,占位符的下标从 1 开始

- 参数值只作为“值”传入，而非语句的一部分

- 即使输入了 ' OR 1=1，也只会被当作普通字符串处理

****
# 八. PreparedStatement 完成 CRUD

[CRUD.package](./MyJDBC/src/main/java/com/cell/pstmt_crud)

批处理操作:

启用批处理需要在URL后面添加这个的参数：rewriteBatchedStatements=true

****
# 八. JDBC 调用存储过程

[StoredProcedure.java](./MyJDBC/src/main/java/com/cell/stored_procedure/StoredProcedure.java)

调用存储过程不再是使用 PreparedStatement ,而是使用它的子接口 CallableStatement,存储过程的参数类型和顺序必须完全匹配,对 OUT 和 INOUT 参数必须使用 registerOutParameter() 注册,
可以通过 CallableStatement 获取返回值，但不能直接 return,需要通过 OUT 参数接收

```java
CallableStatement cs = conn.prepareCall("{call 存储过程名(参数列表)}");
cs.setInt(1, 1001); // IN
cs.registerOutParameter(2, Types.DOUBLE); // 对于 OUT 或 INOUT 类型的参数，必须调用
cs.execute();
double salary = cs.getDouble(2);
```

****
# 九. 实现简易的员工管理

[Manager.java](./MyJDBC/src/main/java/com/cell/employee/Manager.java)

****
# 十. 使用 DAO 改造员工管理

[dao.package](./MyJDBC/src/main/java/com/cell/employee/dao)

写 insert,update,delete 方法时,它们有很多共同的代码,唯一的区别就是参数不同,所以可以把它们一起封装到一个方法里,让这个方法接收 sql 语句和可变参数,让具体的 sql 编译与执行在这个方法里执行,
而 select 则额外需要一个实体类来接收返回值,所以得使用到泛型,然后利用发射机制,将从数据库中获取到的数据封装到具体的对象中,然后在封装进集合中

****
# 十一. 连接池

Connection 对象是重量级对象,创建 Connection 对象就是建立两个进程之间的通信,非常耗费资源,一次完整的数据库操作，大部分时间都耗费在连接对象的创建上,所以可以使用连接池技术,
提前创建好 N 个连接对象,将其存放到一个集合中（这个集合就是一个缓存）,用户请求时直接从连接池中获取连接对象,不需要创建连接对象,因此效率较高

## 连接池的属性

1.  初始化连接数（initialSize）：连接池初始化时创建的连接数。
2.  最大连接数（maxActive）：连接池中最大的连接数，也就是连接池所能容纳的最大连接数量，当连接池中的连接数量达到此值时，后续请求会被阻塞并等待连接池中有连接被释放后再处理。
3.  最小空闲连接数量（minIdle）： 指连接池中最小的空闲连接数，也就是即使当前没有请求，连接池中至少也要保持一定数量的空闲连接，以便应对高并发请求或突发连接请求的情况。
4.  最大空闲连接数量（maxIdle）： 指连接池中最大的空闲连接数，也就是连接池中最多允许保持的空闲连接数量。当连接池中的空闲连接数量达到了maxIdle设定的值后，多余的空闲连接将会被连接池释放掉。
5.  最大等待时间（maxWait）：当连接池中的连接数量达到最大值时，后续请求需要等待的最大时间，如果超过这个时间，则会抛出异常。
6.  连接有效性检查（testOnBorrow、testOnReturn）：为了确保连接池中只有可用的连接，一些连接池会定期对连接进行有效性检查，这里的属性就是配置这些检查的选项。
7.  连接的 driver、url、user、password 等。

****
## 常用的连接池

- DBCP
    1. 2001年诞生，最早的连接池。
    2. Apache Software Foundation的一个开源项目。
    3. DBCP的设计初衷是为了满足Tomcat服务器对连接池管理的需求。 
- c3p0
    1. 2004年诞生
    2. c3p0是由Steve Waldman于2004年推出的，它是一个高性能、高可靠性、易配置的数据库连接池。c3p0能够提供连接池的容错能力、自动重连等功能，适用于高并发场景和数据量大的应用。
- Druid
    1. 2012年诞生
    2. Druid连接池由阿里巴巴集团开发，于2011年底开始对外公开，2012年正式发布。Druid是一个具有高性能、高可靠性、丰富功能的数据库连接池，不仅可以做连接池，还能做监控、分析和管理数据库，支持SQL防火墙、统计分析、缓存和访问控制等功能。
- HikariCP
    1. 2012年诞生
    2. HikariCP是由Brett Wooldridge于2012年创建的开源项目，它被认为是Java语言下最快的连接池之一，具有快速启动、低延迟、低资源消耗等优点。HikariCP连接池适用于高并发场景和云端应用。
    3. 很单纯的一个连接池，这个产品只做连接池应该做的，其他的不做。所以性能是极致的。相对于Druid来说，它更加轻量级。
    4. Druid连接池在连接管理之外提供了更多的功能，例如SQL防火墙、统计分析、缓存、访问控制等，适用于在数据库访问过程中，需要进行细粒度控制的场景
    5. HikariCP则更侧重于性能方面的优化，对各种数据库的兼容性也更好
- BoneCP
    1. 2015年诞生
    2. BoneCP是一款Java语言下的高性能连接池，于2015年由Dominik Gruntz在GitHub上发布。BoneCP具有分布式事务、连接空闲检查、SQL语句跟踪和性能分析、特定类型的连接池等特点。BoneCP连接池适用于大型应用系统和高并发的负载场景

****
## Druid 连接池的使用

[DruidTest.java](./MyJDBC/src/main/java/com/cell/druid/DruidTest.java)

使用连接池时不再推荐使用 ResourceBundle 的方式,而是使用 ClassLoader + InputStream 的方式,这种方式没有文件路径的限制,也没有文件格式的限制

****
## 使用 Druid 改进 DbUtils 工具类

[DbUtils.java](./MyJDBC/src/main/java/com/cell/druid/DbUtils.java)

****

