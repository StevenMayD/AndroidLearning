package com.example.androidlearning.view.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidlearning.R;
import com.example.androidlearning.bean.BaseResponse;
import com.example.androidlearning.bean.Bean;
import com.example.androidlearning.bean.Job;
import com.example.androidlearning.service.RetrofitHttpBinService;
import com.example.androidlearning.service.WanAndroidService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.reactivestreams.Publisher;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";
    private Retrofit retrofit; // Retrofit请求对象
    private RetrofitHttpBinService httpBinService;  // Retrofit请求服务

    private Retrofit wanAndroidRetrofit;
    private WanAndroidService wanAndroidService;

    private Retrofit wanAndroidRetrofitConverter; // Retrofit请求对象（配置转换器）
    private WanAndroidService wanAndroidServiceConverter; // Retrofit请求服务（直接返回转换对象BaseResponse）

    private Retrofit retrofitAdapter; // retrofit适配器的使用
    private WanAndroidService retrofitServiceAdapter;

    private Map<String, List<Cookie>> cookies = new HashMap<>(); // 声明Map类型的属性并初始化 用于内存中存储Cookie

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);

        // Retrofit注意指定baseUrl
        retrofit = new Retrofit.Builder().baseUrl("https://www.httpbin.org/").build();
        httpBinService = retrofit.create(RetrofitHttpBinService.class);
        // 手动转换 响应数据为对象
        wanAndroidRetrofit = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/").build();
        wanAndroidService = wanAndroidRetrofit.create(WanAndroidService.class);
        // 自动转换 响应数据为对象
        wanAndroidRetrofitConverter = new Retrofit.Builder().baseUrl("https://www.wanandroid.com/")
                .addConverterFactory(GsonConverterFactory.create()) // 添加转换器：因为依赖是 retrofit2:converter-gson，所以使用GsonConverterFactory转换器
                .build(); // Retrofit请求对象 带转换器
        wanAndroidServiceConverter = wanAndroidRetrofitConverter.create(WanAndroidService.class);

        // retrofit适配器的使用
        retrofitAdapter = new Retrofit.Builder()
                .baseUrl("https://www.wanandroid.com/")
                .callFactory((okhttp3.Call.Factory) new OkHttpClient.Builder().cookieJar(new CookieJar() { // 添加自动保存cookie的OkHttpClient (由于 玩安卓示例中的嵌套请求 的关联是 通过cookie进行的)
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        // 以host为key，将Cookie保存在内存里，别的请求需要用时，取出来用即可 (比如 收藏、评论的功能，要求是登录的状态，可以将登录接口返回的Cookie，使用在收藏接口的请求时)
                        cookies.put(httpUrl.host(), list);
                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        // 如果是请求 "玩安卓"的接口，就使用保存好的cookie 否则返回空数组
                        List<Cookie> cookieList = cookies.get(httpUrl.host());
                        return cookieList==null ? new ArrayList<>() : cookieList;
                    }
                }).build())
                .addConverterFactory(GsonConverterFactory.create()) // 添加转换器
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // 添加适配器
                .build();
        retrofitServiceAdapter = retrofitAdapter.create(WanAndroidService.class);

        jsonAnalysis();
    }
    // json解析
    public void jsonAnalysis() {
        javaObject();
        jsonArrayAndList();
        jsonMapAndSet();
    }
    // java对象的解析
    public void javaObject() {
        // Gson提供的gson对象
        Gson gson = new Gson();

        // java对象
        Job job1 = new Job(001, "员工");
        Bean user1 = new Bean("dsw", 7, job1);
        // 序列化toJson：对象转json串
        String userJson1 = gson.toJson(user1);
        Log.d(TAG, "jsonAnalysis-序列化:" + userJson1); // {"age":7,"job":{"jobNum":1,"jobType":"员工"},"name":"dsw"}

        // 对象json串
        String userJson2 = "{\"age\":9,\"job\":{\"jobNum\":2,\"jobType\":\"员工\"},\"name\":\"lyy\"}";
        // 反序列化fromJson：json串转对象
        Bean user2 = gson.fromJson(userJson2, Bean.class);
        Log.d(TAG, "jsonAnalysis-反序列化:" + user2.getName() + "-" + user2.getAge() + "-" + user2.getJob());
    }

    // java 静态数组和list集合的解析
    public void jsonArrayAndList() {
        Job job1 = new Job(001, "员工");
        Gson gson = new Gson();

        // 数组（静态数组）的解析：同java对象的解析
        Bean[] userArray1 = new Bean[3]; // 定义一个元素为Bean类型的数组
        userArray1[0] = new Bean("dsw", 2, job1);
        userArray1[1] = new Bean("lyy", 3, job1);

        // 序列化toJson：数组转json串
        String userArrayJson1 = gson.toJson(userArray1);
        Log.d(TAG, "jsonArrayAndList-数组-序列化:" + userArrayJson1); // [{"age":2,"job":{"jobNum":1,"jobType":"员工"},"name":"dsw"},{"age":3,"job":{"jobNum":1,"jobType":"员工"},"name":"lyy"},null]

        // 反序列化fromJson：json串转数组 (Bean[].class-元素为Bean的数组类)
        Bean[] userArray2 = gson.fromJson(userArrayJson1, Bean[].class); // dsw-3-Job{jobNum=1, jobType='员工'}
        Log.d(TAG, "jsonArrayAndList-数组-反序列化:" + userArray2[0].getName() + "-" + userArray2[1].getAge() + "-" + userArray2[0].getJob());



        /* List集合（动态数组）的解析
        * List集合类型对象需要注意，在反序列化时，因为Java是伪泛型，泛型擦除会导致无法反序列化为List<Bean>，需要使用TypeToken完成反序列化
        * */
        List<Bean> userList1 = new ArrayList<>();
        userList1.add(new Bean("dsw", 22, job1));
        userList1.add(new Bean("lyy", 33, job1));
        userList1.add(null);

        // 序列化toJson： 集合转json串
        String userListJson1 = gson.toJson(userList1);
        Log.d(TAG, "jsonArrayAndList-集合-序列化:" + userListJson1); // [{"age":22,"job":{"jobNum":1,"jobType":"员工"},"name":"dsw"},{"age":33,"job":{"jobNum":1,"jobType":"员工"},"name":"lyy"},null]

        // 反序列化fromJson：json串转集合
        Type type = new TypeToken<List<Bean>>() {
        }.getType();
        List<Bean> userList2 = gson.fromJson(userListJson1, type); // 不能用 gson.fromJson(userListJson1, List.class)
        Log.d(TAG, "jsonArrayAndList-集合-反序列化:" + userList2.get(0).getName() + "-" + userList2.get(0).getAge() + "-" + userList2.get(0).getJob()); // dsw-22-Job{jobNum=1, jobType='员工'}

    }
    // java Map集合和Set集合的解析
    public void jsonMapAndSet() {
        Job job1 = new Job(001, "员工");
        job1.setCls(1); // 这里给cls复制，但序列化出来的是 class属性名 而不是cls
        Gson gson = new Gson();
//        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        // Map集合
        Map<String, Bean> map1 = new HashMap<>();
        map1.put("1", new Bean("dsw", 222, job1));
        map1.put("2", new Bean("lyy", 333, job1));
        map1.put(null, null);

        // 序列化：这里序列化出来的是 class属性名 而不是cls
        String mapJson = gson.toJson(map1);
        Log.d(TAG, "jsonMapAndSet-Map集合-序列化:" + mapJson); // {"1":{"age":222,"job":{"class":1,"jobNum":1,"jobType":"员工"},"name":"dsw"},"2":{"age":333,"job":{"class":1,"jobNum":1,"jobType":"员工"},"name":"lyy"}}
        // 反序列化fromJson：json串转Map集合, 同样用到 TypeToken完成反序列化
        Type type = new TypeToken<Map<String, Bean>>() {
        }.getType();
        Map<String, Bean> map2 = gson.fromJson(mapJson, type); // dsw-222-Job{jobNum=1, jobType='员工'}
        Log.d(TAG, "jsonMapAndSet-Map集合-反序列化:" + map2.get("1").getName() + "-" + map2.get("1").getAge() + "-" + map2.get("1").getJob());


        /* Set集合
        *   如果HashSet类型，则完全使用反序列化为List，因为二者序列化后的json数据完全一致: List<Bean> set2 = gson.fromJson(setJson, type2);
        *   否则 需要使用迭代器进行遍历 iterator().forEachRemaining();
        * */
        Set<Bean> set1 = new HashSet<>();
        set1.add(new Bean("dsw", 222, job1));
        set1.add(new Bean("lyy", 333, job1));
//        set1.add(null);

        // 序列化
        String setJson = gson.toJson(set1);
        Log.d(TAG, "jsonMapAndSet-Set集合-序列化:" + setJson); // [{"age":333,"job":{"jobNum":1,"jobType":"员工"},"name":"lyy"},{"age":222,"job":{"jobNum":1,"jobType":"员工"},"name":"dsw"}]

        // 反序列化1：反序列化为List
        Type type2 = new TypeToken<List<Bean>>() {
        }.getType();
        List<Bean> set2 = gson.fromJson(setJson, type2); // lyy-333-Job{jobNum=1, jobType='员工'}
        Log.d(TAG, "jsonMapAndSet-Set集合-反序列化1:" + set2.get(0).getName() + "-" + set2.get(0).getAge() + "-" + set2.get(0).getJob());

        // 反序列化2：反序列化为Set
        Type type3 = new TypeToken<Set<Bean>>() {
        }.getType();
        Set<Bean> set3 = gson.fromJson(setJson, type3);
        Iterator<Bean> iterator = set3.iterator();
        while (iterator.hasNext()) {
            Bean next = iterator.next();
            Log.d(TAG, "jsonMapAndSet-Set集合-反序列化2:" + next);
        }
    }

    // @Post注解请求
    public void postAsync_form_urlencoded(View view) {
        // Retrofit不用再拼接form参数了，利用注解可以简洁地组织好 参数格式
        Call<ResponseBody> call = httpBinService.postRequest("StevenMayD", "DongBao-3037");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    Log.d(TAG, "retrofit postAsync_form_urlencoded @Post注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    // @Body注解请求
    public void postAsync_form_body(View view) {
        new Thread() {
            public void run() {
                // 注意同步请求，要创建子线程 里执行（否则运行崩溃）
                FormBody fromBody = new FormBody.Builder().add("a", "3").add("b", "5").build();
                try {
                    Response<ResponseBody> response = httpBinService.postBodyRequest(fromBody).execute();
                    Log.d(TAG, "retrofit postAsync_form_data @Body注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // @Path注解请求
    public void postAsync_Path(View view) {
        new Thread() {
            public void run() {
                try {
                    // 相当于请求地址为：https://www.httpbin.org/post
                    Response<ResponseBody> response = httpBinService.postInPathRequest("post", "android","StevenMayD", "DongBao-3037").execute();
                    Log.d(TAG, "retrofit postAsync_Path注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // @Headers注解请求
    public void postAsync_Headers(View view) {
        new Thread() {
            public void run() {
                try {
                    // 相当于请求地址为：https://www.httpbin.org/post
                    Response<ResponseBody> response = httpBinService.postHeadersRequest().execute();
                    Log.d(TAG, "retrofit postAsync_Headers注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // @Url注解请求
    public void postAsync_Url(View view) {
        new Thread() {
            public void run() {
                try {
                    // 相当于请求地址为：https://www.httpbin.org/post
                    Response<ResponseBody> response = httpBinService.postUrlRequest("https://www.httpbin.org/post").execute();
                    Log.d(TAG, "retrofit postAsync_Url注解请求:" + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    //  retrofit 手动转换器
    public void retrofit_converter(View view) {
        new Thread() {
            public void run() {
                try {
                    Call<ResponseBody> call = wanAndroidService.login("StevenMayD", "DongBao-3037");
                    Response<ResponseBody> response = call.execute();
                    String result = response.body().string();
                    // 手动进行数据转换：使用Gson进行反序列化（将json字符串转换为对象，并指定 BaseResponse类 接管）
                    BaseResponse baseResponse = new Gson().fromJson(result, BaseResponse.class);
                    Log.d(TAG, "retrofit_手动转换-Gson:" + baseResponse); // 要想打印对象 需要BaseResponse类添加toString()方法\
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //  retrofit 自动转换器
    public void retrofit_converter_gson(View view) {
        new Thread() {
            public void run() {
                try {
                    // 可以直接使用BaseResponse来接收 而不是ResponseBody
                    Call<BaseResponse> call = wanAndroidServiceConverter.loginWithAutoConverter("StevenMayD", "DongBao-3037");
                    // 自动进行数据转换：使用retrofit2:converter-gson
                    Response<BaseResponse> response = call.execute();
                    BaseResponse baseResponse = response.body();
                    Log.d(TAG, "retrofit_自动转换-retrofit2:converter-gson:" + baseResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // retrofit 适配器：完成优雅的嵌套请求
    public void retrofit_adapter(View view) {
        // 先登陆请求
        retrofitServiceAdapter.login2("StevenMayD", "DongBao-3037")
                .flatMap(new Function<BaseResponse, Publisher<ResponseBody>>() { // flatMap：用于根据login2请求的结果，生成一个新的Publisher（继续进行请求 获取收藏列表接口getArticle）
                    @Override
                    public Publisher<ResponseBody> apply(BaseResponse baseResponse) throws Throwable {
                        // 再请求
                        return retrofitServiceAdapter.getArticle(0);
                    }
                })
                .observeOn(Schedulers.io()) // observeOn：切换到子线程 进行请求
                .subscribeOn(AndroidSchedulers.mainThread()) // subscribeOn：观察回调 则切换到 安卓主线程中
                .subscribe(new Consumer<ResponseBody>() { // subscribe：观察回调 在主线程中 执行 UI等操作
                    @Override
                    // 嵌套请求 最终拿到的是getArticle接口的返回类型ResponseBody
                    public void accept(ResponseBody responseBody) throws Throwable {
                        Log.d(TAG, "retrofit_adapter_适配器-retrofit2:adapter-rxjava3:" + responseBody.string());
                    }
                });
    }

    // retrofit 文件上传
    public void retrofit_upload(View view) {
        new Thread() {
            public void run() {
                try {
                    // 获取待上传文件
                    File file1 = new File("/storage/emulated/0/Pictures/1666838557454-185e93498-afdf-4325-b8fb-3ac8e5002dcf.jpeg");
                    // 参数（一个参数name:参数名(由接口协定)；第二个参数fileName:文件名；第三个参数：文件(用RequestBody包裹起来)）
                    MultipartBody.Part part = MultipartBody.Part.createFormData("file1", file1.getName(), RequestBody.create(file1, MediaType.parse("image/jpeg")));
                    Call<ResponseBody> call = httpBinService.postUploadRequest(part);
                    Log.d(TAG, "retrofit_upload文件上传" + call.execute().body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    // retrofit 文件下载（请求返回Call）
    public void retrofit_download_call(View view) {
        new Thread() {
            public void run() {
                String brand = Build.BRAND;
                String fileName = "Img_" + System.currentTimeMillis() + ".jpg"; // Img_1668413887942.jpg
                String filePath;
                if (brand.equals("xiaomi")) {
                    filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName;
                } else if (brand.equalsIgnoreCase("Huawei")) {
                    filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName; // /storage/emulated/0
                } else {
                    filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + fileName;
                }
                File file = new File(filePath); // filePath=/storage/emulated/0/DCIM/Camera/Img_1668413887942.jpg

                try {
                    Response<ResponseBody> response = httpBinService.postDownloadRequest("https://oss.coolcollege.cn/1835480998511513600.jpg").execute();
                    InputStream inputStream = response.body().byteStream(); // 响应的是下载资源 需要转换为byteStream(), 而不是string()了
                    FileOutputStream fos = new FileOutputStream(file);
                    // io操作-实现存储：不断地从InputStream中读，往FileOutputStream中写
                    int len;
                    byte[] buffer = new byte[4096];
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 当读取长度为-1 表示读取完毕
                        fos.write(buffer, 0, len);
                    }
                    // 存储完成后 关闭
                    fos.close();
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("RetrofitActivity", "retrofit_download_call:" + e.getMessage().toString());
                }
            }
        }.start();
    }

    // retrofit 文件下载（请求返回Flowable）
    public void retrofit_download_flowable(View view) {
        new Thread() {
            public void run() {
                httpBinService.postDownloadRxJavaRequest("https://oss.coolcollege.cn/1835480998511513600.jpg").map(new Function<ResponseBody, File>() {
                    @Override
                    public File apply(ResponseBody responseBody) throws Throwable {
                        String brand = Build.BRAND;
                        String fileName = "Img_" + System.currentTimeMillis() + ".jpg"; // Img_1668413887942.jpg
                        String filePath;
                        if (brand.equals("xiaomi")) {
                            filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName;
                        } else if (brand.equalsIgnoreCase("Huawei")) {
                            filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + fileName; // /storage/emulated/0
                        } else {
                            filePath = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + fileName;
                        }
                        File file = new File(filePath); // filePath=/storage/emulated/0/DCIM/Camera/Img_1668413887942.jpg

                        try {
                            InputStream inputStream = responseBody.byteStream(); // 响应的是下载资源 需要转换为byteStream(), 而不是string()了
                            FileOutputStream fos = new FileOutputStream(file);
                            // io操作-实现存储：不断地从InputStream中读，往FileOutputStream中写
                            int len;
                            byte[] buffer = new byte[4096];
                            while ((len = inputStream.read(buffer)) != -1) {
                                // 当读取长度为-1 表示读取完毕
                                fos.write(buffer, 0, len);
                            }
                            // 存储完成后 关闭
                            fos.close();
                            inputStream.close();
                            return file;
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("RetrofitActivity", "retrofit_download_call:" + e.getMessage().toString());
                            return null;
                        }
                    }
                }).subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) throws Throwable {
                        // TODO: 完成下载后的后续操作（比如下载完.apk后，继续安装.apk）
                    }
                });
                while (true) {

                }
            }
        }.start();
    }

}