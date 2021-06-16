# CommonOkHttp

 ```
   implementation 'com.github.zhoulinxue:CommonOkHttp:1.0.2'
 ```

### Retrofit 是如何 创建 一个http 请求的？
##第一步:创建 Retrofit.Builder
```
 Retrofit.Builder builder = new Retrofit.Builder();
```
 ##第二步：builder 有很多属性 但是 比较重要的 几个 也是必须设置的
 ```
   builder.baseUrl("www.baidu.com");
   builder.addCallAdapterFactory();
   builder.addConverterFactory();
 ```
 注：以上三个 属性必须设置否则会报错，运行不起来   （实际情况中我们 还需要设置 client(OkHttpClient) 如果不设置 retrofit 会默认一个）
 ###a、 baseUrl 如果 不设置 baseUrl  会报错误  错误
```
  "Base URL required."
```
  注a:如果你项目中 一直使用全路径,这个配置 可以随便填一个地址（为了 适配 注解中  url 占位 及 url 路径 的使用方式 所以 retrofit 规定 必须设置 url）

 ### b、设置的属性 时怎么绑定到 OkHttpCall 里面去的？（以下 四个代码点 核心绑定过程。  第四点 实现）

   第一个点：从 Retrofit creat 方法中
   主要作用：是 绑定 Observable<T> 返回值 和方法；（具体代码 我们可以再 Retrofit 源代码中找到）
   ```
    public <T> T create(final Class<T> service) {
       Utils.validateServiceInterface(service);
       if (validateEagerly) {
         eagerlyValidateMethods(service);
       }
       // 通过Proxy 代理类 生成 api 类 的对象（所谓 api 类 就是 我们 写 注解的 那个接口类 下面注释）
       return (T) Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service },
           new InvocationHandler() {
           // 当 你调用某个 接口方法 时 触发这里,..比如 demo api 中调用 getTest 方法时 执行
             private final Platform platform = Platform.get();
             private final Object[] emptyArgs = new Object[0];

             @Override public Object invoke(Object proxy, Method method, @Nullable Object[] args)
                 throws Throwable {
               // If the method is a method from Object then defer to normal invocation.
               if (method.getDeclaringClass() == Object.class) {
                 return method.invoke(this, args);
               }
               if (platform.isDefaultMethod(method)) {
                 return platform.invokeDefaultMethod(method, service, proxy, args);
               }
               //通过 代理类 处理 会执行到这里  N1:invoke
               return loadServiceMethod(method).invoke(args != null ? args : emptyArgs);
             }
           });
     }
   ```
   注：本讲解中 的 api 类
   ```
   public interface WeatherApi {
       @GET("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100")
       public Observable<WeatherInfo> getTest();


       @GET("http://wthrcdn.etouch.cn/weather_mini?citykey=101010100")
       public CommonObservable<WeatherInfo> getCustomTest();

   }
   ```
   第二个点：
   loadServiceMethod
    注：我们会 进入到 ServiceMethod 抽象类  这个ServiceMethod 中 主要是 1、绑定 指定 method 方法 到 retrofit  定义 retrofit 返回类型 抽象方法 invoke N1处执行
   ```
   ServiceMethod<?> loadServiceMethod(Method method) {
       ServiceMethod<?> result = serviceMethodCache.get(method);
       if (result != null) return result;

       synchronized (serviceMethodCache) {
         result = serviceMethodCache.get(method);
         if (result == null) {
           result = ServiceMethod.parseAnnotations(this, method);
           serviceMethodCache.put(method, result);
         }
       }
       return result;
     }
   ```
   第三个点：
   ServiceMethod.parseAnnotations(this, method);
    注：主要作用  1、绑定 指定 method 方法 到 retrofit  关键 代码：RequestFactory.parseAnnotations(retrofit, method) 2、执行 ServiceMethod 类型为 HttpServiceMethod
   ```
static <T> ServiceMethod<T> parseAnnotations(Retrofit retrofit, Method method) {
    RequestFactory requestFactory = RequestFactory.parseAnnotations(retrofit, method);

    Type returnType = method.getGenericReturnType();
    if (Utils.hasUnresolvableType(returnType)) {
      throw methodError(method,
          "Method return type must not include a type variable or wildcard: %s", returnType);
    }
    if (returnType == void.class) {
      throw methodError(method, "Service methods cannot return void.");
    }
    // 这里
    return HttpServiceMethod.parseAnnotations(retrofit, method, requestFactory);
  }
   ```
   第四个点：
  HttpServiceMethod
    注：主要作用 1、绑定 解析 responseConverter 工具及 请求的关系  2、实现 invoke 方法 N1 处 调用的 方法 ReturnT invoke(Object[] args)
  ```
  static <ResponseT, ReturnT> HttpServiceMethod<ResponseT, ReturnT> parseAnnotations(
        Retrofit retrofit, Method method, RequestFactory requestFactory) {
      CallAdapter<ResponseT, ReturnT> callAdapter = createCallAdapter(retrofit, method);
      Type responseType = callAdapter.responseType();
      if (responseType == Response.class || responseType == okhttp3.Response.class) {
        throw methodError(method, "'"
            + Utils.getRawType(responseType).getName()
            + "' is not a valid response body type. Did you mean ResponseBody?");
      }
      if (requestFactory.httpMethod.equals("HEAD") && !Void.class.equals(responseType)) {
        throw methodError(method, "HEAD method must use Void as response type.");
      }

      Converter<ResponseBody, ResponseT> responseConverter =
          createResponseConverter(retrofit, method, responseType);

      okhttp3.Call.Factory callFactory = retrofit.callFactory;
      return new HttpServiceMethod<>(requestFactory, callFactory, callAdapter, responseConverter);
    }

     @Override ReturnT invoke(Object[] args) {
     //N2
        return callAdapter.adapt(
            new OkHttpCall<>(requestFactory, args, callFactory, responseConverter));
      }
  ```
  至此:所以 整个 builder 必须设置的 属性 执行过程就这么多。

  ###第三步：从 注解 代码 检查  关键类 RequestFactory.builder  上面 第三个点 绑定 retrofit 及mothed 的时候操作的 具体方法

  ```
    RequestFactory.parseAnnotations(retrofit, method)
  ```
  ### 第四步：请求 组装及校验  OkHttpCall  这个类 就会传递到 addCallAdapterFactory() 的参数类里面 demo 中 我们 实现了 CommonCallAdapter
     注：该 类 我们时从 retrofit 原生类里面复制出来 自己 组合了 部分 东西 逻辑没变 此处 adapt 方法 即：N2 处的 adapt 方法
        这里面实现了 整个 回调数据 的 数据转换、回调 及 解析
  ```
   @Override
      public Object adapt(Call<R> call) {
          Observable<Response<R>> responseObservable = new CommonCallExecuteObservable<>(call);
          if (isResult) {
              observable = new CommonResultObservable<>(responseObservable);
          } else if (isBody) {
              observable = new CommonBodyObservable<>(responseObservable);
          } else {
              observable = responseObservable;
          }
         
          if (scheduler != null) {
              observable = observable.subscribeOn(scheduler);
          }

          if (isFlowable) {
              return observable.toFlowable(BackpressureStrategy.LATEST);
          }
          if (isSingle) {
              return observable.singleOrError();
          }
          if (isMaybe) {
              return observable.singleElement();
          }
          if (isCompletable) {
              return observable.ignoreElements();
          }
          return observable;
      }
  ```
