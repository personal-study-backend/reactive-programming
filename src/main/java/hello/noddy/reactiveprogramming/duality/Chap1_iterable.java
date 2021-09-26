package hello.noddy.reactiveprogramming.duality;

import java.util.Iterator;

public class Chap1_iterable {

  public static void main(String[] args) {
    // List 타입은 Iterable 인터페이스의 서브 인터페이스이다.
    // Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

    java.lang.Iterable<Integer> iter = () -> new Iterator<>() {

      int i = 0;
      final static int MAX = 10;

      @Override
      public boolean hasNext() {
        return i < MAX;
      }

      @Override
      public Integer next() {
        return ++i;
      }
    };

    // Collections 이기 때문에 가능한 것이 아니라, Iterable이기 때문에 가능한 것.
    for (Integer integer : iter) {
      System.out.println(integer);
    }

    /**
     * Iterable <-------------> Observable
     * 둘은 쌍대성
     * 둘의 궁극적인 목적은 같은데 반대방향으로 표현한 것.
     * Iterable은 pull, Observable은 push 개념
     */

  }
}
