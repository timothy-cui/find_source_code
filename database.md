# MySQL、redis和MongoDB简述
## SQL数据库与NoSQL数据库
* SQL数据库：关系型数据库，常用的有MySQL、SQLServer；
* NoSQL数据库：非关系型数据库，常用的有MongoDB，包含了一下四种类型：
   * 键值数据库：如redis，应用在内容缓存；
   * 列族数据库：如BigTable，应用在分布式数据存储与管理；
   * 文档数据库：如MongoDB,应用在存储、索引并管理文档的数据或者类似的半结构化数据；
   * 图形数据库：如Neo4J，应用在图结构场合。
* 二者的区别：
   * SQL数据库使用关系型的表存储数据。NoSQL数据库使用类JSON的键值对来存储文档；
   * SQL数据库中，除非先定义表和字段，否则无法向其中添加数据。NoSQL数据库中，数据任何时候都可以添加，不需要事先去定义文档和集合；
   * SQL数据库具有数据库的规范化。NoSQL数据库虽然可以同样使用规范化，但是更倾向非规范化；
   * SQL数据库具有JOIN操作，NoSQL数据库则没有；
   * SQL数据库具有数据完整性，NoSQL数据库则不具备数据完整性约束，可以存储任何想存储的数据；
   * SQL数据库需要自定义事物，NoSQL数据库操作单个文档时具备事务性，而操作多个文档时则不具备事务性；
   * SQL数据库使用SQL语言，NoSQL数据库使用类JSON；
   * NOSQL数据库比SQL数据库更快。
* SQL数据库
   * 使用表存储相关数据；
   * 在使用表之前需要先定义表的模式；
   * 鼓励使用规范化来减少数据的冗余；
   * 支持使用JOIN操作，使用一条SQL语句从多张表中取出相关数据；
   * 需要满足数据完整性约束规则；
   * 使用事务来保证数据的一致性；
   * 能够大规模使用；
   * 使用强大的SQL语言进行查询操作。
* NoSQL数据库
   * 使用类JSON格式的文档来存储键值对信息；
   * 存储数据不需要特定的模式；
   * 使用非规范化的标准存储信息，以保证一个文档中包含一个条目的所有信息；
   * 不需要使用JOIN操作；
   * 允许数据不通过验证就可以存储到任意的位置；
   * 保证更新的单个文档，而不是多个文档；
   * 提供卓越的性能和可扩展性；
   * 使用JSON数据对象进行查询。
## 关于redis
* redis是一个使用C语言编写的，开源的高性能非关系型的键值对数据库。有五大数据类型，包括String、List、Set、Sortdset、Hash。
* 为什么要用redis[缓存]：
   * 高性能：用户第一次读取数据，因为是从硬盘上读取，过程会比较慢。将其缓存起来，下一次再访问的时候，就可以从缓存中读取。如果数据库中对应的数据改变，同步缓存中的数据即可。
   * 高并发：直接操作缓存能够承受的请求远远大于直接访问数据库。
* 缓存异常[击穿、穿透、雪崩]
   * 缓存击穿：高并发请求下，某些热门的key突然过期，导致不能从redis中获取缓存数据，进而大量的请求直接访问数据库，给数据库造成极大的压力；
      * 解决方法：
         * 设置热门key永不过期；
         * 定期检测将要过期的key，然后在将要过期时重新从数据库中把数据刷新到缓存中，但这样的话增加了系统复杂度，实现比较复杂；
         * 利用互斥锁，在缓存中没有数据去数据库中查询时加上锁，让一个线程去查询数据库以及更新缓存，其他线程等待，这样减少数据库压力。
   * 缓存穿透：指查询缓存和DB中不存在的数据。先查询缓存为null，再查询数据库依然为null，如果大量请求同时访问这些不存在的key，那么这些请求依然会造成压垮数据库的现象。
      * 解决方法：
         * 布隆过滤器，把一定不存在的key过滤掉，从而避免这些恶意攻击对数据库造成的压力；
         * 如果查询结果为空，不管是数据不存在还是系统故障，仍然把这个空结果进行缓存，但要设置一个很短的过期时间，最长不超过5min。
   * 缓存雪崩：缓存中如果大量缓存在一段时间内集中过期，这时会发生大量的缓存击穿现象，所有的请求都落到了DB上，由于数据量巨大，引起DB压力过大甚至DB宕机。
      * 解决方法：
         * 给缓存的时间加一个随机值，不让它们在同一时刻过期；如果集群部署，让他们分布到不同的redis库也能避免全部失效问题；
         * 使用互斥锁，但是吞吐量明显下降；
         * 设置热点数据永不过期；
         * 设置双缓存，a缓存设置失效时间，b缓存不设置失效时间；如果a缓存中有数据则直接返回，如果没有就从b缓存中去找，直接返回，并启动一个更新线程，同步更新a缓存和b缓存。
