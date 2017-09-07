package com.ascend.wangfeng.latte.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ascend.wangfeng.latte.delegates.web.event.Event;
import com.ascend.wangfeng.latte.delegates.web.event.EventManager;
import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by fengye on 2017/8/15.
 * email 1040441325@qq.com
 * 配置文件
 */

public class Configurator {
    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS =new ArrayList<>();

    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public static Configurator getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initICons();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public final HashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key) {
        checkConfiguration();
        return (T) LATTE_CONFIGS.get(key.name());
    }

    private void initICons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        LATTE_CONFIGS.put(ConfigType.ICON.name(),ICONS);
        return this;
    }
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigType.INTERCEPTORS.name(),INTERCEPTORS);
        return this;
    }
    public Configurator withWeChatAppId(String id) {
        LATTE_CONFIGS.put(ConfigType.WECHAT_APP_ID.name(),id);
        return this;
    }
    public Configurator withWeChatAppSecret(String secret) {
        LATTE_CONFIGS.put(ConfigType.WECHAT_APP_SECRET.name(),secret);
        return this;
    }
    public Configurator withJavascriptInterface(@NonNull String name){
        LATTE_CONFIGS.put(ConfigType.JAVASCRIPT_INTERFACE.name(),name);
        return this;
    }
    public Configurator withWebEvent(@NonNull String name, @NonNull Event event){
        EventManager.getInstance().addEvent(name,event);
        return this;
    }
    public Configurator withWebEvent(@NonNull String url){
        LATTE_CONFIGS.put(ConfigType.WEB_HOST.name(),url);
        return this;
    }
    public Configurator withActivityContext(@NonNull Context context ){
        LATTE_CONFIGS.put(ConfigType.ACTIVITY_CONTEXT.name(),context);
        return this;
    }

}
