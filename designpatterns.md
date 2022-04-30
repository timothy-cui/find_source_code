# 设计模式
## 一，六大设计原则[SOLID]
* 1，单一职责[SRP]：应该有且仅有一个原因引起类的变更。
   * "职责"和"变化原因"都是不可度量的，因项目和实际环境而异。
   * 如果要实现类的单一职责，会引起类间耦合过重，类数量增加；
   * 可进行接口的单一职责设计，实现类实现多个单一职责接口，面向接口编程。
   * 建议接口一定做到单一职责，类尽量做到单一职责。
```
单一职责的好处：
1，类的复杂性降低；
2，可读性提高；
3，可维护性提高；
4，变更引起的风险降低，接口的修改只会影响对应实现类，不会影响其他接口。
``` 
* 2，里氏替换[LSP]:只要父类能出现的地方子类就能出现，使用者无需知晓其是子类还是父类；反过来，子类出现的地方，父类未必能适应。
   * 子类必须完全实现父类的方法；
      * 在类中调用其他类时务必使用父类和接口，如不能，则说明已经违背LSP原则；
      * 如子类不能完整实现父类方法，或者父类某些方法在子类中发生畸变，建议断开父子继承关系，采用依赖、组合、聚合代替继承。
   * 子类可以有自己的特性：
      * 只要父类出现的地方可以替换为子类，但是子类出现的地方，父类不一定能胜任，替换是有风险的。
   * 覆盖或实现父类的方法时输入参数可以被放大[重载]：
      * 在父类出现的地方替换为子类，则原来父类被调用的方法[小的参数]依然是父类的方法，如果想实现子类的独特逻辑，需要[覆写]这个方法；
      * 如果反过来，父类的输入参数更大，则替换后，原来父类被调用的方法[使用了小的参数]会实际调用到子类重载的方法中，造成业务逻辑混乱；
      * 子类中方法的前置条件必须与超类中被覆写的方法的前置条件相同[覆写]或者更宽松[重载]->[使用时注意实际调用到的方法]。
   * 覆写或者实现父类的方法时输出结果可以被缩小：
      * 覆写：子类覆写了父类的方法，返回值要小于或者等于父类中的方法，否则父类调用该方法的场景替换为子类会不安全，这是覆写的要求；
      * 重载：要求输入参数类型或者数量不同，在里氏替换里则表现为参数宽于或等于父类参数，就是说这个方法不会被父类出现的地方调用到。
```
里氏替换的好处：
1，增强了程序的健壮性；
2，版本升级的同时保持良好的兼容性，增加新的子类不会影响以前子类的使用；
3，实际项目中，每个子类代表不同的业务含义，以父类做参数，传递不同的子类完成不同的业务逻辑[与接口不同，公有上层逻辑可放在父类，业务特有逻辑可通过接口覆写放到各个子类]。
```
* 3，依赖倒置[DIP]:面向接口编程[OOD]->模块间的依赖通过抽象发生，实现类之间不发生直接的依赖关系，其依赖关系通过接口或者抽象类产生；接口或者抽象类不依赖实现类；实现类依赖接口或者抽象类。
   * 在java中，只要定义变量就必然要有类型，一个变量可以有两种类型：表面类型和实际类型，表面类型是在定义的时候赋予的类型，实际类型是对象类型；
   * 上层模块在调用底层模块的方法时，使用的就是实际类型[子类/实现类]，底层模块定义方法时使用的是表面类型[抽象父类/接口]。
   * 依赖的三种写法：
      * 1，构造函数传递依赖；
      * 2，Setter方法传递依赖对象；
      * 3，接口声明依赖对象：接口参数传入并使用。
```
依赖倒置的好处：
1，减少类间耦合；
2，提高系统稳定性；
3，降低并行开发风险；
4，提高了代码的可读性和可维护性。
如何更好地使用DIP：
1，每个类尽量都有接口或者抽象类，或者两者都具备；
2，变量的表面类型尽量是接口或者抽象类；
3，任何类都不应该从具体类中派生【不绝对，项目维护时可例外考虑，这样，小的变动可以解决问题】；
4，尽量不要覆写基类的方法：如果基类是一个抽象类，而且这个方法已经实现了，子类尽量不要去覆写。类间依赖的是抽象，覆写了抽象方法，对依赖的稳定性会产生影响。
5，结合里氏替换原则使用，可得出一个通俗规则：接口负责定义public属性和方法，并声明和其他对象的依赖关系，抽象类负责公共构造部分的实现，实现类实现具体的业务逻辑，同时在适当的时候对父类进行细化。
``` 
* 4，接口隔离[ISP]：建立单一接口。区别于单一职责，给每个不同模块的都应该是单一接口。
   * 把一个臃肿的接口变更为两个独立的接口所依赖的原则就是接口隔离原则；
   * 保证接口的纯洁性：
      * 接口尽量小[不是无限度，前提是不违反单一职责。根据接口隔离原则拆分接口时，首先必须满足单一职责原则]；
      * 接口要高内聚[提高接口、类、模块的处理能力，减少对外交互。尽量少对外公布public方法，接口是对外的承诺，承诺越少对系统的开发越有利，变更风险就越少]；
      * 定制服务[单独为一个个体提供优良的服务，只提供给访问者需要的方法]
      * 接口设计是有限度的[接口越小，系统越灵活，但是结构就越复杂，所以设计需要适度，根据实际情况把控]。
```
接口和类尽量使用原子接口或者原子类封装，原子划分的衡量规则；
1，一个接口只服务于一个子模块或者业务逻辑；
2，通过接口逻辑压缩接口中的public方法；
3，已经被污染的接口尽量修改，若变更的风险比较大，则采用适配器模式进行转化处理；
4，了解环境，拒绝盲从，深入了解业务逻辑，结合实际情况设计。
```
* 5，迪米特法则[LoD]:一个对象应该对其他对象有最少的了解，一个类应该对自己需要耦合或者调用的类知道得最少。
   * 出现在成员变量、方法输入输出参数中的类称为成员朋友类；
   * 只和朋友交流：
      * 一个类只和朋友类交流，不与陌生类交流；
      * 类与类之间的关系是建立在类间，而不是方法间，因此一个方法尽量不要引入一个类中不存在的对象[jdkAPI中的除外].
   * 朋友间也是有距离的：
      * 尽量不要对外公布太多的public方法和非静态的public变量，尽量内敛，多使用private、default、protect等访问权限。
   * 是自己的就是自己的:
      * 如果一个方法放在本类中，既不增加类间关系，也对本类不产生负面影响，那就放置在本类中。
   * 谨慎使用Serializable:
      * 客户端与服务端的序列化实体存在区别时，有概率导致序列化失败。
```
1，迪米特法则核心观念：类间解耦、弱耦合；
2，但是会产生大量中转或跳转类，导致系统复杂性提高；
3，使用时需要权衡，做到既要结构清晰，还需高内聚低耦合。
```
* 6，开闭原则[OCP]:对扩展开放，对修改关闭。
   * 开闭原则是非常虚的一个原则，前面5个原则是对开闭原则的具体解释，但是开闭原则并不局限于这么多；
   * 开闭原则对扩展开放，对修改关闭，并不意味着不做任何修改，低层模块的变更，必然要有高层模块进行耦合，否则就是一个孤立无意义的代码片段；
   * 变化可分为以下三种：
      * 逻辑变化；
      * 子模块变化；
      * 可见视图变化。
```
开闭原则的重要性：
1，开闭原则对测试有影响；
2，开闭原则可以提高复用性；
3，开闭原则可以提高可维护性；
4，面向对象开发的要求。
如何使用到实际工作中：
1，抽象约束；
    1）通过接口或抽象类约束扩展，对扩展进行边界限定，不允许出现在接口或抽象类中不存在的public方法；
    2）参数类型、引用对象尽量尽量使用接口或者抽象类，而不是实现类；
    3）抽象层尽量保持稳定，一旦确定下来即不允许修改。
2，元数据（metadata）控制模块行为[通过配置，从文件中获取]；
3，制定项目章程；
4，封装变化。
    1）将相同的变化封装到一个接口或抽象类中；
    2）将不同的变化封装到不同的接口或抽象类中，不应该有两个不同的变化出现在同一个接口或抽象类中。
```
## 二，单例模式
* 定义：确保一个类只有一个实例，而且自行实例化并向整个系统提供这个实例。
* 单例模式的优点：
   * 只有一个实例，减少了内存开支；
   * 减少了系统性能开销；
   * 可避免对资源的过多占用；
   * 可在系统设置全局的访问点，优化和共享资源访问。
* 单例模式的缺点： 
   * 没有接口，扩展困难；
   * 对测试是不利的；
   * 与单一职责原则有冲突，单例模式把要单例和业务逻辑融合在一个类中。
* 单例模式的使用场景：
   * 要求生成唯一序列号的环境；
   * 整个项目中需要一个共享访问点或共享数据；
   * 创建一个对象需要消耗的资源过多；
   * 需要定义大量的静态常量和静态方法；
***
* 单例模式的注意事项：
   * 高并发模式下，需要注意线程同步问题；
```
// 饿汉模式
public class Singleton{
    private static final Singleton singleton = new Singleton();
    private Singleton(){
    }
    public static Singleton getInstance(){
        return singleton;
    }
}
```
* 饿汉模式在类加载的时候就实例化，不会有线程安全问题，但是可能会多出很多不用的实例。
```
// 「懒汉」线程不安全的单例
public class Singleton{
    private static Singleton singleton = null;
    // 限制产生多个对象
    private Singleton(){
    }
    // 获取单例实例
    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
```
* 以上单例在低并发下尚可，并发量高了之后，若线程A执行到new Singleton(),还未完成赋值时，
  线程B执行到了singleton==null的判断，那么此刻题条件为真，线程A，B将各获得一个实例。
