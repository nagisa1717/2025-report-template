# 【課題A】総称型：Java vs. Go

## [Javaの総称型](https://dev.java/learn/generics/)

```
public class SampleBox<T> {
    // 型パラメータTを使って任意の型を保持できるようにしている
    private T val;

    public void set(T v) { this.val = v; }
    // 総称型はメソッドごとでも指定可能
    //ここではsetの引数vの型をTに限定している。

    public T get() { return val; }
}
```
```
    public static void main(String[] args) {
        SampleBox<String> b1 = new SampleBox<>();
        //ここでb1の型をStringと定義している
        b1.set("Hello");
        String s = b1.get();
        System.out.println(s.getClass().getName() + " : " + s);

        SampleBox<Integer> b2 = new SampleBox<>();
        //ここでb1の型をIntegerと定義している
        b2.set(100);
        Integer n = b2.get();
        System.out.println(n.getClass().getName() + " : " + n);
    }
```

## [Goの総称型](https://doi.org/10.1145/3563331)

```
package main

import (
    "fmt"
)

type SampleBox[T any] struct {
    val T
}

func (b *SampleBox[T]) Set(v T) {
    b.val = v
}

func (b *SampleBox[T]) Get() T {
    return b.val
}

func main() {
    // SampleBoxの型パラメータにstringを指定
    b1 := SampleBox[string]{}
    b1.Set("Hello")
    s := b1.Get()
    fmt.Printf("%T : %v\n", s, s) // 型と値を表示

    // SampleBoxの型パラメータにintを指定
    b2 := SampleBox[int]{}
    b2.Set(100)
    n := b2.Get()
    fmt.Printf("%T : %v\n", n, n) // 型と値を表示
}
```

## Java vs. Go

* 具体例を示しつつJavaとGoの総称型を比較
* 違いに対する評価も必要

## 参考文献
