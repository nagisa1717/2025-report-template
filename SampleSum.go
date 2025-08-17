package main

import "fmt"

// Numberを型制約でint型かfloat型のみとする
type Number interface {
    ~int | ~float64
}

func Sum[T Number](list []T) float64 {
	//Sumはリスト内の要素をfloatに変換し合計値を返す
    var s float64
    for _, v := range list {
        s += float64(v)
    }
    return s
}

func main() {
    ints := []int{1, 2}
    fmt.Println("sum ints:", Sum(ints)) // intはNumberの制約に適応しているのでOK

    strs := []string{"a", "b", "c"} // stringはNumberの制約の適応外
    // fmt.Println(Sum(strs)) // ← コンパイルの時点でエラーが起こる
}