```
// 懒汉模式
public class Singleton{
    private static Singleton singleton = null;
    private Singleton(){
    }
    public static synchronized Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();
        }
        return singleton;
    }
}
```
* 每次调用方法都需获取锁释放锁，对性能损耗大。
```
// 懒汉双重检测锁式
public class Singleton{
    private static volatile Singleton singleton = null;
    private Singleton(){
    }
    public static Singleton getInstance(){
        if(singleton == null){
            synchronize(Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
```
* 只锁住部分代码，只有在实例为空的时候才去判空，提高了性能。
* 懒汉双重检测锁式为何在加了同步锁还要进行二次判空？
   * reply:如下，不加的话可能产生多个实例。
   * thread1:[1,第一次判断实例为空]->[2,获取锁----]->[3,初始化实例]->[4,-----------------]
   * thread2:[1,第一次判断实例为空]->[2,等待1释放锁]->[3,--------]->[4,获取到锁，初始化实例]
* 懒汉双重检测锁式为何使用volatile关键字？
   * reply:如下，由于指令重排序的的存在，可能存在访问不到实例的情况，加volatile禁止其初始化时指令重排序。
   * thread1：[1,第一次判断实例为空]->[2,获取锁]->[3,再次检测实例为空]->[4,在堆中分配内存空间]->[5,引用指向分配的内存]->[6,------------------------------]->[7,进行类初始化]
   * thread2：[1,---------------]->[2,-----]->[3,-------------]->[4,---------------]->[5,---------------]->[6,第一次判断实例不为空，获取实例为null]->[7,----------]
* 指令重排序：
   * 一般而言初始化不是原子操作，包含以下三个步骤：
      * 1，在堆中分配内存；
      * 2，执行类初始化；
      * 3，将堆中的地址返回给栈中的引用变量。
   * 由于java内存模型允许"无序写入"，编译器有可能将步骤2和3进行重排序，实际执行步骤变成1->3->2。
```
// 静态内部类式
public class Singleton{
    private static class SingletonHolder{
        private static Singleton INSTANCE = new Singleton();
    }
    private Singleton(){
    }
    public static Singleton getInstance(){
        return SingletonHolder.INSTANCE;
    }
}
```
* 静态内部类在加载的时候不会被加载，在使用的时候才会被加载，但是在加载的时候又是线程安全的；既能延迟加载解决性能问题，又是线程安全的。
* 私有构造器可以被反射调用，破坏单例性。
   * 修改构造器，第多次调用构造器抛出异常；
* 可以通过readObject方法序列化反序列化出新的实例，破坏了单例性。
   * 覆写readResolve方法，防止以上情况下产生新的实例；
   * 使用枚举的方式实现实例，枚举底层禁用了readObject方法，且只有私有构造器；
```
// 枚举实现方式
public enum SingletonEnum{
    // 创建一个枚举对象，其天生为单例。
    INSTANCE;
    public void doSomething(){
        // todo[方法逻辑]
    }
}
```
* 可定义方法，枚举对象内部覆写各种对应逻辑放法以创建不同单例对象。*该实现方式可引发更多思考。*
***
* 单例模式扩展之有上限的多例模式
   * 要求一个类只需要产生固定个数的对象，可以套用单例模式，内部设置对象个数以及容纳对象的数据，对外隐藏构造函数，提供获取实例的public方法。
* spring中，每个bean都是单例的，这样做的优点是便于spring容器管理这个bean的生命周期，决定什么时候创建，什么时候销毁，销毁的时候如何处理，等等；
* 如果采用非单例模式（Prototype模式），则bean初始化后会交给J2EE容器，spring容器不再跟踪管理bean的生命周期。
## 三，工厂方法模式
* 定义：定义一个用于创建对象的接口，让子类决定实例化哪一个类。工厂方法使一个类的实例化延迟到其子类。
```
/** 通用源码 */
// 抽象产品类
public abstract class Product{
    // 产品类的公用方法
    public void method1(){
        // 业务逻辑
    }
    // 抽象方法
    public abstract void method2();
}

// 具体产品类
public class ConcreteProduct1 extends Product{
    @Override
    public void method2(){
        // 业务逻辑处理
    }
}
public class ConcreteProduct2 extends Product{
    @Override
    public void method2(){
        // 业务逻辑处理
    }
}

// 抽象工厂类
public abstract class Creator{
    // 创建一个产品对象，其输入参数可自行设置，通常为String、Enum、Class等，也可为空
    public abstract <T extends Product> T createProduct(Class<T> c);
}

// 具体工厂类
public class ConcreteCreator extends creator {
    @Override
    public <T extends Product> T createProduct(Class<T> c){
        Product product = null;
        try{
            product = (Product)Class.forName(c.getName()).newInstance();
        } catch (Exception e){
            // 异常处理
        }
        return (T)product;
    }
}

// 使用场景
Public class Client{
    public static void main(String[] args){
        Creator creator = new ConcreteCreator();
        Product product = creator.createProduct(ConcreteProduct1.class);
        // 其他业务逻辑。
    }
}
```
* 工厂方法模式的优点：
   * 良好的封装性，代码结构清晰；
   * 扩展性优秀；
   * 屏蔽产品类；
   * 典型的解耦框架，高层模块只需要感知产品的抽象类，其他实现不用关心，符合迪米特、依赖倒置、里氏替换。
* 工厂模式的使用场景：
   * 所有需要产生新对象的地方都可以使用，但需要考虑是否会增加代码复杂度；
   * 需要灵活、可扩展的框架时，可以考虑使用工厂方法模式；
   * 可以用在异构项目当中；
   * 可以使用在测试驱动开发的框架下（JMock&EasyMock的诞生使此场景弱化了，可直接使用JMock&EasyMock进行测试解耦）。
* 工厂模式的扩展
   * 缩小为简单工厂模式：模块只需要一个工厂类，没必要使用抽象工厂，直接去掉继承抽象类，使用实际工厂类，并将产生对象的方法静态化（static）；
   * 升级为多个工厂类：当产品复杂的时候，所有产品放到一个工厂中进行初始化会使得代码结构不清晰，为使结构清晰，可以为每个产品定义一个创造者，然后调用者自己选择跟哪一个实际工厂进行关联。
   * 替代单例模式：不允许使用new产生对象，提供一个工厂使用反射生成这个对象（那其他地方也可以这样产生这个对象，可修改构造器，防止被多次调用）
   * 延迟初始化：一个对象被消费完毕后，并不立刻释放，工厂类保持其初始状态，等待再次被使用；其可降低对象的产生和销毁带来的复杂性。
```
// 延迟初始化示例代码
public class ProductFactory{
    private static final Map<String, Product> prMap = new HashMap();
    public static synchronized Product createProduct(String type) throws Exception{
        Product product = null;
        if (prMap.containsKey(type)){
            product = prMap.get(type);
        } else {
            if (type.equals("Product1")) {
                product = new ConcreteProduct1();
            } else {
                product = new ConcreteProduct2();
            }
            // 同时把对象放到缓存容器中
            prMap.put(type, product);
        }
        return product;
    }
}
```
## 四，抽象工厂模式
* 定义：为创建一组相关或相互依赖的对象提供一个接口，而且无须指定它们的具体类。
```
/** 通用源码 */

// 抽象产品类A
public abstract class AbstractProductA{
    // 每个产品共有的方法
    public void shareMethod(){
    }
    // 每个产品相同方法、不同实现
    public abstract void doSomething();
}
// 产品A1[ProductA1]的实现类：略
// 产品A2[ProductA2]的实现类：略

// 抽象产品类B
public abstract class AbstractProductB{
    // 每个产品共有的方法
    public void shareMethod(){
    }
    // 每个产品相同方法、不同实现
    public abstract void doSomething();
}
// 产品B1[ProductB1]的实现类：略
// 产品B2[ProductB2]的实现类：略

// 抽象工厂类
public abstract class AbstractCreator{
    // 创建A产品家族
    public abstract AbstractProductA createProductA();
    // 创建B产品家族
    public abstract AbstractProductB createProductB();
    /** 有N个产品族，在抽象工厂类中就应该有N个创建方法 */
}
 
// 产品等级1的实现工厂类
public class Creator1 extends AbstractCreator{
    // 只生产产品等级为1的A产品
    public AbstractProductA createProductA(){
        return new ProductA1();
    }
    // 只生产产品等级为1的B产品
    public AbstractProductB createProductB(){
        return new ProductB1();
    } 
}
// 产品等级2的实现工厂类
public class Creator2 extends AbstractCreator{
    // 只生产产品等级为2的A产品
    public AbstractProductA createProductA(){
        return new ProductA2();
    }
    // 只生产产品等级为2的B产品
    public AbstractProductB createProductB(){
        return new ProductB2();
    } 
}
/** 有M个产品等级就应该有M个实现工厂类，在每个实现工厂中，实现不同产品族的生产任务 */

// 场景类
public class Client{
    public static void main(String[] args){
        // 定义两个工厂
        AbstractCreator creator1 = new Creator1();
        AbstractCreator creator2 = new Creator2();
        // 产生A1对象
        AbstractProductA a1 = creator1.createProductA();
        // 产生A2对象
        AbstractProductA a2 = creator2.createProductA();
        // 产生B1对象
        AbstractProductB b1 = creator1.createProductB();
        // 产生B2对象
        AbstractProductB b2 = creator2.createProductB();
        /*
         * 业务逻辑······
         */
    }
}
// 在场景类中，没有任何一个方法与实现类有关系，对于一个产品来说，我们只要知道它的工厂方法就可以直接生产一个产品对象，无须关心它的实现类。
```
* 抽象工厂模式的优点
   * 封装性，高层模块不关心具体实现类，只关心接口/抽象，对象的创建由工厂类去实现，只要知道工厂类就可以创建出所需要的对象；
   * 产品族内的约束为非公开状态，调用工厂类对于高层模块来说是透明的，高层模块无须知道工厂中的约束。
* 抽象工厂模式的缺点
   * 扩展产品族很困难，会违反开闭原则。 
* 抽象工厂模式的使用场景
   * 一个对象族（或是一组没有关系的对象）都有相同的约束，就可以使用抽象工厂模式。例如针对不同操作系统类型实现文本编辑器和图片编辑器，文本编辑器和图片编辑器就有了共同的约束条件：操作系统类型。
* 抽象工厂模式的注意事项
   * 注意是产品族扩展困难，不是产品等级。产品等级的扩展是容易的，直接新增一个对应工厂类来实现生产任务就行；
   * 就是说横向扩展容易，纵向扩展难。
