# 【課題A】総称型：Java vs. Go

## [Javaの総称型](https://dev.java/learn/generics/)
Java のジェネリクスは、型パラメータを用いることで同じクラスやメソッドを異なる型に対して安全に再利用できる仕組みである。以下に示すSampleBox<T> はその典型例であり、型パラメータ T を用いることで任意の型の値を保持できるボックスクラスとして実装されている。内部のvalフィールドはT型として定義され、setメソッドで値を設定し、getメソッドで値を取得する際も同じ型が保証される。この設計により、例えば SampleBox<String> と SampleBox<Integer> のように異なる型でインスタンス化しても、コンパイル時に型チェックが行われ、型の誤用を防ぐことができる。

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
実際の使用例として、SampleBox<String> のインスタンスb1に "Hello" を格納し、SampleBox<Integer> のインスタンス b2に100 を格納する場合、getメソッドの戻り値はそれぞれStringとInteger型として扱われる。これにより、キャスト操作を明示的に書く必要がなくなる。

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
Goのジェネリクスも同様に、型パラメータを用いることで同じメソッドを異なる型に対して安全に再利用できる仕組みである。内部の val フィールドは T 型として定義され、Set メソッドで値を設定し、Get メソッドで値を取得する際も同じ型が保証される。この設計により、例えば SampleBox[string] と SampleBox[int] のように異なる型でインスタンス化しても、コンパイル時に型チェックが行われ、型の誤用が防止される。

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
###型整合の方法 (Type Erasure vs. Monomorphic)
Javaの総称型では、すべての型パラメータをObject型としてキャストする。つまりコンパイル時に型チェックは行うが、バイナリコードに変換されるときは総称型で型が指定されたインスタンスは全てObject型として扱われる。この性質をType Erasureといい生成されるインスタンスとその型の種類に関わらず一種類の型として扱われるためバイナリが節約できるというメリットもあるのだが、次に述べるようなデメリットが大きい。
例えば、本来Integer型にしか適用を想定していないメソッドをString型のオブジェクトに適応しようとした時などである。
具体的なコード例SampleSum.javaを添付した。
この例のようにArrayListにString型である"Hello"と"World"を要素として追加し、sumを適用しようとしてもコンパイル時にはエラーが出ることなく通ってしまう。
しかし実際はsum(strs)を行おうとした時点で実行時エラーとなる。

一方、GoはMonomorphicと呼ばれ、型情報を保持してバイナリコードが生成される。型ごとにバイナリコードが生成されるため、型が増えるとその度にバイナリサイズも増える。
しかしメリットとして、Goでは型違いはコンパイル時に弾かれるため、実行時エラーを防ぐことができる。
Javaのコード例SampleSum.JavaをGoで再現したコード例SampleSum.goを添付した。
同じようにリストに"Hello"と"World"を要素として追加した場合、sumを適用しようするとコンパイルの時点でエラーが発生した。
つまりJavaと比較して型安全が保証されているということが言える。

###両者の比較
両者の特徴を比較すると、Java はバイナリサイズの節約という点で優れているが、型安全性という点では制約がある。一方 Go はバイナリサイズが増える可能性はあるものの、型情報が保持されるため、実行時の型エラーが排除されやすいという利便性の高さでメリットがある。しかしバイナリサイズの節約といっても、ジェネリック型の種類がそこまで膨大ではない場合、型が増えることによるバイナリの肥大は問題なるほどではないケースが多いはずである。
実際の使用を想定すると、多くのケースでは、バイナリサイズの増大よりも型情報が保持されコンパイル時に型の安全性が保証されることの方が利便性の上で重要なのではないかと考えられる。特に大規模システムを構成する場合、型安全性が高いことにより、実行時の不具合発生を大幅に削減できるため、Monomorphic方式の方が使いやすそうであると結論づける。
## 参考文献
