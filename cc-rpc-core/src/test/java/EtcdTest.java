import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

public class EtcdTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 初始化客户端
        Client client = Client.builder()
                .endpoints("http://127.0.0.1:2379")
                .build();

        // 获取 KV 客户端
        KV kvClient = client.getKVClient();

        // 示例操作：写入键值对
        ByteSequence key = ByteSequence.from("test_key", StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from("test_value", StandardCharsets.UTF_8);
        kvClient.put(key, value).get();

        // 示例操作：读取键值对
        GetResponse getResponse = kvClient.get(key).get();
        System.out.println("Value: " + getResponse.getKvs().get(0).getValue().toString(StandardCharsets.UTF_8));

        // 关闭客户端
        client.close();
    }
}