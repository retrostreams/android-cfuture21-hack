package java.util.concurrent;

abstract class CntdCompleter<T> extends FJTask<T> {

    private static final long serialVersionUID = 5232453752276485070L;

    /** This task's completer, or null if none */
    final CntdCompleter<?> completer;

    /**
     * Creates a new CntdCompleter with no completer
     * and an initial pending count of zero.
     */
    protected CntdCompleter() {
        this.completer = null;
    }

    /**
     * The main computation performed by this task.
     */
    abstract void compute();

    /**
     * Implements execution conventions for CountedCompleters.
     */
    protected final boolean exec() {
        compute();
        return false;
    }

    /**
     * Returns the result of the computation.  By default,
     * returns {@code null}, which is appropriate for {@code Void}
     * actions, but in other cases should be overridden, almost
     * always to return a field or function of a field that
     * holds the result upon completion.
     *
     * @return the result of the computation
     */
    public T getRawResult() { return null; }

    /**
     * A method that result-bearing CountedCompleters may optionally
     * use to help maintain result data.  By default, does nothing.
     * Overrides are not recommended. However, if this method is
     * overridden to update existing objects or fields, then it must
     * in general be defined to be thread-safe.
     */
    protected void setRawResult(T t) { }
}
