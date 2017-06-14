package <%= appPackage %>.util

import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import retrofit2.adapter.rxjava.Result

class ExtractResult<T> : ObservableTransformer<Result<T>, T> {

    override fun apply(upstream: Observable<Result<T>>): ObservableSource<T> {

        return upstream.flatMap {
            if (it.isError) {
                return@flatMap ExtractErrorUtil<T, T>().extractError(it)
            }

            it.response()?.let { r ->
                if (!it.isError and r.isSuccessful)
                    return@flatMap Observable.just(r.body())
            }

            ExtractErrorUtil<T, T>().extractError(it)
        }
    }


}