## 五，模板方法模式
* 定义：定义一个操作中的算法的框架，而将一些步骤延迟到子类当中。使得子类可以不改变算法的结构即可重定义该算法的某些特定步骤。
```
/** 为了防止恶意的操作，一般模板方法都加上final关键字，不允许被覆写。 */

// 抽象模板类
public abstract class AbstractClass{
    // 基本方法
    protected abstract void doSomething();
    // 基本放法
    protected abstract void doAnything();
    // 模板方法
    public void templateMethod(){
        /*
         * 调用基本方法，完成算法逻辑
         */
        this.doAnything();
        this.doSomething(); 
    }
}
// 具体模板类1
public class ConcreteClass1 extends AbstractClass{
    // 实现基本方法
    protected void doSomething(){
        // 业务逻辑处理
    }
    protected void doAnything(){
        // 业务逻辑处理
    }
}
// 具体模板类2
public class ConcreteClass2 extends AbstractClass{
    // 实现基本方法
    protected void doSomething(){
        // 业务逻辑处理
    }
    protected void doAnything(){
        // 业务逻辑处理
    }
}
// 场景类
public class Client{
    public static void main(Stirng[] args){
        AbstractClass clazz1 = new ConcreteClass1();
        AbstractClass clazz2 = new ConcreteClass2();
        // 调用模板方法
        clazz1.templateMethod();
        clazz2.templateMethod();
    }
}

/ ** 抽象模板类中的基本方法尽量设置为protected类型，符合LoD，不需要暴露的属性或者方法尽量不要设置为protected类型。实现类若非必要，尽量不要扩大父类中的访问权限 */
```
* 模板方法模式的优点
   * 封装不变部分，扩展可变部分；
   * 提取公共部分代码，便于维护；
   * 行为由父类控制，子类实现。
* 模板方法模式的缺点
   * 抽象类定义了部分抽象方法，由子类实现，子类的执行结果影响了父类的结果，也就是子类对父类产生了影响，这在复杂的项目中，会带来代码阅读的难度。
* 模板方法模式的使用场景
   * 多个子类有公有的方法，并且逻辑基本相同时；
   * 重要、复杂的算法，可以把核心算法设计为模板方法，周边的相关细节功能则由各个子类实现；
   * 重构时，模板方法是一个经常使用的模式，把相同代码抽取到父类当中，然后通过"钩子函数"约束其行为。
* 模板方法模式的扩展
   * "钩子方法"：在抽象父类中定义protect方法(钩子函数)，并在模板方法中使用该方法的返回值约束方法行为，然后在具体实现子类中覆写该方法，便可实现具体子类对模板方法中的行为约束。
## 六，建造者模式
* 定义：将一个复杂对象的构建与它的表示分离，使得同样的构建过程可以创建不同的表示。
* 建造者模式中有以下四个角色：
   * Product产品类：通常是实现了模板方法模式，也就是有基本方法和模板方法。
   * Builder抽象建造者：规范的产品组建。
   * ConcreteBuilder具体建造者：实现抽象类中的所有方法，并返回一个组建好的对象。
   * Director导演类：负责安排已有的模块顺序，然后让Builder开始建造。
```
// 产品类
public class Product{
    /*
     * 引入模板方法模式的话，此处则为模板方法，各基本方法可看作零部件
     */
    public void doSomething(){
        // 独立业务处理
    }
}
// 抽象建造者
public abstract class Builder{
    // 设置产品的不同部分，以获得不同的产品
    // 【引入模板方法模式的话，可在产品类中定义一个String的数组，来决定个基本方法（零部件）的组装，该方法则用来设置此数组】
    public abstract void setPart();
    // 构造产品
    public abstract Product buildProduct();
}
// 具体建造者
public class ConcreteProduct extends Builder{
    // 引入模板方法模式的话，此处实例化具体的模版类
    private Product product = new Product();
    // 设置产品零部件
    public void setPart(){
        // 产品类内的零部件组装逻辑
    }
    // 组建一个产品
    public Product buildProduct(){
        return product;
    }
}
// 导演类
public class Director{
    private Builder builder = new ConcreteProduct();
    
    // 构建不同的产品
    public Product getAProduct(){
        builder.setPart();
        return builder.buildProduct();
    }
}
/** 导演类起到封装作用，避免高层模块深入到建造者内部的实现类。在建造者模式比较庞大时，导演类可以有多个 */
```
* 建造者模式的优点
   * 封装性；
   * 建造者独立，容易扩展；
   * 便于控制细节风险
* 建造者模式的注意事项
   * 其关注的是零件类型和装配工艺（顺序），这是其与工厂模式最大的不同之处。
* 建造者模式的应用场景
   * 相同的方法，不同的执行顺序，产生不同的事件结果时；
   * 多个部件或者零件，都可以装配到一个对象中，但是产生的运行结果又不相同时；
   * 产品类非常的复杂，或者产品类中的调用顺序不同产生了不同的效能时；
* 建造者模式的扩展
   * 引入模板方法模式。
## 七，代理模式
* 定义：为其他对象提供一种代理以控制对这个对象的访问。
* 代理模式中包括以下三个角色：
   * Subject抽象主题角色：可以是一个抽象类也可以是一个接口，是一个最普通的业务类型定义；
   * RealSubject具体主题角色：被代理角色，业务逻辑的具体执行者；
   * Proxy代理主题角色：代理类，负责对真实角色的应用，把所有抽象主题类定义的方法限制委托给真实主题角色实现，并且在真实主题角色处理完毕前后做预处理和善后处理工作。
```
// 抽象主题类
public interface Subject{
    // 定义一个方法
    public void request()；
}

// 真实主题类
public class RealSubject implements Subject{
    // 实现方法
    public void request(){
        // 业务逻辑处理
    }
}

// 代理类
public class Proxy implements Subject{
    // 要代理的实现类
    private Subject subject = null;
    // 默认被代理者
    public Proxy(){
        this.subject = new RealSubject();
    }
    // 通过构造函数指定被代理者
    public Proxy(Subject _subject){
        this.subject = _subject;
    }
    // 实现接口中定义的方法
    public void request(){
        this.before();
        this.subject.request();
        this.after();
    }
    // 预处理
    private void before(){
        // do something.
    }
    // 善后处理
    private void after(){
        // do something.
    }
}
/** 通常一个接口只需要一个代理类就可以了，具体实现类由高层场景决定，可通过构造函数设置 */
```
* 代理模式的优点：
   * 职责清晰，真实主题类只实现业务逻辑，不用关心其他非本职责的事务，通过代理去完成；
   * 高扩展性，真实主题实现是会发生变化的，但只要实现了接口，代理就可不做修改直接使用；
   * 智能化；
* 代理模式的使用场景：
   *例如springAOP；
* 代理模式的扩展：
*  * 普通代理
```
/*
 * 要求客户端只能访问代理角色，而不能访问真实角色。
 */
 
 public interface IGamePlayer{
    public void play();
 }
 
// 普通代理的游戏者
public class GamePlayer implements IGamePlayer{
    private String name = "";
    // 构造函数限制谁能创建对象，并同时传递姓名
    public GamePlayer(IGamePlayer _gamePlayer, String _name) throws Exception{
        if (_gamePlayer == null) {
            throw new Exception("不能创建真实角色！");
        } else {
            this.name = _name;
        }
    }
    // 实际实现的逻辑方法
    public void play(){
        // do something.
    }
}
/** 构造函数中限制了谁能创建真实角色，也可以做其他限制··· */

// 普通代理的代理者
public class GamePlayerProxy implements IGamePlayer{
    private IGameplayer gameplayer = null;
    // 通过构造函数传递要代理的目标
    public GamePlayerProxy(String name){
        try{
            gamePlayer = new GamePlayer(this, name);
        } catch (Exception e){
            // 处理异常
        }
    }
    
    public void play(){
        this.gamePlayer.play();
    }
}

// 普通代理的场景类
public class Client{
    public static void main(String[] args){
        /* 可以直接访问实现类情形下定义一个代理
        IGamePlayer player = new GamePlayer("Jane");
        IGamePlayer proxy = new GamePlayerProxy(player);
        +/
        // 普通代理情形下定义一个代理
         IGamePlayer proxy = new GamePlayerProxy("Jane");
         proxy.play();
    }
}
```
*  * 强制代理
```
/*
 * 必须通过真实角色找到代理角色并进行访问，否则不可以访问
 */
 
// 强制代理的接口类
public interface IGamePlayer{
    public void play();
    // 每个实现类都可以找一下自己的代理。
    public IGamePlayer getProxy();
}

// 强制代理的真实角色
public class GamePlayer implements IGamePlayer{
    private String name = "";
    // 代理
    private IGamePlayer proxy = null;
    public IGamePlayer(String _name){
        this.name = _name;
    }
    // 找到自己的代理
    public IGamePLayer getProxy(){
        this.proxy = new GamePlayerProxy(this);
        return this.proxy;
    }
    // 校验是否代理访问
    private boolean isProxy(){
        if (this.proxy == null) {
            return false;
        } else {
            return true;
        }
    }
    
    public void play(){
        if (this.isProxy()){
            // do something.
        } else {
            System.out.printl("请使用指定的代理进行访问！");
        }
    }
}

// 强制代理的代理类
public class GamePlayerProxy implements IGamePlayer{
    private IGamePlayer gamePlayer = null;
     
    public GamePlayerProxy(IGamePlayer _gamePlayer){
        this.gamePlayer = _gamePLayer;
    }
    
    public void play(){
        this.gamePlayer.play();
    }
    
    // 没有代理返回this[代理实际上还可被再代理]
    public IGamePlayer getProxy(){
        return this;
    }
}

// 强制代理的场景类
public class Client{
    public static void main(String[] args){
        // 定义一个游戏的角色
        IGamePlayer player = new GamePlayer("Jane");
        
        // 获得指定的代理[自己new出来的代理是无法正常代理的，因为不是真实角色指定的代理]
        IGamePlayer proxy = player.getProxy();
        
        proxy.play();
    }
}
```
*  * 动态代理
```
/*
 * 动态代理在实现阶段不关心代理谁，而在运行阶段才指定代理哪一个对象。
 * InvocationHandler是JDK提供的动态代理接口，对被代理类的方法进行代理。
 */
 
// 抽象主题
public interface Subject{
    // 业务操作
    public void doSomething(String str);
}

// 真实主题
public class RealSubject implements Subject{
    // 业务操作
    public void doSomething(String str){
        System.out.println("do somthing :" + str);
    }
}

// 动态代理的Handler
public class MyInvocationHandler implements InvocationHandler {
    // 被代理的对象
    private Object target = null;
    // 通过构造函数传递一个对象
    public MyInvocationHandler(Object _obj){
        this.target = _obj;
    }
    // 代理方法
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        // 执行被代理的方法
        return method.invoke(this.target, args);
    }
}

// 动态代理类
public class DynamicProxy<T> {
    public static <T> T newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) {
        // 寻找JoinPoint连接点，AOP框架使用元数据定义
        if (true) {
            // 执行一个前置通知
            (new BeforeAdvice()).exec();
        }
        // 执行目标，并返回结果
        return (T)Proxy.newProxyInstance(loader, interface, h);
   }
}

// 通知接口及实现
public interface  {
    // 通知只有一个简单的方法，执行即可
    public void exec();
}
public class BeforeAdvice implements IAdvice {
    public void exec(){
        System.out.println("执行前置通知！");
    }
}

// 具体业务的动态代理
public class SubjectDynamicProxy extends DynamicProxy {
    public static <T> T newProxyInstance(Subject subject) {
        // 获得ClassLoader
        ClassLoader loader = subject.getClass().getClassLoader();
        // 获得接口数据
        Class<?> classes = subject.getClass().getInterfaces();
        // 获得handler
        InvocationHandler handler = new MyInvocationHandler(subject);
        // 重新生成一个对象为subject做代理，找到subject的所有接口classes，并将其实现交给handler。
        return newProxyInstance(loader, classes, handler);
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 定义一个主题
        Subject subject = new RealSubject();
        // 定义主题的代理
        Subject proxy = SubjectDynamicProxy.newProxyInstance(subject);
        // 代理行为
        proxy.soSomething("test");
    }
}

/** 实现动态代理的首要条件是：被代理的类必须实现一个接口。 CGLIB 可以实现不需要接口也可以实现动态代理的方式 */
```
## 八，原型模式
* 定义：用原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。
```
/*
 * 实现Cloneable接口，并覆写Object类中的clone()方法。
 * 可以产生包含大量公共信息的类，然后修正细节。
 */
 
// 原型模式通用源码
public class PrototypeClass implements Cloneable {
    // 覆写父类Object中的clone()方法
    @Override
    public PrototypeClass clone() {
        PrototypeClass prototypeClass = null;
        try {
            prototypeClass = (PrototypeClass)super.clone();
        } catch (CloneNotSupportedException e) {
            // 异常处理。
        }
        return prototypeClass;
    }
}
```
* 原型模式的优点
   * 性能优良，在内存二进制流的拷贝，性能比new一个对象还很多；
   * 逃避构造函数的约束，即是优点也是缺点。
