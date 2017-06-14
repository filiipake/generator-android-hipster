package <%= appPackage %>.ui.base

<% if (nucleus == true) { %>import nucleus.presenter.RxPresenter; <% } %>

<% if (nucleus == false) { %>import io.reactivex.disposables.Disposable
import java.util.* <% } %>

<% if (timber == true) { %>import timber.log.Timber <% } %>

<% if (nucleus == true) { %>abstract class BasePresenter<V : PresenterView> : Rx } Presenter<V>()<% } %>

<% if (nucleus == false) { %>abstract class BasePresenter<V : PresenterView> : Presenter<V>() {
    private var subscriptionList = ArrayList<Disposable>()

    fun add(subscription: Disposable) {
        subscriptionList.add(subscription)
    }

    fun unSubscribe() {
        subscriptionList
                .filter { it.isDisposed }
                .forEach {
                    try {
                        it.dispose()
                    } catch (e: Throwable) { Timber.e(e, "unSubscribe()")  }
                }
    }

    override fun dropView() {
        super.dropView()
        unSubscribe()
    }
}
<% } %>