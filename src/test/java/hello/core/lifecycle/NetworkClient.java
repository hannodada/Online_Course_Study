package hello.core.lifecycle;

//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient /* implements InitializingBean, DisposableBean*/ {

    private String url;

    public NetworkClient(){
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 매시지");

    }

    public void setUrl(String url) {
        this.url = url;
    }


    // 서비스 시작 메서드
    public void connect(){
        System.out.println("connect : "+ url);
    }

    public void call(String message){
        System.out.println("call: "+ url +"message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect(){
        System.out.println("disconnect");
    }

    //@Override
    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    //@Override
    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
}