* 原型模式的使用场景
   * 资源优化场景，类初始化需要消耗非常多的资源；
   * 一个对象多个修改者，一个对象需要提供给其他对象访问，而且各个调用者可能都需要修改其值时，可以考虑使用原型模式拷贝多个对象供调用者使用。
   * 原型模式很少单独出现，一般是和工厂模式一起出现，通过clone的方法创建一个对象，然后由工厂方法提供给调用者。
* 原型模式的使用注意事项
   * 构造函数不会被执行
   * 深拷贝和浅拷贝
     * 浅拷贝：Object类提供的方法clone只是拷贝本对象，其对象内部的数组、引用对象等都不拷贝，还是指向原生对象的内部元素地址；
     * 深拷贝：对私有的类变量进行独立的拷贝，使得两个对象没有任何瓜葛，也可以自己写二进制流来操作对象完成深拷贝。
   * clone和final
     * 两个关键字冲突，如果要使用clone方法，类的成员变量上不要加final关键字。
## 九，中介者模式
* 定义：用一个中介对象封装一系列的对象交互，中介者使各对象不需要显示地相互作用，从而使其耦合松散，而且可以独立地改变它们之间的交互。
* 中介者模式包含以下几个部分：
   * Mediator抽象中介者角色，抽象中介者角色定义统一的接口，用于各同事角色之间的通信；
   * ConcreteMediator具体中介者角色，通过协调各同事角色实现写协作行为，必须依赖于各个同事角色；
   * Colleague同事角色，每个同事角色都应该知道中介者角色，且与其他同事角色通信的时候，一定要通过中介者角色协作。
```
// 通用抽象中介者
public abstract class Mediator {
    // 定义同事类
    protected ConcreteColleague1 c1;
    protected ConcreteColleague2 c2;
    // 通过getter/setter方法把同事类注入进来
    ......
    
    // 中介者模式的业务逻辑
    public abstract void doSomething1();
    public abstract void doSomething2();
}

// 通用中介者
public class ConcreteMediator extends Mediator {
    @Override
    public void doSomething1() {
        // 调用同事类的方法，只要是public方法都可以调用
        super.c1.selfMethod1();
        super.c2.selfMethod1();
    }
    
    @Override
    public void doSomething2() {
         super.c1.selfMethod2();
         super.c2.selfMethod2();
    }
}

// 抽象同事类
public abstract class Colleague {
    protect Mediator mediator;
    public Colleague(Mediator _mediator) {
        this.mediator = _mediator;
    }
} 

// 具体同事类
public class ConcreteColleague1 extends Colleague {
    // 通过构造函数传递中介者
    public ConcreteColleague1(Mediator _mediator) {
        super(_mediator);
    }
    // 自有方法 self-method
    public void selfMethod1() {
        // 处理自己的业务逻辑。
    }
    
    // 依赖方法
    public void depMethod1() {
        // 处理自己的业务逻辑
        // 自己不能处理的业务逻辑，委托给中介者处理
        super.mediator.doSomething1();
    }
}

public class ConcreteColleague2 extends Colleague {
    // 通过构造函数传递中介者
    public ConcreteColleague2(Mediator _mediator) {
        super(_mediator);
    }
    // 自有方法 self-method
    public void selfMethod2() {
        // 处理自己的业务逻辑。
    }
    
    // 依赖方法
    public void depMethod2() {
        // 处理自己的业务逻辑
        // 自己不能处理的业务逻辑，委托给中介者处理
        super.mediator.doSomething2();
    }  
}
/*
 * 同事类必须有中介者，而中介者却可以只有部分同事类。
 */
```
* 中介者模式的优点
   * 减少了类间依赖，把原有的一对多的依赖变成了一对一的依赖，同事类只依赖中介者，减少了依赖，同时降低了类间的耦合。
* 中介者模式的缺点
   * 中介者会膨胀的很大，且逻辑复杂，同事类越多，逻辑就越复杂。
* 中介者模式的使用场景
   * 适用于多个对象紧密耦合的情况，紧密耦合的标准是：在类图中出现了蜘蛛网结构，这种情况下考虑使用中介者模式，可以把蜘蛛网结构梳理为星型结构。
* 中介者模式的实际应用
   * 机场调度中心
   * MVC框架
   * 媒体网关
   * 中介服务
## 十，命令模式
* 定义：将一个请求封装成一个对象，从而让你使用不同的请求把客户端参数化，对请求排队或者记录请求日志，可以提供命令的撤销会恢复功能。
* 命令模式包含以下三个角色：
   * Receive接受者角色：干活，命令传递到这里需要被执行；
   * Command命令角色：需要执行的所有命令在这里声明；
   * Invoker调用者角色：接收到命令并执行命令。
```
// 通用Receiver类
public abstract class Receiver {
    // 抽象接收者，定义每个接收者都必须完成的业务
    public abstract void doSomething();
}

// 具体的Receiver类
public class ConcreteReceiver1 extends Receiver {
    public void doSomething() {
        // 业务逻辑。
    }
}
public class ConcreteReceiver2 extends Receiver {
    public void doSomething() {
        // 业务逻辑。
    }
}

// 抽象的Command类
public abstract class Command {
    // 每个命令类都必须有一个执行命令的方法
    public abstract void execute();
}

// 具体的Command类
public class ConcreteCommand1 extends Command {
    // 对哪个Receiver类进行命令处理
    private Receiver receiver;
    // 构造函数传递接收者
    public ConcreteCommand1(Receiver _receiver) {
        this.receiver = _receiver;
    }
    // 必须执行一个命令
    public void execute() {
        // 业务处理
        this.receiver.doSomething();
    }
}
public class ConcreteCommand2 extends Command {
    // 对哪个Receiver类进行命令处理
    private Receiver receiver;
    // 构造函数传递接收者
    public ConcreteCommand2(Receiver _receiver) {
        this.receiver = _receiver;
    }
    // 必须执行一个命令
    public void execute() {
        // 业务处理
        this.receiver.doSomething();
    }
}

// 调用者Invoker类
public class Invoker {
    private Command command;
    // 接受命令
    public void setCommand(Command _command) {
        this.command = _command;
    }
    // 执行命令
    public void action() {
        this.command.execute();
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 首先声明调用者Invoker
        Invoker invoker = new Invoker();
        // 定义接收者【实际运用中，接受者可隐藏于Command内部】
        Receiver receiver = new ConcreteReceiver1();
        // 定义一个发送给接收者的命令
        Command command = new ConcreteCommand1(receiver);
        // 把命令交给调用者执行
        invoker.setCommand(command);
        invoker.action();
    }
}
```
* 命令模式的优点
   * 类间解耦：调用者角色和接收者角色之间没有任何依赖关系，调用者实现功能时只需要调用Command抽象类的execute方法，不需要了解到底是哪个接收者执行。
   * 可扩展性：Command子类很容易扩展，而调用者Invoker和高层次模块Client不产生严重的代码耦合；
   * 可结合其他模式：结合责任链模式，实现命令族解析任务；结合模板方法模式，可以减少Command子类膨胀问题。
* 命令模式的缺点
   * 如果有N个命令，Command就会膨胀为N个。
