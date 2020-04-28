package cn.tgozzz.legal.utils;

import cn.tgozzz.legal.domain.Token;
import cn.tgozzz.legal.domain.User;
import cn.tgozzz.legal.repository.UserRepository;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;


@Component
public class TokenUtils {

    private final UserRepository repository;

    private final ReactiveRedisTemplate reactiveRedisTemplate;


    public TokenUtils(UserRepository repository, ReactiveRedisTemplate reactiveRedisTemplate) {
        this.repository = repository;
        this.reactiveRedisTemplate = reactiveRedisTemplate;
    }

    /**
     * 验证token是否还在缓存时间内
     */
    public Mono<Boolean> isValidToken(String token) {

        ReactiveValueOperations<String, Token> operations = reactiveRedisTemplate.opsForValue();

        return operations.get(token)
                .map(token1 -> true)
                .switchIfEmpty(Mono.just(false));
    }

    /**
     * 根据token获取用户实体对象
     */
    public Mono<User> getUser(ServerRequest request) {

        String tokenStr = request.headers().header("Authorization").get(0);
        ReactiveValueOperations<String, Token> operations = reactiveRedisTemplate.opsForValue();

        return operations.get(tokenStr)
                .map(Token::getUid)
                .flatMap(repository::findById);
    }

    /**
     * 顺便保存用户
     */
    public Mono<User> saveUser(User user) {
        return repository.save(user);
    }
}
