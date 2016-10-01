# Algorithms PartI by Princeton University

这个Repo是我在Coursera上[《普林斯顿大学算法课I》](https://www.coursera.org/learn/introduction-to-algorithms/)时的课程作业，本Repo也会根据我在Cousera上的进度而持续跟进，希望我的作业能对大家在学习这门课时有所帮助，也欢迎大家在issue's上一起讨论问题。

##  [Programming Assignment 1: Percolation](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)

### Dynamic Connectivity问题
Dynamic Connectivity问题简单来说就是图论中判断两个点是否存在一条通路。在研究这个问题时，我们先定义点的集合，以及对集合的两种操作Union和Find。

#### 点的集合
点以正整数，0，1，2，3，...来表示。点的集合以花括号表示。列入 {0，1，2} {3，4} 表示点0，1和2在一个集合里，点3，4在另一个集合里。

#### 对集合的操作
* Union(x, y): 连接x点和y点，返回值void
* Find(x, y): x点和y点存在一条通路吗，如果点x和y在一个集合里，那么返回true否则返回false

#### 例子
* 初始点集合: {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}
* Union(0,1) => {0, 1}, 2, 3, 4, 5, 6, 7, 8
* Union(1,2) => {0, 1, 2}, 3, 4, 5, 6, 7, 8
* Find(1,3) => return **False**
* Union(1,3) => => {0, 1, 2, 3}, 4, 5, 6, 7, 8
* Find(1.3) => return **True**

#### 算法 QuickUnion

* 数据结构采用一个长度为N的数组，数组的index表示点，相应的值表示属于哪个集合，初始化时，每个点都属于自己的集合
* Union
* Find
```java
public class QuickFindUF {
    private int[] id;
    //构造函数，数组元素初始化为其index值 O(N)
    public QuickFindUF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }
    // Find方法 O(1)
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }
    // Union方法 O(N)
    public void union(int p, int q) {
        int pid = id[p];
        int qid = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pid) id[i] = qid;
        }
    }
}
```

#### 算法 QuickFind
#### 改进


### Percolation问题
Percolation问题：在一个平面被分为相等大小的N×N个正方形块，每一块有三种可能状态：**关闭**，**打开**和**被填满**。只有**打开**的正方形块才有可能**被填满**，**打开**的正方形块**被填满**的条件是：*该打开的正方形块的上方，左方或者右方有被填满的正方形块*。现假设任意一个正方形块由关闭到打开的概率为**P**，且只要第一排正方形块被**打开**的同时就会**被填满**，就好像水从第一排正方形块要向下通过打开的正方形块一只流到最后一排那样，问概率**P**为多少时，我们几乎可以确定最后一排中有**被填满**的正方形块，如果水能从第一排打开的正方形块一只流到最后一排打开的正方形块上，我们就说这个系统是percolate的。

该问题貌似没有现成的数学公式可以直接计算出结果，不过我们可以用数值计算（*Monte Carlo Simulation*）的方法来进行有限次数的模拟，最终计算出P的值。

解决思路：N×N个正方形块类比为N×N个UnionFind节点，并且想象有两个不存在的节点，一个在第一排正方形块上方，一个在最后一排正方形块下方。如果这两个节点相邻，那么就可以判断这个系统是percolate的。

下面是代码snippet
```java
   private WeightedQuickUnionUF map;
   private WeightedQuickUnionUF full;
   private boolean[][] openSite;
   private int n;
```
首先是类变量的声明，比较tricky的是，需要声明两个UnionFind类，一个用来判断系统是否percolate，一个用来判断某一个正方形块是否**被填满**。初始化时，第一个UnionFind类中，假想的两个节点分别与第一排与最后一排正方形块链接，而第二个UnionFind类中，只有第一个假想的节点与第一排正方形块链接。因为在判断系统是否percolate时，我们只需要判断两个假想节点是否相连而判断某个正方形块是否**被填满**时，我们需要判断该正方形块是否与第一个假想节点相连且该节点是**打开**的。

```java
   public Percolation(int n) {
       if (n <= 0) {
           throw new IllegalArgumentException();
       }

       int num = n*n+2;
       this.n = n;
       this.map = new WeightedQuickUnionUF(num);
       this.full = new WeightedQuickUnionUF(num);
       for (int i = 0; i < n; i++) {
           map.union(0, i+1);
           map.union(num-1, num-2-i);

           full.union(0, i+1);
       }
       openSite = new boolean[n][n];
   }
```

接着是构造函数，根据上面的说明，我们依次初始化两个UnionFind类。

```java
   public boolean isOpen(int i, int j) {
       testRange(i, j, this.n);
       return openSite[i-1][j-1];
   }
```

判断正方形块是否**打开**只需查询下二位boolean数组的值就好了，下面两个函数稍微有些复杂

>判断系统是否percolate时，我们只需要判断两个假想节点是否相连

```java
public boolean percolates() {
       if (this.n == 1) {
           return isOpen(1, 1);
       }
       return map.connected(0, n*n+1);
   }
```

>判断某个正方形块是否**被填满**时，我们需要判断该正方形块是否与第一个假想节点相连且该节点是**打开**的

```java
   public boolean isFull(int i, int j) {
       testRange(i, j, this.n);
       return full.connected(0, (i-1)*n+(j-1)+1) && isOpen(i, j);
```

在判断某个正方形块是否**被填满**时，有个tricky的点是，当系统只有1×1大小时，经过我们初始化后的系统便是perolate的，因为第一排和最后一排重合，此时假想的两个点也当然相连了。因此我们需要单独判断这种情况。

```java
   public void open(int i, int j) {
       testRange(i, j, this.n);
       j = j-1;
       i = i-1;
       if (!openSite[i][j]) {
           openSite[i][j] = true;
       }
       // left
       if ((j-1 >= 0) && (openSite[i][j-1])) {
           map.union((i*n+j+1), (i*n+(j-1)+1));
           full.union((i*n+j+1), (i*n+(j-1)+1));
       }
       // right
       if ((j+1 <= n-1) && (openSite[i][j+1])) {
           map.union((i*n+j+1), (i*n+(j+1)+1));
           full.union((i*n+j+1), (i*n+(j+1)+1));
       }
       // up
       if ((i-1 >= 0) && (openSite[i-1][j])) {
           map.union((i*n+j+1), ((i-1)*n+j+1));
           full.union((i*n+j+1), ((i-1)*n+j+1));
       }
       // down
       if ((i+1 <= n-1) && (openSite[i+1][j])) {
           map.union((i*n+j+1), ((i+1)*n+j+1));
           full.union((i*n+j+1), ((i+1)*n+j+1));
       }
   }
```

接着是open函数，根据相连正方形块是否打开，union函数可能被执行0至4次，这个也比较好理解。

下面是进行Monte Carlo Simulation的PercolationStats类
```java
   public PercolationStats(int n, int trials) {
       if ((n <= 0) || (trials <= 0)) {
           throw new IllegalArgumentException();
       }

       this.count = new double[trials];
       this.trials = trials;
       for (int i = 0; i < trials; i++) {
           Percolation test = new Percolation(n);
           int counter = 0;

           while(!test.percolates()) {
               int rand = StdRandom.uniform(0, n*n);
               int row = rand/n;
               int col = rand % n;
               if (!test.isOpen(row+1, col+1)) {    
                   test.open(row+1, col+1);
                   counter += 1;
               }
           }                    
           count[i] = (double) counter/(double) (n*n);
       }
       this.mean = StdStats.mean(this.count);
       this.stddev = StdStats.stddev(this.count);   
   }
```

关于该类API的定义，[问题描述](http://coursera.cs.princeton.edu/algs4/assignments/percolation.html)里都阐述的比较清楚，这里比较tricky的一点是，随机选取正方形块打开时，只有在正方形块前状态是关闭时才算做一次打开并计入次数。也就是说，两次随机选取了一样的正方形块打开只能算作一次。