* 命令模式的使用场景
   * 只要认为是命令的地方就可以采用命令模式。
* 命令模式的扩展
   * 增加需求：可增加命令或者修改现有命令中execute方法里的执行逻辑；
   * 返回问题：可增加撤销命令，并调用对应Receiver的rollback方法根据日志进行回滚。
## 十一，责任链模式
* 定义：使多个对象都有机会处理请求，从而避免了请求的发送者和接收者之间的耦合关系。将这些对象连成一条链，并沿着这条链传递该请求，直到有对象处理它为止。
```
// 抽象处理者
public abstract class Handler {
    private Handler nextHandler;
    // 每个处理者都必须对请求做出处理
    public final Response handleMessage(Request request) {
        Response response = null;
        // 判断是否是自己的处理级别
        if(this.handleLevel().equals(request.getRequestLevel())) {
            response = this.echo(request);
        } else {
            // 不属于自己的处理级别，判断是否有下一级的处理者
            if(this.nextHandler != null) {
                response = this.nextHandler.handleMessage(request);
            } else {
                // 没有适当的处理者，业务自行处理。
            }
        }
        return response;
    }
    
    // 设置下一个处理者是谁
    public void setNext(Handler _handler) {
        this.nextHandler = _handler;
    }
    
    // 每个处理者都有一个处理级别
    protected abstract Level getHandlerLevel();
    // 每个处理者都有必须实现的处理任务
    protected abstract Response echo(Request request); 
}

/*
 * 抽象处理者的三大职责
 * 1，定义请求的处理放法handleMessage，唯一对外开放的方法；
 * 2，链的编排方法setNext,设置下一个处理者；
 * 3，定义具体处理者必须要实现的两个方法。
 * 另外，可以考虑变形使用，可前一节点消费部分信息，后面节点再消费部分信息，可以有返回结果也可以没有返回结果，不用纠结是否是纯的责任链模式，这只是一个程序设计思想。
 */
 
// 具体的处理者
public class concreteHandler1 extends Handler {
    // 定义自己的处理逻辑
    protected Response echo(Request request) {
        // 完成处理逻辑
        return null;
    }
    
    // 设置自己的处理级别[Level,Request,Response由自己定义]
    protected Level getHandlerLevel() {
        return null;
    }
}
public class concreteHandler2 extends Handler {
    ······
}
public class concreteHandler3 extends Handler {
    ······
}

// 场景类[实际应用中，可用一个封装类来代替Client进行责任链的封装]
public class Client {
    public static void main(String[] args) {
        // 声明所有的处理节点
        Handler handler1 = new ConcreteHandler1();
        Handler handler2 = new ConcreteHandler2();
        Handler handler3 = new ConcreteHandler3();
        // 设置顺序
        handler1.setNext(handler2);
        handler2.setNext(handler3);
        // 提交请求
        Response response = handler1.handlerMessage(new Request); 
    }
} 
```
* 责任链模式的优点
   * 请求和处理分开，提高了灵活性。
* 责任链模式的缺点
   * 性能问题，每次都从链头到链尾；调试不方便。
* 责任链模式的注意事项
   * 需要控制节点数量，避免出现超长链路。
## 十二，装饰模式
* 定义：动态地给一个对象添加一些额外的职责。就增加功能来说，装饰模式相比生成子类更为灵活。
* 装饰模式有以下四个角色：
   * Component抽象构件；
   * ConcreteComponent具体构件，需要被装饰的实现类；
   * Decorator装饰角色，一般为抽象类，属性里必有一个private变量指向Component抽象构件；
   * 具体装饰角色。
```
// 抽象构件
public abstract class Component {
  // 抽象的方法
  public abstract void operate();
}

// 具体构件
public class ConcreteComponent extends Component {
  // 具体实现
  @Override
  public void operate() {
    System.out.println("do somthing");
  }
}

// 抽象装饰者
public abstract class Decorator extends Component {
  private Component component = null;
  // 通过构造函数传递被修饰者
  public Decorator(Component _component) {
    this.component = _component;
  }
  // 委托给被修饰者执行
  @Override
  public void operate() {
    this.component.operate();
  }
}

// 具体的装饰类
public class ConcreteDecorator1 extends Decorator {
  // 定义被修饰者
  public ConcreteDecorator1(Component _component) {
    super(_component);
  }
  // 定义自己的修饰方法
  public void method1() {
    System.out.println("method1 修饰");
  }
  
  // 重写父类的Operate方法
  public void operate() {
    this.method1();
    super.operate();
  }
}
public class ConcreteDecorator2 extends Decorator {
  // 定义被修饰者
  public ConcreteDecorator2(Component _component) {
    super(_component);
  }
  // 定义自己的修饰方法
  public void method2() {
    System.out.println("method2 修饰");
  }
  
  // 重写父类的Operate方法
  public void operate() {
     super.operate();
     this.method2();
  }
}
/** 原始方法和装饰方法的执行顺序在具体的装饰类中是固定的，可以通过方法重载实现多种执行顺序 */

// 场景类
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();
        // 第一次修饰
        component = new ConcreteDecorator1(component);
        // 第二次修饰
        component = new ConcreteDecorator1(component);
        // 修饰后运行
        component.operate();
    } 
}
```
* 装饰模式的优点
   * 装饰类和被装饰类都可以独立发展，而不会相互耦合；
   * 是继承关系的一个替代方案，一个补充；
   * 可以动态扩展一个实现类的功能。
* 装饰模式的缺点
   * 多层的装饰是比较复杂的。
* 装饰模式的使用场景
   * 需要扩展一个类的功能，或给一个类增加附加功能；
   * 需要动态地给一个对象增加功能，这些功能可以再动态地撤销；
   * 需要为一批的兄弟类进行改装或加装功能。
## 十三，策略模式
* 定义：定义一组算法，将每个算法都封装起来，并且使它们之间可以互换。
* 策略模式的三个角色：
   * Context封装角色，也叫上下文，起承上启下作用，屏蔽高层模块对策略、算法的直接访问，封装可能的变化；
   * Strategy抽象策略角色，通常为抽象，定义每个策略或者算法必须具备的属性和方法；
   * ConcreteStrategy具体策略角色，实现抽象策略中的操作，含有具体的算法。
```
// 抽象的策略角色
public interface Strategy {
    // 策略模式的运算法则
    public void doSomething();
}

// 具体策略角色
public class ConcreteStrategy1 implements Strategy {
    public void doSomething() {
        System.out.println("策略1");
    }
}
public class ConcreteStrategy2 implements Strategy {
    public void doSomething() {
        System.out.println("策略2");
    }
}

// 封装角色
public class Context {
    // 抽象策略
    private Strategy strategy = null;
    // 构造函数设置具体策略
    public Context(Strategy _strategy) {
        this.strategy = _strategy;
    }
    // 封装后的策略方法
    public void doAnything() {
        this.strategy.doSomething();
    }
}

// 高层模块
public class Client {
    public static void main(String[] args) {
        // 声明一个具体的策略
        Strategy strategy = new ConcreteStrategy1();
        // 声明上下文对象
        Context context = new Context(strategy);
        // 执行封装后的方法
        context.doAnything();
    }
}
```
* 策略模式的优点
   * 算法可以自由切换；
   * 避免使用多重条件判断；
   * 扩展性良好。
* 策略模式的缺点
   * 策略类数量增多；
   * 所有的策略类都需要对外暴露；
* 策略模式的使用场景
   * 多个类只有在算法或者行为上稍有不同的场景；
   * 算法需要自由切换的场景；
   * 需要屏蔽算法规则的场景。
* 策略模式的扩展
   * 策略枚举：是一个枚举；一个浓缩了的策略模式的枚举。
```
public enum Calculator {
     // 加法运算
     ADD("+") {
          public int exec(int a, int b) {
              return a + b;
          }
     }
     // 减法运算
     SUB("-") {
          public int exec(int a, int b) {
              return a - b;
          }
     }
     
     String value = "";
     
     // 定义成员值类型
     private Calculator(String _value) {
          this.value = _value;
     }
     
     // 获得枚举成员的值
     public String getValue() {
          return this.value;
     }
     
     // 声明一个抽象方法
     public abstract int exec(int a, int b);
}
/*
 * 策略枚举受枚举类型限制，每个枚举项都是public、final、static的，扩展性受约束，一般担当不经常发生变化的角色。
 */
```
## 十四，适配器模式
* 定义：将一个类的接口变换成客户端所期待的另一种接口，从而使原本接口不匹配而无法在一起工作的两个类能够在一起工作。
* 适配器模式的三个角色：
   * Target目标角色，定义将其他类转换为何种接口；
   * Adaptee源角色，已经存在的、运行良好的类或者对象；
   * Adapter适配器角色，核心角色，将源角色转换为目标角色。
```
// 目标角色
public interface Target {
    // 目标角色有自己的方法
    public void request();
}

// 目标角色的实现类
public class ConcreteTarget extends Target {
    public void request() {
        System.out.println("test");
    }
} 

// 源角色
public class Adaptee {
    // 运行良好的业务逻辑
    public void doSomething() {
        System.out.println("do something!");
    }
}

// 适配器角色
public class Adapter extends Adaptee implements Target {
     public void request() {
        super.doSomething();
     }
} 

// 场景类
public class Client {
    public static void main(String[] args) {
        // 目标角色的逻辑
        Target target = new Target();
        taeget.request();
        // 适配器将源角色转成现在已在运行的逻辑
        Target target2 = new Adapter();
        target2.request();
    }
}
```
* 适配器模式的优点
   * 可以让两个没有任何关系的类在一起运行；
   * 增加了类的透明性；
   * 提高了类的复用度；
   * 灵活性好。
* 适配器模式的使用场景
   * 动机修改一个已经投产中的接口。
* 适配器模式的注意事项
   * 设计阶段不考虑这个模式，因为其是为了解决正在服役的项目问题。
* 适配器模式的扩展
   * 类适配器器：类间继承，如代码示例；
   * 对象适配器：对象的合成关系，也就是类的关联依赖，通过对源角色的依赖，转换为目标角色的格式，实际项目中运用的比较多。
