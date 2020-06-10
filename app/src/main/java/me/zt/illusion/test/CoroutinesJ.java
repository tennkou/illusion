//package me.zt.illusion.test;
//
//import kotlin.Metadata;
//import kotlin.ResultKt;
//import kotlin.Unit;
//import kotlin.coroutines.Continuation;
//import kotlin.coroutines.CoroutineContext;
//import kotlin.coroutines.intrinsics.IntrinsicsKt;
//import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
//import kotlin.coroutines.jvm.internal.DebugMetadata;
//import kotlin.coroutines.jvm.internal.SuspendLambda;
//import kotlin.jvm.functions.Function2;
//import kotlin.jvm.internal.Intrinsics;
//import kotlinx.coroutines.BuildersKt;
//import kotlinx.coroutines.CoroutineScope;
//import kotlinx.coroutines.CoroutineScopeKt;
//import kotlinx.coroutines.Deferred;
//import kotlinx.coroutines.DelayKt;
//import kotlinx.coroutines.Dispatchers;
//import org.jetbrains.annotations.NotNull;
//import org.jetbrains.annotations.Nullable;
//
//@Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\000\032\n\002\030\002\n\002\020\000\n\002\b\002\n\002\020\016\n\002\b\003\n\002\020\002\n\000\030\0002\0020\001B\005\006\002\020\002J\031\020\003\032\0020\0042\006\020\005\032\0020\004H\001\000\006\002\020\006J\016\020\007\032\0020\b2\006\020\005\032\0020\004\002\004\n\002\b\031\006\t"}, d2 = {"Lme/zt/illusion/test/Coroutines;", "", "()V", "doLoad", "", "url", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "load", "", "app_debug"})
//public final class CoroutinesJ {
//    public final void load(@NotNull String url) {
//        Intrinsics.checkParameterIsNotNull(url, "url");
//        Thread thread = Thread.currentThread();
//        boolean bool = false;
//        System.out.println(thread);
//        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope((CoroutineContext)Dispatchers.getMain()), null, null, new Coroutines$load$1(url, null), 3, null);
//    }
//
//    @DebugMetadata(f = "Coroutines.kt", l = {13}, i = {0}, s = {"L$0"}, n = {"$this$launch"}, m = "invokeSuspend", c = "me.zt.illusion.test.Coroutines$load$1")
//    @Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 3, d1 = {"\000\016\n\000\n\002\020\002\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"})
//    static final class Coroutines$load$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
//        private CoroutineScope p$;
//
//        Object L$0;
//
//        int label;
//
//        @Nullable
//        public final Object invokeSuspend(@NotNull Object $result) {
//            CoroutineScope $this$launch;
//            String str1, result;
//            boolean bool1;
//            Deferred deferred;
//            String str2;
//            boolean bool2;
//            Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
//            switch (this.label) {
//                case 0:
//                    ResultKt.throwOnFailure($result);
//                    $this$launch = this.p$;
//                    str1 = "launch " + Thread.currentThread();
//                    bool1 = false;
//                    System.out.println(str1);
//                    this.L$0 = $this$launch;
//                    this.label = 1;
//                    if (Coroutines.this.doLoad(this.$url, (Continuation<? super String>)this) == object)
//                        return object;
//                    result = (String)Coroutines.this.doLoad(this.$url, (Continuation<? super String>)this);
//                    deferred = BuildersKt.async$default($this$launch, null, null, new Coroutines$load$1$deferred$1(null), 3, null);
//                    str2 = "launch result " + result + ", " + Thread.currentThread();
//                    bool2 = false;
//                    System.out.println(str2);
//                    return Unit.INSTANCE;
//                case 1:
//                    $this$launch = (CoroutineScope)this.L$0;
//                    ResultKt.throwOnFailure($result);
//                    result = (String)$result;
//                    deferred = BuildersKt.async$default($this$launch, null, null, new Coroutines$load$1$deferred$1(null), 3, null);
//                    str2 = "launch result " + result + ", " + Thread.currentThread();
//                    bool2 = false;
//                    System.out.println(str2);
//                    return Unit.INSTANCE;
//            }
//            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
//        }
//
//        Coroutines$load$1(String param1String, Continuation param1Continuation) {
//            super(2, param1Continuation);
//        }
//
//        @NotNull
//        public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation completion) {
//            Intrinsics.checkParameterIsNotNull(completion, "completion");
//            Coroutines$load$1 coroutines$load$1 = new Coroutines$load$1(((Coroutines$load$1)super).$url, completion);
//            coroutines$load$1.p$ = (CoroutineScope)value;
//            return (Continuation<Unit>)coroutines$load$1;
//        }
//
//        public final Object invoke(Object param1Object1, Object param1Object2) {
//            return ((Coroutines$load$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
//        }
//
//        @DebugMetadata(f = "Coroutines.kt", l = {14}, i = {0}, s = {"L$0"}, n = {"$this$async"}, m = "invokeSuspend", c = "me.zt.illusion.test.Coroutines$load$1$deferred$1")
//        @Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 3, d1 = {"\000\016\n\000\n\002\020\016\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"})
//        static final class Coroutines$load$1$deferred$1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
//            private CoroutineScope p$;
//
//            Object L$0;
//
//            int label;
//
//            @Nullable
//            public final Object invokeSuspend(@NotNull Object $result) {
//                CoroutineScope $this$async;
//                Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
//                switch (this.label) {
//                    case 0:
//                        ResultKt.throwOnFailure($result);
//                        this.L$0 = $this$async = this.p$;
//                        this.label = 1;
//                        return (Coroutines.this.doLoad(Coroutines$load$1.this.$url, (Continuation<? super String>)this) == object) ? object : Coroutines.this.doLoad(Coroutines$load$1.this.$url, (Continuation<? super String>)this);
//                    case 1:
//                        $this$async = (CoroutineScope)this.L$0;
//                        ResultKt.throwOnFailure($result);
//                        return $result;
//                }
//                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
//            }
//
//            Coroutines$load$1$deferred$1(Continuation param1Continuation) {
//                super(2, param1Continuation);
//            }
//
//            @NotNull
//            public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation completion) {
//                Intrinsics.checkParameterIsNotNull(completion, "completion");
//                Coroutines$load$1$deferred$1 coroutines$load$1$deferred$1 = new Coroutines$load$1$deferred$1(completion);
//                coroutines$load$1$deferred$1.p$ = (CoroutineScope)value;
//                return (Continuation<Unit>)coroutines$load$1$deferred$1;
//            }
//
//            public final Object invoke(Object param1Object1, Object param1Object2) {
//                return ((Coroutines$load$1$deferred$1)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
//            }
//        }
//    }
//
//    @DebugMetadata(f = "Coroutines.kt", l = {21}, i = {0}, s = {"L$0"}, n = {"$this$withContext"}, m = "invokeSuspend", c = "me.zt.illusion.test.Coroutines$doLoad$2")
//    @Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 3, d1 = {"\000\016\n\000\n\002\020\016\n\002\030\002\n\002\b\002\020\000\032\0020\001*\0020\002H\006\004\b\003\020\004"}, d2 = {"<anonymous>", "", "Lkotlinx/coroutines/CoroutineScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"})
//    static final class Coroutines$doLoad$2 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super String>, Object> {
//        private CoroutineScope p$;
//
//        Object L$0;
//
//        int label;
//
//        @Nullable
//        public final Object invokeSuspend(@NotNull Object $result) {
//            CoroutineScope $this$withContext;
//            String str;
//            boolean bool;
//            Object object = IntrinsicsKt.getCOROUTINE_SUSPENDED();
//            switch (this.label) {
//                case 0:
//                    ResultKt.throwOnFailure($result);
//                    $this$withContext = this.p$;
//                    str = "doLoad  " + Thread.currentThread();
//                    bool = false;
//                    System.out.println(str);
//                    this.L$0 = $this$withContext;
//                    this.label = 1;
//                    if (DelayKt.delay(2000L, (Continuation)this) == object)
//                        return object;
//                    DelayKt.delay(2000L, (Continuation)this);
//                    str = "doLoad2  " + Thread.currentThread();
//                    bool = false;
//                    System.out.println(str);
//                    return "result : " + this.$url;
//                case 1:
//                    $this$withContext = (CoroutineScope)this.L$0;
//                    ResultKt.throwOnFailure($result);
//                    str = "doLoad2  " + Thread.currentThread();
//                    bool = false;
//                    System.out.println(str);
//                    return "result : " + this.$url;
//            }
//            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
//        }
//
//        Coroutines$doLoad$2(String param1String, Continuation param1Continuation) {
//            super(2, param1Continuation);
//        }
//
//        @NotNull
//        public final Continuation<Unit> create(@Nullable Object value, @NotNull Continuation completion) {
//            Intrinsics.checkParameterIsNotNull(completion, "completion");
//            Coroutines$doLoad$2 coroutines$doLoad$2 = new Coroutines$doLoad$2(((Coroutines$doLoad$2)super).$url, completion);
//            coroutines$doLoad$2.p$ = (CoroutineScope)value;
//            return (Continuation<Unit>)coroutines$doLoad$2;
//        }
//
//        public final Object invoke(Object param1Object1, Object param1Object2) {
//            return ((Coroutines$doLoad$2)create(param1Object1, (Continuation)param1Object2)).invokeSuspend(Unit.INSTANCE);
//        }
//    }
//}