* 主从复用与主备切换
   * 主从复用
      * 读写分离：主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，master以写为主，slaver以读为主；
      * 容灾快速恢复：从服务器挂掉，再读读不到，可以根据策略让其读别的从服务器；
   * 主备切换
      * 哨兵机制：配置哨兵一直去ping这个主服务器，如果在一定时间内没有ping到，认为主服务器挂掉，一半以上的哨兵都认为主服务器挂掉，主服务器就挂掉了，之后会进行一个选举投票，一半以上的哨兵认为某个从服务器可以选举成为主服务器，那么这个从服务器就可以变为主服务器，已经挂掉的主服务器重新恢复自动变更为从服务器。
* 持久化机制
   * 持久化就是把内存中的数据存放到磁盘中，防止宕机后内存数据丢失；
   * Redis DataBase [RDB]:默认的持久化方式，按照指定的时间间隔内将内存的数据以快照的形式保存到硬盘当中，对应产生的数据文件为dump.rdb,可通过配置定义周期。恢复时，将rdb文件放到redis的启动目录上，redis会自动检查dump.rdb文件，恢复数据；
      * 具体操作： 
         * 当满足条件时，redis需要执行RDB的时候会执行以下操作：
             * 1，redis调用系统的fork函数创建一个子进程；
             * 2，子进程将数据集写入一个临时的RDB文件；
             * 3，当子进程完成对临时的RDB文件写入时，redis用新的RDB文件替换旧的RDB文件。
      * 优点：
         * 适合大规模的数据恢复（使用单独子进程来进行持久化，主进程不会进行任何IO操作，保证了redis的高性能）。
      * 缺点：
         * 需要一定的时间间隔来进行操作，无法做到实时持久化，所以适合对数据完整性不高的持久化操作；
         * fork子进程的时候，会占用一定的内存空间；
   * Append Only File [AOP]:默认不使用，将redis执行的每次写命令记录到日志文件中，当重启Redis会加载appendonly.aof文件来恢复数据。
      * AOF是存放每条写命令的，所以会不断的增大，当大到一定程度时，AOF会做rewrite操作，rewrite操作就是基于当时redis的数据重新构造一个小的AOF文件，然后将大的AOF文件删除。
      * 优点：
         * AOF日志文件适合做灾难性的误删除紧急恢复；
         * 每进行一次命令操作就记录到aof文件中一次，保证了数据的安全性和完整性；
         * AOF以append-only的模式写入，所以没有任何的磁盘寻址的开销，写入性能非常的高。
      * 缺点：
         * AOF文件比RDB文件大，且恢复速度慢。
* Redis数据淘汰策略【redis数据集大小上升到一定大小的时候，会施行数据淘汰策略】
   * noeviction(默认策略)：对于写请求不再提供服务，直接返回错误；
   * allkeys-lru：从所有key中使用LRU算法进行淘汰；
   * volatile-lru：从设置了过期时间的key中使用LRU算法进行淘汰；
   * allkeys-random：从所有key中随机淘汰数据；
   * volatile-random：从设置了过期时间的key中随机淘汰；
   * volatile-ttl：在设置了过期时间的key中，淘汰过期时间剩余最短的；
   * 当使用volatile、volatile-random、volatile-ttl这三种策略时，如果没有key可以被淘汰，则和noeviction一样返回错误。
## 关于MongoDB
* MongoDB是由C++语言编写的，是一个基于分布式文件存储的开源数据库系统，在大量数据下，承载性能好。
* 优点：
   * 相对于MySQL来说，不需要提前创建表，以及表结构；
   * 存储持久化，面向集合存储，易存储对象类型的数据；
   * 性能方面能够快速查询（支持动态查询，完全索引）；
   * json格式存储。
* 缺点：
   * MongoDB占用空间过大；
   * 关系能力较弱。
## 简要比较
   * MongoDB更类似于MySQL，支持字段索引、游标操作，其优势在于查询功能比较强大，能存储海量事务，但是不支持事务；
   * MySQL在大数据量时效率显著下降，MongoDB更多时候作为关系数据库的一种替代；
   * Redis是内存中的数据结构存储系统，可以用作数据库、缓存和消息中间件，事务支持比较弱，只能保证事务中的每个操作连续执行。