## 十五，迭代器模式
* 定义：提供一种方法访问一个容器对象中各个元素，而又不需要暴露该对象内部的细节。
* 迭代器模式的四个角色：
   * Iterator抽象迭代器，负责定义访问和遍历元素的接口，一般包含first()、next()、hasNext()方法；
   * ConcreteIterator具体迭代器，实现迭代器接口，完成容器元素的遍历；
   * Aggregate抽象容器，提供具体迭代器角色的接口，必然提供createIterator()的功能方法；
   * ConcreteAggregate具体容器，创建容纳迭代器的对象。
```
// 抽象迭代器
public interface Iterator {
    // 遍历到下一个元素
    public Object next();
    // 是否已经遍历到底部
    public boolean hasNext();
    // 删除当前指向元素
    public boolean remove();
}

// 具体迭代器
public class ConcreteIterator implements Iterator {
    private Vector vector = new Vector();
    // 定义当前游标
    public int cursor = 0;
    @SuppressWarning("unchecked")
    public ConcreteIterator(Vector _vector) {
        this.vector = _vector;
    }
    
    // 判断是否到达尾部
    public boolean hasNext() {
        if (this.cursor == this.vector.size()) {
            return false;
        } else {
            return true;
        }
    }
    
    // 返回下一个元素
    public object next() {
        Object result = null;
        if (this.hasNext()) {
            result = this.vector.get(this.cursor++);
        }
        return result;
    }
    
    // 删除当前元素
    public boolean remove() {
        this,vector.remove(this.cursor);
        return true
    }
    /** 删除方法应完成两个逻辑：1，删除当前元素；2，当前游标指向下一个元素。 */
}

// 抽象容器
public interface Aggregate {
    // 是容器必然有元素的增加
    public void add(Object object);
    // 减少元素
    public void remove(Object object);
    // 由迭代器来遍历所有的元素
    public Iterator iterator();
}

// 具体容器
public class ConcreteAggregate implements Aggregate {
    // 容纳对象的容器
    private Vector vector = new Vector();
    // 增加一个元素
    public void add(Object object) {
        this.vector.add(object);
    }
    // 删除一个元素
    public void remove(Object object) {
        this.vector.remove(object)
    }
    // 返回迭代器对象
    public Iterator iterator() {
        return new ConcreteIterator(this.vector);
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 声明容器
        Aggregate agg = new ConcreteAggregate();
        // 产生对象数据放进去
        agg.add("abc);
        agg.add("123");
        // 遍历一下
        Iterator iterator = agg.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
``` 
* java已经将迭代器融入到api中了，Iterator能满足一般的需求，尽量不用自己写迭代器了。
## 十六，组合模式
* 定义：将对象组合成树形结构以表示"部分-整体"的层次结构，使得用户对单个对象和组合对象的使用具有一致性。
* 组合模式中的几个角色：
   * Component抽象构件角色，定义一些默认的行为或属性；
   * Leaf叶子构件，其下无其他分支，遍历的最小单位；
   * Composite树枝构件，组合树枝节点和叶子节点形成一个树形结构。
```
// 抽象构件
public abstract class Component {
    // 个体和整体都具有的共享
    public void doSomething() {
        // 业务逻辑
    }
}

// 树枝构件
public class Composite extends Component {
    // 构件容器
    private ArrayList<Component> componentList = new ArrayList();
    // 增加一个叶子构件或树枝构件
    public void add(Component component) {
        this.componentList.add(component);
    }
    // 删除一个叶子构件或树枝构件
    public void remove(Component component) {
        this.componentList.remove(component);
    }
    // 获得分支下的所有叶子构件和树枝构件
    public ArrayList<Component> getChildren() {
        return this.componentList;
    }
}

// 叶子构件
public class Leaf extends Component {
    /*
     * 没有下级对象的对象；
     * 可以覆写父类的方法。
     */
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 创建一个根节点
        Composite root = new Composite();
        root.doSomething();
        // 创建一个树枝构件
        Composite branch = new Composite();
        // 创建一个叶子节点
        Leaf leaf = new Leaf();
        // 建立整体
        root.add(branch);
        branch.add(leaf);
    }
    
    // 通过递归遍历树
    public static void display(Composite root) {
        for (Component c : root.getChildren()) {
            if (c instanceof Leaf) {
                // 叶子节点
                c.doSomething();
            } else {
                display((Composite) c);
            }
        }
    }
}
```
* 组合模式的优点
   * 高层模块调用简单；
   * 节点自由增加；
* 组合模式的缺点
   * 直接使用了实现类，与依赖倒置原则冲突，限制了接口的影响范围。
* 组合模式的使用场景
   * 维护和展示部分-整体关系的场景；
   * 从一个整体中能够独立出部分模块或功能的场景。
* 组合模式的注意事项
   * 只要是树形结构接可以考虑使用该模式。
* 组合模式的扩展 
   * 真实的组合模式
      * 在实际项目中使用的组合模式，依靠了关系型数据库的非对象存储性能。
   * 透明的组合模式
      * 组合模式的两种不同实现：透明模式和安全模式。安全模式如前面描述，树枝和树叶不同结构；透明模式的树枝和树叶相同结构，若其无子节点则为树叶，需要特殊处理。
   * 组合模式的遍历
      * 增加父节点属性，按照树的遍历算法实现不同的遍历。
## 十七，观察者模式
* 定义：也叫做发布订阅模式。定义对象间一种一对多的依赖关系，使得每当一个对象改变状态，则所有依赖于它的对象都会得到通知并被自动更新。
* 观察者模式的几个角色：
   * Subject被观察者，管理观察者并通知观察者；
   * Observer观察者，对接收到的信息进行处理；
   * ConcreteSubject具体的被观察者，定义被观察者自己的业务逻辑；
   * ConcreteObserver具体的观察者，各个观察者自己的业务逻辑。
```
// 被观察者
public abstract class Subject {
    // 定义一个观察者数组
    private Vector<Observer> obsVector = new Vector<>();
    // 增加一个观察者
    public void addObserver(Observer o) {
        this.obsVector.add(o);
    }
    // 删除一个观察者
    public void delObserver(Observer o) {
        this.obsVector.remove(o);
    }
    // 通知所有的观察者
    public void notifyObservers() {
        fot (Observer o : this.obsVector) {
            o.update();
        }
    }
}

// 具体的被观察者
public class ConreteSubject extends Subject {
    // 具体的业务
    public void doSomething() {
        /*
         * do something.
         */
         
        // 观察者活动 
        super.notifyObservers();
    }
} 

// 观察者
public interface Observer {
    // 更新方法
    public void update();
}

// 具体观察者
public class ConcreteObserver extends Observer {
    // 实现更新方法
    public void update() {
        System.out.println("acquire message, begin to deal with.");
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 创建一个被观察者
        ConcreteSubject subject = new ConcreteSubject();
        // 定义一个观察者
        Observer obs = new ConcreteObserver();
        // 观察者观察被观察者
        subject.addObserver(obs);
        // 观察者开始活动
        subject.doSomething();
    }
}
```
* 观察者模式的优点
   * 观察者和被观察者之间是抽象耦合；
   * 建立一套触发机制。
* 观察者模式的缺点
   * 需要考虑开发效率和运行效率。java消息的通知默认是顺序执行，一个观察者卡住，会影响整体执行，这种情况可考虑异步执行。
* 观察者模式的使用场景
   * 关联行为场景；
   * 事件多级触发场景；
   * 跨系统的消息交换场景，如消息队列的处理机制。
* 观察者模式的注意事项
   * 广播链问题。一个观察者可以即使观察者也是被观察者，会形成链，可维护性差，故设计时，可限制最多出现一个双重身份的观察者对象（消息最多转发一次）；
   * 异步处理问题。
* 观察者模式的扩展
   * JAVA中的观察者模式
      * java的JDK中提供了java.util.Observable被观察者实现类和java.util.Observer观察者接口。
   * 项目中真实的观察者模式
      * 观察者和被观察者之间的消息沟通
         * 被观察者状态改变会触发观察者的一个行为，同时传递一个消息给观察者，一般观察者会接收两个参数，一个是被观察者，一个是用来传递数据的DTO。
      * 观察者响应方式
         * 解决观察者的性能问题：1，采用多线程技术，异步架构；2，采用缓存技术。
      * 发布订阅模型
         * kafka，activeMQ等。
## 十八，门面模式 
* 定义：也叫外观模式。要求一个子系统的外部与其内部的通信必须通过一个统一的对象进行。门面模式提供一个高层次的接口，使得子系统更易于使用。
* 门面模式的角色：
   * Facade门面角色。客户端可以调用这个角色的方法，此角色知晓子系统的所有功能和职责。
   * subsystem子系统角色。可以同时有一个或者多个子系统，子系统是一个类的集合，其不知道门面的存在。
```
// 子系统
public class ClazzA {
    public void doSomethingA() {
        // 业务逻辑
    }
}
public class ClazzB {
    public void doSomethingB() {
        // 业务逻辑
    }
}
public class ClazzC {
    public void doSomethingV() {
        // 业务逻辑
    }
}

// 门面对象
public class Facade {
    // 被委托的对象
    private ClazzA a = new ClazzA();
    private ClazzB b = new ClazzB();
    private ClazzC c = new ClazzC();
    
    // 提供给外部访问的方法
    public void methodA() {
        this.a.doSomethingA();
    }
    public void methodB() {
        this.b.doSomethingB();
    }
    public void methodC() {
        this.c.doSomethingC();
    }
}
```
* 门面模式的优点
   * 减少系统的相互依赖；
   * 提高了灵活性；
   * 提高安全性。
* 门面模式的缺点
   * 不符合开闭原则，门面对象有问题只能修改。
* 门面模式的使用场景
   * 为一个复杂的模块或者子系统提供一个供外界访问的接口；
   * 子系统相对独立---外界对子系统的访问只要黑箱操作即可；
   * 预防低水平人员带来的风险扩散，"画地为牢"，使其在指定的子系统中开发。
* 门面模式的注意事项
   * 一个子系统可以有多个门面
      * 门面已经庞大到不能忍受的程度；
      * 子系统可以提供不同访问路径，可以新增门面，将逻辑委托给已有的门面去处理。
   * 门面不参与子系统内的业务逻辑
      * 复杂的业务逻辑，建立封装类Context去实现，门面去依赖这个封装类进行处理，不宜直接将逻辑放进门面类中。
