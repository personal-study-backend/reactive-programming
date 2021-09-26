package hello.noddy.reactiveprogramming.duality;

import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class Chap2_Observable {

  /**
   * Observable은 event source라고 생각하자. event를 Observer한테 던진다. Observer를 Observable한테 등록시킨다. 새로운 정보
   * 발생하면 Observer한테 전달해준다.
   *
   * Iterable의 pull은 데이터를 받는 쪽에서 it.next()로 데이터를 당겨온다.
   * Observable의 push는 보내는 쪽에서 notiObservers로 데이터를 밀어준다.
   */

  static class IntObservable extends Observable implements Runnable{

    @Override
    public void run() {
      for (int i = 1; i <= 10; i++) {
        setChanged();
        notifyObservers(i); // 옵저버한테 알린다.
      }
    }

  }

  public static void main(String[] args) {
    Observer ob = new Observer() {
      @Override
      public void update(Observable o, Object arg) {
        System.out.println(arg);
      }
    };

    IntObservable io = new IntObservable();
    io.addObserver(ob); // 이 시점부터 io Observable이 던지는 모든 이벤트는 ob 옵저버가 받는다.

    io.run();
  }
}