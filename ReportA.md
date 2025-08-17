# 【課題A】総称型：Java vs. Go

## [Javaの総称型](https://dev.java/learn/generics/)

* 最新の[Javaの総称型](https://dev.java/learn/generics/)を概説
* 具体例を示す

```
public class SampleBox<T> {
    // 
    private T val;

    public void set(T v) { this.val = v; }

    public T get() { return val; }

    public static void main(String[] args) {
        SampleBox<String> b1 = new SampleBox<>();
        b1.set("Hello");
        String s = b1.get();
        System.out.println(s.getClass().getName() + " : " + s);

        SampleBox<Integer> b2 = new SampleBox<>();
        b2.set(100);
        Integer n = b2.get();
        System.out.println(n.getClass().getName() + " : " + n);
    }
}
```

## [Goの総称型](https://doi.org/10.1145/3563331)

* 最新の[Goの総称型](https://doi.org/10.1145/3563331)を概説
* 具体例を示す

## Java vs. Go

* 具体例を示しつつJavaとGoの総称型を比較
* 違いに対する評価も必要

## 参考文献