## 十九，备忘录模式
* 定义：在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。这样以后就可将该对象恢复到原先保存的状态。
* 备忘录模式的三个角色
   * Originator发起人角色。记录当前时刻的内部状态，负责定义哪些属于备份范围的状态，负责创建和恢复备忘录数据。
   * Memento备忘录角色。负责存储发起人对象的内部状态，在需要的时候提供发起人需要的内部状态。
   * Caretaker备忘录管理员角色。对备忘录进行管理、保存和提供备忘录。
```
// 发起人角色
public class Originator {
    // 内部状态
    private String state = "";
    
    public String getState() {
        return this.state;
    }
    public void setState(String _state) {
        this.state = _state;
    }
    
    // 创建一个备忘录
    public Memento createMemento() {
        return new Memento(this.state);
    }
    
    // 恢复一个备忘录
    public void restoreMemento(Memento _memento) {
        this.setState(_memento.getState());
    }
}

// 备忘录角色
public class Memento {
    // 发起人的内部状态
    private String state = "";
    // 构造函数传递参数
    public Memento(String _state) {
        this.state = _state;
    }
    public String getState() {
        return state;
    }
    public void setState(String _state) {
        this.state = _state;
    }
}

// 备忘录管理员角色
public class Caretaker {
    // 备忘录对象
    private Memento memento;
    
    public Memento getMemento() {
        return memento;
    }
    
    public void setMemento(Memento _memento) {
        this.memento = _memento;
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 定义发起人
        Originator originator = new Originator();
        // 定义备忘录管理员
        CareTaker careTaker = new CareTaker();
        // 创建一个备忘录
        careTaker.setMemento(originator.createMemento());
        // 恢复一个备忘录
        originator.restoreMemento(careTaker.getMemento());
    }
}
```
* 备忘录模式的使用场景
   * 需要保存和恢复数据的相关状态场景；
   * 提供一个可回滚的操作；
   * 需要监控的副本场景中；
   * 数据库连接的事务管理就是用的备忘录模式。
* 备忘录模式的注意事项
   * 备忘录的生命周期。备忘录创建出来就要在"最近"的代码中使用，要注意管理它的生命周期。
   * 备忘录的性能。不要在频繁建立备份的场景中使用备忘录模式。因为：1，控制不了备忘里建立的对象数量；2，大对象的创建是消耗资源的，系统的性能需要考虑。
* 备忘录模式的扩展
*   * clone方式的备忘录
      * 使用原型模式，发起人角色融合了发起人角色和备忘录角色，使用clone的方式备份；
      * java中也可以在发起人角色内部定义一个发起人属性，实现自主备份和自主恢复。
*   * 多状态的备忘录模式
      * 当需要备份多状态的对象时，可以增加一个BeanUtils类使用HashMap来进行多状态的备份；
```
public class BeanUtils {
    // 把bean的所有属性以及数值放入到HashMap中
    public static HashMap<String, Object> backupProp(Object bean) {
        HashMap<String, Object> result = new HashMap<>();
        try {
            // 获得Bean描述
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            // 获得属性描述
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            // 遍历所有属性
            for (PropertyDescriptor des : descriptors) {
                // 属性名称
                String fieldName = des.getName();
                // 读取属性的方法
                Method getter = des.getReadMethod();
                // 读取属性值
                Object fieldValue = getter.invoke(bean, new Object[]{});
                
                if (!fieldName.equalsIgnoreCase("class")) {
                    result.put(fieldName, fieldValue);
                }
            } 
        } catch (Exception e) {
            // 异常处理
        }
        return result;
    }
    
    // 把HashMap的值返回到bean中
    public static void restoreProp(Object bean, HaspMap<String, Object> propMap) {
        try {
            // 获得Bean描述
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass);
            // 获得属性描述
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
            // 遍历所有属性
            for (PropertyDescriptor des : descriptors) {
                // 属性名称
                String fieldName = des.getName();
                // 如果有这个属性
                if (propMap.containKey(fieldName)) {
                    // 写属性的方法
                    Method setter = des.getWriteMethod();
                    setter.invoke(bean, new Object[]{propMap.get(fieldName)});
                }
            }
        } catch (Exception e) {
            // 异常处理
        }
    }
```
*   * 多备份的备忘录
       * 备忘录管理员增加一个HashMap的容器，来根据戳记进行备份；
       * 需要注意设置容器的上限，不然容易出现内存溢出的问题。
*   * 封装得更好一点
       * 双接口设计：一个类可以实现多个接口，在系统设计时，如果考虑对象的安全问题，则可以提供两个接口，一个是业务的正常接口，实现必要的业务逻辑，叫做宽接口；另外一个接口是一个空接口，什么方法都没有，其目的是提供给子系统外的模块访问，这个叫做窄接口，由于窄接口中没有提供任何可供操作数据的方法，所以相对比较安全。
```
/*
 * 使备份的数据不能随意被修改
 */
 
// 备忘录接口
public interface IMemento{
 
}

// 发起人角色
public class Originator {
    // 内部状态
    private String state = "";
    
    public String getState() {
        return state;
    }
    
    public void setState(String _state) {
        this.state = _state;
    }
    
    // 创建一个备忘录
    public IMemento createMemento() {
        return new Memento(this.state);
    }
    
    // 恢复一个备忘录
    public void restoreMemento(IMemento _memento) {
        this.setState(((Memento)_memento).getState());
    }
    
    // 内置类
    private class Memento implements IMemento {
        // 发起人的内部状态
        private String state = "";
        // 构造函数传递参数
        private Memento(String _state) {
            this.state = _state;
        }
        
        private String getState() {
            return state;
        }
        
        private void setState(String _state) {
            this.state = _state;
        }
    }
}

// 备忘录管理者
public class CareTaker {
    // 备忘录对象[通过接口访问，无法直接去访问内部的属性]
    private IMemento memento;
    
    public IMemento getMemento() {
        return memento;
    }
    
    public void setMemento(IMemento _memento) {
        this.memento = _memento;
    }
}
```
## 二十，访问者模式
* 定义：封装一些作用于某种数据结构中的各元素的操作，它可以在不改变数据结构的前提下定义作用于这些元素的新的操作。
* 访问者模式的几个角色
   * Visitor抽象访问者。声明访问者可以访问哪些元素。
   * ConcreteVisitor具体访问者。影响访问者访问到一个类后干什么。
   * Element抽象元素。声明接受哪一类的访问者访问，通过accept方法中的参数定义。
   * ConcreteElement具体元素。实现accept方法。
   * ObjectStruture结构对象。元素产生者，一般容纳在多个不同类、不同接口的容器。
```
// 抽象元素
public abstract class Element {
    // 定义业务逻辑
    public abstract void doSomething();
    // 允许谁来访问
    public abstract void accept(IVisitor visitor);
}

// 具体元素
public class ConcreteElement1 extends Element {
    // 完善业务逻辑
    public void doSomething() {
        // 业务处理
    }
    
    // 允许哪个访问者访问
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}
public class ConcreteElement2 extends Element {
    // 完善业务逻辑
    public void doSomething() {
        // 业务处理
    }
    
    // 允许哪个访问者访问
    public void accept(IVisitor visitor) {
        visitor.visit(this);
    }
}

// 抽象访问者
public interface IVisitor {
    // 可以访问哪些对象
    public void visit(ConcreteElement1 el1);
    public void visit(ConcreteElement2 el2);
}

// 具体访问者
public class Visitor implements IVisitor {
    // 访问el1元素
    public void visit(ConcreteElement1 el1) {
        el1.doSomething();
    }
    // 访问el2元素
    public void visit(ConcreteElement2 el2) {
        el2.doSomething();
    }
}

// 结构对象
public class ObjectStruture {
    // 对象生成器，这里通过一个工厂方法模式模拟
    public static Element createElement() {
        Random rand = new Random();
        if (rand.nextInt(100) > 50) {
            return new ConcreteElement1();
        } else {
            return new ConcreteElement2();
        }
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        for (int i = 0 ; i < 10 ; i++) {
            // 获得元素对象
            Element el = ObjectStruture.createElement();
            // 接受访问者访问
            e1.accept(new Visitor);
        }
    }
}
```
* 访问者模式的优点
   * 符合单一职责原则
   * 优秀的扩展性
   * 灵活性非常高
* 访问者模式的缺点
   * 具体元素对访问者公布细节
   * 具体元素变更比较困难
   * 违背了依赖倒置原则
* 访问者模式的使用场景
   * 一个对象结构包含很多类对象，它们有不同的接口，想对这些对象实施一些依赖于具体类的操作；
   * 需要对一个对象结构中的对象进行很多不同并且不相关的操作；
   * 业务规则要求遍历多个不同的对象。
* 访问者模式的扩展
   * 统计功能。
   * 多个访问者。不同访问者做不同的事情。
   * 双分派。可以有多个访问者，同时有多个不同具体实现的角色，不同的访问者对不同的角色实现不同的逻辑。
* 访问者模式是一种集中规整的模式，特别适用于大规模重构的项目，进行功能集中化，例如报表运算。
## 二十一，状态模式
* 定义：当一个对象内在状态改变时允许其改变行为，这个对象看起来像改变其类。
* 状态模式中的三个角色：
   * State抽象状态角色。
   * ConcreteState具体状态角色。需要完成两个职责：本状态的行为管理以及趋向状态管理。
   * Context环境角色。定义客户端需要的接口，负责具体状态的切换。
