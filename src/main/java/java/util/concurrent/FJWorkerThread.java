/*
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 */
package java.util.concurrent;

/**
 * A thread managed by a {@link FJPool}, which executes
 * {@link FJTask}s.
 * This class is subclassable solely for the sake of adding
 * functionality -- there are no overridable methods dealing with
 * scheduling or execution.  However, you can override initialization
 * and termination methods surrounding the main task processing loop.
 * If you do create such a subclass, you will also need to supply a
 * custom {@link FJPool.ForkJoinWorkerThreadFactory} to
 * <a href="../../../java/util/concurrent/FJPool.html#ForkJoinPool-int-java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory-java.lang.Thread.UncaughtExceptionHandler-boolean-int-int-int-java.util.function.Predicate-long-java.util.concurrent.TimeUnit-">
 * use it</a> in a {@code FJPool}.
 *
 * @since 1.7
 * @author Doug Lea
 */
class FJWorkerThread extends Thread {
// CVS rev. 1.78
    /*
     * ForkJoinWorkerThreads are managed by ForkJoinPools and perform
     * ForkJoinTasks. For explanation, see the internal documentation
     * of class FJPool.
     *
     * This class just maintains links to its pool and WorkQueue.  The
     * pool field is set immediately upon construction, but the
     * workQueue field is not set until a call to registerWorker
     * completes. This leads to a visibility race, that is tolerated
     * by requiring that the workQueue field is only accessed by the
     * owning thread.
     *
     * Support for (non-public) subclass InnocuousForkJoinWorkerThread
     * requires that we break quite a lot of encapsulation (via helper
     * methods in ThreadLocalRandom) both here and in the subclass to
     * access and set Thread fields.
     */

    // A placeholder name until a useful name can be set in registerWorker
    private static final String NAME_PLACEHOLDER = "aForkJoinWorkerThread";

    final FJPool pool;                // the pool this thread works in
    final FJPool.WorkQueue workQueue; // work-stealing mechanics

    /**
     * Version for use by the default pool.  This is a
     * separate constructor to avoid affecting the
     * protected constructor.
     */
    FJWorkerThread(FJPool pool, ClassLoader ccl) {
        // Use a placeholder until a useful name can be set in registerWorker
        super(NAME_PLACEHOLDER);
        this.pool = pool;
        this.workQueue = pool.registerWorker(this);
    }

    /**
     * This method is required to be public, but should never be
     * called explicitly. It performs the main run loop to execute
     * {@link FJTask}s.
     */
    public void run() {
        if (workQueue.array == null) { // only run once
            Throwable exception = null;
            try {
                pool.runWorker(workQueue);
            } catch (Throwable ex) {
                exception = ex;
            } finally {
                pool.deregisterWorker(this, exception);
            }
        }
    }

    /**
     * Non-public hook method for InnocuousForkJoinWorkerThread.
     */
    void afterTopLevelExec() {
    }
}
