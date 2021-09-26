package hello.noddy.reactiveprogramming.duality;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class Chap3_PublisherSubscriber {

  public static void main(String[] args) {
    // Publisher (이전의 Observable과 같은 역할)
    // Subscriber (이전의 Observer와 같은 역할)

    // A Publisher is a provider of a potentially unbounded number of sequenced elements, publishing them according to the demand received from its Subscriber(s).
    Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5);

    Publisher<Integer> publisher = new Publisher<>() {

      Iterator<Integer> it = iter.iterator();

      @Override
      public void subscribe(Subscriber subscriber) {
        subscriber.onSubscribe(new Subscription() {
          @Override
          public void request(long n) {
            try {
              while (n-- > 0) {
                if (it.hasNext()) {
                  subscriber.onNext(it.next());
                } else {
                  subscriber.onComplete();
                  break;
                }
              }
            } catch (RuntimeException e) {
              subscriber.onError(e);
            }
          }

          @Override
          public void cancel() {

          }
        });
      }
    };

    Subscriber<Integer> subscriber = new Subscriber<>() {

      Subscription subscription;

      @Override
      public void onSubscribe(Subscription subscription) {
        System.out.println("onSubscribe");

        // 데이터 어떻게 받겠다는 back presure가 필요하다.
        // 퍼블리셔는 100만개를 막 주는데, subscriber는 한개 처리하는데 오래 걸린다.
        // 중간에 큰 버퍼를 만들던지 그런 해결책이 있어야되서, back presure를 통해 조절해야 한다.
        this.subscription = subscription;
        this.subscription.request(1);
        // back presure로 한번 요청한 후 그다음 데이터를 받는 부분은 onNext에서 처리하면 된다.
      }

      @Override
      public void onNext(Integer item) {
        // observer 패턴의 update와 같다.
        // 퍼블리셔가 데이터를 주면, onNext가 받는다.
        System.out.println("onNext " + item);
        this.subscription.request(1);
      }

      @Override
      public void onError(Throwable throwable) {
        // 예외 이 메소드가 받아서 할 수 있도록 만든 것.
        System.out.println(throwable.toString());
      }

      @Override
      public void onComplete() {
        // 퍼블리셔가 줄 데이터가 더 이상 없다고 완료됬다고 처리하는 것
        System.out.println("onComplete");
      }
    };

    publisher.subscribe(subscriber);
  }
}
