import java.util.ArrayList;
import java.util.List;

class SampleSum {

    // Number に制約した汎用 sum メソッド
    public static <T extends Number> double sum(List<T> list) {
         // sumメソッドはNumber型のメソッドにのみ
        double s = 0;
        for (T v : list) {
            s += v.doubleValue();
        }
        return s;
    }

    public static void main(String[] args) {
        List ints = new ArrayList();
        //javaのジェネリクスでは<>で指定しなくても、型推論により自動でキャストされる。
        ints.add(1);
        System.out.println(ints.getClass().getName() + " : " + ints);
        ints.add(2);
        System.out.println("sum ints: " + sum(ints));
        // IntegerはNumberのサブクラスであるのでsumは適用可能。

        List strs = new ArrayList();
        strs.add("Hello");
        strs.add("World");
        // strsの要素はNumber
        try {
            double x = sum(strs); //コンパイルは通るけど、実行時エラーが起こる
        } catch (ClassCastException e) {
            System.out.println("実行時エラー: " + e);
        }
    }
}