```
// 抽象环境角色
public abstract class State {
    // 定义一个环境角色，提供子类访问
    protected Context context;
    // 设置环境角色
    public void setContext(Context _context) {
        this.context = _context;
    }
    // 行为1
    public abstract void handle1();
    // 行为2
    public abstract void handle2();
}

// 环境角色
public class ConcreteState1 extends State {
    @Override
    public void handle1() {
        // 本状态下必须处理的逻辑
    }
    @Override
    public void handle2() {
        // 设置当前状态为state2
        super.context.setCurrentState(Context.STATE2);
        // 过渡到state2状态，由Context实现
        super.context.handle2();
    }
}
public class ConcreteState2 extends State {
    @Override
    public void handle1() {
        // 设置当前状态为state1
        super.context.setCurrentState(Context.STATE1);
        // 过渡到state1状态，由Context实现
        super.context.handle1(); 
    }
    @Override
    public void handle2() {
        // 本状态下必须处理的逻辑
    }
}

// 具体环境角色
public class Context {
    // 定义状态
    public final static State STATE1 = new ConcreteState1();
    public final static State STATE2 = new ConcreteState2();
    
    // 当前状态
    private State currentState;
    
    // 获得当前状态
    public State getCurrentState() {
        return currentState;
    }
    
    // 设置当前状态
    public void setCurrentState(State _currentState) {
        this.currentState = _currentState;
        // 切换状态
        this.currentState.setContext(this);
    }
    
    // 行为委托
    public void handle1() {
        this.currentState.handle1();
    }
    public void handle2() {
        this.currentState.handle2();
    }
}

/*
 * 把状态对象声明为静态常量，有几个状态对象就声明几个静态常量；
 * 环境角色具有状态抽象角色定义的所有行为，具体执行使用委托方式。
 */
 
// 场景类
public class Client {
    public static void main(String[] args) {
        // 定义环境角色
        Context context = new Context();
        // 初始化状态
        context.setCurrentState(new ConcreteState1());
        // 行为执行
        context.handle1();
        context.handle2();
    }
}
```
* 状态模式的优点
   * 结构清晰
   * 遵循设计原则。体现了开闭原则和单一职责原来，每个状态都是一个子类。
   * 封装性非常好。状态变换放置到类的内部来实现，外部的调用不用知道类内部如何实现状态和行为的变换。
* 状态模式的缺点
   * 子类会太多导致类膨胀
* 状态模式的使用场景
   * 行为随状态改变而改变的场景；
   * 条件、分支判断语句的替代者。
* 状态模式的注意事项
   * 行为受状态约束时可以使用状态模式，而且使用时对象的状态最好不要超过5个。
## 二十二，解释器模式
* 定义：给定一门语言，定义它的文法的一种表示，并定义一个解释器，该解释器使用该表示来解释语言中的句子。
* 解释器模式是一个比较少用的模式，它的几个角色如下：
   * AbstractExpression抽象解释器。具体的解释任务由各实现类完成。
   * TerminalExpression终结符表达式。实现相关的解释操作，通常一个解释器模式中只有一个终结符表达式，但有多个实例，对应不同的终结符。
   * NonterminalExpression非终结符表达式。每条规则对应一个非终结表达式。
   * Context环境角色。
```
// 抽象表达式
public abstract class Expression {
    // 每个表达式必须有一个解析任务
    public abstract Object interpreter(Context ctx);
}

// 终结符表达式
public class TerminalExpression extends Expression {
    // 通常终结符表达式只有一个，但是有多个对象
    public Object interpreter(Context ctx) {
        return null;
    } 
}

// 非终结符表达式
public class NonterminalExpression extends Expression {
    // 每个非终结符表达式都会对其他表达式产生依赖
    public NonterminalExpression(Expression... expression){
    }
    
    public Object interpreter(Context ctx) {
        // 进行规则处理
        return null;
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        Context ctx = new Context();
        // 通常定义一个语法容器，容纳一个具体的表达式，通常为ListArray、LinkedList、Stack等类型
        Stack<Expression> stack = null;
        for(;;){
            // 进行语法判断，并产生递归调用
        }
        // 产生一个完整的语法树，由各个具体的语法分析进行解析
        Expression exp = stack.pop();
        // 具体元素进入场景
        exp.interpreter(ctx);
    }
}
```
* 解释器模式的优点
   * 其是一个简单的语法分析工具，有良好的扩展性，修改语法规则只需要修改非终结符表达式。
* 解释器模式的缺点
   * 解释器模式会引起类膨胀；
   * 采用了递归调用方法，导致调试复杂；
   * 由于使用了大量的循环和递归，会存在效率问题。
* 解释器模式的使用场景
   * 重复发生的问题可以使用解释器模式；
   * 一个简单语法需要解释的场景；
* 解释器模式的注意事项
   * 尽量不要在重要的模块中使用解释器模式，因为其很难维护。
## 二十三，享元模式
* 定义：使用共享对象可有效地支持大量的细粒度的对象。
* 细粒度的对象可将对象分为两个部分：
   * 内部状态。对象可共享出来的信息，存储在享元对象内部并且不会随环境改变而改变。
   * 外部状态。是对象得以依赖的一个标记，随环境改变而改变的、不可共享的状态。
* 享元模式的角色：
   * Flyweight抽象享元角色。一个产品的抽象类。
   * ConcreteFlyweight具体享元角色。具体的产品类。注意内部状态的处理与环境无关，不应该出现一个操作改变了内部状态，同时改变了外部状态。
   * unsharedConcreteFlyweight不可共享的享元角色。不能够使用共享技术的对象。
   * FlyweightFactory享元工厂。
```
// 抽象享元角色
public abstract class Flyweight {
    // 内部状态
    private String intrinsic;
    // 外部状态
    protected final String extrinsic;
    // 要求享元角色必须接受外部状态
    public Flyweight(String _extrinsic) {
        this.extrinsic = _extrinsic;
    }
    
    // 定义业务操作
    public abstract void operate();
    
    // 内部状态的getter/setter
    public String getIntrinsic() {
        return intrinsic;
    }
    public void setIntrinsic(String _intrinsic) {
        this.intrinsic = _intrinsic;
    } 
}

// 具体享元角色
public class ConcreteFlyweight1 extends Flyweight {
    // 接受外部状态
    public ConcreteFlyweight1(String __extrinsic) {
        this.extrinsic = _extrinsic;
    }
    // 根据外部状态进行逻辑处理
    public void operate() {
        // 业务处理。
    }
}
public class ConcreteFlyweight2 extends Flyweight {
    // 接受外部状态
    public ConcreteFlyweight2(String _extrinsic) {
        this.extrinsic = _extrinsic;
    }
    // 根据外部状态进行逻辑处理
    public void operate() {
        // 业务处理。
    }
}
/** 程序开发中，确认只需要一次赋值的属性则设置为final类型，避免无意修改导致逻辑混乱 */

// 享元工厂
public class FlyweightFactory {
    // 定义一个容器池
    private static HashMap<String, Flyweight> pool = new HashMap<>();
    // 享元工厂
    public static Flyweight getFlyweight(String _extrinsic) {
        // 需要返回的对象
        Flyweight flyweight = null;
        // 池中没有该对象
        if (pool.containsKey(_extrinsic)) {
            flyweight = pool.get(_extrinsic);
        } else {
            // 根据外部状态创建享元对象
            flyweight = new ConcreteFlyweight1(_extrinsic);
            // 放置到池中
            pool.put(_extrinsic, flyweight);
        }
        return flyweight;
    }
}
```
* 享元模式的优点和缺点
   * 减少了应用程序创建的对象，降低程序内存的占用，增强程序的性能；同时提高了系统复杂度，需要分理处外部状态与内部状态，且外部状态具有固化属性，不应该随内部状态变化而变化，否则会导致系统逻辑混乱。
* 享元模式的使用场景
   * 系统中存在大量相似对象；
   * 细粒度的对象都具备有较为接近的外部状态，而且内部状态与环境无关；
   * 需要缓冲池的场景。
* 享元模式的扩展
   * 线程安全问题。多线程访问对象池时，需要考虑线程安全。
   * 性能平衡。用自己写的类作为外部状态，性能会下降，最好用基础类型"String","int"等，，，
## 二十四，桥梁模式
* 定义：将抽象和实现解耦，使得两者可以独立地变化。
* 桥梁模式的重点是在"解耦"上，其有四个角色：
   * Abstraction抽象化角色。定义出该角色的行为。同时保存一个对实现化角色的引用。
   * Implementor实现化角色。定义该角色必须的行为和属性。
   * RefinedAbstraction修正抽象化角色。实现化角色对抽象化角色的修正。
   * ConcreteImplementor具体实现化角色。
```
// 实现化角色
public interface Implementor {
    // 基本方法
    public void doSomething();
    public void doAnything();
}

// 具体实现化角色
public class ConcreteImplementor1 implements Implementor {
    public void doSomething() {
        // 业务逻辑处理
    }
    public void doAnything() {
        // 业务逻辑处理
    }
}
public class ConcreteImplementor2 implements Implementor {
    public void doSomething() {
        // 业务逻辑处理
    }
    public void doAnything() {
        // 业务逻辑处理
    }
}

// 抽象化角色
public abstract class Abstraction {
    // 定义对实现化角色的引用
    private Implementor imp;
    // 约束子类必须实现该构造函数
    public Abstraction(Implementor _imp) {
        this.imp = _imp;
    }
    // 自身的行为和属性
    public void request() {
        this.imp.doSomething();
    }
    // 获得实现化角色
    public Implementor getImp() {
        return imp;
    }
}

// 具体化抽象角色
public class RefinedAbstraction extends Abstraction {
    // 覆写构造函数
    public RefinedAbstraction(Implementor _imp) {
        super(_imp);
    }
    
    // 修正父类行为
    @Override
    public void request() {
        /*
         * 业务处理
         */
         
         super.request();
         super.getImp().doAnything();
    }
}

// 场景类
public class Client {
    public static void main(String[] args) {
        // 定义一个实现化角色
        Implementor imp = new ConcreteImplementor2();
        // 定义一个抽象化角色
        Abstraction abs = new RefinedAbstraction(imp);
        // 执行
        abs.request();
    }
}
```
* 桥梁模式的优点
   * 抽象和实现分离；
   * 优秀的扩充能力；
   * 实现细节对客户透明。
* 桥梁模式的使用场景
   * 不希望或者不适用使用继承的场景；
   * 接口或者抽象类不稳定的场景；
   * 重要性要求较高的场景。
* 桥梁模式的注意事项
   * 主要考虑如何拆分抽象和实现，不是设计继承就用,其意图是对变化的封装，尽量把可能变化的因素封装到最细、最小的逻辑单元，避免风险扩散。