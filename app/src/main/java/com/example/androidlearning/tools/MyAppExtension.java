package com.example.androidlearning.tools;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.example.androidlearning.R;

// 给一个自定义的类 添加 @GlideExtension注解
@GlideExtension
public class MyAppExtension {
    // 该类中需要一个 私有的 无参的 构造方法
    private MyAppExtension() {
    }

    // MyAppExtension中的静态方法需要用 @GlideOption 声明（该静态方法需要接收 BaseRequestOptions<?>类型的参数， 并返回BaseRequestOptions<?>类型的参数）
    @GlideOption
    public static BaseRequestOptions<?> defaultImg(BaseRequestOptions<?> options) {
        return options
                .placeholder(R.drawable.wechat_normal)
                .error(R.drawable.address_book_normal)
                .fallback(R.drawable.find_normal)
                .override(100, 100)
                .transform(new GranularRoundedCorners(30, 80, 80, 30));
    }
}
