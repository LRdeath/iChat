package io.weizc.github.italker.push;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import io.weizc.github.italker.push.provider.GsonProvider;
import io.weizc.github.italker.push.service.AcountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * Created by Vzc on 2017/6/5.
 */
public class Application extends ResourceConfig{
    public Application() {
        //注册逻辑处理的包名
        packages(AcountService.class.getPackage().getName());
        //注册JSON转化器
        register(GsonProvider.class);

        //注册日志打印输出
        register(Logger.class);


    }
}
